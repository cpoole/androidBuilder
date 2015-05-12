
var UserRouter = function(express, passport, loginChecker,mongoose){
  var User = require('../models/userModel')(mongoose).User;
  var router = new express.Router();
  var Bcrypt = require('bcrypt');
  
  /* GET users listing. */
  router.get('/', function(req, res) {
    res.send('respond with a resource');
  });
  
  router.post('/signup', passport.authenticate('local-signup',{
    successRedirect: '/',
    failureRedirect: '/pages/signup',
    failureFlash: true
  }));
  
  router.post('/createUser', function(req, res){
  
      var salt = Bcrypt.genSaltSync(10);
      var hash = Bcrypt.hashSync(req.body.password, salt);
      newUser = new User({
        email: req.body.email,
        password: hash
      });
      
      newUser.save(function(err){
        if(err){
          res.send('User va')
        }
        console.log('User created');
        
      });
      
      res.send('post success');
  });
  
  router.post('/login', function(req, res, next){
    passport.authenticate('local-login', function(err, user, info){
      if(err){
        return next(err);
      }
      if(!user){
        return res.send(401, {success: false, message: 'authentication failed'});
      } 
      return res.send({success:true, message:'authentication succeeded'});
    })(req, res, next);
  });
  
  return router;

};

module.exports = UserRouter;
