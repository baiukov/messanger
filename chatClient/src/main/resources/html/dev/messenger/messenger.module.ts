import { MessengerController } from './messenger.controller.js'
import { MessengerService } from './messenger.service.js'

export class MessengerModule {

	constructor() {
		const messengerService = new MessengerService()
		new MessengerController(messengerService)
	}
}