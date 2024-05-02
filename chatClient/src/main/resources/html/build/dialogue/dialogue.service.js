import { App } from '../App.js';
import { Events } from '../enums/Events.enum.js';
import { LocalUser } from '../messenger/LocalUser.js';
import { Partner } from '../messenger/Partner.js';
import { log } from '../utils/log.js';
import { secToMs } from '../utils/secToMs.js';
import { DialogueView } from './dialogue.view.js';
/*
    Třída DialogueService - je třída služby chatů, která se zabývá zpracováním logiky chatů
*/
var DialogueService = /** @class */ (function () {
    // konstruktor třídy, po načítání stránky bude vyvolána metoda poslouchání stisknutí tlačitek a ověření nových zpráv
    function DialogueService() {
        var _this = this;
        // uložení instance partneru
        this.partner = Partner.getUser();
        // uložení instance uživatele
        this.user = LocalUser.getUser();
        // uložení instance třídy vzhledových prvků
        this.view = new DialogueView();
        this.initListeners();
        setInterval(function () {
            App.emitClient(Events.FETCHMESSAGES, [_this.user.getID(), _this.partner.getID()]);
        }, secToMs(1));
    }
    // metoda nastavení tlačítek
    DialogueService.prototype.initListeners = function () {
        var _this = this;
        // tlačítko vracení zpět na hlávní stránku
        $(".back-button").click(function () {
            //@ts-ignore
            window.javaConnector.switchPage("main", null);
        });
        // forma pro odeslání zprávy
        $(".send-box").on('submit', function () {
            var message = $("#message").val();
            if (!message)
                return;
            App.emitClient(Events.SEND, [_this.user.getID(), _this.partner.getID(), message]);
            return false;
        });
        // tlačitko pro submitnutí formy
        $(".submit-send").click(function () {
            $(".send-box").submit();
            $("#message").val("");
        });
        // ukazat hned poslední zprávy
        $(".dialogue").scrollTop($(".dialogue").prop("scrollHeight"));
        $(".dialogue").css("scroll-behavior", "smooth");
        // odeslání zprávy stisknutím tlačítka ENTER
        $("#message").keypress(function (event) {
            if (event.which === 13 && !event.shiftKey) {
                event.preventDefault();
                $(".send-box").submit();
                $("#message").val("");
            }
        });
    };
    // metoda nastavení identifikačního čísla
    DialogueService.prototype.setID = function (id) {
        this.user.setID(id);
    };
    // metoda nastavení dat partneru
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
    // metoda pro zobrazení všech zpráv v chatu
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
            // pokud odesílatel zprávy se liší, nebo poslední zprávu odeslal více jak před pěti minuty, zobrazí se jeho title znovu
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
