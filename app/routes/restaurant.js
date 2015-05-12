var RestaurantRouter = function(express, passport, loginChecker, mongoose){
	var router = new express.Router();
	var Restaurant = require('../models/restaurantModel.js')(mongoose).Restaurant;
	var Account = require('../models/accountModel.js')(mongoose).Account;
	
	router.get('/getRestaurants', loginChecker, function(req, res){
		var user = req.user;
		if(!user){
			return res.send(401, {"success": false, "message":"authentication failed" });
		}
		Account.find({_users:user.id} ,function(account, err){
			if(err){
				throw err;
			}
			var restaurantArray = new Array();
			for(var i=0; i< account._restaurants.length; i++){
				Restaurant.findById(account._restaurants[i], function(resta, err){
					if(err){
						throw err;
					}
					
					restaurantArray.push(resta);
				});
			}
			return res.send({"success":true, "restaurants": restaurantArray});
		});	
	});
	
	router.post('/postCreateRestaurant', loginChecker, function(req, res){
		var user = req.user;
		if(!user){
			return res.send({success: false});
		}
		return res.send({success: true});
	});
	
	router.get('/createRestaurant', loginChecker, function(req, res){
		var user = req.user;
	});
	
	return router;
	
};

module.exports = RestaurantRouter;