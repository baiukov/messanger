import { DialogueModule } from './dialogue/dialogue.module.js'
import { Events } from './enums/Events.enum.js'
import { MessengerModule } from './messenger/messenger.module.js'
import { NotificationsModule } from './notifications/notifications.module.js'
import { RegisterModule } from './register/register.module.js'
import { log } from './utils/log.js'

/*
	Třída App - je třída modulu aplikace, která se zabývá vytvařením všech ostatních modulu a služby aplikace
*/
export class App {

	constructor() {		
		new RegisterModule()  
		new NotificationsModule()
		new MessengerModule()
		new DialogueModule()
		log("Application has been succesfully initialized")
	}

	// uložení seznamu eventů
	private static events: Record<string, Function[]> = {}

	// uložení seznamu serverových eventů
	private static serverEvents: Record<string, Function[]> = {}

	// uložení identifikačního čísla uživatele
	public static id: string;

	// metoda pro registrace eventu
	public static on = (eventName: string, event: Function) => {
		if (!this.events[eventName]) {
			this.events[eventName] = [event]
			return
		}
		this.events[eventName].push(event)
		//log(`Event ${eventName} has been registered`)
	}

	// metoda pro vyvolání některého z eventu, vyhledá ho v seznamu, pokud najde vyvolá příslušnou metodu
	public static emit = (eventName: string, data: any) => {
		log(this.events.toString())
		Object.keys(this.events).forEach((key: string) => {
			// log("EVENT " + eventName + " " + data + " " + key + " " + (eventName === key).toString())
			if (key === eventName) {
				this.events[key].forEach((event) => {
					event(data)
					// log(`Event ${eventName} has been processed with data: ${data}`)
				})
			}
		})
	}

	// metoda pro získání dat z klientu, vyvolá ji klient a tím způsobem předá data
	public static sendDataToFront = (dataStr: string) => {
		const data = dataStr.split(" ")
		const eventName: string = data[0].trim()
		// log("Received message from the client: " + dataStr)

		Object.keys(this.serverEvents).forEach((key: string) => {
			if (key === eventName) {
				this.serverEvents[key].forEach((event) => {
					event(data)
					// log(`Server event ${eventName} has been processed with data ${data}`)
				})
			}
		})
	}

	// metoda pro registrace serverových eventu
	public static onClient = (eventName: string, event: Function) => {
		log(this.events.toString())
		if (!this.serverEvents[eventName]) {
			this.serverEvents[eventName] = [event]
			return
		}
		this.serverEvents[eventName].push(event) 
		// log(`Server event ${eventName} has been registered`)
	}

	// metoda pro vyvolání některého ze serverových eventu, vyhledá ho v seznamu, pokud najde vyvolá příslušnou metodu
	public static emitClient = (eventName: string, data: Array<any>) => {
		log(this.events.toString())
		let message = eventName + " "
		data.forEach((element: any) => {
			message += element + " "
		})
		// @ts-ignore
		window.javaConnector.receiveMessage(message)
	}

	// setter
	public static setID = (id: string) => {
		App.emit(Events.SETID, id)
		// log(`User ID has been set to ${id}`)
	}
	
}