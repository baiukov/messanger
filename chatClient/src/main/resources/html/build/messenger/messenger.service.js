import { App } from '../App.js';
import { Events } from '../enums/Events.enum.js';
import { log } from '../utils/log.js';
import { secToMs } from '../utils/secToMs.js';
import { LocalUser } from './LocalUser.js';
import { MessengerView } from './messenger.view.js';
var MessengerService = /** @class */ (function () {
    function MessengerService() {
        var _this = this;
        this.view = new MessengerView();
        this.setListeners = function () {
            $("#user").keyup(function () {
                $(".profile").css("display", "none");
                var val = $("#user").val();
                $(".user-list").empty();
                App.emitClient(Events.FETCHUSERS, [val]);
            });
            $(".profile-avatar").click(function (event) {
                $(".profile").css("display", "flex");
                event.stopPropagation();
            });
            $(document.body).click(function () {
                $(".profile").css("display", "none");
            });
            $(".profile").click(function (event) {
                event.stopPropagation();
            });
            $("#save").click(function (event) {
                var shouldClose = _this.update();
                _this.fetchData(LocalUser.getUser().getID() || "");
                event.stopPropagation();
                if (shouldClose) {
                    $(".profile").css("display", "none");
                }
                return false;
            });
        };
        this.fetchData = function (id) {
            LocalUser.getUser().setID(id);
            App.emitClient(Events.FETCHNAME, [id]);
            App.emitClient(Events.FETCHCOLOR, [id]);
        };
        this.setFullName = function (message) {
            var firstName;
            var lastName;
            var user = LocalUser.getUser();
            if (message.length < 3) {
                firstName = "Unknown";
                lastName = "Unknown";
            }
            else {
                firstName = message[1];
                lastName = message[2];
            }
            user.setFirstName(firstName);
            user.setLastName(lastName);
            var firstLetter = firstName.charAt(0).toUpperCase();
            $("#firstLetter").text(firstLetter);
            $("#bigFirstLetter").text(firstLetter);
            $("#name").val(firstName);
            $("#surname").val(lastName);
        };
        this.update = function () {
            var currentPassword = $("#currentPassword").val();
            if (!currentPassword) {
                _this.showProfileError(["", "Type in your current password"]);
                return false;
            }
            var newPassword = $("#newPassword").val();
            var repeatPassword = $("#passwordRepeat").val();
            if (newPassword && newPassword != repeatPassword) {
                _this.showProfileError(["", "Passwords does not match"]);
                return false;
            }
            var name = $("#name").val();
            var surname = $("#surname").val();
            var space = " ";
            if (name.includes(space) || surname.includes(space) || newPassword.includes(space)) {
                _this.showProfileError(["", "Don't use spaces"]);
                return false;
            }
            _this.showProfileError(["", ""]);
            $("#currentPassword").val("");
            $("#newPassword").val("");
            $("#passwordRepeat").val("");
            App.emitClient(Events.UPDATE, [
                LocalUser.getUser().getID(),
                name || "null",
                surname || "null",
                newPassword || "null"
            ]);
            return true;
        };
        this.showUsers = function (message) {
            var _loop_1 = function (i) {
                var name_1 = message[i];
                var surname = message[i + 1];
                var partnerID = message[i + 2];
                var box = _this.view.getUserBox(name_1, surname);
                var userID = LocalUser.getUser().getID() || '';
                $(box).click(function () {
                    log([Events.READMESSAGES, userID, partnerID].toString());
                    App.emitClient(Events.READMESSAGES, [userID, partnerID]);
                    // @ts-ignore
                    window.javaConnector.goToDialogue(partnerID);
                });
                $(".user-list").append(box);
            };
            for (var i = 1; i < message.length; i += 3) {
                _loop_1(i);
            }
        };
        this.setColor = function (message) {
            var color;
            if (message.length < 2) {
                color = "F2C4DE";
            }
            else {
                color = message[1];
            }
            var hex = "#" + color.toLowerCase();
            LocalUser.getUser().setColor(hex);
            $("#avatar").css("background-color", hex);
            $(".big-avatar").css("background-color", hex);
        };
        this.showDialogues = function (message) {
            var messagesBox = $(".messages-box");
            var _loop_2 = function (i) {
                var partnerID = message[i];
                if ($("#".concat(partnerID)).length)
                    return "continue";
                var name_2 = message[i + 1];
                var surname = message[i + 2];
                var partnerColor = message[i + 3];
                var text = message[i + 4].replaceAll("/+", " ");
                var unread = message[i + 5];
                var isPinned = message[i + 6] === "true";
                var hex = "#" + partnerColor.toLowerCase();
                var dialogue = _this.view.getDialogue(hex, name_2, surname, text, parseInt(unread));
                $(dialogue).attr("id", partnerID);
                var id = LocalUser.getUser().getID();
                $(dialogue).click(function () {
                    // @ts-ignore
                    window.javaConnector.goToDialogue(partnerID);
                });
                $(dialogue).on('contextmenu', function (event) {
                    $(".relations").remove();
                    var relationBox = _this.view.getRelationBox(id || "", partnerID, isPinned);
                    $(relationBox).css("margin-left", event.clientX - 20);
                    $(dialogue).append(relationBox);
                    $(".profile").css("display", "none");
                    event.preventDefault();
                });
                $(messagesBox).append(dialogue);
            };
            for (var i = 1; i < message.length; i += 7) {
                _loop_2(i);
            }
        };
        this.showProfileError = function (message) {
            var error = "";
            var delimiter = "";
            for (var i = 1; i < message.length; i++) {
                error += delimiter + message[i];
                delimiter = " ";
            }
            $(".error-text").text(error);
        };
        this.setListeners();
        setInterval(function () {
            var id = LocalUser.getUser().getID();
            if (id) {
                App.emitClient(Events.FETCHDIALOGUES, [LocalUser.getUser().getID()]);
            }
        }, secToMs(0.5));
    }
    return MessengerService;
}());
export { MessengerService };
