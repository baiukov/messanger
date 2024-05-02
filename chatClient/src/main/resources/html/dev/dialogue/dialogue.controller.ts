import { App } from '../App.js'
import { Events } from '../enums/Events.enum.js'
import { DialogueService } from './dialogue.service.js'

/*
	Třída DialogueController - je třída správce chatů, která se zabývá operováním eventů. Nejdřív zaregestruje všechny eventy do aplikace, pak při vyvolání některého z těch eventu, spustí příslušnou metodu ze služby
*/
export class DialogueController {

	private dialogueService: DialogueService

	constructor(dialogueService: DialogueService) {
		this.dialogueService = dialogueService

		// registrace serverového eventu pro nastavení ID partneru
		App.onClient(Events.SETDIALOGUEWITH, (message: string[]) => {
			App.emitClient(Events.FETCHPARTNERDATA, [message[1]])
		})

		// registrace serverového eventu pro nastavení dat partneru
		App.onClient(Events.FETCHPARTNERDATA, (message: string[]) => {
			this.dialogueService.setPartnerData(message)
		})

		// registrace serverového eventu ID uživatele
		App.on(Events.SETID, (id: string) => {
			this.dialogueService.setID(id)
		})

		// registrace serverového eventu pro získání zpráv
		App.onClient(Events.FETCHMESSAGES, (message: string[]) => {
			this.dialogueService.showMessages(message)
		})
	}

}