import { log } from '../utils/log.js'
import { DialogueController } from './dialogue.controller.js'
import { DialogueService } from './dialogue.service.js'

/*
	Třída DialogueModule - je třída modulu zpracování chatu, která se zabývá vytvařením služby a správce aktuálního chatu
*/
export class DialogueModule {

	constructor() {
		const dialogueService = new DialogueService()
		new DialogueController(dialogueService)
		log("Register Module has been successfully initialized")
	}

}