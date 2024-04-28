/*
 * ATTENTION: The "eval" devtool has been used (maybe by default in mode: "development").
 * This devtool is neither made for production nor for readable output files.
 * It uses "eval()" calls to create a separate source file in the browser devtools.
 * If you are trying to read the output file, select a different devtool (https://webpack.js.org/configuration/devtool/)
 * or disable the default devtool with "devtool: false".
 * If you are looking for production-ready output files, see mode: "production" (https://webpack.js.org/configuration/mode/).
 */
/******/ (() => { // webpackBootstrap
/******/ 	"use strict";
/******/ 	var __webpack_modules__ = ({

/***/ "./build/App.js":
/*!**********************!*\
  !*** ./build/App.js ***!
  \**********************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

eval("__webpack_require__.r(__webpack_exports__);\n/* harmony export */ __webpack_require__.d(__webpack_exports__, {\n/* harmony export */   App: () => (/* binding */ App)\n/* harmony export */ });\n/* harmony import */ var _dialogue_dialogue_module_js__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./dialogue/dialogue.module.js */ \"./build/dialogue/dialogue.module.js\");\n/* harmony import */ var _enums_Events_enum_js__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./enums/Events.enum.js */ \"./build/enums/Events.enum.js\");\n/* harmony import */ var _logger_logger_module_js__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./logger/logger.module.js */ \"./build/logger/logger.module.js\");\n/* harmony import */ var _messenger_messenger_module_js__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ./messenger/messenger.module.js */ \"./build/messenger/messenger.module.js\");\n/* harmony import */ var _notifications_notifications_module_js__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ./notifications/notifications.module.js */ \"./build/notifications/notifications.module.js\");\n/* harmony import */ var _register_register_module_js__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! ./register/register.module.js */ \"./build/register/register.module.js\");\n\n\n\n\n\n\nvar App = /** @class */function () {\n  function App() {\n    new _register_register_module_js__WEBPACK_IMPORTED_MODULE_5__.RegisterModule();\n    new _logger_logger_module_js__WEBPACK_IMPORTED_MODULE_2__.LoggerModule();\n    new _notifications_notifications_module_js__WEBPACK_IMPORTED_MODULE_4__.NotificationsModule();\n    new _messenger_messenger_module_js__WEBPACK_IMPORTED_MODULE_3__.MessengerModule();\n    new _dialogue_dialogue_module_js__WEBPACK_IMPORTED_MODULE_0__.DialogueModule();\n  }\n  var _a;\n  _a = App;\n  App.events = {};\n  App.serverEvents = {};\n  App.on = function (eventName, event) {\n    if (!_a.events[eventName]) {\n      _a.events[eventName] = [event];\n      return;\n    }\n    _a.events[eventName].push(event);\n  };\n  App.emit = function (eventName, data) {\n    Object.keys(_a.events).forEach(function (key) {\n      // log([key, eventName,  key === eventName].toString())\n      if (key === eventName) {\n        _a.events[key].forEach(function (event) {\n          event(data);\n        });\n      }\n    });\n  };\n  App.sendDataToFront = function (dataStr) {\n    var data = dataStr.split(\" \");\n    var eventName = data[0].trim();\n    Object.keys(_a.serverEvents).forEach(function (key) {\n      // App.emitClient(Events.LOG, [dataStr, eventName, key, eventName === key])\n      if (key === eventName) {\n        _a.serverEvents[key].forEach(function (event) {\n          event(data);\n        });\n      }\n    });\n  };\n  App.onClient = function (eventName, event) {\n    if (!_a.serverEvents[eventName]) {\n      _a.serverEvents[eventName] = [event];\n      return;\n    }\n    _a.serverEvents[eventName].push(event);\n  };\n  App.emitClient = function (eventName, data) {\n    var message = eventName + \" \";\n    data.forEach(function (element) {\n      message += element + \" \";\n    });\n    // @ts-ignore\n    window.javaConnector.receiveMessage(message);\n  };\n  App.setID = function (id) {\n    _a.emit(_enums_Events_enum_js__WEBPACK_IMPORTED_MODULE_1__.Events.SETID, id);\n  };\n  return App;\n}();\n\n\n//# sourceURL=webpack://html/./build/App.js?");

/***/ }),

/***/ "./build/dialogue/dialogue.controller.js":
/*!***********************************************!*\
  !*** ./build/dialogue/dialogue.controller.js ***!
  \***********************************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

eval("__webpack_require__.r(__webpack_exports__);\n/* harmony export */ __webpack_require__.d(__webpack_exports__, {\n/* harmony export */   DialogueController: () => (/* binding */ DialogueController)\n/* harmony export */ });\n/* harmony import */ var _App_js__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ../App.js */ \"./build/App.js\");\n/* harmony import */ var _enums_Events_enum_js__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ../enums/Events.enum.js */ \"./build/enums/Events.enum.js\");\n\n\nvar DialogueController = /** @class */function () {\n  function DialogueController(dialogueService) {\n    var _this = this;\n    this.dialogueService = dialogueService;\n    _App_js__WEBPACK_IMPORTED_MODULE_0__.App.onClient(_enums_Events_enum_js__WEBPACK_IMPORTED_MODULE_1__.Events.SETDIALOGUEWITH, function (message) {\n      _App_js__WEBPACK_IMPORTED_MODULE_0__.App.emitClient(_enums_Events_enum_js__WEBPACK_IMPORTED_MODULE_1__.Events.FETCHPARTNERDATA, [message[1]]);\n    });\n    _App_js__WEBPACK_IMPORTED_MODULE_0__.App.onClient(_enums_Events_enum_js__WEBPACK_IMPORTED_MODULE_1__.Events.FETCHPARTNERDATA, function (message) {\n      _this.dialogueService.setPartnerData(message);\n    });\n    _App_js__WEBPACK_IMPORTED_MODULE_0__.App.on(_enums_Events_enum_js__WEBPACK_IMPORTED_MODULE_1__.Events.SETID, function (id) {\n      _this.dialogueService.setID(id);\n    });\n    _App_js__WEBPACK_IMPORTED_MODULE_0__.App.onClient(_enums_Events_enum_js__WEBPACK_IMPORTED_MODULE_1__.Events.FETCHMESSAGES, function (message) {\n      _this.dialogueService.showMessages(message);\n    });\n  }\n  return DialogueController;\n}();\n\n\n//# sourceURL=webpack://html/./build/dialogue/dialogue.controller.js?");

