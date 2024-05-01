import { DialogueModule } from './dialogue/dialogue.module.js'
import { Events } from './enums/Events.enum.js'
import { MessengerModule } from './messenger/messenger.module.js'
import { NotificationsModule } from './notifications/notifications.module.js'
import { RegisterModule } from './register/register.module.js'
import { log } from './utils/log.js'

export class App {

	constructor() {		
		new RegisterModule()  
		new NotificationsModule()
		new MessengerModule()
		new DialogueModule()
		log("Application has been succesfully initialized")
	}

	private static events: Record<string, Function[]> = {}

	private static serverEvents: Record<string, Function[]> = {}

	public static id: string;

	public static on = (eventName: string, event: Function) => {
		if (!this.events[eventName]) {
			this.events[eventName] = [event]
			return
		}
		this.events[eventName].push(event)
		log(`Event ${eventName} has been registered`)
	}

	public static emit = (eventName: string, data: any) => {
		Object.keys(this.events).forEach((key: string) => {
			if (key === eventName) {
				this.events[key].forEach((event) => {
					event(data)
					log(`Event ${eventName} has been processed with data: ${data}`)
				})
			}
		})
	}

	public static sendDataToFront = (dataStr: string) => {
		const data = dataStr.split(" ")
		const eventName: string = data[0].trim()
		log("Received message from the client: " + dataStr)

		Object.keys(this.serverEvents).forEach((key: string) => {
			if (key === eventName) {
				this.serverEvents[key].forEach((event) => {
					event(data)
					log(`Server event ${eventName} has been processed with data ${data}`)
				})
			}
		})
	}

	public static onClient = (eventName: string, event: Function) => {
		if (!this.serverEvents[eventName]) {
			this.serverEvents[eventName] = [event]
			return
		}
		this.serverEvents[eventName].push(event) 
		log(`Server event ${eventName} has been registered`)
	}

	public static emitClient = (eventName: string, data: Array<any>) => {
		let message = eventName + " "
		data.forEach((element: any) => {
			message += element + " "
		})
		log(`Server event ${eventName} has been sent to server with data ${data}` )
		// @ts-ignore
		window.javaConnector.receiveMessage(message)
	}

	public static setID = (id: string) => {
		App.emit(Events.SETID, id)
		log(`User ID has been set to ${id}`)
	}
	
}