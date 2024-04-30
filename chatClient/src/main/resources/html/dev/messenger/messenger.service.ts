import { App } from '../App.js'
import { Events } from '../enums/Events.enum.js'
import { log } from '../utils/log.js'
import { secToMs } from '../utils/secToMs.js'
import { LocalUser } from './LocalUser.js'
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

		setInterval(() => {
			const id = LocalUser.getUser().getID()
			if (id) {
				App.emitClient(Events.FETCHDIALOGUES, [LocalUser.getUser().getID()])
			}
		}, secToMs(0.5))

	}

	public fetchData(id: string) {
		LocalUser.getUser().setID(id)
		App.emitClient(Events.FETCHNAME, [id])
		App.emitClient(Events.FETCHCOLOR, [id])
	}

	public setFullName(message: string[]) {
		let firstName: string
		let lastName: string
		const user = LocalUser.getUser();
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
		for (let i = 1; i < message.length; i += 3) {
			const name = message[i]
			const surname = message[i + 1]
			const partnerID = message[i + 2]
			const box = this.view.getUserBox(name, surname)
			const userID = LocalUser.getUser().getID() || ''
			$(box).click(() => {
				log([Events.READMESSAGES, userID, partnerID].toString())
				App.emitClient(Events.READMESSAGES, [userID, partnerID])
				// @ts-ignore
				window.javaConnector.goToDialogue(partnerID)
			})
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
		LocalUser.getUser().setColor(hex)
		$("#avatar").css("background-color", hex)
	}

	public showDialogues(message: string[]) {
		const messagesBox = $(".messages-box")
		for (let i = 1; i < message.length; i += 7) {
			const partnerID = message[i]
			if ($(`#${partnerID}`).length) continue

			const name = message[i + 1]
			const surname = message[i + 2]
			const partnerColor = message[i + 3]
			const text = message[i + 4].replaceAll("/+", " ")
			const unread = message[i + 5]
			const isPinned = message[i + 6] === "true"

			const hex = "#" + partnerColor.toLowerCase()
			
			const dialogue = this.view.getDialogue(hex, name, surname, text, parseInt(unread))
			$(dialogue).attr("id", partnerID)
			const id = LocalUser.getUser().getID()
			$(dialogue).click(() => {
				// @ts-ignore
				window.javaConnector.goToDialogue(partnerID)
			})	
			$(dialogue).on('contextmenu', (event) => {
				$(".relations").remove()
				const relationBox = this.view.getRelationBox(id || "", partnerID, isPinned)
				$(relationBox).css("margin-left", event.clientX - 20) 
				$(dialogue).append(relationBox)
				event.preventDefault()
			})
			$(messagesBox).append(dialogue)
		}
	}

}