/***/ }),

/***/ "./build/dialogue/dialogue.module.js":
/*!*******************************************!*\
  !*** ./build/dialogue/dialogue.module.js ***!
  \*******************************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

eval("__webpack_require__.r(__webpack_exports__);\n/* harmony export */ __webpack_require__.d(__webpack_exports__, {\n/* harmony export */   DialogueModule: () => (/* binding */ DialogueModule)\n/* harmony export */ });\n/* harmony import */ var _dialogue_controller_js__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./dialogue.controller.js */ \"./build/dialogue/dialogue.controller.js\");\n/* harmony import */ var _dialogue_service_js__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./dialogue.service.js */ \"./build/dialogue/dialogue.service.js\");\n\n\nvar DialogueModule = /** @class */function () {\n  function DialogueModule() {\n    var dialogueService = new _dialogue_service_js__WEBPACK_IMPORTED_MODULE_1__.DialogueService();\n    new _dialogue_controller_js__WEBPACK_IMPORTED_MODULE_0__.DialogueController(dialogueService);\n  }\n  return DialogueModule;\n}();\n\n\n//# sourceURL=webpack://html/./build/dialogue/dialogue.module.js?");

/***/ }),

/***/ "./build/dialogue/dialogue.service.js":
/*!********************************************!*\
  !*** ./build/dialogue/dialogue.service.js ***!
  \********************************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

eval("__webpack_require__.r(__webpack_exports__);\n/* harmony export */ __webpack_require__.d(__webpack_exports__, {\n/* harmony export */   DialogueService: () => (/* binding */ DialogueService)\n/* harmony export */ });\n/* harmony import */ var _App_js__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ../App.js */ \"./build/App.js\");\n/* harmony import */ var _enums_Events_enum_js__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ../enums/Events.enum.js */ \"./build/enums/Events.enum.js\");\n/* harmony import */ var _messenger_User_js__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ../messenger/User.js */ \"./build/messenger/User.js\");\n/* harmony import */ var _utils_log_js__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ../utils/log.js */ \"./build/utils/log.js\");\n/* harmony import */ var _utils_secToMs_js__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ../utils/secToMs.js */ \"./build/utils/secToMs.js\");\n/* harmony import */ var _dialogue_view_js__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! ./dialogue.view.js */ \"./build/dialogue/dialogue.view.js\");\n\n\n\n\n\n\nvar DialogueService = /** @class */function () {\n  function DialogueService() {\n    var _this = this;\n    this.partner = {};\n    this.user = _messenger_User_js__WEBPACK_IMPORTED_MODULE_2__.User.getUser();\n    this.view = new _dialogue_view_js__WEBPACK_IMPORTED_MODULE_5__.DialogueView();\n    this.initListeners();\n    setInterval(function () {\n      _App_js__WEBPACK_IMPORTED_MODULE_0__.App.emitClient(_enums_Events_enum_js__WEBPACK_IMPORTED_MODULE_1__.Events.FETCHMESSAGES, [_this.user.getID().trim()]);\n    }, (0,_utils_secToMs_js__WEBPACK_IMPORTED_MODULE_4__.secToMs)(1));\n  }\n  DialogueService.prototype.initListeners = function () {\n    var _this = this;\n    $(\".back-button\").click(function () {\n      //@ts-ignore\n      window.javaConnector.switchPage(\"main\", null);\n    });\n    $(\".send-box\").on('submit', function () {\n      var message = $(\"#message\").val();\n      if (!message) return;\n      _App_js__WEBPACK_IMPORTED_MODULE_0__.App.emitClient(_enums_Events_enum_js__WEBPACK_IMPORTED_MODULE_1__.Events.SEND, [_this.user.getID(), _this.partner.id, message]);\n      return false;\n    });\n    $(\".submit-send\").click(function () {\n      $(\".send-box\").submit();\n    });\n    $(\".dialogue\").scrollTop($(\".dialogue\").prop(\"scrollHeight\"));\n    $(\".dialogue\").css(\"scroll-behavior\", \"smooth\");\n    $(\"#message\").keypress(function (event) {\n      if (event.which === 13 && !event.shiftKey) {\n        event.preventDefault();\n        $(\".send-box\").submit();\n        $(\"#message\").val(\"\");\n      }\n    });\n  };\n  DialogueService.prototype.setID = function (id) {\n    this.user.id = id;\n  };\n  DialogueService.prototype.setPartnerData = function (message) {\n    if (message.length < 4) return;\n    var id = message[1];\n    var name = message[2];\n    var surname = message[3];\n    var color = message[4];\n    var hex = \"#\" + color.toLowerCase();\n    var firstLetter = name.charAt(0).toUpperCase();\n    this.partner.id = id;\n    this.partner.name = name;\n    this.partner.surname = surname;\n    this.partner.hex = hex;\n    $(\"#partnerName\").text(name + \" \" + surname);\n    $(\"#partnersAvatar\").css(\"background-color\", hex);\n    $(\"#partnersFirstLetter\").text(firstLetter);\n  };\n  DialogueService.prototype.showMessages = function (message) {\n    var dialogue = $(\".messages-wrapper\");\n    for (var i = 1; i < message.length; i += 5) {\n      var messageID = message[i];\n      if ($(\"#\".concat(messageID)).length) continue;\n      var senderID = message[i + 1];\n      var text = message[i + 2];\n      var dateStr = message[i + 3];\n      var timeStr = message[i + 4];\n      var isSameSender = i > 1 && message[i - 4] === senderID;\n      var isUserSender = this.user.getID() === senderID;\n      var color = isUserSender ? this.user.getColor() : this.partner.hex;\n      var name_1 = isUserSender ? this.user.getFirstName() + \" \" + this.user.getLastName() : this.partner.name + \" \" + this.partner.surname;\n      var msg = text.replaceAll(\"/+\", \" \");\n      var date = new Date(dateStr + \" \" + timeStr);\n      var lastDate = i > 1 ? new Date(message[i - 2] + \" \" + message[i - 1]) : null;\n      // @ts-ignore\n      var isLongerThanFiveMinutes = lastDate && (date - lastDate) / 60 / 1000 >= 5;\n      // @ts-ignore\n      (0,_utils_log_js__WEBPACK_IMPORTED_MODULE_3__.log)(msg);\n      var box = !isSameSender || isLongerThanFiveMinutes ? this.view.getMessageWithTitle(color, name_1, msg) : this.view.getMessage(msg);\n      $(box).attr(\"id\", messageID);\n      $(dialogue).append(box);\n    }\n    $(\".dialogue\").scrollTop($(\".dialogue\").prop(\"scrollHeight\"));\n  };\n  return DialogueService;\n}();\n\n\n//# sourceURL=webpack://html/./build/dialogue/dialogue.service.js?");

