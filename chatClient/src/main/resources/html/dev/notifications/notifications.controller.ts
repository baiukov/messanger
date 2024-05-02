import { App } from '../App.js'
import { Events } from '../enums/Events.enum.js'
import { NotificationsService } from './notifications.service.js'

/*
	Třída NotificationsController - je třída správce notifikací, která se zabývá operováním eventů. Nejdřív zaregestruje všechny eventy do aplikace, pak při vyvolání některého z těch eventu, spustí příslušnou metodu ze služby
*/
export class NotificationsController {

	// uložení služby notifikací
	private notificationsService: NotificationsService

	constructor(notificationsService: NotificationsService) {
		this.notificationsService = notificationsService

		// registrace eventu notifikace
		App.on(Events.NOTIFY, (message: string) => {
			this.notificationsService.show(message)
		})

		// registrace získání chyby ze serveru
		App.onClient(Events.ERROR, (message: string[]) => {
			this.notificationsService.showError(message)
		})
	}


}