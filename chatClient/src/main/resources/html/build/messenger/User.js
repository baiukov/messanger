var User = /** @class */ (function () {
    function User() {
    }
    User.prototype.getID = function () { return this.id; };
    User.prototype.getUserName = function () { return this.userName; };
    User.prototype.getFirstName = function () { return this.firstName; };
    User.prototype.getLastName = function () { return this.lastName; };
    User.prototype.getColor = function () { return this.color; };
    User.prototype.setID = function (id) { this.id = id; };
    User.prototype.setUserName = function (userName) { this.userName = userName; };
    User.prototype.setFirstName = function (firstName) { this.firstName = firstName; };
    User.prototype.setLastName = function (lastName) { this.lastName = lastName; };
    User.prototype.setColor = function (color) { this.color = color; };
    return User;
}());
export { User };
