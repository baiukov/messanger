import { DialogueModule } from './dialogue/dialogue.module.js';
import { Events } from './enums/Events.enum.js';
import { LoggerModule } from './logger/logger.module.js';
import { MessengerModule } from './messenger/messenger.module.js';
import { NotificationsModule } from './notifications/notifications.module.js';
import { RegisterModule } from './register/register.module.js';
import { log } from './utils/log.js';
var App = /** @class */ (function () {
    function App() {
        new RegisterModule();
        new LoggerModule();
        new NotificationsModule();
        new MessengerModule();
        new DialogueModule();
    }
    var _a;
    _a = App;
    App.events = {};
    App.serverEvents = {};
    App.on = function (eventName, event) {
        if (!_a.events[eventName]) {
            _a.events[eventName] = [event];
            return;
        }
        _a.events[eventName].push(event);
    };
    App.emit = function (eventName, data) {
        Object.keys(_a.events).forEach(function (key) {
            log([key, eventName, key === eventName].toString());
            if (key === eventName) {
                _a.events[key].forEach(function (event) {
                    event(data);
                });
            }
        });
    };
    App.sendDataToFront = function (dataStr) {
        var data = dataStr.split(" ");
        var eventName = data[0].trim();
        Object.keys(_a.serverEvents).forEach(function (key) {
            // App.emitClient(Events.LOG, [dataStr, eventName, key, eventName === key])
            if (key === eventName) {
                _a.serverEvents[key](data);
            }
        });
    };
    App.onClient = function (eventName, event) {
        _a.serverEvents[eventName] = event;
    };
    App.emitClient = function (eventName, data) {
        var message = eventName + " ";
        data.forEach(function (element) {
            message += element + " ";
        });
        // @ts-ignore
        window.javaConnector.receiveMessage(message);
    };
    App.setID = function (id) {
        _a.emit(Events.SETID, id);
    };
    return App;
}());
export { App };
