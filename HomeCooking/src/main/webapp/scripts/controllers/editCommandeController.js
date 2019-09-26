

angular.module('homeCooking').controller('EditCommandeController', function($scope, $routeParams, $location, flash, CommandeResource , UtilisateurResource, RestaurantResource) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.commande = new CommandeResource(self.original);
            UtilisateurResource.queryAll(function(items) {
                $scope.clientSelectionList = $.map(items, function(item) {
                    var wrappedObject = {
                        id : item.id
                    };
                    var labelObject = {
                        value : item.id,
                        text : item.id
                    };
                    if($scope.commande.client && item.id == $scope.commande.client.id) {
                        $scope.clientSelection = labelObject;
                        $scope.commande.client = wrappedObject;
                        self.original.client = $scope.commande.client;
                    }
                    return labelObject;
                });
            });
            RestaurantResource.queryAll(function(items) {
                $scope.restaurantSelectionList = $.map(items, function(item) {
                    var wrappedObject = {
                        id : item.id
                    };
                    var labelObject = {
                        value : item.id,
                        text : item.id
                    };
                    if($scope.commande.restaurant && item.id == $scope.commande.restaurant.id) {
                        $scope.restaurantSelection = labelObject;
                        $scope.commande.restaurant = wrappedObject;
                        self.original.restaurant = $scope.commande.restaurant;
                    }
                    return labelObject;
                });
            });
        };
        var errorCallback = function() {
            flash.setMessage({'type': 'error', 'text': 'The commande could not be found.'});
            $location.path("/Commandes");
        };
        CommandeResource.get({CommandeId:$routeParams.CommandeId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.commande);
    };

    $scope.save = function() {
        var successCallback = function(){
            flash.setMessage({'type':'success','text':'The commande was updated successfully.'}, true);
            $scope.get();
        };
        var errorCallback = function(response) {
            if(response && response.data && response.data.message) {
                flash.setMessage({'type': 'error', 'text': response.data.message}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        };
        $scope.commande.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/Commandes");
    };

    $scope.remove = function() {
        var successCallback = function() {
            flash.setMessage({'type': 'error', 'text': 'The commande was deleted.'});
            $location.path("/Commandes");
        };
        var errorCallback = function(response) {
            if(response && response.data && response.data.message) {
                flash.setMessage({'type': 'error', 'text': response.data.message}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        }; 
        $scope.commande.$remove(successCallback, errorCallback);
    };
    
    $scope.$watch("clientSelection", function(selection) {
        if (typeof selection != 'undefined') {
            $scope.commande.client = {};
            $scope.commande.client.id = selection.value;
        }
    });
    $scope.$watch("restaurantSelection", function(selection) {
        if (typeof selection != 'undefined') {
            $scope.commande.restaurant = {};
            $scope.commande.restaurant.id = selection.value;
        }
    });
    
    $scope.get();
});