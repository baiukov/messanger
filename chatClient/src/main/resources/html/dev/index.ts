import { App } from './App.js'

// @ts-ignore
window.sendDataToFront = App.sendDataToFront

// @ts-ignore
window.getID = App.setID

new App()