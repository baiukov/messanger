var User = /** @class */ (function () {
    function User() {
    }
    User.getUser = function () {
        if (!User.user) {
            User.user = new User();
        }
        return User.user;
    };
    User.prototype.getID = function () { return this.id; };
    User.prototype.getUserName = function () { return this.userName; };
    User.prototype.getFirstName = function () { return this.firstName; };
    User.prototype.getLastName = function () { return this.lastName; };
    User.prototype.setID = function (id) { this.id = id; };
    User.prototype.setUserName = function (userName) { this.userName = userName; };
    User.prototype.setFirstName = function (firstName) { this.firstName = firstName; };
    User.prototype.setLastName = function (lastName) { this.lastName = lastName; };
    return User;
}());
export { User };
