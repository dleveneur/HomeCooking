package org.HomeCooking.webservice;


import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
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
	@Path("/utilisateur/{id}")
	@Produces("application/json")
	public Response affichePremierUser(@PathParam("id") Long id) {
		Utilisateur utilisateur = em.find(Utilisateur.class, id);
		return Response.ok(utilisateur).build();
	}
	
	@GET
	@Path("/utilisateurs")
	@Produces("application/json")
	public Response listUser() {
		List<Utilisateur> list = em.createQuery("SELECT u FROM Utilisateur u", Utilisateur.class).getResultList();
		return Response.ok(list).build();
	}
	
	@GET
	@Path("/restauration/boissons")
	@Produces("application/json")
	public Response listBoissons() {
		List<Restauration> listB = em.createQuery("SELECT b FROM Restauration b WHERE type = 'Boisson' ", Restauration.class).getResultList();
		return Response.ok(listB).build();
	}
	
	@GET
	@Path("/restauration/plats")
	@Produces("application/json")
	public Response listPlats() {
		List<Restauration> listP = em.createQuery("SELECT p FROM Restauration p WHERE type = 'Plat' ", Restauration.class).getResultList();
		return Response.ok(listP).build();
	}
	
	@GET
	@Path("/restaurants")
	@Produces("application/json")
	public Response listRestaurants() {
		List<Restaurant> listRestaurants = em.createQuery("SELECT r FROM Restaurant r ", Restaurant.class).getResultList();
		return Response.ok(listRestaurants).build();
	}
	
	// recherche d'un plat via le nom
	@GET
	@Path("/recherche/restauration/{nom}")
	@Produces("application/json")
	public Response affichePremierUser(@PathParam("nom") String nom) {
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