/***/ }),

/***/ "./build/dialogue/dialogue.view.js":
/*!*****************************************!*\
  !*** ./build/dialogue/dialogue.view.js ***!
  \*****************************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

eval("__webpack_require__.r(__webpack_exports__);\n/* harmony export */ __webpack_require__.d(__webpack_exports__, {\n/* harmony export */   DialogueView: () => (/* binding */ DialogueView)\n/* harmony export */ });\nvar DialogueView = /** @class */function () {\n  function DialogueView() {}\n  DialogueView.prototype.getMessageWithTitle = function (color, fullName, message) {\n    var messageBox = document.createElement(\"div\");\n    $(messageBox).addClass(\"message-with-avatar\");\n    var wrapper = document.createElement(\"div\");\n    $(wrapper).addClass(\"message-wrapper\");\n    var avatar = document.createElement(\"div\");\n    $(avatar).addClass(\"avatar\");\n    $(avatar).css(\"background-color\", color);\n    var firstLetter = document.createElement(\"h2\");\n    $(firstLetter).text(fullName.charAt(0).toUpperCase());\n    $(avatar).append(firstLetter);\n    $(wrapper).append(avatar);\n    $(messageBox).append(wrapper);\n    var content = document.createElement(\"div\");\n    $(content).addClass(\"message-content\");\n    var title = document.createElement(\"h3\");\n    $(title).text(fullName);\n    $(content).append(title);\n    var text = document.createElement(\"p\");\n    $(text).text(message);\n    $(text).addClass(\"message-text\");\n    $(content).append(text);\n    $(wrapper).append(content);\n    return messageBox;\n  };\n  DialogueView.prototype.getMessage = function (message) {\n    var messageBox = document.createElement(\"div\");\n    $(messageBox).addClass(\"message\");\n    var text = document.createElement(\"p\");\n    $(text).addClass(\"message-text\");\n    $(text).text(message);\n    $(messageBox).append(text);\n    return messageBox;\n  };\n  return DialogueView;\n}();\n\n\n//# sourceURL=webpack://html/./build/dialogue/dialogue.view.js?");

/***/ }),

/***/ "./build/enums/Events.enum.js":
/*!************************************!*\
  !*** ./build/enums/Events.enum.js ***!
  \************************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

eval("__webpack_require__.r(__webpack_exports__);\n/* harmony export */ __webpack_require__.d(__webpack_exports__, {\n/* harmony export */   Events: () => (/* binding */ Events)\n/* harmony export */ });\nvar Events;\n(function (Events) {\n  Events[\"LOGIN\"] = \"LOGIN\";\n  Events[\"REGISTER\"] = \"REGISTER\";\n  Events[\"LOG\"] = \"LOG\";\n  Events[\"NOTIFY\"] = \"NOTIFY\";\n  Events[\"ERROR\"] = \"ERROR\";\n  Events[\"SUCCESSLOGIN\"] = \"SUCCESSLOGIN\";\n  Events[\"SUCCESSREGISTER\"] = \"SUCCESSREGISTER\";\n  Events[\"SETID\"] = \"SETID\";\n  Events[\"FETCHNAME\"] = \"FETCHNAME\";\n  Events[\"FETCHUSERS\"] = \"FETCHUSERS\";\n  Events[\"FETCHCOLOR\"] = \"FETCHCOLOR\";\n  Events[\"SETDIALOGUEWITH\"] = \"SETDIALOGUEWITH\";\n  Events[\"FETCHPARTNERDATA\"] = \"FETCHPARTNERDATA\";\n  Events[\"SEND\"] = \"SEND\";\n  Events[\"FETCHMESSAGES\"] = \"FETCHMESSAGES\";\n})(Events || (Events = {}));\n\n//# sourceURL=webpack://html/./build/enums/Events.enum.js?");

/***/ }),

/***/ "./build/enums/logLevels.enum.js":
/*!***************************************!*\
  !*** ./build/enums/logLevels.enum.js ***!
  \***************************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

eval("__webpack_require__.r(__webpack_exports__);\n/* harmony export */ __webpack_require__.d(__webpack_exports__, {\n/* harmony export */   LogLevels: () => (/* binding */ LogLevels)\n/* harmony export */ });\n/**\n *  Dostuné úrovně logování zpráv\n */\nvar LogLevels;\n(function (LogLevels) {\n  LogLevels[LogLevels[\"INFO\"] = 0] = \"INFO\";\n  LogLevels[LogLevels[\"WARN\"] = 1] = \"WARN\";\n  LogLevels[LogLevels[\"ERROR\"] = 2] = \"ERROR\";\n  LogLevels[LogLevels[\"DEBUG\"] = 3] = \"DEBUG\";\n})(LogLevels || (LogLevels = {}));\n\n//# sourceURL=webpack://html/./build/enums/logLevels.enum.js?");

/***/ }),

/***/ "./build/index.js":
/*!************************!*\
  !*** ./build/index.js ***!
  \************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

eval("__webpack_require__.r(__webpack_exports__);\n/* harmony import */ var _App_js__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./App.js */ \"./build/App.js\");\n\n// @ts-ignore\nwindow.sendDataToFront = _App_js__WEBPACK_IMPORTED_MODULE_0__.App.sendDataToFront;\n// @ts-ignore\nwindow.sendID = _App_js__WEBPACK_IMPORTED_MODULE_0__.App.setID;\nnew _App_js__WEBPACK_IMPORTED_MODULE_0__.App();\n\n//# sourceURL=webpack://html/./build/index.js?");

