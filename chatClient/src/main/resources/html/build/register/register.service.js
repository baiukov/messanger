import { App } from '../App.js';
import { Events } from '../enums/Events.enum.js';
/*
    Třída RegisterService - je třída služby příhlášení a registrace, která se zabývá zpracováním logiky příhlášení a registrace
*/
var RegisterService = /** @class */ (function () {
    // konstruktor třídy, po načítání stránky, bude vyvolána metoda poslouchání stisknutí tlačitek
    function RegisterService() {
        // metoda nastavení tlačítka příhlášení a registrace. Ověří, jestli data jsou uvedená správně a pošle požadavek na server o registrace nového hráče a přesměrování ho do hlavní stránky
        this.startListener = function () {
            $("#login").submit(function () {
                var name = $("#userName").val();
                var password = $("#password").val();
                if (!name || !password)
                    return false;
                var space = " ";
                if (name.includes(space) || password.includes(space)) {
                    App.emit(Events.NOTIFY, "Don't use spaces");
                    return false;
                }
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
                var space = " ";
                if (firstName.includes(space) || lastName.includes(space) || password.includes(space)) {
                    App.emit(Events.NOTIFY, "Don't use spaces");
                    return false;
                }
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
        // po úspěšném přihlášení přesměruje uživatele na hlavní stránku a uloží jeho identifikační číslo do klienta
        this.moveToMain = function (message) {
            if (message.length < 2) {
                App.emit(Events.NOTIFY, "Unknown error happened");
                return;
            }
            App.id = message[1];
            // @ts-ignore
            window.javaConnector.setID(message[1]);
            // @ts-ignore
            window.javaConnector.switchPage("main", null);
        };
        this.startListener();
    }
    return RegisterService;
}());
export { RegisterService };
