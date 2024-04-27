import { DialogueController } from './dialogue.controller.js'
import { DialogueService } from './dialogue.service.js'

export class DialogueModule {

	constructor() {
		const dialogueService = new DialogueService()
		new DialogueController(dialogueService)
	}

}