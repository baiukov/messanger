import { DialogueModule } from './dialogue/dialogue.module.js'
import { Events } from './enums/Events.enum.js'
import { LoggerModule } from './logger/logger.module.js'
import { MessengerModule } from './messenger/messenger.module.js'
import { NotificationsModule } from './notifications/notifications.module.js'
import { RegisterModule } from './register/register.module.js'
import { log } from './utils/log.js'

export class App {

	constructor() {		
		new RegisterModule()  
		new LoggerModule()
		new NotificationsModule()
		new MessengerModule()
		new DialogueModule()
	}

	private static events: Record<string, Function[]> = {}

	private static serverEvents: Record<string, Function> = {}

	public static id: string;

	public static on = (eventName: string, event: Function) => {
		if (!this.events[eventName]) {
			this.events[eventName] = [event]
			return
		}
		this.events[eventName].push(event)
	}

	public static emit = (eventName: string, data: any) => {
		Object.keys(this.events).forEach((key: string) => {
			log([key, eventName,  key === eventName].toString())
			if (key === eventName) {
				this.events[key].forEach((event) => {
					event(data)
				})
			}
		})
	}

	public static sendDataToFront = (dataStr: string) => {
		const data = dataStr.split(" ")
		const eventName: string = data[0].trim()

		Object.keys(this.serverEvents).forEach((key: string) => {
			// App.emitClient(Events.LOG, [dataStr, eventName, key, eventName === key])
			if (key === eventName) {
				this.serverEvents[key](data)
			}
		})
	}

	public static onClient = (eventName: string, event: Function) => {
		this.serverEvents[eventName] = event 
	}

	public static emitClient = (eventName: string, data: Array<any>) => {
		let message = eventName + " "
		data.forEach((element: any) => {
			message += element + " "
		})
		// @ts-ignore
		window.javaConnector.receiveMessage(message)
	}

	public static setID = (id: string) => {
		App.emit(Events.SETID, id)
	}
	
}