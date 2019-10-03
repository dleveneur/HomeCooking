package org.HomeCooking.webservice;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
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
import javax.ws.rs.core.Response.ResponseBuilder;

import org.HomeCooking.model.Commande;
import org.HomeCooking.model.CommandeDetail;
import org.HomeCooking.model.Restaurant;
import org.HomeCooking.model.Restauration;
import org.HomeCooking.model.Utilisateur;



@Stateless
@Path("/ws")
public class WS {

	@PersistenceContext(unitName = "primary")
	public EntityManager em;

	@PermitAll
	@GET
	@Path("/")
	@Produces({ MediaType.TEXT_PLAIN })
	public Response index() {
		return Response.status(200).header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Credentials", "true")
				.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
				.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD").entity("").build();
	}

	@PermitAll
	// recherche utilisateur par l'id
	@GET
	@Path("/utilisateur/id/{id}")
	@Produces("application/json")
	public Response getUserById(@PathParam("id") Long id) {
		Utilisateur utilisateur = em.find(Utilisateur.class, id);
		return Response.ok(utilisateur).build();
	}

	@PermitAll
	// recherche utilisateur par le nom
	@GET
	@Path("/utilisateur/nom/{nom}")
	@Produces("application/json")
	public Response getUserById(@PathParam("nom") String nom) {
		List<Utilisateur> list = em
				.createQuery("SELECT u FROM Utilisateur u WHERE u.nom LIKE '%:nom%' ", Utilisateur.class)
				.setParameter("nom", nom).getResultList();
		return Response.ok(list).build();
	}

	// liste des utilisateurs
	@RolesAllowed({"Admin"})
	@GET
	@Path("/utilisateurs")
	@Produces("application/json")
	public Response getAllUser() {
		List<Utilisateur> list = em.createQuery("SELECT u FROM Utilisateur u", Utilisateur.class).getResultList();
		return Response.ok(list).build();
	}

	// liste des boissons
	@PermitAll
	@GET
	@Path("/restauration/boissons")
	@Produces("application/json")
	public Response getAllBoissons() {
		List<Restauration> listB = em
				.createQuery("SELECT b FROM Restauration b WHERE type = 'Boisson' ", Restauration.class)
				.getResultList();
		return Response.ok(listB).build();
	}

	// liste des plats
	@PermitAll
	@GET
	@Path("/restauration/plats")
	@Produces("application/json")
	public Response listPlats() {
		List<Restauration> listP = em
				.createQuery("SELECT p FROM Restauration p WHERE type = 'Plat' ", Restauration.class).getResultList();
		return Response.ok(listP).build();
	}

	// liste de tous les restaurants
	@PermitAll
	@GET
	@Path("/restaurants")
	@Produces("application/json")
	public Response getAllRestaurants() {
		List<Restaurant> listRestaurants = em.createQuery("SELECT r FROM Restaurant r ", Restaurant.class)
				.getResultList();
		return Response.ok(listRestaurants).build();
	}

	// recherche de restaurant par l'id
	@PermitAll
	@GET
	@Path("/recherche/restaurant/{id}")
	@Produces("application/json")
	public Response getRestaurantById(@PathParam("id") Long id) {
		List<Restaurant> listRestaurants = em
				.createQuery("SELECT r FROM Restaurant r WHERE r.id = :id ", Restaurant.class).setParameter("id", id)
				.getResultList();
		return Response.ok(listRestaurants).build();
	}

	// recherche de restaurant par le nom
	@PermitAll
	@GET
	@Path("/recherche/restaurant/nom/{nom}")
	@Produces("application/json")
	public Response getRestaurantByName(@PathParam("nom") String nom) {
		List<Restaurant> listRestaurants = em
				.createQuery("SELECT r FROM Restaurant r WHERE upper(r.nom) LIKE :nom ORDER BY r.id ", Restaurant.class)
				.setParameter("nom", "%" + nom + "%").getResultList();
		return Response.ok(listRestaurants).build();
	}

