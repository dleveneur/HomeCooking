angular.module('homeCooking').factory('RestaurantResource', function($resource){
    var resource = $resource('rest/restaurants/:RestaurantId',{RestaurantId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});