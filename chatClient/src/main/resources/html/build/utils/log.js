import { App } from '../App.js';
import { Events } from '../enums/Events.enum.js';
// metoda pro posílání požadavku o logování do loggeru. Jenom pro zjednodušení syntaxe
export var log = function (type, message) {
    App.emit(Events.LOG, { type: type, message: message });
};
