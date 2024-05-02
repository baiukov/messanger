import { log } from '../utils/log';
import { RegisterController } from './register.controller';
import { RegisterService } from './register.service.js';
/*
    Třída RegisterModule - je třída modulu zpracování příhlášení, která se zabývá vytvařením služby a správce příhlášení
*/
var RegisterModule = /** @class */ (function () {
    function RegisterModule() {
        var registerService = new RegisterService();
        new RegisterController(registerService);
        log("Register Module has been successfully initialized");
    }
    return RegisterModule;
}());
export { RegisterModule };
