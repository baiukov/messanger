import { App } from '../App.js';
import { Events } from '../enums/Events.enum.js';
import { log } from '../utils/log.js';
import { User } from './User.js';
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
    };
    MessengerService.prototype.fetchData = function (id) {
        User.getUser().setID(id);
        App.emitClient(Events.FETCHNAME, [id]);
        App.emitClient(Events.FETCHCOLOR, [id]);
    };
    MessengerService.prototype.setFullName = function (message) {
        var firstName;
        var lastName;
        var user = User.getUser();
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
        log(message.toString());
        var _loop_1 = function (i) {
            var name_1 = message[i];
            var surname = message[i + 1];
            var id = message[i + 2];
            var box = this_1.view.getUserBox(name_1, surname);
            $(box).click(function () {
                // @ts-ignore
                window.javaConnector.goToDialogue(id);
            });
            log($(box).text());
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
        User.getUser().setColor(hex);
        log(hex);
        $("#avatar").css("background-color", hex);
    };
    MessengerService.prototype.showDialogues = function (message) {
    };
    return MessengerService;
}());
export { MessengerService };
