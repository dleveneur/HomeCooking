
angular.module('homeCooking').controller('NewCommandeController', function ($scope, $location, locationParser, flash, CommandeResource , UtilisateurResource, RestaurantResource) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.commande = $scope.commande || {};
    
    $scope.clientList = UtilisateurResource.queryAll(function(items){
        $scope.clientSelectionList = $.map(items, function(item) {
            return ( {
                value : item.id,
                text : item.id
            });
        });
    });
    $scope.$watch("clientSelection", function(selection) {
        if ( typeof selection != 'undefined') {
            $scope.commande.client = {};
            $scope.commande.client.id = selection.value;
        }
    });
    
    $scope.restaurantList = RestaurantResource.queryAll(function(items){
        $scope.restaurantSelectionList = $.map(items, function(item) {
            return ( {
                value : item.id,
                text : item.id
            });
        });
    });
    $scope.$watch("restaurantSelection", function(selection) {
        if ( typeof selection != 'undefined') {
            $scope.commande.restaurant = {};
            $scope.commande.restaurant.id = selection.value;
        }
    });
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            flash.setMessage({'type':'success','text':'The commande was created successfully.'});
            $location.path('/Commandes');
        };
        var errorCallback = function(response) {
            if(response && response.data) {
                flash.setMessage({'type': 'error', 'text': response.data.message || response.data}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        };
        CommandeResource.save($scope.commande, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/Commandes");
    };
});