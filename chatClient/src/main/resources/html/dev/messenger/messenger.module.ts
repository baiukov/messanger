import { log } from '../utils/log.js'
import { MessengerController } from './messenger.controller.js'
import { MessengerService } from './messenger.service.js'

/*
	Třída MessengerModule - je třída modulu zpracování hlávní stánky, která se zabývá vytvařením služby a správce hlávní stránky
*/
export class MessengerModule {

	constructor() {
		const messengerService = new MessengerService()
		new MessengerController(messengerService)
		log("Register Module has been successfully initialized")
	}
}