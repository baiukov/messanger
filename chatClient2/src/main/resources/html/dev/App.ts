export class App {

	private static events: Record<string, Function> = {}

	public static on = (eventName: string, event: Function) => {
		this.events[eventName] = event
	}

	public static emit = (eventName: string, data: any) => {
		Object.keys(this.events).forEach((key: string) => {
			if (key === eventName) {
				this.events[key](data)
			}
		})
	}

}