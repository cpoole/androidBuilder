var homeApp = angular.module('homeApp',['ngMaterial']);

homeApp.controller('toolSideCtrl', function($scope,$mdSidenav,$http) {
  $scope.toggleSide = function(){
    console.log("clicked")
    $mdSidenav('left').toggle();
  };

  $scope.submitRestaurant = function(){
    jsonData = {};
    jsonData.title = $scope.title;
    jsonData.about = $scope.about;
    jsonData.address = $scope.address;
    jsonData.phone = $scope.phone;

    $http.post('/restaurant/postCreateRestaurant', jsonData)
      .success(function(response){
        window.location='/pages/profile'
      })
      .error(function(response){

      })

  }
  
  
});