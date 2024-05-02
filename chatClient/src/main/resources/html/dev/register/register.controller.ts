import { App } from '../App.js'
import { Events } from '../enums/Events.enum.js'
import { RegisterService } from './register.service.js'

/*
	Třída RegisterController - je třída správce příhlášení, která se zabývá operováním eventů. Nejdřív zaregestruje všechny eventy do aplikace, pak při vyvolání některého z těch eventu, spustí příslušnou metodu ze služby
*/
export class RegisterController {

	// uložení služby příhlášení
	private registerService: RegisterService

	constructor(registerService: RegisterService) {
		this.registerService = registerService

		// registrace eventu vyvoláného po úšpěšné registraci
		App.onClient(Events.SUCCESSREGISTER, (message: string[]) => {
			this.registerService.moveToMain(message)
		})

		// registrace eventu vyvoláného po úšpěšném příhlášení
		App.onClient(Events.SUCCESSLOGIN, (message: string[]) => {
			this.registerService.moveToMain(message)
		})
	}

}