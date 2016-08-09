cordova.define("cordova-plugin-custom-browser-tabs.CustomBrowserTabs", function(require, exports, module) {
var exec = require('cordova/exec');

exports.open = function(arg0, arg1, success, error) {
    arg1 = arg1 || {};
    exec(success, error, "CustomBrowserTabs", "open", [arg0,arg1]);
};

});
