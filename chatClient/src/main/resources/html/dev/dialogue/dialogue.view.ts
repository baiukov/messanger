export class DialogueView {

	public getMessageWithTitle = (color: string, fullName: string, message: string) => {
		const messageBox = document.createElement("div")
		$(messageBox).addClass("message-with-avatar")
		const wrapper = document.createElement("div")
		$(wrapper).addClass("message-wrapper")

		const avatar = document.createElement("div")
		$(avatar).addClass("avatar")
		$(avatar).css("background-color", color)
		const firstLetter = document.createElement("h2")
		$(firstLetter).text(fullName.charAt(0).toUpperCase())
		$(avatar).append(firstLetter)

		$(wrapper).append(avatar)
		$(messageBox).append(wrapper)

		const content = document.createElement("div")
		$(content).addClass("message-content")
		const title = document.createElement("h3")
		$(title).text(fullName)
		$(content).append(title)
		const text = document.createElement("p")
		$(text).text(message)
		$(text).addClass("message-text")
		$(content).append(text)

		$(wrapper).append(content)
		return messageBox
	}

	public getMessage = (message: string) => {
		const messageBox = document.createElement("div")
		$(messageBox).addClass("message")
		const text = document.createElement("p")
		$(text).addClass("message-text")
		$(text).text(message)
		$(messageBox).append(text)
		return messageBox
	}
 
}