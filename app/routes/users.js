
var UserRouter = function(express, passport, loginChecker,mongoose){
  var User = mongoose.model('User');
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
      var newUser = new User({
        email: req.body.email,
        password: hash
      });
      
      newUser.save(function(err){
        if(err){
          throw err;
        }
        console.log('User created');
        
      });
      
      res.send('post success');
  });
  
  router.post('/login', function(req, res, next){
    passport.authenticate('local-login', function(err, user, info){
      console.log(user)
      if(err){
        return next(err);
      }
      if(!user){
        return res.send(401, {success: false, message: 'authentication failed'});
      } 
      req.logIn(user, function(err){
        if(err){
          return next(err);
        }
        return res.send({success:true, message:'authentication succeeded'});
      });
    })(req, res, next);
  });
  
  return router;

};

module.exports = UserRouter;
