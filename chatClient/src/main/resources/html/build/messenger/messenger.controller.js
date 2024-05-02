import { App } from '../App.js';
import { Events } from '../enums/Events.enum.js';
import { log } from '../utils/log.js';
/*
    Třída MessengerController - je třída správce hlávní stránky, která se zabývá operováním eventů. Nejdřív zaregestruje všechny eventy do aplikace, pak při vyvolání některého z těch eventu, spustí příslušnou metodu ze služby
*/
var MessengerController = /** @class */ (function () {
    function MessengerController(messengerSerice) {
        var _this = this;
        // uložení služby hlávní stránky
        this.messengerService = messengerSerice;
        // registrace eventu pro nastavení identifikačního čísla
        App.on(Events.SETID, function (id) {
            log("HERE");
            _this.messengerService.fetchData(id);
        });
        // registrace serverového eventu pro získaní celého jména
        App.onClient(Events.FETCHNAME, function (message) {
            _this.messengerService.setFullName(message);
        });
        // registrace serverového eventu pro získaní uživatelů podle části jména
        App.onClient(Events.FETCHUSERS, function (message) {
            _this.messengerService.showUsers(message);
        });
        // registrace serverového eventu pro získání barvy uživatele
        App.onClient(Events.FETCHCOLOR, function (messsage) {
            _this.messengerService.setColor(messsage);
        });
        // registrace serverového eventu pro všech chatů spojených s uživatelem 
        App.onClient(Events.FETCHDIALOGUES, function (message) {
            _this.messengerService.showDialogues(message);
        });
        // registrace serverového eventu pro vyhození chyby uživateli
        App.onClient(Events.ERROR, function (message) {
            _this.messengerService.showProfileError(message);
        });
    }
    return MessengerController;
}());
export { MessengerController };
