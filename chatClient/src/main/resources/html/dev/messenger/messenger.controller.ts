import { App } from '../App.js'
import { Events } from '../enums/Events.enum.js'
import { log } from '../utils/log.js'
import { MessengerService } from './messenger.service.js'

/*
	Třída MessengerController - je třída správce hlávní stránky, která se zabývá operováním eventů. Nejdřív zaregestruje všechny eventy do aplikace, pak při vyvolání některého z těch eventu, spustí příslušnou metodu ze služby
*/
export class MessengerController {

	private messengerService: MessengerService

	constructor(messengerSerice: MessengerService) {
		
		// uložení služby hlávní stránky
		this.messengerService = messengerSerice;

		// registrace eventu pro nastavení identifikačního čísla
		App.on(Events.SETID, (id: string) => {
			log("HERE")
			this.messengerService.fetchData(id)
		})

		// registrace serverového eventu pro získaní celého jména
		App.onClient(Events.FETCHNAME, (message: string[]) => {
			this.messengerService.setFullName(message)
		}) 

		// registrace serverového eventu pro získaní uživatelů podle části jména
		App.onClient(Events.FETCHUSERS, (message: string[]) => {
			this.messengerService.showUsers(message)
		})

		// registrace serverového eventu pro získání barvy uživatele
		App.onClient(Events.FETCHCOLOR, (messsage: string[]) => {
			this.messengerService.setColor(messsage)
		})

		// registrace serverového eventu pro všech chatů spojených s uživatelem 
		App.onClient(Events.FETCHDIALOGUES, (message: string[]) => {
			this.messengerService.showDialogues(message)
		})

		// registrace serverového eventu pro vyhození chyby uživateli
		App.onClient(Events.ERROR, (message: string[]) => {
			this.messengerService.showProfileError(message)
		})
	}

}