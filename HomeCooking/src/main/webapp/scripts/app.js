'use strict';

angular.module('homeCooking',['ngRoute','ngResource'])
  .config(['$routeProvider', function($routeProvider) {
    $routeProvider
      .when('/',{templateUrl:'views/landing.html',controller:'LandingPageController'})
      .when('/Restaurants',{templateUrl:'views/Restaurant/search.html',controller:'SearchRestaurantController'})
      .when('/Restaurants/new',{templateUrl:'views/Restaurant/detail.html',controller:'NewRestaurantController'})
      .when('/Restaurants/edit/:RestaurantId',{templateUrl:'views/Restaurant/detail.html',controller:'EditRestaurantController'})
      .when('/Restaurations',{templateUrl:'views/Restauration/search.html',controller:'SearchRestaurationController'})
      .when('/Restaurations/new',{templateUrl:'views/Restauration/detail.html',controller:'NewRestaurationController'})
      .when('/Restaurations/edit/:RestaurationId',{templateUrl:'views/Restauration/detail.html',controller:'EditRestaurationController'})
      .when('/Utilisateurs',{templateUrl:'views/Utilisateur/search.html',controller:'SearchUtilisateurController'})
      .when('/Utilisateurs/new',{templateUrl:'views/Utilisateur/detail.html',controller:'NewUtilisateurController'})
      .when('/Utilisateurs/edit/:UtilisateurId',{templateUrl:'views/Utilisateur/detail.html',controller:'EditUtilisateurController'})
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
