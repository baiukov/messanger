import { App } from '../App.js';
import { Events } from '../enums/Events.enum.js';
/*
    Třída RegisterController - je třída správce příhlášení, která se zabývá operováním eventů. Nejdřív zaregestruje všechny eventy do aplikace, pak při vyvolání některého z těch eventu, spustí příslušnou metodu ze služby
*/
var RegisterController = /** @class */ (function () {
    function RegisterController(registerService) {
        var _this = this;
        this.registerService = registerService;
        // registrace eventu vyvoláného po úšpěšné registraci
        App.onClient(Events.SUCCESSREGISTER, function (message) {
            _this.registerService.moveToMain(message);
        });
        // registrace eventu vyvoláného po úšpěšném příhlášení
        App.onClient(Events.SUCCESSLOGIN, function (message) {
            _this.registerService.moveToMain(message);
        });
    }
    return RegisterController;
}());
export { RegisterController };
