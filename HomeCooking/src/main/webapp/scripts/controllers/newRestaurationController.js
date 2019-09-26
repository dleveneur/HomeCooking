
angular.module('homeCooking').controller('NewRestaurationController', function ($scope, $location, locationParser, flash, RestaurationResource ) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.restauration = $scope.restauration || {};
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            flash.setMessage({'type':'success','text':'The restauration was created successfully.'});
            $location.path('/Restaurations');
        };
        var errorCallback = function(response) {
            if(response && response.data) {
                flash.setMessage({'type': 'error', 'text': response.data.message || response.data}, true);
            } else {
                flash.setMessage({'type': 'error', 'text': 'Something broke. Retry, or cancel and start afresh.'}, true);
            }
        };
        RestaurationResource.save($scope.restauration, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/Restaurations");
    };
});