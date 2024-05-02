import { App } from '../App.js';
import { Events } from '../enums/Events.enum.js';
/*
    Třída NotificationsController - je třída správce notifikací, která se zabývá operováním eventů. Nejdřív zaregestruje všechny eventy do aplikace, pak při vyvolání některého z těch eventu, spustí příslušnou metodu ze služby
*/
var NotificationsController = /** @class */ (function () {
    function NotificationsController(notificationsService) {
        var _this = this;
        this.notificationsService = notificationsService;
        // registrace eventu notifikace
        App.on(Events.NOTIFY, function (message) {
            _this.notificationsService.show(message);
        });
        // registrace získání chyby ze serveru
        App.onClient(Events.ERROR, function (message) {
            _this.notificationsService.showError(message);
        });
    }
    return NotificationsController;
}());
export { NotificationsController };
