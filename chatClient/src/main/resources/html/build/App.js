import { LoggerModule } from './logger/logger.module.js';
import { NotificationsModule } from './notifications/notifications.module.js';
import { RegisterModule } from './register/register.module.js';
var App = /** @class */ (function () {
    function App() {
        new RegisterModule();
        new LoggerModule();
        new NotificationsModule();
    }
    var _a;
    _a = App;
    App.events = {};
    App.serverEvents = {};
    App.on = function (eventName, event) {
        _a.events[eventName] = event;
    };
    App.emit = function (eventName, data) {
        Object.keys(_a.events).forEach(function (key) {
            if (key === eventName) {
                _a.events[key](data);
            }
        });
    };
    App.sendDataToFront = function (dataStr) {
        var data = dataStr.split(" ");
        var eventName = data[0].trim();
        Object.keys(_a.serverEvents).forEach(function (key) {
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
        _a.id = id;
    };
    return App;
}());
export { App };
