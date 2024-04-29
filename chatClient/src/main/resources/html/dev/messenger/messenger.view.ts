export class MessengerView {

	public getUserBox(name: string, surname: string) {
		const box = document.createElement("div")
		$(box).addClass("user-option").text(name + " " + surname)
		return box;
	}

	public getDialogue(color: string, name: string, surname: string, messageStr: string, unread: number) {
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

		if (unread > 0) {
			const unreadBox = document.createElement("div")
			$(unreadBox).addClass("unread-amount")
			const unreadTitle = document.createElement("h3")
			$(unreadTitle).text(unread)
			$(unreadBox).append(unreadTitle)	
			$(messageBox).append(unreadBox)	
		}

		return messageBox
		
	}
}