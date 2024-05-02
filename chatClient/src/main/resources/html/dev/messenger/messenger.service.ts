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

		setInterval(() => {
			const id = LocalUser.getUser().getID()
			if (id) {
				App.emitClient(Events.FETCHDIALOGUES, [LocalUser.getUser().getID()])
			}
		}, secToMs(0.1))
	}

	public setListeners = () => {

		$("#user").keyup(() => {
			$(".profile").css("display", "none")
			const val = $("#user").val()
			$(".user-list").empty()
			App.emitClient(Events.FETCHUSERS, [val])
			log("User type in search field")
		})

		$(".profile-avatar").click((event) => {
			$(".profile").css("display", "flex")
			event.stopPropagation()
			log("User clicked on profile avatar")
		})

		$(document.body).click(() => {
			$(".profile").css("display", "none")
			log("User clicked on body")
		})

		$(".profile").click((event) => {
			event.stopPropagation()
			log("User clicked on profile box")
		})

		$("#save").click((event) => {
			const shouldClose = this.update()
			this.fetchData(LocalUser.getUser().getID() || "")
			event.stopPropagation()
			if (shouldClose) { $(".profile").css("display", "none") }
			log("User clicked on save button in profile")
			return false
		})
	}

	public fetchData = (id: string) => {
		LocalUser.getUser().setID(id)
		App.emitClient(Events.FETCHNAME, [id])
		App.emitClient(Events.FETCHCOLOR, [id])
	}

	public setFullName = (message: string[]) => {
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

		const firstLetter = firstName.charAt(0).toUpperCase()
		$("#firstLetter").text(firstLetter)
		$("#bigFirstLetter").text(firstLetter)
		$("#name").val(firstName)
		$("#surname").val(lastName)
		log(`User full name has been set. Name: ${firstName}, Last name: ${lastName}. First letter: ${firstLetter}`)
	}

	public update = () => {
		const currentPassword = $("#currentPassword").val()
		
		if (!currentPassword) {
			this.showProfileError(["", "Type in your current password"])
			return false
		}

		const newPassword = $("#newPassword").val() as string
		const repeatPassword = $("#passwordRepeat").val() as string

		if (newPassword && newPassword != repeatPassword) {
			this.showProfileError(["", "Passwords does not match"])
			return false
		}

		const name = $("#name").val() as string
		const surname = $("#surname").val() as string

		const space = " "
		if (name.includes(space) || surname.includes(space) || newPassword.includes(space)) {
			this.showProfileError(["", "Don't use spaces"])
			return false
		}

		this.showProfileError(["", ""])
		$("#currentPassword").val("")
		$("#newPassword").val("")
		$("#passwordRepeat").val("")

		log(`User successfully filled the form of profile updating, procceed to the server`)
		App.emitClient(Events.UPDATE, [
			LocalUser.getUser().getID(), 
			name || "null", 
			surname || "null", 
			newPassword || "null"
		])
		return true
	}

	public showUsers = (message: string[]) => {
		log("User list in search will be updated with " + message.length + " user(-s)")
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

	public setColor = (message: string[]) => {
		let color;
		if (message.length < 2) {
			color = "F2C4DE"
		} else {
			color = message[1]
		}
		const hex = "#" + color.toLowerCase()
		LocalUser.getUser().setColor(hex)
		$("#avatar").css("background-color", hex)
		$(".big-avatar").css("background-color", hex)
		log("Colour for local user has been updated to " + hex)
	}

	public showDialogues = (message: string[]) => {
		const messagesBox = $(".messages-box")
		log(Math.floor(message.length / 7) + " dialogue(-s) will be shown for user")
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
				$(".profile").css("display", "none")
				event.preventDefault()
			})
			$(messagesBox).append(dialogue)
		}
	}

	public showProfileError = (message: string[]) => {
		let error = "";
		let delimiter = "";
		for (let i = 1; i < message.length; i++) {
			error += delimiter + message[i]
			delimiter = " "
		} 
		$(".error-text").text(error)
		log("Error happened on profile updating " + error)
	} 

}