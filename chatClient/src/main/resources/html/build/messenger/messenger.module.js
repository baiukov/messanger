import { MessengerController } from './messenger.controller.js';
import { MessengerService } from './messenger.service.js';
var MessengerModule = /** @class */ (function () {
    function MessengerModule() {
        var messengerService = new MessengerService();
        new MessengerController(messengerService);
    }
    return MessengerModule;
}());
export { MessengerModule };
