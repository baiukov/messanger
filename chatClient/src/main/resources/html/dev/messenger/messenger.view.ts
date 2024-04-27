export class MessengerView {

	public getUserBox(name: string, surname: string) {
		const box = document.createElement("div")
		$(box).addClass("user-option").text(name + " " + surname)
		return box;
	}
}