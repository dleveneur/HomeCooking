angular.module('homeCooking').factory('UtilisateurResource', function($resource){
    var resource = $resource('rest/utilisateurs/:UtilisateurId',{UtilisateurId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});