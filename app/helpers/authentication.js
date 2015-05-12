var LoginChecker = function(req, res, next){
	if(req.isAuthenticated()){
		
		console.log("route is authenticated");
		return next();
	}
	console.log('///////////////////////////////////////////')
	console.log(req);
	console.log("route isnt authenticated");
	res.redirect('/');
}

module.exports = LoginChecker;
