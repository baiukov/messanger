var App = /** @class */ (function () {
    function App() {
    }
    var _a;
    _a = App;
    App.events = {};
    App.on = function (eventName, event) {
        _a.events[eventName] = event;
    };
    App.emit = function (eventName, data) {
        Object.keys(_a.events).forEach(function (key) {
            if (key === eventName) {
                _a.events[key](data);
            }
        });
    };
    return App;
}());
export { App };
