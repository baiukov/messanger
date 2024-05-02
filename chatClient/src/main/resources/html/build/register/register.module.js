import { RegisterController } from './register.controller';
import { RegisterService } from './register.service.js';
/*
    Třída RegisterModule - je třída modulu zpracování příhlášení, která se zabývá vytvařením služby a správce příhlášení
*/
var RegisterModule = /** @class */ (function () {
    function RegisterModule() {
        var registerService = new RegisterService();
        new RegisterController(registerService);
    }
    return RegisterModule;
}());
export { RegisterModule };
