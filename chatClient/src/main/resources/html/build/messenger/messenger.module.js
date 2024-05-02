import { MessengerController } from './messenger.controller.js';
import { MessengerService } from './messenger.service.js';
/*
    Třída MessengerModule - je třída modulu zpracování hlávní stánky, která se zabývá vytvařením služby a správce hlávní stránky
*/
var MessengerModule = /** @class */ (function () {
    function MessengerModule() {
        var messengerService = new MessengerService();
        new MessengerController(messengerService);
    }
    return MessengerModule;
}());
export { MessengerModule };
