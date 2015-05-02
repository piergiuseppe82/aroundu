package com.aroundu.rest.resource;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.aroundu.core.infrastructure.ServiceBeanFactory;
import com.aroundu.core.model.Person;
import com.aroundu.core.services.PersonServiceBean;

/**
 * @author piergiuseppe82
 *
 */
@Path("/profiles")
public class PersonResource {
    
	private PersonServiceBean personServiceBean =  ServiceBeanFactory.getInstance().getPersonServiceBean();
	
    @Path("{accountId}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})  //add MediaType.APPLICATION_XML if you want XML as well (don't forget @XmlRootElement)
    public Response getPerson(@PathParam("accountId") String accountId){
    	try {
			Person person = new Person();
			person.setAccountId(accountId);
			person = personServiceBean.getPerson(person);
			if(person != null){
				return Response.ok(person).build();
			}else{
				return Response.status(Status.NOT_FOUND).build();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
    }
    
    @Path("example")
    @GET
    @Produces({MediaType.APPLICATION_JSON})  //add MediaType.APPLICATION_XML if you want XML as well (don't forget @XmlRootElement)
    public Response example(){
    	try {
			Person person = new Person();
			person.setAccountId("piergiuseppe82");
			person.setEmail("piergiuseppe82@gmail.com");
			person.setFullName("Piergiuseppe La Cava");
			person.setPassword("secret1");
			person.setProfileImage("-");
			return Response.ok(person).build();
			
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
    }
    
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/authenticate")
    public Response postPerson(Person person, @Context HttpServletRequest req) throws Exception{
         try {
			Person checkPerson = personServiceBean.checkPerson(person.getAccountId(), person.getPassword());
			 if(checkPerson != null){
				req.getSession().setAttribute("user", checkPerson);
				return Response.ok(checkPerson).build();
			 }else{
				 req.getSession().invalidate();
				 return  Response.status(Status.UNAUTHORIZED).build();
			 }			 
		} catch (Exception e) {
			 e.printStackTrace();
			 req.getSession().invalidate();
			 return Response.serverError().build();
		}
    }
    
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/register")
    public Response resgisterPerson(Person person, @Context HttpServletRequest req) throws Exception{
         try {
			Person personNew = personServiceBean.addPerson(person);
			 if(personNew != null){
				 req.getSession().setAttribute("user", personNew);
				 URI location = new URI(req.getContextPath()+"/service/profiles/"+personNew.getAccountId());
				 return Response.created(location).build();
			 }else{
				 req.getSession().invalidate();
				 return  Response.status(Status.UNAUTHORIZED).build();
			 }			 
		} catch (Exception e) {
			 e.printStackTrace();
			 req.getSession().invalidate();
			 return Response.serverError().build();
		}
    }

}
