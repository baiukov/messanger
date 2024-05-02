/*
	Třída User - je abstraktní třída entity uživatelů uložených lokálně
*/
export abstract class User {

	// identifikační číslo
	private id: string | undefined

	// uživatelské jméno
	private userName: string | undefined

	// jméno
	private firstName: string | undefined 

	// přijmení
	private lastName: string | undefined

	// barva avataru
	private color: string | undefined

	// Konstrukto třídy
	public constructor() { }

	// Gettery
	public getID = () => { return this.id }

	public getUserName = () => { return this.userName }

	public getFirstName = () => { return this.firstName }

	public getLastName = () => { return this.lastName }

	public getColor = () => { return this.color }


	// Settery
	public setID = (id: string) => { this.id = id }

	public setUserName = (userName: string) => { this.userName = userName }

	public setFirstName = (firstName: string) => { this.firstName = firstName }

	public setLastName = (lastName: string) => { this.lastName = lastName }

	public setColor = (color: string) => { this.color = color}

}