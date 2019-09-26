angular.module('homeCooking').factory('RestaurationResource', function($resource){
    var resource = $resource('rest/restaurations/:RestaurationId',{RestaurationId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});