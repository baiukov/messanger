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
import { User } from './User.js';
/*
    Třída LocalUser - je třída entity lokálního uživatele dědicí po třídě uživatele. Implementuje pattern SINGLETONE
*/
var LocalUser = /** @class */ (function (_super) {
    __extends(LocalUser, _super);
    // privatní konstruktor
    function LocalUser() {
        return _super.call(this) || this;
    }
    // metoda pro získání instance třídy
    LocalUser.getUser = function () {
        if (!this.user) {
            this.user = new LocalUser();
        }
        return this.user;
    };
    return LocalUser;
}(User));
export { LocalUser };
