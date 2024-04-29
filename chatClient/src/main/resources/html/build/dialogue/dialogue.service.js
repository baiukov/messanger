import { App } from '../App.js';
import { Events } from '../enums/Events.enum.js';
import { LocalUser } from '../messenger/LocalUser.js';
import { Partner } from '../messenger/Partner.js';
import { log } from '../utils/log.js';
import { secToMs } from '../utils/secToMs.js';
import { DialogueView } from './dialogue.view.js';
var DialogueService = /** @class */ (function () {
    function DialogueService() {
        var _this = this;
        this.partner = Partner.getUser();
        this.user = LocalUser.getUser();
        this.view = new DialogueView();
        this.initListeners();
        setInterval(function () {
            App.emitClient(Events.FETCHMESSAGES, [_this.user.getID(), _this.partner.getID()]);
        }, secToMs(1));
    }
    DialogueService.prototype.initListeners = function () {
        var _this = this;
        $(".back-button").click(function () {
            //@ts-ignore
            window.javaConnector.switchPage("main", null);
        });
        $(".send-box").on('submit', function () {
            var message = $("#message").val();
            if (!message)
                return;
            App.emitClient(Events.SEND, [_this.user.getID(), _this.partner.getID(), message]);
            return false;
        });
        $(".submit-send").click(function () {
            $(".send-box").submit();
            $("#message").val("");
        });
        $(".dialogue").scrollTop($(".dialogue").prop("scrollHeight"));
        $(".dialogue").css("scroll-behavior", "smooth");
        $("#message").keypress(function (event) {
            if (event.which === 13 && !event.shiftKey) {
                event.preventDefault();
                $(".send-box").submit();
                $("#message").val("");
            }
        });
    };
    DialogueService.prototype.setID = function (id) {
        this.user.setID(id);
    };
    DialogueService.prototype.setPartnerData = function (message) {
        if (message.length < 4)
            return;
        var id = message[1];
        var name = message[2];
        var surname = message[3];
        var color = message[4];
        var hex = "#" + color.toLowerCase();
        var firstLetter = name.charAt(0).toUpperCase();
        this.partner.setID(id);
        this.partner.setFirstName(name);
        this.partner.setLastName(surname);
        this.partner.setColor(hex);
        $("#partnerName").text(name + " " + surname);
        $("#partnersAvatar").css("background-color", hex);
        $("#partnersFirstLetter").text(firstLetter);
        // @ts-ignore
        window.javaConnector.receiveMessage("AFTERCLIENT");
        App.emitClient(Events.READMESSAGES, [LocalUser.getUser().getID(), id]);
    };
    DialogueService.prototype.showMessages = function (message) {
        var dialogue = $(".messages-wrapper");
        for (var i = 1; i < message.length; i += 5) {
            var messageID = message[i];
            if ($("#".concat(messageID)).length)
                continue;
            var senderID = message[i + 1];
            var text = message[i + 2];
            var dateStr = message[i + 3];
            var timeStr = message[i + 4];
            var isSameSender = i > 1 && message[i - 4] === senderID;
            var isUserSender = this.user.getID() === senderID;
            var color = isUserSender ? this.user.getColor() : this.partner.getColor();
            var name_1 = isUserSender ? this.user.getFirstName() + " " + this.user.getLastName()
                : this.partner.getFirstName() + " " + this.partner.getLastName();
            var msg = text.replaceAll("/+", " ");
            var date = new Date(dateStr + " " + timeStr);
            var lastDate = i > 1 ? new Date(message[i - 2] + " " + message[i - 1]) : null;
            // @ts-ignore
            var isLongerThanFiveMinutes = lastDate && (date - lastDate) / 60 / 1000 >= 5;
            // @ts-ignore
            log(msg);
            var box = !isSameSender || isLongerThanFiveMinutes ? this.view.getMessageWithTitle(color || "", name_1, msg) : this.view.getMessage(msg);
            $(box).attr("id", messageID);
            $(dialogue).append(box);
        }
        $(".dialogue").scrollTop($(".dialogue").prop("scrollHeight"));
    };
    return DialogueService;
}());
export { DialogueService };
