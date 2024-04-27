var MessengerView = /** @class */ (function () {
    function MessengerView() {
    }
    MessengerView.prototype.getUserBox = function (name, surname) {
        var box = document.createElement("div");
        $(box).addClass("user-option").text(name + " " + surname);
        return box;
    };
    return MessengerView;
}());
export { MessengerView };
