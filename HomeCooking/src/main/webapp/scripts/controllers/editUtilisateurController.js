

angular.module('homeCooking').controller('EditUtilisateurController', function($scope, $routeParams, $location, flash, UtilisateurResource ) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.utilisateur = new UtilisateurResource(self.original);
        };
        var errorCallback = function() {
            flash.setMessage({'type': 'error', 'text': 'The utilisateur could not be found.'});
            $location.path("/Utilisateurs");
        };
        UtilisateurResource.get({UtilisateurId:$routeParams.UtilisateurId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.utilisateur);
    };

    $scope.save = function() {
        var successCallback = function(){
            flash.setMessage({'type':'success','text':'The utilisateur was updated successfully.'}, true);
            $scope.get();
        };
        var errorCallback = function(response) {
            if(response && response.data && response.data.message) {
                flash.setMessage({'type': 'error', 'text': response.data.message}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        };
        $scope.utilisateur.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/Utilisateurs");
    };

    $scope.remove = function() {
        var successCallback = function() {
            flash.setMessage({'type': 'error', 'text': 'The utilisateur was deleted.'});
            $location.path("/Utilisateurs");
        };
        var errorCallback = function(response) {
            if(response && response.data && response.data.message) {
                flash.setMessage({'type': 'error', 'text': response.data.message}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        }; 
        $scope.utilisateur.$remove(successCallback, errorCallback);
    };
    
    $scope.roleList = [
        "Restaurateur",  
        "Client",  
        "Admin"  
    ];
    
    $scope.get();
});