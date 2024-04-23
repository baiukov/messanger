import { App } from '../App.js';
import { Events } from '../enums/Events.enum.js';
var RegisterService = /** @class */ (function () {
    function RegisterService() {
        this.startListener = function () {
            $("#login").submit(function () {
                var name = $("#userName").val();
                var password = $("#password").val();
                if (!name || !password)
                    return;
                App.emitClient(Events.LOGIN, [name, password]);
                return false;
            });
            $("#register").submit(function () {
                var name = $("#userName").val();
                var firstName = $("#firstName").val();
                var lastName = $("#lastName").val();
                var password = $("#password").val();
                var repeatPassword = $("#passwordRepeat").val();
                if (!name || !password || !firstName || !lastName || !repeatPassword) {
                    App.emit(Events.NOTIFY, "You didn't fill the form properly");
                    return false;
                }
                ;
                if (password != repeatPassword) {
                    App.emit(Events.NOTIFY, "Passwords don't match");
                    return false;
                }
                if (password.length < 6) {
                    App.emit(Events.NOTIFY, "Password should contain at least 6 characters");
                    return false;
                }
                if (name.length < 4) {
                    App.emit(Events.NOTIFY, "User name should contain at least 4 characters");
                    return false;
                }
                App.emitClient(Events.REGISTER, [name, firstName, lastName, password]);
                return false;
            });
        };
        this.startListener();
    }
    RegisterService.prototype.moveToMain = function (message) {
        if (message.length < 2) {
            App.emit(Events.NOTIFY, "Unknown error happened");
            return;
        }
        // @ts-ignore
        window.javaConnector.setID(message[1]);
        // @ts-ignore
        window.javaConnector.goToMain();
    };
    return RegisterService;
}());
export { RegisterService };