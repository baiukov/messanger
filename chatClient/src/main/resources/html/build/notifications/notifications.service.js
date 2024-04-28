import { secToMs } from '../utils/secToMs.js';
/*
    Třída NotificationsService - je třída služby notifikací, která se zabývá zpracováním logiky vypísování zpráv na obrazovku a jejich pak mazáním
*/
var NotificationsService = /** @class */ (function () {
    function NotificationsService() {
    }
    // metoda která přidá zprávu na obrazovku a po 10 sekudnách ji smaže
    NotificationsService.prototype.show = function (message) {
        var error = document.createElement("div");
        $(error).hide();
        $(error).addClass("error");
        var errorText = document.createElement("p");
        $(errorText)
            .addClass("error-message")
            .text(message);
        $(error).append(errorText);
        $(error).click(function () { $(error).remove(); });
        $(".error-pane").append(error);
        var unshow = function (elem) {
            $(elem).fadeOut(secToMs(1), function () {
                $(elem).remove();
            });
        };
        var show = function (elem) {
            $(error).fadeIn(secToMs(1), function () {
                setTimeout(function () { unshow(elem); }, secToMs(10));
            });
        };
        show(error);
        //log(LogLevels.INFO, "Notification has been shown. Message: " + message)
    };
    NotificationsService.prototype.showError = function (errorData) {
        var message = "";
        for (var i = 1; i < errorData.length; i++) {
            message += errorData[i] + " ";
        }
        this.show(message);
    };
    NotificationsService.prototype.showDialogues = function (message) {
    };
    return NotificationsService;
}());
export { NotificationsService };
