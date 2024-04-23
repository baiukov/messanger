import { LoggerModule } from './logger/logger.module.js'
import { NotificationsModule } from './notifications/notifications.module.js'
import { RegisterModule } from './register/register.module.js'

export class App {

	constructor() {		
		new RegisterModule()  
		new LoggerModule()
		new NotificationsModule()
	}

	private static events: Record<string, Function> = {}

	private static serverEvents: Record<string, Function> = {}

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

	public static sendDataToFront = (data: string) => {
		$(".test").text(data)

		// Object.keys(this.serverEvents).forEach((key: string) => {
		// 	if (key === eventName) {
		// 		this.events[key](data)
		// 	}
		// })
	}

	public static onClient = (eventName: string, event: Function) => {
		this.events[eventName] = event 
	}

	public static emitClient = (eventName: string, data: Array<any>) => {
		let message = eventName + " "
		data.forEach((element: any) => {
			message += element + " "
		})
		// @ts-ignore
		window.javaConnector.receiveMessage(message)
		$("#username").text(1)
	}
}