/***/ }),

/***/ "./build/logger/logger.controller.js":
/*!*******************************************!*\
  !*** ./build/logger/logger.controller.js ***!
  \*******************************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

eval("__webpack_require__.r(__webpack_exports__);\n/* harmony export */ __webpack_require__.d(__webpack_exports__, {\n/* harmony export */   LoggerController: () => (/* binding */ LoggerController)\n/* harmony export */ });\n/* harmony import */ var _App_js__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ../App.js */ \"./build/App.js\");\n/* harmony import */ var _enums_Events_enum_js__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ../enums/Events.enum.js */ \"./build/enums/Events.enum.js\");\n\n\n/*\n    Třída LoggerController - je třída správce loggeru, která se zabývá operováním eventů. Nejdřív zaregestruje všechny eventy do aplikace, pak při vyvolání některého z těch eventu, spustí příslušnou metodu ze služby\n*/\nvar LoggerController = /** @class */function () {\n  function LoggerController(loggerService) {\n    var _this = this;\n    this.loggerService = loggerService;\n    // registrace eventu logování\n    _App_js__WEBPACK_IMPORTED_MODULE_0__.App.on(_enums_Events_enum_js__WEBPACK_IMPORTED_MODULE_1__.Events.LOG, function (data) {\n      _this.loggerService.log(data.type, data.message);\n    });\n  }\n  return LoggerController;\n}();\n\n\n//# sourceURL=webpack://html/./build/logger/logger.controller.js?");

/***/ }),

/***/ "./build/logger/logger.module.js":
/*!***************************************!*\
  !*** ./build/logger/logger.module.js ***!
  \***************************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

eval("__webpack_require__.r(__webpack_exports__);\n/* harmony export */ __webpack_require__.d(__webpack_exports__, {\n/* harmony export */   LoggerModule: () => (/* binding */ LoggerModule)\n/* harmony export */ });\n/* harmony import */ var _logger_controller_js__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./logger.controller.js */ \"./build/logger/logger.controller.js\");\n/* harmony import */ var _logger_service_js__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./logger.service.js */ \"./build/logger/logger.service.js\");\n\n\n/*\n    Třída LoggerModule - je třída modulu zpracování loggeru, která se zabývá vytvařením služby a správce loggeru\n*/\nvar LoggerModule = /** @class */function () {\n  function LoggerModule() {\n    var loggerService = new _logger_service_js__WEBPACK_IMPORTED_MODULE_1__.LoggerService();\n    new _logger_controller_js__WEBPACK_IMPORTED_MODULE_0__.LoggerController(loggerService);\n    //log(LogLevels.INFO, \"Logger module has been initialized successfully\")\n  }\n  return LoggerModule;\n}();\n\n\n//# sourceURL=webpack://html/./build/logger/logger.module.js?");

/***/ }),

/***/ "./build/logger/logger.service.js":
/*!****************************************!*\
  !*** ./build/logger/logger.service.js ***!
  \****************************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

eval("__webpack_require__.r(__webpack_exports__);\n/* harmony export */ __webpack_require__.d(__webpack_exports__, {\n/* harmony export */   LoggerService: () => (/* binding */ LoggerService)\n/* harmony export */ });\n/* harmony import */ var _enums_logLevels_enum_js__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ../enums/logLevels.enum.js */ \"./build/enums/logLevels.enum.js\");\n\n/*\n    Třída LoggerService - je třída služby loggeru, která se zabývá zpracováním logiky logování informací tak, že do konzole vypíše získanou zprávu úpavenou podle patternu, klient ji převezme a zaloguje vlastnimi prostředky\n*/\nvar LoggerService = /** @class */function () {\n  function LoggerService() {}\n  LoggerService.prototype.log = function (type, message) {\n    if (type < 0 || type > 4) {\n      console.log(\"Error happened, when tried to log message: \" + message + \", with type of logging: #\" + type);\n      return;\n    }\n    var date = new Date();\n    var toPrint = \"%d{\".concat(date.getHours, \":\").concat(date.getMinutes, \":\").concat(date.getSeconds, \".\").concat(date.getMilliseconds, \"} [frontend] \").concat(_enums_logLevels_enum_js__WEBPACK_IMPORTED_MODULE_0__.LogLevels[type], \": \").concat(message);\n    console.log(toPrint);\n    // App.emitClient(Events.LOG, [toPrint])\n  };\n  return LoggerService;\n}();\n\n\n//# sourceURL=webpack://html/./build/logger/logger.service.js?");

/***/ }),

/***/ "./build/messenger/User.js":
/*!*********************************!*\
  !*** ./build/messenger/User.js ***!
  \*********************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

eval("__webpack_require__.r(__webpack_exports__);\n/* harmony export */ __webpack_require__.d(__webpack_exports__, {\n/* harmony export */   User: () => (/* binding */ User)\n/* harmony export */ });\nvar User = /** @class */function () {\n  function User() {}\n  User.getUser = function () {\n    if (!User.user) {\n      User.user = new User();\n    }\n    return User.user;\n  };\n  User.prototype.getID = function () {\n    return this.id;\n  };\n  User.prototype.getUserName = function () {\n    return this.userName;\n  };\n  User.prototype.getFirstName = function () {\n    return this.firstName;\n  };\n  User.prototype.getLastName = function () {\n    return this.lastName;\n  };\n  User.prototype.getColor = function () {\n    return this.color;\n  };\n  User.prototype.setID = function (id) {\n    this.id = id;\n  };\n  User.prototype.setUserName = function (userName) {\n    this.userName = userName;\n  };\n  User.prototype.setFirstName = function (firstName) {\n    this.firstName = firstName;\n  };\n  User.prototype.setLastName = function (lastName) {\n    this.lastName = lastName;\n  };\n  User.prototype.setColor = function (color) {\n    this.color = color;\n  };\n  return User;\n}();\n\n\n//# sourceURL=webpack://html/./build/messenger/User.js?");

