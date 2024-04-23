import { App } from '../App.js';
import { Events } from '../enums/Events.enum.js';
var RegisterController = /** @class */ (function () {
    function RegisterController(registerService) {
        var _this = this;
        this.registerService = registerService;
        App.onClient(Events.SUCCESSREGISTER, function (message) {
            _this.registerService.moveToMain(message);
        });
        App.onClient(Events.SUCCESSLOGIN, function (message) {
            _this.registerService.moveToMain(message);
        });
    }
    return RegisterController;
}());
export { RegisterController };
