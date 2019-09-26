'use strict';

angular.module('homeCooking',['ngRoute','ngResource'])
  .config(['$routeProvider', function($routeProvider) {
    $routeProvider
      .when('/',{templateUrl:'views/landing.html',controller:'LandingPageController'})
      .when('/Restaurations',{templateUrl:'views/Restauration/search.html',controller:'SearchRestaurationController'})
      .when('/Restaurations/new',{templateUrl:'views/Restauration/detail.html',controller:'NewRestaurationController'})
      .when('/Restaurations/edit/:RestaurationId',{templateUrl:'views/Restauration/detail.html',controller:'EditRestaurationController'})
      .otherwise({
        redirectTo: '/'
      });
  }])
  .controller('LandingPageController', function LandingPageController() {
  })
  .controller('NavController', function NavController($scope, $location) {
    $scope.matchesRoute = function(route) {
        var path = $location.path();
        return (path === ("/" + route) || path.indexOf("/" + route + "/") == 0);
    };
  });
