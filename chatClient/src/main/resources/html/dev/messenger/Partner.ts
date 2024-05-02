import { log } from '../utils/log.js'
import { User } from './User.js'

/*
	Třída User - je třída entity partneru v chatu dědicí po třídě uživatele. Implementuje pattern SINGLETONE
*/
export class Partner extends User {

	// privatní instance třídy
	private static user: Partner | undefined;

	// metoda pro získání instance třídy
	public static getUser = () => {
		if (!this.user) {
			this.user = new Partner()
		}
		return this.user
	}

	// privatní konstruktor
	private constructor() {
		super()
		log("Partner has been initialized")
	}
}