	// liste de tous les restaurations
	@PermitAll
	@GET
	@Path("/restaurations")
	@Produces("application/json")
	public Response getAllRestaurations() {
		List<Restauration> listRestaurants = em.createQuery("SELECT r FROM Restauration r ", Restauration.class)
				.getResultList();
		return Response.ok(listRestaurants).build();
	}

	// recherche d'une restauration via l'id
	@PermitAll
	@GET
	@Path("/recherche/restauration/{id}")
	@Produces("application/json")
	public Response getRestaurationByName(@PathParam("id") Long id) {
		List<Restauration> restaurations = null;
		TypedQuery<Restauration> requete = em
				.createQuery("SELECT r FROM Restauration r WHERE r.id = :pId ", Restauration.class)
				.setParameter("pId", id);
		restaurations = requete.getResultList();
		return Response.ok(restaurations).build();
	}

	// recherche d'une restauration via le nom
	@PermitAll
	@GET
	@Path("/recherche/restauration/nom/{nom}")
	@Produces("application/json")
	public Response getRestaurationByName(@PathParam("nom") String nom) {
		List<Restauration> restaurations = null;
		// TypedQuery<Restauration> requete = em.createQuery("SELECT r FROM Restauration
		// r WHERE upper(r.nom) LIKE :nom ORDER BY r.id ",
		// Restauration.class).setParameter("nom" , (("%"+nom+"%").toUpperCase()));
		TypedQuery<Restauration> requete = em
				.createQuery("SELECT r FROM Restauration r WHERE upper(r.nom) LIKE upper(:pNom) ORDER BY r.id ",
						Restauration.class)
				.setParameter("pNom", ("%" + nom + "%"));

		restaurations = requete.getResultList();
		return Response.ok(restaurations).build();
	}

	// recherche d'un plat via l'id
	@PermitAll
	@GET
	@Path("/recherche/plat/{id}")
	@Produces("application/json")
	public Response getPlatById(@PathParam("id") Long id) {
		List<Restauration> restaurations = null;
		TypedQuery<Restauration> requete = em
				.createQuery("SELECT r FROM Restauration r WHERE r.type = 'Plat' AND r.id = :id", Restauration.class)
				.setParameter("id", id);
		restaurations = requete.getResultList();
		return Response.ok(restaurations).build();
	}

	// recherche d'un plat via le nom
	@PermitAll
	@GET
	@Path("/recherche/plat/nom/{nom}")
	@Produces("application/json")
	public Response getPlatByName(@PathParam("nom") String nom) {
		List<Restauration> restaurations = null;
		TypedQuery<Restauration> requete = em
				.createQuery("SELECT r FROM Restauration r WHERE r.type = 'Plat' AND r.nom LIKE CONCAT('%',:pNom,'%')",
						Restauration.class)
				.setParameter("pNom", nom);
		restaurations = requete.getResultList();
		return Response.ok(restaurations).build();
	}

	// recherche d'une boisson via l'id
	@PermitAll
	@GET
	@Path("/recherche/boisson/{id}")
	@Produces("application/json")
	public Response getBoissonById(@PathParam("id") Long id) {
		List<Restauration> restaurations = null;
		TypedQuery<Restauration> requete = em
				.createQuery("SELECT r FROM Restauration r WHERE r.type = 'Boisson' AND r.id = :id", Restauration.class)
				.setParameter("id", id);
		restaurations = requete.getResultList();
		return Response.ok(restaurations).build();
	}

	// recherche d'une boisson via le nom
	@PermitAll
	@GET
	@Path("/recherche/boisson/nom/{nom}")
	@Produces("application/json")
	public Response getBoissonByName(@PathParam("nom") String nom) {
		List<Restauration> restaurations = null;
		TypedQuery<Restauration> requete = em.createQuery(
				"SELECT r FROM Restauration r WHERE r.type = 'Boisson' AND r.nom LIKE CONCAT('%',:pNom,'%')",
				Restauration.class).setParameter("pNom", nom);
		restaurations = requete.getResultList();
		return Response.ok(restaurations).build();
	}

