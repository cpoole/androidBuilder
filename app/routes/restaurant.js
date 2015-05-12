var RestaurantRouter = function(express, passport, loginChecker, mongoose){
	var router = new express.Router();
	var Restaurant = mongoose.model('Restaurant').Restaurant;
	var Account = mongoose.model('Account').Account;
	
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
		var newRestaurant = new Restaurant({
			title: req.body.title,
			address: req.body.address,
			about: req.body.about,
			phone: req.body.phone,
		});
		
		newRestaurant.save(function(err){
			if(err){
				throw err;
			}
			return res.send({success:true, message:"user was created"});
		});
	});
	
	router.get('/createRestaurant', loginChecker, function(req, res){
		var user = req.user;
	});
	
	return router;
	
};

module.exports = RestaurantRouter;