/***/ }),

/***/ "./build/messenger/messenger.controller.js":
/*!*************************************************!*\
  !*** ./build/messenger/messenger.controller.js ***!
  \*************************************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

eval("__webpack_require__.r(__webpack_exports__);\n/* harmony export */ __webpack_require__.d(__webpack_exports__, {\n/* harmony export */   MessengerController: () => (/* binding */ MessengerController)\n/* harmony export */ });\n/* harmony import */ var _App_js__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ../App.js */ \"./build/App.js\");\n/* harmony import */ var _enums_Events_enum_js__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ../enums/Events.enum.js */ \"./build/enums/Events.enum.js\");\n\n\nvar MessengerController = /** @class */function () {\n  function MessengerController(messengerSerice) {\n    var _this = this;\n    console.log(1);\n    this.messengerService = messengerSerice;\n    _App_js__WEBPACK_IMPORTED_MODULE_0__.App.on(_enums_Events_enum_js__WEBPACK_IMPORTED_MODULE_1__.Events.SETID, function (id) {\n      _this.messengerService.fetchData(id);\n    });\n    _App_js__WEBPACK_IMPORTED_MODULE_0__.App.onClient(_enums_Events_enum_js__WEBPACK_IMPORTED_MODULE_1__.Events.FETCHNAME, function (message) {\n      _this.messengerService.setFullName(message);\n    });\n    _App_js__WEBPACK_IMPORTED_MODULE_0__.App.onClient(_enums_Events_enum_js__WEBPACK_IMPORTED_MODULE_1__.Events.FETCHUSERS, function (message) {\n      _this.messengerService.showUsers(message);\n    });\n    _App_js__WEBPACK_IMPORTED_MODULE_0__.App.onClient(_enums_Events_enum_js__WEBPACK_IMPORTED_MODULE_1__.Events.FETCHCOLOR, function (messsage) {\n      _this.messengerService.setColor(messsage);\n    });\n    _App_js__WEBPACK_IMPORTED_MODULE_0__.App.onClient(_enums_Events_enum_js__WEBPACK_IMPORTED_MODULE_1__.Events.FETCHMESSAGES, function (message) {\n      _this.messengerService.showDialogues(message);\n    });\n  }\n  return MessengerController;\n}();\n\n\n//# sourceURL=webpack://html/./build/messenger/messenger.controller.js?");

/***/ }),

/***/ "./build/messenger/messenger.module.js":
/*!*********************************************!*\
  !*** ./build/messenger/messenger.module.js ***!
  \*********************************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

eval("__webpack_require__.r(__webpack_exports__);\n/* harmony export */ __webpack_require__.d(__webpack_exports__, {\n/* harmony export */   MessengerModule: () => (/* binding */ MessengerModule)\n/* harmony export */ });\n/* harmony import */ var _messenger_controller_js__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./messenger.controller.js */ \"./build/messenger/messenger.controller.js\");\n/* harmony import */ var _messenger_service_js__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./messenger.service.js */ \"./build/messenger/messenger.service.js\");\n\n\nvar MessengerModule = /** @class */function () {\n  function MessengerModule() {\n    var messengerService = new _messenger_service_js__WEBPACK_IMPORTED_MODULE_1__.MessengerService();\n    new _messenger_controller_js__WEBPACK_IMPORTED_MODULE_0__.MessengerController(messengerService);\n  }\n  return MessengerModule;\n}();\n\n\n//# sourceURL=webpack://html/./build/messenger/messenger.module.js?");

/***/ }),

/***/ "./build/messenger/messenger.service.js":
/*!**********************************************!*\
  !*** ./build/messenger/messenger.service.js ***!
  \**********************************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

eval("__webpack_require__.r(__webpack_exports__);\n/* harmony export */ __webpack_require__.d(__webpack_exports__, {\n/* harmony export */   MessengerService: () => (/* binding */ MessengerService)\n/* harmony export */ });\n/* harmony import */ var _App_js__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ../App.js */ \"./build/App.js\");\n/* harmony import */ var _enums_Events_enum_js__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ../enums/Events.enum.js */ \"./build/enums/Events.enum.js\");\n/* harmony import */ var _utils_log_js__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ../utils/log.js */ \"./build/utils/log.js\");\n/* harmony import */ var _User_js__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ./User.js */ \"./build/messenger/User.js\");\n/* harmony import */ var _messenger_view_js__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ./messenger.view.js */ \"./build/messenger/messenger.view.js\");\n\n\n\n\n\nvar MessengerService = /** @class */function () {\n  function MessengerService() {\n    this.view = new _messenger_view_js__WEBPACK_IMPORTED_MODULE_4__.MessengerView();\n    this.setListeners();\n  }\n  MessengerService.prototype.setListeners = function () {\n    $(\"#user\").keyup(function () {\n      var val = $(\"#user\").val();\n      $(\".user-list\").empty();\n      _App_js__WEBPACK_IMPORTED_MODULE_0__.App.emitClient(_enums_Events_enum_js__WEBPACK_IMPORTED_MODULE_1__.Events.FETCHUSERS, [val]);\n    });\n  };\n  MessengerService.prototype.fetchData = function (id) {\n    _User_js__WEBPACK_IMPORTED_MODULE_3__.User.getUser().setID(id);\n    _App_js__WEBPACK_IMPORTED_MODULE_0__.App.emitClient(_enums_Events_enum_js__WEBPACK_IMPORTED_MODULE_1__.Events.FETCHNAME, [id]);\n    _App_js__WEBPACK_IMPORTED_MODULE_0__.App.emitClient(_enums_Events_enum_js__WEBPACK_IMPORTED_MODULE_1__.Events.FETCHCOLOR, [id]);\n  };\n  MessengerService.prototype.setFullName = function (message) {\n    var firstName;\n    var lastName;\n    var user = _User_js__WEBPACK_IMPORTED_MODULE_3__.User.getUser();\n    if (message.length < 3) {\n      firstName = \"Unknown\";\n      lastName = \"Unknown\";\n    } else {\n      firstName = message[1];\n      lastName = message[2];\n    }\n    user.setFirstName(firstName);\n    user.setLastName(lastName);\n    $(\"#firstLetter\").text(firstName.charAt(0).toUpperCase());\n  };\n  MessengerService.prototype.showUsers = function (message) {\n    (0,_utils_log_js__WEBPACK_IMPORTED_MODULE_2__.log)(message.toString());\n    var _loop_1 = function _loop_1(i) {\n      var name_1 = message[i];\n      var surname = message[i + 1];\n      var id = message[i + 2];\n      var box = this_1.view.getUserBox(name_1, surname);\n      $(box).click(function () {\n        // @ts-ignore\n        window.javaConnector.goToDialogue(id);\n      });\n      (0,_utils_log_js__WEBPACK_IMPORTED_MODULE_2__.log)($(box).text());\n      $(\".user-list\").append(box);\n    };\n    var this_1 = this;\n    for (var i = 1; i < message.length; i += 3) {\n      _loop_1(i);\n    }\n  };\n  MessengerService.prototype.setColor = function (message) {\n    var color;\n    if (message.length < 2) {\n      color = \"F2C4DE\";\n    } else {\n      color = message[1];\n    }\n    var hex = \"#\" + color.toLowerCase();\n    _User_js__WEBPACK_IMPORTED_MODULE_3__.User.getUser().setColor(hex);\n    (0,_utils_log_js__WEBPACK_IMPORTED_MODULE_2__.log)(hex);\n    $(\"#avatar\").css(\"background-color\", hex);\n  };\n  MessengerService.prototype.showDialogues = function (message) {};\n  return MessengerService;\n}();\n\n\n//# sourceURL=webpack://html/./build/messenger/messenger.service.js?");

