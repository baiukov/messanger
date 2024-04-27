import { App } from '../App.js';
import { Events } from '../enums/Events.enum.js';
import { log } from '../utils/log.js';
var DialogueService = /** @class */ (function () {
    function DialogueService() {
        this.initListeners();
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
            console.log(_this.partnerID);
            App.emitClient(Events.SEND, [_this.userID, _this.partnerID, message]);
            return false;
        });
        $(".submit-send").click(function () {
            console.log(1);
            $(".send-box").submit();
        });
        console.log($(".dialogue").prop("scrollHeight"));
        $(".dialogue").scrollTop($(".dialogue").prop("scrollHeight"));
        $(".dialogue").css("scroll-behavior", "smooth");
    };
    DialogueService.prototype.setID = function (id) {
        this.userID = id;
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
        this.partnerID = id;
        log(['partner', id, name, surname, hex, firstLetter].toString());
        $("#partnerName").text(name + " " + surname);
        $("#partnersAvatar").css("background-color", hex);
        $("#partnersFirstLetter").text(firstLetter);
    };
    return DialogueService;
}());
export { DialogueService };
