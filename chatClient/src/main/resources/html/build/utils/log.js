import { App } from '../App.js';
import { Events } from '../enums/Events.enum.js';
// metoda pro posílání požadavku o logování do loggeru. Jenom pro zjednodušení syntaxe
export var log = function (message) {
    App.emitClient(Events.LOG, [message]);
    console.log(message);
};
