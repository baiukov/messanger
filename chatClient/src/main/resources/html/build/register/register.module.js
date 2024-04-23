import { RegisterController } from './register.controller';
import { RegisterService } from './register.service.js';
var RegisterModule = /** @class */ (function () {
    function RegisterModule() {
        var registerService = new RegisterService();
        new RegisterController(registerService);
    }
    return RegisterModule;
}());
export { RegisterModule };
