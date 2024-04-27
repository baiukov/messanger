var DialogueService = /** @class */ (function () {
    function DialogueService() {
        this.initListeners();
    }
    DialogueService.prototype.initListeners = function () {
        $(".back-button").click(function () {
            //@ts-ignore
            window.javaConnector.switchPage("main");
        });
    };
    DialogueService.prototype.setPartnerData = function (message) {
        if (message.length < 4)
            return;
        var name = message[1];
        var surname = message[2];
        var color = message[3];
        var hex = "#" + color.toLowerCase();
        var firstLetter = name.charAt(0).toUpperCase();
        $("#partnerName").text(name + " " + surname);
        $("#partnersAvatar").css("background-color", hex);
        $("#partnersFirstLetter").text(firstLetter);
    };
    return DialogueService;
}());
export { DialogueService };
