var bodyParser = require('body-parser'); 
var cookieParser = require('cookie-parser'); 
var morgan = require('morgan'); 
var session = require('express-session'); 
var express = require('express'); 
var sys = require('sys'); 
var passport = require('passport');
var favicon = require('serve-favicon');
var path = require('path');
var flash = require('connect-flash');
var mongoose = require('mongoose');

var env = process.env.NODE_ENV || 'development';
var root_path = process.cwd();

var app = express();

//==============================CONFIGURATION========================================
mongoose.connect('mongodb://localhost/androidBuilder');

//load all schemas to be referenced by mongoose
require('./app/models/restaurantModel.js')(mongoose);
require('./app/models/userModel.js')(mongoose);
require('./app/models/menuModel.js')(mongoose);
require('./app/models/hoursModel.js')(mongoose);
require('./app/models/accountModel.js')(mongoose);

//configure passport
passport = require('./app/config/passport')(passport, mongoose);

// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'ejs');

//app.use(favicon(__dirname + '/public/favicon.ico'));
app.use(morgan('combined'));
app.use(cookieParser());
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: true}));
app.use(express.static(path.join(__dirname, 'public')));
app.use(session({secret:'lazercatz', saveUninitialized: true, resave: true}));
app.use(passport.initialize());
app.use(passport.session());
app.use(flash());


var loginChecker = require('./app/helpers/authentication.js');
var restaurantRoutes = require('./app/routes/restaurant')(express, passport, loginChecker, mongoose);
var indexRoutes = require('./app/routes/index')(express, passport, loginChecker, mongoose);
var pageRoutes = require('./app/routes/pages')(express, passport, loginChecker, mongoose);
var userRoutes = require('./app/routes/users')(express, passport, loginChecker, mongoose);

app.use('/', indexRoutes);
app.use('/restaurant', restaurantRoutes);
app.use('/pages', pageRoutes);
app.use('/users', userRoutes);


/// catch 404 and forward to error handler
app.use(function(req, res, next) {
    var err = new Error('Not Found');
    err.status = 404;
    next(err);
});

/// error handlers

// development error handler
// will print stacktrace
if (app.get('env') === 'development') {
    app.use(function(err, req, res, next) {
        res.status(err.status || 500);
        res.render('pages/errors/error', {
            message: err.message,
            error: err.stack
        });
    });
}

// production error handler
// no stacktraces leaked to user
if (app.get('env') === 'production') {
    app.use(function(err, req, res, next) {
        res.status(err.status || 500);
        res.render('error', {
            message: err.message,
            error: err
        });
    });
}

module.exports = app;
