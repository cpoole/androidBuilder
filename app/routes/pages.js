var PageRouter = function(express, passport, loginChecker){
  var router = new express.Router();
  /* GET profile page. */
  router.get('/profile', loginChecker, function(req, res){
    var user = req.user;
      if(!user){
        console.log("not logged in");
        return res.render('pages/index');
      }
      return res.render('pages/profile');
    });

  
  return router;

};


module.exports = PageRouter;
