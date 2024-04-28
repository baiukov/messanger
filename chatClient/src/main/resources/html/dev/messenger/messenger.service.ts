import { App } from '../App.js'
import { Events } from '../enums/Events.enum.js'
import { log } from '../utils/log.js'
import { User } from './User.js'
import { MessengerView } from './messenger.view.js'

export class MessengerService {

	private view: MessengerView = new MessengerView()

	constructor() {
		this.setListeners()
	}

	public setListeners() {

		$("#user").keyup(() => {
			const val = $("#user").val()
			$(".user-list").empty()
			App.emitClient(Events.FETCHUSERS, [val])
		})

	}

	public fetchData(id: string) {
		User.getUser().setID(id)
		App.emitClient(Events.FETCHNAME, [id])
		App.emitClient(Events.FETCHCOLOR, [id])
	}

	public setFullName(message: string[]) {
		let firstName: string
		let lastName: string
		const user = User.getUser();
		if (message.length < 3) {
			firstName = "Unknown"
		  lastName = "Unknown"
		} else {
			firstName = message[1]
			lastName = message[2]
		}
		
		user.setFirstName(firstName)
		user.setLastName(lastName)

		$("#firstLetter").text(firstName.charAt(0).toUpperCase())
	}

	public showUsers(message: string[]) {
		log(message.toString())
		for (let i = 1; i < message.length; i += 3) {
			const name = message[i]
			const surname = message[i + 1]
			const id = message[i + 2]
			const box = this.view.getUserBox(name, surname)
			$(box).click(() => {
				// @ts-ignore
				window.javaConnector.goToDialogue(id)
			})
			log($(box).text())
			$(".user-list").append(box)
		}
	} 

	public setColor(message: string[]) {
		let color;
		if (message.length < 2) {
			color = "F2C4DE"
		} else {
			color = message[1]
		}
		const hex = "#" + color.toLowerCase()
		User.getUser().setColor(hex)
		log(hex)
		$("#avatar").css("background-color", hex)
	}

	public showDialogues(message: string[]) {
		
	}

}