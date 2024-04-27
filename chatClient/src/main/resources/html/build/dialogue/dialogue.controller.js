import { App } from '../App.js';
import { Events } from '../enums/Events.enum.js';
var DialogueController = /** @class */ (function () {
    function DialogueController(dialogueService) {
        var _this = this;
        this.dialogueService = dialogueService;
        App.onClient(Events.SETDIALOGUEWITH, function (message) {
            App.emitClient(Events.FETCHPARTNERDATA, [message[1]]);
        });
        App.onClient(Events.FETCHPARTNERDATA, function (message) {
            _this.dialogueService.setPartnerData(message);
        });
    }
    return DialogueController;
}());
export { DialogueController };
