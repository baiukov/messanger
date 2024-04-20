import { RegisterService } from './register.service.js'

export class RegisterController {

	private registerService: RegisterService

	constructor(registerService: RegisterService) {
		this.registerService = registerService

	}

}