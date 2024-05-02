import { App } from '../App.js';
import { Events } from '../enums/Events.enum.js';
/*
    Třída DialogueController - je třída správce chatů, která se zabývá operováním eventů. Nejdřív zaregestruje všechny eventy do aplikace, pak při vyvolání některého z těch eventu, spustí příslušnou metodu ze služby
*/
var DialogueController = /** @class */ (function () {
    function DialogueController(dialogueService) {
        var _this = this;
        this.dialogueService = dialogueService;
        // registrace serverového eventu pro nastavení ID partneru
        App.onClient(Events.SETDIALOGUEWITH, function (message) {
            App.emitClient(Events.FETCHPARTNERDATA, [message[1]]);
        });
        // registrace serverového eventu pro nastavení dat partneru
        App.onClient(Events.FETCHPARTNERDATA, function (message) {
            _this.dialogueService.setPartnerData(message);
        });
        // registrace serverového eventu ID uživatele
        App.on(Events.SETID, function (id) {
            _this.dialogueService.setID(id);
        });
        // registrace serverového eventu pro získání zpráv
        App.onClient(Events.FETCHMESSAGES, function (message) {
            _this.dialogueService.showMessages(message);
        });
    }
    return DialogueController;
}());
export { DialogueController };
