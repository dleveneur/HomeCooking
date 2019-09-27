package org.HomeCooking.webservice;


import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.HomeCooking.model.Utilisateur;

@Stateless
@Path("/ws")
public class WS {
	
   @PersistenceContext(unitName="primary")
   public EntityManager em; 
	
	@GET
	@Path("/first")
	@Produces("application/json")
	public Response affichePremierUser() {
		Utilisateur utilisateur = em.find(Utilisateur.class, 1L);
		return Response.ok(utilisateur).build();
	}
	
	@GET
	@Path("/second")
	@Produces("application/json")
	public Response listUser() {
		List<Utilisateur> list = em.createQuery("SELECT u FROM Utilisateur u", Utilisateur.class).getResultList();
		return Response.ok(list).build();
	}
}
