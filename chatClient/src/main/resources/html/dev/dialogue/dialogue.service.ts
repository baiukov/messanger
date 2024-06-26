import { App } from '../App.js'
import { Events } from '../enums/Events.enum.js'
import { LocalUser } from '../messenger/LocalUser.js'
import { Partner } from '../messenger/Partner.js'
import { log } from '../utils/log.js'
import { secToMs } from '../utils/secToMs.js'
import { DialogueView } from './dialogue.view.js'

export class DialogueService {

	private partner: Partner = Partner.getUser()

	private user: LocalUser = LocalUser.getUser()

	private view = new DialogueView()

	constructor() {
		this.initListeners()

		setInterval(() => {
			if (this.user.getID()) {
				App.emitClient(Events.FETCHMESSAGES, [this.user.getID(), this.partner.getID()])
			}
		}, secToMs(0.1))
	}

	public initListeners = () => {
		$(".back-button").click(() =>  {
			log("Back button has been clicked. Redirecting to main page...")
			//@ts-ignore
			window.javaConnector.switchPage("main", null)
		})

		$(".send-box").on('submit', () => {
			const message = $("#message").val()
			if (!message) return;
			log("Send form has been submitted. Message to " + this.partner.getID() + " will proceed to the server. Message " + message)
			App.emitClient(Events.SEND, [this.user.getID(), this.partner.getID(), message])
			return false;
		})

		$(".submit-send").click(() => {
			$(".send-box").submit()
			$("#message").val("")
		})

		$(".dialogue").scrollTop($(".dialogue").prop("scrollHeight"));
		$(".dialogue").css("scroll-behavior", "smooth")

		$("#message").keypress((event) => {
			const enterButtonClickedEvent = 13;
			if (event.which === enterButtonClickedEvent && !event.shiftKey) {
					event.preventDefault()
					$(".send-box").submit()
					$("#message").val("")
			}
	});
	}

	public setID = (id: string) => {
		this.user.setID(id);
		log("ID for local user has been set to " + id)
	}

	public setPartnerData = (message: string[]) => {
		if (message.length < 4) return
		const id = message[1]
		const name: string = message[2]
		const surname = message[3]
		const color = message[4]
		const hex = "#" + color.toLowerCase()
		const firstLetter = name.charAt(0).toUpperCase()
		this.partner.setID(id)
		this.partner.setFirstName(name)
		this.partner.setLastName(surname)
		this.partner.setColor(hex)
		$("#partnerName").text(name + " " + surname)
		$("#partnersAvatar").css("background-color", hex)
		$("#partnersFirstLetter").text(firstLetter) 
		log(`Partner data has been set. ID: ${id}, name: ${name}, surname: ${surname}, color: ${hex}`)
		App.emitClient(Events.READMESSAGES, [LocalUser.getUser().getID(), id])
	}

	public showMessages = (message: string[]) => {
		const dialogue = $(".messages-wrapper")
		log("Fetched messages with partner. Will be shown " + Math.floor(message.length / 5) + "message(-s)")
		for (let i = 1; i < message.length; i += 5) {
			const messageID = message[i]
			if ($(`#${messageID}`).length) continue;

			const senderID = message[i + 1]
			const text = message[i + 2]
			const dateStr = message[i + 3]
			const timeStr = message[i + 4]
			
			const isSameSender = i > 1 && message[i - 4] === senderID
			const isUserSender = this.user.getID() === senderID
			const color = isUserSender ? this.user.getColor() : this.partner.getColor()
			const name = isUserSender ? this.user.getFirstName() + " " + this.user.getLastName() 
				: this.partner.getFirstName() + " " + this.partner.getLastName()

			const msg = text.replaceAll("/+", " ")
			const date = new Date(dateStr + " " + timeStr)

			const lastDate = i > 1 ? new Date(message[i - 2] + " " + message[i - 1]) : null
			// @ts-ignore
			const isLongerThanFiveMinutes = lastDate && (date - lastDate) / 60 / 1000 >= 5;
			// @ts-ignore
			log(msg)

			const box = !isSameSender || isLongerThanFiveMinutes ? this.view.getMessageWithTitle(color || "", name, msg) : this.view.getMessage(msg)
			$(box).attr("id", messageID)
			$(dialogue).append(box)
		}
		$(".dialogue").scrollTop($(".dialogue").prop("scrollHeight"));
	}
}