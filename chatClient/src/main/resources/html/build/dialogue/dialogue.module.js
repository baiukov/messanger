import { DialogueController } from './dialogue.controller.js';
import { DialogueService } from './dialogue.service.js';
var DialogueModule = /** @class */ (function () {
    function DialogueModule() {
        var dialogueService = new DialogueService();
        new DialogueController(dialogueService);
    }
    return DialogueModule;
}());
export { DialogueModule };
