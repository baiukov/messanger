import { App } from '../App.js'
import { Events } from '../enums/Events.enum.js'
import { DialogueService } from './dialogue.service.js'

export class DialogueController {

	private dialogueService: DialogueService

	constructor(dialogueService: DialogueService) {
		this.dialogueService = dialogueService

		App.onClient(Events.SETDIALOGUEWITH, (message: string[]) => {
			App.emitClient(Events.FETCHPARTNERDATA, [message[1]])
		})

		App.onClient(Events.FETCHPARTNERDATA, (message: string[]) => {
			this.dialogueService.setPartnerData(message)
		})
	}

}