angular.module('homeCooking').factory('CommandeResource', function($resource){
    var resource = $resource('rest/commandes/:CommandeId',{CommandeId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});