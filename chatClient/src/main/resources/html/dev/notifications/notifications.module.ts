import { log } from '../utils/log.js'
import { NotificationsController } from './notifications.controller.js'
import { NotificationsService } from './notifications.service.js'

/*
	Třída LoginModule - je třída modulu zpracování notifikací, která se zabývá vytvařením služby a správce notifikací
*/
export class NotificationsModule {

	constructor() {
		const notificationsService = new NotificationsService()
		new NotificationsController(notificationsService)
		log("Register Module has been successfully initialized")
	}

}