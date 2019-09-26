
angular.module('homeCooking').controller('NewRestaurantController', function ($scope, $location, locationParser, flash, RestaurantResource , UtilisateurResource) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.restaurant = $scope.restaurant || {};
    
    $scope.restaurateurList = UtilisateurResource.queryAll(function(items){
        $scope.restaurateurSelectionList = $.map(items, function(item) {
            return ( {
                value : item.id,
                text : item.id
            });
        });
    });
    $scope.$watch("restaurateurSelection", function(selection) {
        if ( typeof selection != 'undefined') {
            $scope.restaurant.restaurateur = {};
            $scope.restaurant.restaurateur.id = selection.value;
        }
    });
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            flash.setMessage({'type':'success','text':'The restaurant was created successfully.'});
            $location.path('/Restaurants');
        };
        var errorCallback = function(response) {
            if(response && response.data) {
                flash.setMessage({'type': 'error', 'text': response.data.message || response.data}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        };
        RestaurantResource.save($scope.restaurant, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/Restaurants");
    };
});