/***/ }),

/***/ "./build/messenger/messenger.view.js":
/*!*******************************************!*\
  !*** ./build/messenger/messenger.view.js ***!
  \*******************************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

eval("__webpack_require__.r(__webpack_exports__);\n/* harmony export */ __webpack_require__.d(__webpack_exports__, {\n/* harmony export */   MessengerView: () => (/* binding */ MessengerView)\n/* harmony export */ });\nvar MessengerView = /** @class */function () {\n  function MessengerView() {}\n  MessengerView.prototype.getUserBox = function (name, surname) {\n    var box = document.createElement(\"div\");\n    $(box).addClass(\"user-option\").text(name + \" \" + surname);\n    return box;\n  };\n  return MessengerView;\n}();\n\n\n//# sourceURL=webpack://html/./build/messenger/messenger.view.js?");

/***/ }),

/***/ "./build/notifications/notifications.controller.js":
/*!*********************************************************!*\
  !*** ./build/notifications/notifications.controller.js ***!
  \*********************************************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

eval("__webpack_require__.r(__webpack_exports__);\n/* harmony export */ __webpack_require__.d(__webpack_exports__, {\n/* harmony export */   NotificationsController: () => (/* binding */ NotificationsController)\n/* harmony export */ });\n/* harmony import */ var _App_js__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ../App.js */ \"./build/App.js\");\n/* harmony import */ var _enums_Events_enum_js__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ../enums/Events.enum.js */ \"./build/enums/Events.enum.js\");\n\n\n/*\n    Třída NotificationsController - je třída správce notifikací, která se zabývá operováním eventů. Nejdřív zaregestruje všechny eventy do aplikace, pak při vyvolání některého z těch eventu, spustí příslušnou metodu ze služby\n*/\nvar NotificationsController = /** @class */function () {\n  function NotificationsController(notificationsService) {\n    var _this = this;\n    this.notificationsService = notificationsService;\n    // registrace eventu notifikace\n    _App_js__WEBPACK_IMPORTED_MODULE_0__.App.on(_enums_Events_enum_js__WEBPACK_IMPORTED_MODULE_1__.Events.NOTIFY, function (message) {\n      _this.notificationsService.show(message);\n    });\n    _App_js__WEBPACK_IMPORTED_MODULE_0__.App.onClient(_enums_Events_enum_js__WEBPACK_IMPORTED_MODULE_1__.Events.ERROR, function (message) {\n      _this.notificationsService.showError(message);\n    });\n  }\n  return NotificationsController;\n}();\n\n\n//# sourceURL=webpack://html/./build/notifications/notifications.controller.js?");

/***/ }),

/***/ "./build/notifications/notifications.module.js":
/*!*****************************************************!*\
  !*** ./build/notifications/notifications.module.js ***!
  \*****************************************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

eval("__webpack_require__.r(__webpack_exports__);\n/* harmony export */ __webpack_require__.d(__webpack_exports__, {\n/* harmony export */   NotificationsModule: () => (/* binding */ NotificationsModule)\n/* harmony export */ });\n/* harmony import */ var _notifications_controller_js__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./notifications.controller.js */ \"./build/notifications/notifications.controller.js\");\n/* harmony import */ var _notifications_service_js__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./notifications.service.js */ \"./build/notifications/notifications.service.js\");\n\n\n/*\n    Třída LoginModule - je třída modulu zpracování notifikací, která se zabývá vytvařením služby a správce notifikací\n*/\nvar NotificationsModule = /** @class */function () {\n  function NotificationsModule() {\n    var notificationsService = new _notifications_service_js__WEBPACK_IMPORTED_MODULE_1__.NotificationsService();\n    new _notifications_controller_js__WEBPACK_IMPORTED_MODULE_0__.NotificationsController(notificationsService);\n    //log(LogLevels.INFO, \"Notification module has been initialized successfully\")\n  }\n  return NotificationsModule;\n}();\n\n\n//# sourceURL=webpack://html/./build/notifications/notifications.module.js?");

/***/ }),

