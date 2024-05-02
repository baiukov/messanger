import { App } from '../App.js'
import { Events } from '../enums/Events.enum.js'
import { log } from '../utils/log.js'

/*
	Třída MessengerView - je třída pro vytváření přehledu statických prvků hlávní stránky a nastavení jejich dynamických zpracování událostí
*/
export class MessengerView {

	// metoda pro získání ramečku uživatele obsahujícího jméno, příjmení
	public getUserBox = (name: string, surname: string) => {
		const box = document.createElement("div")
		$(box).addClass("user-option").text(name + " " + surname)
		log("User box has been created " + box)
		return box;
	}

	// metoda pro získání ramečku chatu, obsahující prvky partneru, poslední zprávu a počet nepřečtených zpráv
	public getDialogue = (color: string, name: string, surname: string, messageStr: string, unread: number) => {
		const messageBox = document.createElement("div")
		$(messageBox).addClass("message-box")
		if (unread > 0) {
			$(messageBox).addClass("unread")
		}

		const messageWithAvatar = document.createElement("div")
		$(messageWithAvatar).addClass("message-with-avatar")

		const messageWrapper = document.createElement("div")
		$(messageWrapper).addClass("message-wrapper")
		$(messageWithAvatar).append(messageWrapper)
		$(messageBox).append(messageWithAvatar)
		
		const avatar = document.createElement("div")
		$(avatar).addClass("avatar")
		$(avatar).css("background-color", color)
		const letter = document.createElement("h2")
		$(letter).text(name.charAt(0).toUpperCase())
		$(avatar).append(letter)
		$(messageWrapper).append(avatar)

		const content = document.createElement("message-content")
		$(content).addClass("message-content")
		const title = document.createElement("h3")
		$(title).text(name + " " + surname)
		$(content).append(title)

		const message = document.createElement("p")
		$(message).addClass("short-message")
		$(message).text(messageStr)
		$(content).append(message)
		$(messageWrapper).append(content)

		// pokud nepřečtený zprávy nejsou, nezobrazovat ctvereček s počtem
		if (unread > 0) {
			const unreadBox = document.createElement("div")
			$(unreadBox).addClass("unread-amount")
			const unreadTitle = document.createElement("h3")
			$(unreadTitle).text(unread)
			$(unreadBox).append(unreadTitle)	
			$(messageBox).append(unreadBox)	
		}

		log("Message box has been created " + messageBox)
		return messageBox
	}

	// metoda pro získání kontextového menu pro upravení vztahu uživatelů
	public getRelationBox = (userID: string, partnerID: string, isAlreadyPinned: boolean) => {
		const box = document.createElement("div")
		$(box).addClass("relations")
		const pin = document.createElement("div")
		$(pin).addClass("pin")
		$(pin).text(isAlreadyPinned ? "Unpin" : "Pin")

		// při kliknutí na Pin/Unpin, pošle požadavek o připnutí/Odepnutí
		$(pin).click((event) => {
			App.emitClient(isAlreadyPinned ? Events.UNPIN : Events.PIN, [userID, partnerID])
			event.stopPropagation()
			$(".messages-box").empty()
		}) 

		const block = document.createElement("div") 
		$(block).addClass("block")
		$(block).text("Block")

		// při kliknutí na Block, pošle požadavek o zablokování
		$(block).click((event) => {
			App.emitClient(Events.BLOCK, [userID, partnerID])
			event.stopPropagation()
			$(".messages-box").empty()
		})
		
		$(box).append(pin, block)
		log("Relation dialogue menu has been created: " + box)
		return box
	}
}