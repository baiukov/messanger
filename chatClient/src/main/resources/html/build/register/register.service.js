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
                if (!name || !password || !firstName || !lastName || !repeatPassword)
                    return;
                if (password != repeatPassword) {
                    App.emit(Events.NOTIFY, "Passwords don't match");
                }
                // App.emitClient(Events.REGISTER, [name, firstName, lastName, password] )
                return false;
            });
        };
        this.startListener();
    }
    return RegisterService;
}());
export { RegisterService };
