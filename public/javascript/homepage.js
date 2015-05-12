var homeApp = angular.module('homeApp',['ngMaterial']);

homeApp.controller('toolSideCtrl', function($scope,$mdSidenav,$http) {
	$scope.toggleSide = function(){
		console.log("clicked")
		$mdSidenav('left').toggle();
	};
	
	$scope.signIn = function(){
		jsonData = {"email":$scope.email, "password":$scope.password};
		$http.post("/users/login",jsonData)
			.success(function(response){
				window.location='/pages/profile';
			})
			.error(function(response){
				
			})
	};
});