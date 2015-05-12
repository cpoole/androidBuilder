var IndexRouter = function(express, passport, loginChecker){
  var router = new express.Router();
  /* GET home page. */
  router.get('/', function(req, res) {
    res.render('pages/index', { title: 'Express' });
  });
  return router;

};

module.exports = IndexRouter;
