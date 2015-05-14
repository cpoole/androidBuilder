var homeApp = angular.module('homeApp',['ngMaterial']);

homeApp.controller('toolSideCtrl', function($scope,$mdSidenav,$http) {
	$scope.toggleSide = function(){
		console.log("clicked")
		$mdSidenav('left').toggle();
	};

  $http.get('/users/getApps')
    .success(function(res){
      console.log(res);
      $scope.Restaurants = res.apps;
    
    })
    .error(function(res){

    })

  $scope.clickFunc = function(cardID){
    console.log("clicked")
    window.location = '/pages/createEditApp?id='+cardID;
    // $http.get('/pages/createEditApp', {
    //   params:{id : cardID}
    // })
    //   .success(function(res){
    //     window
    //   })
  }
	
	
});