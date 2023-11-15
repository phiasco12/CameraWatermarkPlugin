var exec = require('cordova/exec');

exports.addWatermark = function (success, error, options) {
    exec(success, error, 'CameraWatermarkPlugin', 'addWatermark', [options]);
};