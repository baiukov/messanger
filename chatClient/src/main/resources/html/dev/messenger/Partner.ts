import { User } from './User.js'

export class Partner extends User {

	private static user: Partner | undefined;

	public static getUser = () => {
		if (!this.user) {
			this.user = new Partner()
		}
		return this.user
	}

	private constructor() {
		super()
	}
}