/***/ "./build/notifications/notifications.service.js":
/*!******************************************************!*\
  !*** ./build/notifications/notifications.service.js ***!
  \******************************************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

eval("__webpack_require__.r(__webpack_exports__);\n/* harmony export */ __webpack_require__.d(__webpack_exports__, {\n/* harmony export */   NotificationsService: () => (/* binding */ NotificationsService)\n/* harmony export */ });\n/* harmony import */ var _utils_secToMs_js__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ../utils/secToMs.js */ \"./build/utils/secToMs.js\");\n\n/*\n    Třída NotificationsService - je třída služby notifikací, která se zabývá zpracováním logiky vypísování zpráv na obrazovku a jejich pak mazáním\n*/\nvar NotificationsService = /** @class */function () {\n  function NotificationsService() {}\n  // metoda která přidá zprávu na obrazovku a po 10 sekudnách ji smaže\n  NotificationsService.prototype.show = function (message) {\n    var error = document.createElement(\"div\");\n    $(error).hide();\n    $(error).addClass(\"error\");\n    var errorText = document.createElement(\"p\");\n    $(errorText).addClass(\"error-message\").text(message);\n    $(error).append(errorText);\n    $(error).click(function () {\n      $(error).remove();\n    });\n    $(\".error-pane\").append(error);\n    var unshow = function unshow(elem) {\n      $(elem).fadeOut((0,_utils_secToMs_js__WEBPACK_IMPORTED_MODULE_0__.secToMs)(1), function () {\n        $(elem).remove();\n      });\n    };\n    var show = function show(elem) {\n      $(error).fadeIn((0,_utils_secToMs_js__WEBPACK_IMPORTED_MODULE_0__.secToMs)(1), function () {\n        setTimeout(function () {\n          unshow(elem);\n        }, (0,_utils_secToMs_js__WEBPACK_IMPORTED_MODULE_0__.secToMs)(10));\n      });\n    };\n    show(error);\n    //log(LogLevels.INFO, \"Notification has been shown. Message: \" + message)\n  };\n  NotificationsService.prototype.showError = function (errorData) {\n    var message = \"\";\n    for (var i = 1; i < errorData.length; i++) {\n      message += errorData[i] + \" \";\n    }\n    this.show(message);\n  };\n  NotificationsService.prototype.showDialogues = function (message) {};\n  return NotificationsService;\n}();\n\n\n//# sourceURL=webpack://html/./build/notifications/notifications.service.js?");

/***/ }),

/***/ "./build/register/register.controller.js":
/*!***********************************************!*\
  !*** ./build/register/register.controller.js ***!
  \***********************************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

eval("__webpack_require__.r(__webpack_exports__);\n/* harmony export */ __webpack_require__.d(__webpack_exports__, {\n/* harmony export */   RegisterController: () => (/* binding */ RegisterController)\n/* harmony export */ });\n/* harmony import */ var _App_js__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ../App.js */ \"./build/App.js\");\n/* harmony import */ var _enums_Events_enum_js__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ../enums/Events.enum.js */ \"./build/enums/Events.enum.js\");\n\n\nvar RegisterController = /** @class */function () {\n  function RegisterController(registerService) {\n    var _this = this;\n    this.registerService = registerService;\n    _App_js__WEBPACK_IMPORTED_MODULE_0__.App.onClient(_enums_Events_enum_js__WEBPACK_IMPORTED_MODULE_1__.Events.SUCCESSREGISTER, function (message) {\n      _this.registerService.moveToMain(message);\n    });\n    _App_js__WEBPACK_IMPORTED_MODULE_0__.App.onClient(_enums_Events_enum_js__WEBPACK_IMPORTED_MODULE_1__.Events.SUCCESSLOGIN, function (message) {\n      _this.registerService.moveToMain(message);\n    });\n  }\n  return RegisterController;\n}();\n\n\n//# sourceURL=webpack://html/./build/register/register.controller.js?");

/***/ }),

/***/ "./build/register/register.module.js":
/*!*******************************************!*\
  !*** ./build/register/register.module.js ***!
  \*******************************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

eval("__webpack_require__.r(__webpack_exports__);\n/* harmony export */ __webpack_require__.d(__webpack_exports__, {\n/* harmony export */   RegisterModule: () => (/* binding */ RegisterModule)\n/* harmony export */ });\n/* harmony import */ var _register_controller__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./register.controller */ \"./build/register/register.controller.js\");\n/* harmony import */ var _register_service_js__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./register.service.js */ \"./build/register/register.service.js\");\n\n\nvar RegisterModule = /** @class */function () {\n  function RegisterModule() {\n    var registerService = new _register_service_js__WEBPACK_IMPORTED_MODULE_1__.RegisterService();\n    new _register_controller__WEBPACK_IMPORTED_MODULE_0__.RegisterController(registerService);\n  }\n  return RegisterModule;\n}();\n\n\n//# sourceURL=webpack://html/./build/register/register.module.js?");

/***/ }),

