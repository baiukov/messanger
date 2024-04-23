import { App } from '../App.js'
import { Events } from '../enums/Events.enum.js'

export class RegisterService {

	constructor() {
		this.startListener()
	}

	private startListener = () => {

		$("#login").submit(() => {
			const name = $("#userName").val()
			const password = $("#password").val()

			if (!name || !password) return

			App.emitClient(Events.LOGIN, [name, password] )
			return false;
		})

		$("#register").submit(() => {
			const name = $("#userName").val()
			const firstName = $("#firstName").val()
			const lastName = $("#lastName").val()
			const password = $("#password").val()
			const repeatPassword = $("#passwordRepeat").val()

			if (!name || !password || !firstName || !lastName || !repeatPassword) return

			if (password != repeatPassword) {
				App.emit(Events.NOTIFY, "Passwords don't match")
			}

			// App.emitClient(Events.REGISTER, [name, firstName, lastName, password] )
			return false;
		})
	}

}