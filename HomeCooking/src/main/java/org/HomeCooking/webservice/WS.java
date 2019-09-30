package org.HomeCooking.webservice;


import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

//import org.AgentTicket.model.Agent;
import org.HomeCooking.model.Restaurant;
import org.HomeCooking.model.Restauration;
import org.HomeCooking.model.Utilisateur;

@Stateless
@Path("/ws")
public class WS {
	
   @PersistenceContext(unitName="primary")
   public EntityManager em; 
	
   
	   @GET
	   @Path("/")
	   @Produces({MediaType.TEXT_PLAIN})
	   public Response index() {
	       return Response
	         .status(200)
	         .header("Access-Control-Allow-Origin", "*")
	     .header("Access-Control-Allow-Credentials", "true")
	     .header("Access-Control-Allow-Headers",
	       "origin, content-type, accept, authorization")
	     .header("Access-Control-Allow-Methods", 
	       "GET, POST, PUT, DELETE, OPTIONS, HEAD")
	     .entity("")
	         .build();
	   }
   
	// recherche utilisateur par l'id
	@GET
	@Path("/utilisateur/{id}")
	@Produces("application/json")
	public Response getUserById(@PathParam("id") Long id) {
		Utilisateur utilisateur = em.find(Utilisateur.class, id);
		return Response.ok(utilisateur).build();
	}
	
	// recherche utilisateur par le nom
	@GET
	@Path("/utilisateur/{nom}")
	@Produces("application/json")
	public Response getUserById(@PathParam("nom") String nom) {
		List<Utilisateur> list = em.createQuery("SELECT u FROM Utilisateur u WHERE u.nom LIKE '%:nom%' ", Utilisateur.class).setParameter("nom",nom).getResultList();
		return Response.ok(list).build();
	}
	
	// liste des utilisateurs
	@GET
	@Path("/utilisateurs")
	@Produces("application/json")
	public Response getAllUser() {
		List<Utilisateur> list = em.createQuery("SELECT u FROM Utilisateur u", Utilisateur.class).getResultList();
		return Response.ok(list).build();
	}
	
	// liste des boissons
	@GET
	@Path("/restauration/boissons")
	@Produces("application/json")
	public Response getAllBoissons() {
		List<Restauration> listB = em.createQuery("SELECT b FROM Restauration b WHERE type = 'Boisson' ", Restauration.class).getResultList();
		return Response.ok(listB).build();
	}
	
	// liste des plats
	@GET
	@Path("/restauration/plats")
	@Produces("application/json")
	public Response listPlats() {
		List<Restauration> listP = em.createQuery("SELECT p FROM Restauration p WHERE type = 'Plat' ", Restauration.class).getResultList();
		return Response.ok(listP).build();
	}
	
	// liste de tous les restaurants
	@GET
	@Path("/restaurants")
	@Produces("application/json")
	public Response getAllRestaurants() {
		List<Restaurant> listRestaurants = em.createQuery("SELECT r FROM Restaurant r ", Restaurant.class).getResultList();
		return Response.ok(listRestaurants).build();
	}
	
	// recherche de restaurant par l'id
	@GET
	@Path("/recherche/restaurant/{id}")
	@Produces("application/json")
	public Response getRestaurantById(@PathParam("id") int id) {
		List<Restaurant> listRestaurants = em.createQuery("SELECT r FROM Restaurant r WHERE r.id = :id ", Restaurant.class).setParameter("id", id).getResultList();
		return Response.ok(listRestaurants).build();
	}
	
	// recherche de restaurant par le nom
	@GET
	@Path("/recherche/restaurant/{nom}")
	@Produces("application/json")
	public Response getRestaurantByName(@PathParam("nom") String nom) {
		List<Restaurant> listRestaurants = em.createQuery("SELECT r FROM Restaurant r WHERE r.nom = :nom ", Restaurant.class).setParameter("nom", nom).getResultList();
		return Response.ok(listRestaurants).build();
	}
	
	// liste de tous les restaurations
	@GET
	@Path("/restaurations")
	@Produces("application/json")
	public Response getAllRestaurations() {
		List<Restauration> listRestaurants = em.createQuery("SELECT r FROM Restauration r ", Restauration.class).getResultList();
		return Response.ok(listRestaurants).build();
	}
	
	// recherche d'une restauration via l'id
	@GET
	@Path("/recherche/restauration/{id}")
	@Produces("application/json")
	public Response getRestaurationByName(@PathParam("id") int id) {
		List<Restauration> restaurations = null;
		TypedQuery<Restauration> requete	=	em.createQuery("SELECT r FROM Restauration r WHERE r.id = :pId ", Restauration.class).setParameter("pId" ,id);
		//TypedQuery<Restauration> requete	=	em.createQuery("SELECT r FROM Restauration r WHERE r.nom LIKE '%:?%' OR r.ingredients LIKE '%:?%' ", Restauration.class).setParameter(1,nom).setParameter(2,nom);
		restaurations = requete.getResultList();
		return Response.ok(restaurations).build();
	}
	
	// recherche d'une restauration via le nom
	@GET
	@Path("/recherche/restauration/{nom}")
	@Produces("application/json")
	public Response getRestaurationByName(@PathParam("nom") String nom) {
		List<Restauration> restaurations = null;
		TypedQuery<Restauration> requete	=	em.createQuery("SELECT r FROM Restauration r WHERE r.nom LIKE CONCAT('%',:pNom,'%')", Restauration.class).setParameter("pNom" ,nom);
		//TypedQuery<Restauration> requete	=	em.createQuery("SELECT r FROM Restauration r WHERE r.nom LIKE '%:?%' OR r.ingredients LIKE '%:?%' ", Restauration.class).setParameter(1,nom).setParameter(2,nom);
		restaurations = requete.getResultList();
		return Response.ok(restaurations).build();
	}
	
	@GET
	@Path("/debug")
	//@Produces("application/json")
	public String debug() {
		
		System.out.println("Hello");
		
		return("debug");
		
		//List<Restaurant> listRestaurants = em.createQuery("SELECT r FROM Restaurant r ", Restaurant.class).getResultList();
		//return Response.ok(listRestaurants).build();
	}

}
