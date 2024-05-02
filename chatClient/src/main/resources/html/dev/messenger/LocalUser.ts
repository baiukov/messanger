import { log } from '../utils/log.js'
import { User } from './User.js'

/*
	Třída User - je třída entity lokálního uživatele dědicí po třídě uživatele. Implementuje pattern SINGLETONE
*/
export class LocalUser extends User {

	// privatní instance třídy
	private static user: LocalUser | undefined;

	// metoda pro získání instance třídy
	public static getUser = () => {
		if (!this.user) {
			this.user = new LocalUser()
		}
		return this.user
	}

	// privatní konstruktor
	private constructor() {
		super()
		log("Local user has been initialized")
	}
}