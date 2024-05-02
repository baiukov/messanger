import { App } from '../App.js';
import { Events } from '../enums/Events.enum.js';
/*
    Třída MessengerView - je třída pro vytváření přehledu statických prvků hlávní stránky a nastavení jejich dynamických zpracování událostí
*/
var MessengerView = /** @class */ (function () {
    function MessengerView() {
    }
    // metoda pro získání ramečku uživatele obsahujícího jméno, příjmení
    MessengerView.prototype.getUserBox = function (name, surname) {
        var box = document.createElement("div");
        $(box).addClass("user-option").text(name + " " + surname);
        return box;
    };
    // metoda pro získání ramečku chatu, obsahující prvky partneru, poslední zprávu a počet nepřečtených zpráv
    MessengerView.prototype.getDialogue = function (color, name, surname, messageStr, unread) {
        var messageBox = document.createElement("div");
        $(messageBox).addClass("message-box");
        if (unread > 0) {
            $(messageBox).addClass("unread");
        }
        var messageWithAvatar = document.createElement("div");
        $(messageWithAvatar).addClass("message-with-avatar");
        var messageWrapper = document.createElement("div");
        $(messageWrapper).addClass("message-wrapper");
        $(messageWithAvatar).append(messageWrapper);
        $(messageBox).append(messageWithAvatar);
        var avatar = document.createElement("div");
        $(avatar).addClass("avatar");
        $(avatar).css("background-color", color);
        var letter = document.createElement("h2");
        $(letter).text(name.charAt(0).toUpperCase());
        $(avatar).append(letter);
        $(messageWrapper).append(avatar);
        var content = document.createElement("message-content");
        $(content).addClass("message-content");
        var title = document.createElement("h3");
        $(title).text(name + " " + surname);
        $(content).append(title);
        var message = document.createElement("p");
        $(message).addClass("short-message");
        $(message).text(messageStr);
        $(content).append(message);
        $(messageWrapper).append(content);
        // pokud nepřečtený zprávy nejsou, nezobrazovat ctvereček s počtem
        if (unread > 0) {
            var unreadBox = document.createElement("div");
            $(unreadBox).addClass("unread-amount");
            var unreadTitle = document.createElement("h3");
            $(unreadTitle).text(unread);
            $(unreadBox).append(unreadTitle);
            $(messageBox).append(unreadBox);
        }
        return messageBox;
    };
    // metoda pro získání kontextového menu pro upravení vztahu uživatelů
    MessengerView.prototype.getRelationBox = function (userID, partnerID, isAlreadyPinned) {
        var box = document.createElement("div");
        $(box).addClass("relations");
        var pin = document.createElement("div");
        $(pin).addClass("pin");
        $(pin).text(isAlreadyPinned ? "Unpin" : "Pin");
        // při kliknutí na Pin/Unpin, pošle požadavek o připnutí/Odepnutí
        $(pin).click(function (event) {
            App.emitClient(isAlreadyPinned ? Events.UNPIN : Events.PIN, [userID, partnerID]);
            event.stopPropagation();
            $(".messages-box").empty();
        });
        var block = document.createElement("div");
        $(block).addClass("block");
        $(block).text("Block");
        // při kliknutí na Block, pošle požadavek o zablokování
        $(block).click(function (event) {
            App.emitClient(Events.BLOCK, [userID, partnerID]);
            event.stopPropagation();
            $(".messages-box").empty();
        });
        $(box).append(pin, block);
        return box;
    };
    return MessengerView;
}());
export { MessengerView };
