package org.HomeCooking.webservice;


import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
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

import org.HomeCooking.model.Commande;
import org.HomeCooking.model.CommandeDetail;
//import org.AgentTicket.model.Agent;
import org.HomeCooking.model.Restaurant;
import org.HomeCooking.model.Restauration;
import org.HomeCooking.model.Utilisateur;

import LecteurFichiers.JSON;

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
	@Path("/utilisateur/id/{id}")
	@Produces("application/json")
	public Response getUserById(@PathParam("id") Long id) {
		Utilisateur utilisateur = em.find(Utilisateur.class, id);
		return Response.ok(utilisateur).build();
	}
	
	// recherche utilisateur par le nom
	@GET
	@Path("/utilisateur/nom/{nom}")
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
	public Response getRestaurantById(@PathParam("id") Long id) {
		List<Restaurant> listRestaurants = em.createQuery("SELECT r FROM Restaurant r WHERE r.id = :id ", Restaurant.class).setParameter("id", id).getResultList();
		return Response.ok(listRestaurants).build();
	}
	
	// recherche de restaurant par le nom
	@GET
	@Path("/recherche/restaurant/nom/{nom}")
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
	public Response getRestaurationByName(@PathParam("id") Long id) {
		List<Restauration> restaurations = null;
		TypedQuery<Restauration> requete	=	em.createQuery("SELECT r FROM Restauration r WHERE r.id = :pId ", Restauration.class).setParameter("pId" ,id);
		restaurations = requete.getResultList();
		return Response.ok(restaurations).build();
	}
	
	// recherche d'une restauration via le nom
	@GET
	@Path("/recherche/restauration/nom/{nom}")
	@Produces("application/json")
	public Response getRestaurationByName(@PathParam("nom") String nom) {
		List<Restauration> restaurations = null;
		TypedQuery<Restauration> requete	=	em.createQuery("SELECT r FROM Restauration r WHERE r.nom LIKE CONCAT('%',:pNom,'%')", Restauration.class).setParameter("pNom" ,nom);
		//TypedQuery<Restauration> requete	=	em.createQuery("SELECT r FROM Restauration r WHERE r.nom LIKE '%:?%' OR r.ingredients LIKE '%:?%' ", Restauration.class).setParameter(1,nom).setParameter(2,nom);
		restaurations = requete.getResultList();
		return Response.ok(restaurations).build();
	}
	
	// recherche d'un plat via l'id
	@GET
	@Path("/recherche/plat/{id}")
	@Produces("application/json")
	public Response getPlatById(@PathParam("id") Long id) {
		List<Restauration> restaurations = null;
		TypedQuery<Restauration> requete	=	em.createQuery("SELECT r FROM Restauration r WHERE r.type = 'Plat' AND r.id = :id", Restauration.class).setParameter("id" ,id);
		restaurations = requete.getResultList();
		return Response.ok(restaurations).build();
	}
	
	// recherche d'un plat via le nom
	@GET
	@Path("/recherche/plat/nom/{nom}")
	@Produces("application/json")
	public Response getPlatByName(@PathParam("nom") String nom) {
		List<Restauration> restaurations = null;
		TypedQuery<Restauration> requete	=	em.createQuery("SELECT r FROM Restauration r WHERE r.type = 'Plat' AND r.nom LIKE CONCAT('%',:pNom,'%')", Restauration.class).setParameter("pNom" ,nom);
		restaurations = requete.getResultList();
		return Response.ok(restaurations).build();
	}
	
	// recherche d'une boisson via l'id
	@GET
	@Path("/recherche/boisson/{id}")
	@Produces("application/json")
	public Response getBoissonById(@PathParam("id") Long id) {
		List<Restauration> restaurations = null;
		TypedQuery<Restauration> requete	=	em.createQuery("SELECT r FROM Restauration r WHERE r.type = 'Boisson' AND r.id = :id", Restauration.class).setParameter("id" ,id);
		restaurations = requete.getResultList();
		return Response.ok(restaurations).build();
	}
	
	// recherche d'une boisson via le nom
	@GET
	@Path("/recherche/boisson/nom/{nom}")
	@Produces("application/json")
	public Response getBoissonByName(@PathParam("nom") String nom) {
		List<Restauration> restaurations = null;
		TypedQuery<Restauration> requete	=	em.createQuery("SELECT r FROM Restauration r WHERE r.type = 'Boisson' AND r.nom LIKE CONCAT('%',:pNom,'%')", Restauration.class).setParameter("pNom" ,nom);
		restaurations = requete.getResultList();
		return Response.ok(restaurations).build();
	}
	
	// liste des commandes
	@GET
	@Path("/commandes")
	@Produces("application/json")
	public Response getAllCommande() {
		List<Commande> commande = null;
		TypedQuery<Commande> requete	=	em.createQuery("SELECT c FROM Commande c ", Commande.class);
		commande = requete.getResultList();
		return Response.ok(commande).build();
	}
	
	
	//	recherche d'une commande par ID
	@GET
	@Path("/commande/{id}")
	@Produces("application/json")
	public Response getCommandeById(@PathParam("id") Long id) {
		List<Commande> commande = null;
		TypedQuery<Commande> requete	=	em.createQuery("SELECT c FROM Commande c WHERE c.id = :id ", Commande.class).setParameter("id", id);
		commande = requete.getResultList();
		return Response.ok(commande).build();
	}
	
	//	Details d'une Commande par Id
	@GET
	@Path("/commande/detail/{id}")
	@Produces("application/json")
	public Response getDetailCommandeById(@PathParam("id") Long id) {
		List<CommandeDetail> commandeDetail = null;
		TypedQuery<CommandeDetail> requete	=	em.createQuery("SELECT c FROM Commande c WHERE c.commande_id = :id ", CommandeDetail.class).setParameter("id", id);
		commandeDetail = requete.getResultList();
		return Response.ok(commandeDetail).build();
	}
		
	@GET
	@Path("/csv/restaurations")
	@Produces("application/json")
	public String csvRestauration() {
		
		List<Restauration> restaurations = null;
		TypedQuery<Restauration> requete	=	em.createQuery("SELECT r FROM Restauration r", Restauration.class);
		//TypedQuery<Restauration> requete	=	em.createQuery("SELECT r FROM Restauration r WHERE r.nom LIKE '%:?%' OR r.ingredients LIKE '%:?%' ", Restauration.class).setParameter(1,nom).setParameter(2,nom);
		restaurations = requete.getResultList();
		
		return restaurations.toString();
		
		//JSON donneesRestauration = new JSON();
		/*
		donneesRestauration.getList(restaurations.toString());
		
		
		// Our example data
		List<List<String>> rows = Arrays.asList(
		    Arrays.asList("Jean", "author", "Java"),
		    Arrays.asList("David", "editor", "Python"),
		    Arrays.asList("Scott", "editor", "Node.js")
		);
		

		FileWriter csvWriter;
		try {
			csvWriter = new FileWriter("C:\\Users\\ryan.miranville\\Desktop\\CSV\\new.csv");
			
			csvWriter.append("Name");
			csvWriter.append(",");
			csvWriter.append("Role");
			csvWriter.append(",");
			csvWriter.append("Topic");
			csvWriter.append("\n");

			for (List<String> rowData : rows) {
			    csvWriter.append(String.join(",", rowData));
			    csvWriter.append("\n");
			}

			csvWriter.flush();
			csvWriter.close();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		return("debug");
		*/
		
		
		
	}

}
