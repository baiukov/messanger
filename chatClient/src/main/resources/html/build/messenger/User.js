var User = /** @class */ (function () {
    function User() {
        var _this = this;
        this.getID = function () { return _this.id; };
        this.getUserName = function () { return _this.userName; };
        this.getFirstName = function () { return _this.firstName; };
        this.getLastName = function () { return _this.lastName; };
        this.getColor = function () { return _this.color; };
        this.setID = function (id) { _this.id = id; };
        this.setUserName = function (userName) { _this.userName = userName; };
        this.setFirstName = function (firstName) { _this.firstName = firstName; };
        this.setLastName = function (lastName) { _this.lastName = lastName; };
        this.setColor = function (color) { _this.color = color; };
    }
    return User;
}());
export { User };
