var LoginChecker = function(req, res, next){
	if(req.isAuthenticated()){
		return next();
	}
	res.status(401);
	res.render('pages/index');
	//res.redirect(401,'/');
};

module.exports = LoginChecker;
