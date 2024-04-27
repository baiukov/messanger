export class DialogueService {

	constructor() {
		this.initListeners()
	}

	public initListeners() {
		$(".back-button").click(() =>  {
			//@ts-ignore
			window.javaConnector.switchPage("main")
		})
	}

	public setPartnerData(message: string[]) {
		if (message.length < 4) return
		const name: string = message[1]
		const surname = message[2]
		const color = message[3]
		const hex = "#" + color.toLowerCase()
		const firstLetter = name.charAt(0).toUpperCase()
		$("#partnerName").text(name + " " + surname)
		$("#partnersAvatar").css("background-color", hex)
		$("#partnersFirstLetter").text(firstLetter)
	}
}