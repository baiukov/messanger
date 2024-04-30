import { App } from '../App.js';
import { Events } from '../enums/Events.enum.js';
import { log } from '../utils/log.js';
import { secToMs } from '../utils/secToMs.js';
import { LocalUser } from './LocalUser.js';
import { MessengerView } from './messenger.view.js';
var MessengerService = /** @class */ (function () {
    function MessengerService() {
        this.view = new MessengerView();
        this.setListeners();
    }
    MessengerService.prototype.setListeners = function () {
        $("#user").keyup(function () {
            var val = $("#user").val();
            $(".user-list").empty();
            App.emitClient(Events.FETCHUSERS, [val]);
        });
        setInterval(function () {
            var id = LocalUser.getUser().getID();
            if (id) {
                App.emitClient(Events.FETCHDIALOGUES, [LocalUser.getUser().getID()]);
            }
        }, secToMs(0.5));
    };
    MessengerService.prototype.fetchData = function (id) {
        LocalUser.getUser().setID(id);
        App.emitClient(Events.FETCHNAME, [id]);
        App.emitClient(Events.FETCHCOLOR, [id]);
    };
    MessengerService.prototype.setFullName = function (message) {
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
        $("#firstLetter").text(firstName.charAt(0).toUpperCase());
    };
    MessengerService.prototype.showUsers = function (message) {
        var _loop_1 = function (i) {
            var name_1 = message[i];
            var surname = message[i + 1];
            var partnerID = message[i + 2];
            var box = this_1.view.getUserBox(name_1, surname);
            var userID = LocalUser.getUser().getID() || '';
            $(box).click(function () {
                log([Events.READMESSAGES, userID, partnerID].toString());
                App.emitClient(Events.READMESSAGES, [userID, partnerID]);
                // @ts-ignore
                window.javaConnector.goToDialogue(partnerID);
            });
            $(".user-list").append(box);
        };
        var this_1 = this;
        for (var i = 1; i < message.length; i += 3) {
            _loop_1(i);
        }
    };
    MessengerService.prototype.setColor = function (message) {
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
    };
    MessengerService.prototype.showDialogues = function (message) {
        var _this = this;
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
            var dialogue = this_2.view.getDialogue(hex, name_2, surname, text, parseInt(unread));
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
                event.preventDefault();
            });
            $(messagesBox).append(dialogue);
        };
        var this_2 = this;
        for (var i = 1; i < message.length; i += 7) {
            _loop_2(i);
        }
    };
    return MessengerService;
}());
export { MessengerService };
