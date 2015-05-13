var homeApp = angular.module('homeApp',['ngMaterial']);

homeApp.controller('toolSideCtrl', function($scope,$mdSidenav,$http) {
	$scope.toggleSide = function(){
		console.log("clicked")
		$mdSidenav('left').toggle();
	};

  $http.get('/users/getApps')
    .success(function(res){

    })
    .error(function(res){

    })
	
	
});