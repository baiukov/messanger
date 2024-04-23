import { App } from '../App.js'
import { Events } from '../enums/Events.enum.js'
import { RegisterService } from './register.service.js'

export class RegisterController {

	private registerService: RegisterService

	constructor(registerService: RegisterService) {
		this.registerService = registerService

		App.onClient(Events.SUCCESSREGISTER, (message: string[]) => {
			this.registerService.moveToMain(message)
		})

		App.onClient(Events.SUCCESSLOGIN, (message: string[]) => {
			this.registerService.moveToMain(message)
		})
	}

}