/***/ "./build/register/register.service.js":
/*!********************************************!*\
  !*** ./build/register/register.service.js ***!
  \********************************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

eval("__webpack_require__.r(__webpack_exports__);\n/* harmony export */ __webpack_require__.d(__webpack_exports__, {\n/* harmony export */   RegisterService: () => (/* binding */ RegisterService)\n/* harmony export */ });\n/* harmony import */ var _App_js__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ../App.js */ \"./build/App.js\");\n/* harmony import */ var _enums_Events_enum_js__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ../enums/Events.enum.js */ \"./build/enums/Events.enum.js\");\n\n\nvar RegisterService = /** @class */function () {\n  function RegisterService() {\n    this.startListener = function () {\n      $(\"#login\").submit(function () {\n        var name = $(\"#userName\").val();\n        var password = $(\"#password\").val();\n        if (!name || !password) return false;\n        _App_js__WEBPACK_IMPORTED_MODULE_0__.App.emitClient(_enums_Events_enum_js__WEBPACK_IMPORTED_MODULE_1__.Events.LOGIN, [name, password]);\n        return false;\n      });\n      $(\"#register\").submit(function () {\n        var name = $(\"#userName\").val();\n        var firstName = $(\"#firstName\").val();\n        var lastName = $(\"#lastName\").val();\n        var password = $(\"#password\").val();\n        var repeatPassword = $(\"#passwordRepeat\").val();\n        if (!name || !password || !firstName || !lastName || !repeatPassword) {\n          _App_js__WEBPACK_IMPORTED_MODULE_0__.App.emit(_enums_Events_enum_js__WEBPACK_IMPORTED_MODULE_1__.Events.NOTIFY, \"You didn't fill the form properly\");\n          return false;\n        }\n        ;\n        if (password != repeatPassword) {\n          _App_js__WEBPACK_IMPORTED_MODULE_0__.App.emit(_enums_Events_enum_js__WEBPACK_IMPORTED_MODULE_1__.Events.NOTIFY, \"Passwords don't match\");\n          return false;\n        }\n        if (password.length < 6) {\n          _App_js__WEBPACK_IMPORTED_MODULE_0__.App.emit(_enums_Events_enum_js__WEBPACK_IMPORTED_MODULE_1__.Events.NOTIFY, \"Password should contain at least 6 characters\");\n          return false;\n        }\n        if (name.length < 4) {\n          _App_js__WEBPACK_IMPORTED_MODULE_0__.App.emit(_enums_Events_enum_js__WEBPACK_IMPORTED_MODULE_1__.Events.NOTIFY, \"User name should contain at least 4 characters\");\n          return false;\n        }\n        _App_js__WEBPACK_IMPORTED_MODULE_0__.App.emitClient(_enums_Events_enum_js__WEBPACK_IMPORTED_MODULE_1__.Events.REGISTER, [name, firstName, lastName, password]);\n        return false;\n      });\n    };\n    this.startListener();\n  }\n  RegisterService.prototype.moveToMain = function (message) {\n    if (message.length < 2) {\n      _App_js__WEBPACK_IMPORTED_MODULE_0__.App.emit(_enums_Events_enum_js__WEBPACK_IMPORTED_MODULE_1__.Events.NOTIFY, \"Unknown error happened\");\n      return;\n    }\n    _App_js__WEBPACK_IMPORTED_MODULE_0__.App.id = message[1];\n    // @ts-ignore\n    window.javaConnector.setID(message[1]);\n    // @ts-ignore\n    window.javaConnector.switchPage(\"main\", null);\n  };\n  return RegisterService;\n}();\n\n\n//# sourceURL=webpack://html/./build/register/register.service.js?");

/***/ }),

/***/ "./build/utils/log.js":
/*!****************************!*\
  !*** ./build/utils/log.js ***!
  \****************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

eval("__webpack_require__.r(__webpack_exports__);\n/* harmony export */ __webpack_require__.d(__webpack_exports__, {\n/* harmony export */   log: () => (/* binding */ log)\n/* harmony export */ });\n/* harmony import */ var _App_js__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ../App.js */ \"./build/App.js\");\n/* harmony import */ var _enums_Events_enum_js__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ../enums/Events.enum.js */ \"./build/enums/Events.enum.js\");\n\n\n// metoda pro posílání požadavku o logování do loggeru. Jenom pro zjednodušení syntaxe\nvar log = function log(message) {\n  _App_js__WEBPACK_IMPORTED_MODULE_0__.App.emitClient(_enums_Events_enum_js__WEBPACK_IMPORTED_MODULE_1__.Events.LOG, [message]);\n  console.log(message);\n};\n\n//# sourceURL=webpack://html/./build/utils/log.js?");

/***/ }),

/***/ "./build/utils/secToMs.js":
/*!********************************!*\
  !*** ./build/utils/secToMs.js ***!
  \********************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

eval("__webpack_require__.r(__webpack_exports__);\n/* harmony export */ __webpack_require__.d(__webpack_exports__, {\n/* harmony export */   secToMs: () => (/* binding */ secToMs)\n/* harmony export */ });\nvar secToMs = function secToMs(sec) {\n  return sec * 1000;\n};\n\n//# sourceURL=webpack://html/./build/utils/secToMs.js?");

/***/ })

/******/ 	});
/************************************************************************/
/******/ 	// The module cache
/******/ 	var __webpack_module_cache__ = {};
/******/ 	
/******/ 	// The require function
/******/ 	function __webpack_require__(moduleId) {
/******/ 		// Check if module is in cache
/******/ 		var cachedModule = __webpack_module_cache__[moduleId];
/******/ 		if (cachedModule !== undefined) {
/******/ 			return cachedModule.exports;
/******/ 		}
/******/ 		// Create a new module (and put it into the cache)
/******/ 		var module = __webpack_module_cache__[moduleId] = {
/******/ 			// no module.id needed
/******/ 			// no module.loaded needed
/******/ 			exports: {}
/******/ 		};
/******/ 	
/******/ 		// Execute the module function
/******/ 		__webpack_modules__[moduleId](module, module.exports, __webpack_require__);
/******/ 	
/******/ 		// Return the exports of the module
/******/ 		return module.exports;
/******/ 	}
/******/ 	
/************************************************************************/
/******/ 	/* webpack/runtime/define property getters */
/******/ 	(() => {
/******/ 		// define getter functions for harmony exports
/******/ 		__webpack_require__.d = (exports, definition) => {
/******/ 			for(var key in definition) {
/******/ 				if(__webpack_require__.o(definition, key) && !__webpack_require__.o(exports, key)) {
/******/ 					Object.defineProperty(exports, key, { enumerable: true, get: definition[key] });
/******/ 				}
/******/ 			}
/******/ 		};
/******/ 	})();
/******/ 	
/******/ 	/* webpack/runtime/hasOwnProperty shorthand */
/******/ 	(() => {
/******/ 		__webpack_require__.o = (obj, prop) => (Object.prototype.hasOwnProperty.call(obj, prop))
/******/ 	})();
/******/ 	
/******/ 	/* webpack/runtime/make namespace object */
/******/ 	(() => {
/******/ 		// define __esModule on exports
/******/ 		__webpack_require__.r = (exports) => {
/******/ 			if(typeof Symbol !== 'undefined' && Symbol.toStringTag) {
/******/ 				Object.defineProperty(exports, Symbol.toStringTag, { value: 'Module' });
/******/ 			}
/******/ 			Object.defineProperty(exports, '__esModule', { value: true });
/******/ 		};
/******/ 	})();
/******/ 	
/************************************************************************/
/******/ 	
/******/ 	// startup
/******/ 	// Load entry module and return exports
/******/ 	// This entry module can't be inlined because the eval devtool is used.
/******/ 	var __webpack_exports__ = __webpack_require__("./build/index.js");
/******/ 	
/******/ })()
;