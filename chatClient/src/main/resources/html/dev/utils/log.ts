import { App } from '../App.js'
import { Events } from '../enums/Events.enum.js'

// metoda pro posílání požadavku o logování do loggeru. Jenom pro zjednodušení syntaxe
export const log = (type: number, message: string) => {
	App.emit(Events.LOG, { type, message })
}