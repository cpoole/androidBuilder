var PageRouter = function(express, passport, loginChecker){
  var router = new express.Router();
  /* GET profile page. */
  router.get('/profile', loginChecker, function(req, res){
      return res.render('pages/profile');
    });
    
    router.get('/createEditApp', loginChecker, function(req, res){
      return res.render('pages/createEditApp');
    });

  
  return router;

};


module.exports = PageRouter;
