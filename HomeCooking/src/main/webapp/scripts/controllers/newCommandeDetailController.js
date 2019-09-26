
angular.module('homeCooking').controller('NewCommandeDetailController', function ($scope, $location, locationParser, flash, CommandeDetailResource , RestaurationResource, CommandeResource) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.commandeDetail = $scope.commandeDetail || {};
    
    $scope.restaurationList = RestaurationResource.queryAll(function(items){
        $scope.restaurationSelectionList = $.map(items, function(item) {
            return ( {
                value : item.id,
                text : item.nom
            });
        });
    });
    $scope.$watch("restaurationSelection", function(selection) {
        if ( typeof selection != 'undefined') {
            $scope.commandeDetail.restauration = {};
            $scope.commandeDetail.restauration.id = selection.value;
        }
    });
    
    $scope.commandeList = CommandeResource.queryAll(function(items){
        $scope.commandeSelectionList = $.map(items, function(item) {
            return ( {
                value : item.id,
                text : item.prixTotal
            });
        });
    });
    $scope.$watch("commandeSelection", function(selection) {
        if ( typeof selection != 'undefined') {
            $scope.commandeDetail.commande = {};
            $scope.commandeDetail.commande.id = selection.value;
        }
    });
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            flash.setMessage({'type':'success','text':'The commandeDetail was created successfully.'});
            $location.path('/CommandeDetails');
        };
        var errorCallback = function(response) {
            if(response && response.data) {
                flash.setMessage({'type': 'error', 'text': response.data.message || response.data}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        };
        CommandeDetailResource.save($scope.commandeDetail, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/CommandeDetails");
    };
});