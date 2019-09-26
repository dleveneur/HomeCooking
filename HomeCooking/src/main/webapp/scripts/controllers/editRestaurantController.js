

angular.module('homeCooking').controller('EditRestaurantController', function($scope, $routeParams, $location, flash, RestaurantResource , UtilisateurResource) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.restaurant = new RestaurantResource(self.original);
            UtilisateurResource.queryAll(function(items) {
                $scope.restaurateurSelectionList = $.map(items, function(item) {
                    var wrappedObject = {
                        id : item.id
                    };
                    var labelObject = {
                        value : item.id,
                        text : item.id
                    };
                    if($scope.restaurant.restaurateur && item.id == $scope.restaurant.restaurateur.id) {
                        $scope.restaurateurSelection = labelObject;
                        $scope.restaurant.restaurateur = wrappedObject;
                        self.original.restaurateur = $scope.restaurant.restaurateur;
                    }
                    return labelObject;
                });
            });
        };
        var errorCallback = function() {
            flash.setMessage({'type': 'error', 'text': 'The restaurant could not be found.'});
            $location.path("/Restaurants");
        };
        RestaurantResource.get({RestaurantId:$routeParams.RestaurantId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.restaurant);
    };

    $scope.save = function() {
        var successCallback = function(){
            flash.setMessage({'type':'success','text':'The restaurant was updated successfully.'}, true);
            $scope.get();
        };
        var errorCallback = function(response) {
            if(response && response.data && response.data.message) {
                flash.setMessage({'type': 'error', 'text': response.data.message}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        };
        $scope.restaurant.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/Restaurants");
    };

    $scope.remove = function() {
        var successCallback = function() {
            flash.setMessage({'type': 'error', 'text': 'The restaurant was deleted.'});
            $location.path("/Restaurants");
        };
        var errorCallback = function(response) {
            if(response && response.data && response.data.message) {
                flash.setMessage({'type': 'error', 'text': response.data.message}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        }; 
        $scope.restaurant.$remove(successCallback, errorCallback);
    };
    
    $scope.$watch("restaurateurSelection", function(selection) {
        if (typeof selection != 'undefined') {
            $scope.restaurant.restaurateur = {};
            $scope.restaurant.restaurateur.id = selection.value;
        }
    });
    
    $scope.get();
});