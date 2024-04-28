export class User {

	private static user: User

	private id: string | undefined

	private userName: string | undefined

	private firstName: string | undefined

	private lastName: string | undefined

	private color: string | undefined

	private constructor() {

	}

	public static getUser() {
		if (!User.user) { User.user = new User() }
		return User.user;
	}

	public getID() { return this.id }

	public getUserName() { return this.userName }

	public getFirstName() { return this.firstName }

	public getLastName() { return this.lastName }

	public getColor() { return this.color }


	public setID(id: string) { this.id = id }

	public setUserName(userName: string) { this.userName = userName }

	public setFirstName(firstName: string) { this.firstName = firstName }

	public setLastName(lastName: string) { this.lastName = lastName }

	public setColor(color: string) { this.color = color}

}