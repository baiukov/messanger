import { App } from '../App.js'
import { Events } from '../enums/Events.enum.js'
import { log } from '../utils/log.js'

export class DialogueService {

	private partnerID: string | undefined;

	private userID: string | undefined;

	constructor() {
		this.initListeners()
	}

	public initListeners() {
		$(".back-button").click(() =>  {
			//@ts-ignore
			window.javaConnector.switchPage("main", null)
		})

		$(".send-box").on('submit', () => {
			const message = $("#message").val()
			if (!message) return;
			console.log(this.partnerID)
			App.emitClient(Events.SEND, [this.userID, this.partnerID, message])
			return false;
		})

		$(".submit-send").click(() => {
			console.log(1)
			$(".send-box").submit()
		})

		console.log($(".dialogue").prop("scrollHeight"))
		$(".dialogue").scrollTop($(".dialogue").prop("scrollHeight"));
		$(".dialogue").css("scroll-behavior", "smooth")
	}

	public setID(id: string) {
		this.userID = id;
	}

	public setPartnerData(message: string[]) {
		if (message.length < 4) return
		const id = message[1]
		const name: string = message[2]
		const surname = message[3]
		const color = message[4]
		const hex = "#" + color.toLowerCase()
		const firstLetter = name.charAt(0).toUpperCase()
		this.partnerID = id
		log(['partner', id, name, surname, hex, firstLetter].toString())
		$("#partnerName").text(name + " " + surname)
		$("#partnersAvatar").css("background-color", hex)
		$("#partnersFirstLetter").text(firstLetter)
	}
}