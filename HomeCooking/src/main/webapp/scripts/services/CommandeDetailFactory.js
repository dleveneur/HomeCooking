angular.module('homeCooking').factory('CommandeDetailResource', function($resource){
    var resource = $resource('rest/commandedetails/:CommandeDetailId',{CommandeDetailId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});