	// liste des commandes
	@PermitAll
	@GET
	@Path("/commandes")
	@Produces("application/json")
	public Response getAllCommande() {
		List<Commande> commande = null;
		TypedQuery<Commande> requete = em.createQuery("SELECT c FROM Commande c ", Commande.class);
		commande = requete.getResultList();
		return Response.ok(commande).build();
	}

	@PermitAll
	// recherche d'une commande par ID
	@GET
	@Path("/commande/{id}")
	@Produces("application/json")
	public Response getCommandeById(@PathParam("id") Long id) {
		List<Commande> commande = null;
		TypedQuery<Commande> requete = em.createQuery("SELECT c FROM Commande c WHERE c.id = :id ", Commande.class)
				.setParameter("id", id);
		commande = requete.getResultList();
		return Response.ok(commande).build();
	}

	@PermitAll
	// Details d'une Commande par Id
	@GET
	@Path("/commande/detail/{id}")
	@Produces("application/json")
	public Response getDetailCommandeById(@PathParam("id") Long id) {
		List<CommandeDetail> commandeDetail = null;
		TypedQuery<CommandeDetail> requete = em
				.createQuery("SELECT c FROM Commande c WHERE c.commande_id = :id ", CommandeDetail.class)
				.setParameter("id", id);
		commandeDetail = requete.getResultList();
		return Response.ok(commandeDetail).build();
	}

	@GET
	@Path("/csv/restaurations")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response csvRestauration() {

		List<Restauration> restaurations = null;
		TypedQuery<Restauration> requete = em.createQuery("SELECT r FROM Restauration r", Restauration.class);
		restaurations = requete.getResultList();

		List<String> colonnes = null;

		Field[] fields = Restauration.class.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			colonnes.add(fields[i].toString());
			System.out.println("Field = " + fields[i].toString());
		}

		List<String> values = null;

		for (Restauration Data : restaurations) {
			values.add(Data.getId() + ";" + Data.getNom() + ";" + Data.getIngredients() + ";" + Data.getType() + ";"
					+ Data.getPrix());
		}

		File csvFile = new File("C:\\Users\\ryan.miranville\\Desktop\\CSV\\Restauration.csv");

		createCSV(csvFile, colonnes, values);

		// File csvFile = new
		// File("C:\\Users\\ryan.miranville\\Desktop\\CSV\\Restauration.csv");

		/*
		 * FileWriter csvWriter; try { csvWriter = new FileWriter(csvFile);
		 * 
		 * 
		 * csvWriter.append("Id"); csvWriter.append(";"); csvWriter.append("Nom");
		 * csvWriter.append(";"); csvWriter.append("Description");
		 * csvWriter.append(";"); csvWriter.append("Type"); csvWriter.append(";");
		 * csvWriter.append("Prix"); csvWriter.append("\n");
		 * 
		 * 
		 * 
		 * for (Restauration Data : restaurations) {
		 * values.add(Data.getId()+";"+Data.getNom()+";"+Data.getIngredients()+";"+Data.
		 * getType()+";"+Data.getPrix());
		 * csvWriter.append(Data.getId()+";"+Data.getNom()+";"+Data.getIngredients()+";"
		 * +Data.getType()+";"+Data.getPrix()); csvWriter.append("\n"); }
		 * 
		 * csvWriter.flush(); csvWriter.close();
		 * 
		 * } catch (IOException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */

		return (getFile(csvFile, "Restauration.csv").build());

	}

	public ResponseBuilder getFile(File file, String fileName) {

		ResponseBuilder response = Response.ok((Object) file);
		response.header("Content-Disposition", "attachment; filename=" + fileName);
		return response;

	}

	public void createCSV(File file, List<String> colonnes, List<String> values) {

		File csvFile = file;

		FileWriter csvWriter;
		try {
			csvWriter = new FileWriter(csvFile);

			for (String Data : colonnes) {
				csvWriter.append(String.join(";", Data));
				csvWriter.append("\n");
			}

			for (String Data : values) {
				csvWriter.append(String.join(";", Data));
				csvWriter.append("\n");
			}

			csvWriter.flush();
			csvWriter.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
