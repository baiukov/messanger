var MessengerView = /** @class */ (function () {
    function MessengerView() {
    }
    MessengerView.prototype.getUserBox = function (name, surname) {
        var box = document.createElement("div");
        $(box).addClass("user-option").text(name + " " + surname);
        return box;
    };
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
    return MessengerView;
}());
export { MessengerView };
