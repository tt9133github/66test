/**
 * log4js 日志输出配置文件
 * @type {exports}
 */
var log4js = require('log4js');
var log4js_config = require("./log4js.json");

// logger configure
log4js.configure(log4js_config);

module.exports = log4js;