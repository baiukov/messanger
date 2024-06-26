/**
 *  Dostupné názvy eventů k registraci a vyvolání
 */
export var Events;
(function (Events) {
    Events["LOGIN"] = "LOGIN";
    Events["REGISTER"] = "REGISTER";
    Events["LOG"] = "LOG";
    Events["NOTIFY"] = "NOTIFY";
    Events["ERROR"] = "ERROR";
    Events["SUCCESSLOGIN"] = "SUCCESSLOGIN";
    Events["SUCCESSREGISTER"] = "SUCCESSREGISTER";
    Events["SETID"] = "SETID";
    Events["FETCHNAME"] = "FETCHNAME";
    Events["FETCHUSERS"] = "FETCHUSERS";
    Events["FETCHCOLOR"] = "FETCHCOLOR";
    Events["SETDIALOGUEWITH"] = "SETDIALOGUEWITH";
    Events["FETCHPARTNERDATA"] = "FETCHPARTNERDATA";
    Events["SEND"] = "SEND";
    Events["FETCHMESSAGES"] = "FETCHMESSAGES";
    Events["FETCHDIALOGUES"] = "FETCHDIALOGUES";
    Events["READMESSAGES"] = "READMESSAGES";
    Events["PIN"] = "PIN";
    Events["UNPIN"] = "UNPIN";
    Events["BLOCK"] = "BLOCK";
    Events["UPDATE"] = "UPDATE";
})(Events || (Events = {}));
