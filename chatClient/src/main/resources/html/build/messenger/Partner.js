var __extends = (this && this.__extends) || (function () {
    var extendStatics = function (d, b) {
        extendStatics = Object.setPrototypeOf ||
            ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
            function (d, b) { for (var p in b) if (Object.prototype.hasOwnProperty.call(b, p)) d[p] = b[p]; };
        return extendStatics(d, b);
    };
    return function (d, b) {
        if (typeof b !== "function" && b !== null)
            throw new TypeError("Class extends value " + String(b) + " is not a constructor or null");
        extendStatics(d, b);
        function __() { this.constructor = d; }
        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
    };
})();
import { log } from '../utils/log.js';
import { User } from './User.js';
/*
    Třída User - je třída entity partneru v chatu dědicí po třídě uživatele. Implementuje pattern SINGLETONE
*/
var Partner = /** @class */ (function (_super) {
    __extends(Partner, _super);
    // privatní konstruktor
    function Partner() {
        var _this = _super.call(this) || this;
        log("Partner has been initialized");
        return _this;
    }
    var _a;
    _a = Partner;
    // metoda pro získání instance třídy
    Partner.getUser = function () {
        if (!_a.user) {
            _a.user = new _a();
        }
        return _a.user;
    };
    return Partner;
}(User));
export { Partner };
