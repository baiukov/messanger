import { DialogueModule } from './dialogue/dialogue.module.js';
import { Events } from './enums/Events.enum.js';
import { MessengerModule } from './messenger/messenger.module.js';
import { NotificationsModule } from './notifications/notifications.module.js';
import { RegisterModule } from './register/register.module.js';
/*
    Třída App - je třída modulu aplikace, která se zabývá vytvařením všech ostatních modulu a služby aplikace
*/
var App = /** @class */ (function () {
    function App() {
        new RegisterModule();
        new NotificationsModule();
        new MessengerModule();
        new DialogueModule();
    }
    var _a;
    _a = App;
    // uložení seznamu eventů
    App.events = {};
    // uložení seznamu serverových eventů
    App.serverEvents = {};
    // metoda pro registrace eventu
    App.on = function (eventName, event) {
        if (!_a.events[eventName]) {
            _a.events[eventName] = [event];
            return;
        }
        _a.events[eventName].push(event);
    };
    // metoda pro vyvolání některého z eventu, vyhledá ho v seznamu, pokud najde vyvolá příslušnou metodu
    App.emit = function (eventName, data) {
        Object.keys(_a.events).forEach(function (key) {
            // log([key, eventName,  key === eventName].toString())
            if (key === eventName) {
                _a.events[key].forEach(function (event) {
                    event(data);
                });
            }
        });
    };
    // metoda pro získání dat z klientu, vyvolá ji klient a tím způsobem předá data
    App.sendDataToFront = function (dataStr) {
        var data = dataStr.split(" ");
        var eventName = data[0].trim();
        Object.keys(_a.serverEvents).forEach(function (key) {
            // App.emitClient(Events.LOG, [dataStr, eventName, key, eventName === key])
            if (key === eventName) {
                _a.serverEvents[key].forEach(function (event) {
                    event(data);
                });
            }
        });
    };
    // metoda pro registrace serverových eventu
    App.onClient = function (eventName, event) {
        if (!_a.serverEvents[eventName]) {
            _a.serverEvents[eventName] = [event];
            return;
        }
        _a.serverEvents[eventName].push(event);
    };
    // metoda pro vyvolání některého ze serverových eventu, vyhledá ho v seznamu, pokud najde vyvolá příslušnou metodu
    App.emitClient = function (eventName, data) {
        var message = eventName + " ";
        data.forEach(function (element) {
            message += element + " ";
        });
        // @ts-ignore
        window.javaConnector.receiveMessage(message);
    };
    // setter
    App.setID = function (id) {
        _a.emit(Events.SETID, id);
    };
    return App;
}());
export { App };
