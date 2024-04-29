import { User } from './User.js'

export class LocalUser extends User {

	private static user: LocalUser | undefined;

	public static getUser() {
		if (!this.user) {
			this.user = new LocalUser()
		}
		return this.user
	}

	private constructor() {
		super()
	}
}