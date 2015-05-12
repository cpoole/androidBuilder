var homeApp = angular.module('homeApp',['ngMaterial']);

homeApp.controller('toolSideCtrl', function($scope,$mdSidenav,$http) {
	$scope.toggleSide = function(){
		console.log("clicked")
		$mdSidenav('left').toggle();
	};
	
	
});