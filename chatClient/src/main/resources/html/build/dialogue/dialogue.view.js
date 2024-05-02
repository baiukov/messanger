/*
    Třída DialogueView - je třída pro vytváření přehledu statických prvků chatu a nastavení jejich dynamických zpracování událostí
*/
var DialogueView = /** @class */ (function () {
    function DialogueView() {
    }
    // metoda pro získání většího rámečku se zpravou, obsahující taky avatar a celé jméno odesílatele
    DialogueView.prototype.getMessageWithTitle = function (color, fullName, message) {
        var messageBox = document.createElement("div");
        $(messageBox).addClass("message-with-avatar");
        var wrapper = document.createElement("div");
        $(wrapper).addClass("message-wrapper");
        var avatar = document.createElement("div");
        $(avatar).addClass("avatar");
        $(avatar).css("background-color", color);
        var firstLetter = document.createElement("h2");
        $(firstLetter).text(fullName.charAt(0).toUpperCase());
        $(avatar).append(firstLetter);
        $(wrapper).append(avatar);
        $(messageBox).append(wrapper);
        var content = document.createElement("div");
        $(content).addClass("message-content");
        var title = document.createElement("h3");
        $(title).text(fullName);
        $(content).append(title);
        var text = document.createElement("p");
        $(text).text(message);
        $(text).addClass("message-text");
        $(content).append(text);
        $(wrapper).append(content);
        return messageBox;
    };
    // metoda pro získaní malého rámečku zprávy (jenom textový řádek)
    DialogueView.prototype.getMessage = function (message) {
        var messageBox = document.createElement("div");
        $(messageBox).addClass("message");
        var text = document.createElement("p");
        $(text).addClass("message-text");
        $(text).text(message);
        $(messageBox).append(text);
        return messageBox;
    };
    return DialogueView;
}());
export { DialogueView };
