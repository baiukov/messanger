import { log } from '../utils/log'
import { RegisterController } from './register.controller'
import { RegisterService } from './register.service.js'

export class RegisterModule {

	constructor() {
		const registerService = new RegisterService()
		new RegisterController(registerService)
		log("Register Module has been successfully initialized")
	}
} 