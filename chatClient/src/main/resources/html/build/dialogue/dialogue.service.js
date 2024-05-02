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
        // metoda nastavení tlačítek
        this.initListeners = function () {
            // tlačítko vracení zpět na hlávní stránku
            $(".back-button").click(function () {
                log("Back button has been clicked. Redirecting to main page...");
                //@ts-ignore
                window.javaConnector.switchPage("main", null);
            });
            // forma pro odeslání zprávy
            $(".send-box").on('submit', function () {
                var message = $("#message").val();
                if (!message)
                    return;
                log("Send form has been submitted. Message to " + _this.partner.getID() + " will proceed to the server. Message " + message);
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
                var enterButtonClickedEvent = 13;
                if (event.which === enterButtonClickedEvent && !event.shiftKey) {
                    event.preventDefault();
                    $(".send-box").submit();
                    $("#message").val("");
                }
            });
        };
        // metoda nastavení identifikačního čísla
        this.setID = function (id) {
            _this.user.setID(id);
            log("ID for local user has been set to " + id);
        };
        // metoda nastavení dat partneru
        this.setPartnerData = function (message) {
            if (message.length < 4)
                return;
            var id = message[1];
            var name = message[2];
            var surname = message[3];
            var color = message[4];
            var hex = "#" + color.toLowerCase();
            var firstLetter = name.charAt(0).toUpperCase();
            _this.partner.setID(id);
            _this.partner.setFirstName(name);
            _this.partner.setLastName(surname);
            _this.partner.setColor(hex);
            $("#partnerName").text(name + " " + surname);
            $("#partnersAvatar").css("background-color", hex);
            $("#partnersFirstLetter").text(firstLetter);
            log("Partner data has been set. ID: ".concat(id, ", name: ").concat(name, ", surname: ").concat(surname, ", color: ").concat(hex));
            App.emitClient(Events.READMESSAGES, [LocalUser.getUser().getID(), id]);
        };
        // metoda pro zobrazení všech zpráv v chatu
        this.showMessages = function (message) {
            var dialogue = $(".messages-wrapper");
            log("Fetched messages with partner. Will be shown " + Math.floor(message.length / 5) + "message(-s)");
            for (var i = 1; i < message.length; i += 5) {
                var messageID = message[i];
                if ($("#".concat(messageID)).length)
                    continue;
                var senderID = message[i + 1];
                var text = message[i + 2];
                var dateStr = message[i + 3];
                var timeStr = message[i + 4];
                var isSameSender = i > 1 && message[i - 4] === senderID;
                var isUserSender = _this.user.getID() === senderID;
                var color = isUserSender ? _this.user.getColor() : _this.partner.getColor();
                var name_1 = isUserSender ? _this.user.getFirstName() + " " + _this.user.getLastName()
                    : _this.partner.getFirstName() + " " + _this.partner.getLastName();
                var msg = text.replaceAll("/+", " ");
                var date = new Date(dateStr + " " + timeStr);
                // pokud odesílatel zprávy se liší, nebo poslední zprávu odeslal více jak před pěti minuty, zobrazí se jeho title znovu
                var lastDate = i > 1 ? new Date(message[i - 2] + " " + message[i - 1]) : null;
                // @ts-ignore
                var isLongerThanFiveMinutes = lastDate && (date - lastDate) / 60 / 1000 >= 5;
                var box = !isSameSender || isLongerThanFiveMinutes ? _this.view.getMessageWithTitle(color || "", name_1, msg) : _this.view.getMessage(msg);
                $(box).attr("id", messageID);
                $(dialogue).append(box);
            }
            $(".dialogue").scrollTop($(".dialogue").prop("scrollHeight"));
        };
        this.initListeners();
        setInterval(function () {
            if (_this.user.getID()) {
                App.emitClient(Events.FETCHMESSAGES, [_this.user.getID(), _this.partner.getID()]);
            }
        }, secToMs(0.1));
    }
    return DialogueService;
}());
export { DialogueService };
