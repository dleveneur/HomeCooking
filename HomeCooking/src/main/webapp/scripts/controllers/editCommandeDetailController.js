

angular.module('homeCooking').controller('EditCommandeDetailController', function($scope, $routeParams, $location, flash, CommandeDetailResource , RestaurationResource, CommandeResource) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.commandeDetail = new CommandeDetailResource(self.original);
            RestaurationResource.queryAll(function(items) {
                $scope.restaurationSelectionList = $.map(items, function(item) {
                    var wrappedObject = {
                        id : item.id
                    };
                    var labelObject = {
                        value : item.id,
                        text : item.nom
                    };
                    if($scope.commandeDetail.restauration && item.id == $scope.commandeDetail.restauration.id) {
                        $scope.restaurationSelection = labelObject;
                        $scope.commandeDetail.restauration = wrappedObject;
                        self.original.restauration = $scope.commandeDetail.restauration;
                    }
                    return labelObject;
                });
            });
            CommandeResource.queryAll(function(items) {
                $scope.commandeSelectionList = $.map(items, function(item) {
                    var wrappedObject = {
                        id : item.id
                    };
                    var labelObject = {
                        value : item.id,
                        text : item.prixTotal
                    };
                    if($scope.commandeDetail.commande && item.id == $scope.commandeDetail.commande.id) {
                        $scope.commandeSelection = labelObject;
                        $scope.commandeDetail.commande = wrappedObject;
                        self.original.commande = $scope.commandeDetail.commande;
                    }
                    return labelObject;
                });
            });
        };
        var errorCallback = function() {
            flash.setMessage({'type': 'error', 'text': 'The commandeDetail could not be found.'});
            $location.path("/CommandeDetails");
        };
        CommandeDetailResource.get({CommandeDetailId:$routeParams.CommandeDetailId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.commandeDetail);
    };

    $scope.save = function() {
        var successCallback = function(){
            flash.setMessage({'type':'success','text':'The commandeDetail was updated successfully.'}, true);
            $scope.get();
        };
        var errorCallback = function(response) {
            if(response && response.data && response.data.message) {
                flash.setMessage({'type': 'error', 'text': response.data.message}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        };
        $scope.commandeDetail.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/CommandeDetails");
    };

    $scope.remove = function() {
        var successCallback = function() {
            flash.setMessage({'type': 'error', 'text': 'The commandeDetail was deleted.'});
            $location.path("/CommandeDetails");
        };
        var errorCallback = function(response) {
            if(response && response.data && response.data.message) {
                flash.setMessage({'type': 'error', 'text': response.data.message}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        }; 
        $scope.commandeDetail.$remove(successCallback, errorCallback);
    };
    
    $scope.$watch("restaurationSelection", function(selection) {
        if (typeof selection != 'undefined') {
            $scope.commandeDetail.restauration = {};
            $scope.commandeDetail.restauration.id = selection.value;
        }
    });
    $scope.$watch("commandeSelection", function(selection) {
        if (typeof selection != 'undefined') {
            $scope.commandeDetail.commande = {};
            $scope.commandeDetail.commande.id = selection.value;
        }
    });
    
    $scope.get();
});