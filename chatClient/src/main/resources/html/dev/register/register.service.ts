import { App } from '../App.js'
import { Events } from '../enums/Events.enum.js'
import { log } from '../utils/log.js'

/*
	Třída RegisterService - je třída služby příhlášení a registrace, která se zabývá zpracováním logiky příhlášení a registrace
*/
export class RegisterService {

	// konstruktor třídy, po načítání stránky, bude vyvolána metoda poslouchání stisknutí tlačitek
	constructor() {
		this.startListener()
	}

	// metoda nastavení tlačítka příhlášení a registrace. Ověří, jestli data jsou uvedená správně a pošle požadavek na server o registrace nového hráče a přesměrování ho do hlavní stránky
	private startListener = () => {

		$("#login").submit(() => {
			const name = $("#userName").val() as string
			const password = $("#password").val() as string

			if (!name || !password) return false

			const space = " "
			if (name.includes(space) || password.includes(space)) {
				App.emit(Events.NOTIFY, "Don't use spaces")
				return false
			}

			log("User filled login form successfully, proceed to the server")
			App.emitClient(Events.LOGIN, [name, password] )
			return false;
		})

		$("#register").submit(() => {
			const name = $("#userName").val() as string
			const firstName = $("#firstName").val() as string
			const lastName = $("#lastName").val() as string
			const password = $("#password").val() as string
			const repeatPassword = $("#passwordRepeat").val()

			if (!name || !password || !firstName || !lastName || !repeatPassword) {
				App.emit(Events.NOTIFY, "You didn't fill the form properly")
				return false
			};

			const space = " "
			if (firstName.includes(space) || lastName.includes(space) || password.includes(space)) {
				App.emit(Events.NOTIFY, "Don't use spaces")
				return false
			}

			if (password != repeatPassword) {
				App.emit(Events.NOTIFY, "Passwords don't match")
				return false
			}

			if ((password as string).length < 6) {
				App.emit(Events.NOTIFY, "Password should contain at least 6 characters")
				return false
			}

			if ((name as string).length < 4) {
				App.emit(Events.NOTIFY, "User name should contain at least 4 characters")
				return false
			}

			log("User filled register form successfully, proceed to the server")
			App.emitClient(Events.REGISTER, [name, firstName, lastName, password] )
			return false
		})

	}

	// po úspěšném přihlášení přesměruje uživatele na hlavní stránku a uloží jeho identifikační číslo do klienta
	public moveToMain = (message: string[]) => {
		if (message.length < 2) {
			App.emit(Events.NOTIFY, "Unknown error happened")
			return;
		} 
		App.id = message[1];
		log("User will be redirected to the main page")
		// @ts-ignore
		window.javaConnector.setID(message[1])
		// @ts-ignore
		window.javaConnector.switchPage("main", null)
	}

}