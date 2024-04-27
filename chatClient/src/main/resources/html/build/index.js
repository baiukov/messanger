import { App } from './App.js';
// @ts-ignore
window.sendDataToFront = App.sendDataToFront;
// @ts-ignore
window.sendID = App.setID;
new App();
