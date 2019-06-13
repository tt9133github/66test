
var fs = require('fs');
var accessLogfile = fs.createWriteStream('logs/access.log',{flags:'a'});
var errorLogfile = fs.createWriteStream('logs/error.log',{flags:'a'});
//log4js 日志系统
var log4js = require('./log4js');
var access = log4js.getLogger('log_access');
var datelogger = log4js.getLogger('log_date');

var createError = require('http-errors');
var express = require('express');
var path = require('path');
var cookieParser = require('cookie-parser');
var logger = require('morgan');
var bodyParser = require('body-parser');
var multer = require('multer');

var indexRouter = require('./routes/index');
//var usersRouter = require('./routes/users');

var app = express();

// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'jade');

app.use(logger('combined',{stream:accessLogfile}));

//app.use(logger('dev'));
//app.use(express.json());
//app.use(express.urlencoded({ extended: false }));


app.use(bodyParser.json({limit:'50mb'}));
app.use(bodyParser.urlencoded({limit:'50mb',extended:true}));

app.use(cookieParser());


app.use(express.static(path.join(__dirname, 'public')));

app.enable('trust proxy');
app.get('trust proxy');

app.use(log4js.connectLogger(access,{
  level:log4js.levels.INFO,
    format:':remote-addr :method :url :status :response-time ms'
}));



app.use('/', indexRouter);
//app.use('/users', usersRouter);

// catch 404 and forward to error handler
app.use(function(req, res, next) {
  next(createError(404));
});

// error handler
app.use(function(err, req, res, next) {
  // set locals, only providing error in development
  res.locals.message = err.message;
  res.locals.error = req.app.get('env') === 'development' ? err : {};

  // render the error page
  res.status(err.status || 500);
  res.render('error');
});


if(app.get('env')=='development')
{
  app.use(function(err,req,res,next){
    var meta = '['+new Date() +']'+req.url+'\n';
    errorLogfile.write(meta+err.stack+'\n');
    next();
  })
}

if(app.get('env')=='production')
{
    app.use(function(err,req,res,next){
        var meta = '['+new Date() +']'+req.url+'\n';
        errorLogfile.write(meta+err.stack+'\n');
        next();
    })
}

datelogger.info("start web server");

module.exports = app;


process.on('uncaughtException',function (err) {
    var meta = '['+new Date()+']'+'\n';
    console.log(meta+err.stack);

})