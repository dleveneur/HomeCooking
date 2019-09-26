
angular.module('homeCooking').controller('NewUtilisateurController', function ($scope, $location, locationParser, flash, UtilisateurResource ) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.utilisateur = $scope.utilisateur || {};
    
    $scope.roleList = [
        "Restaurateur",
        "Client",
        "Admin"
    ];
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            flash.setMessage({'type':'success','text':'The utilisateur was created successfully.'});
            $location.path('/Utilisateurs');
        };
        var errorCallback = function(response) {
            if(response && response.data) {
                flash.setMessage({'type': 'error', 'text': response.data.message || response.data}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        };
        UtilisateurResource.save($scope.utilisateur, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/Utilisateurs");
    };
});