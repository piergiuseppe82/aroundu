package com.aroundu.rest.resource;

import java.math.BigInteger;
import java.net.URI;
import java.security.SecureRandom;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.client.oauth2.OAuth2ClientSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aroundu.core.infrastructure.ServiceBeanFactory;
import com.aroundu.core.model.User;
import com.aroundu.core.services.UserServiceBean;
import com.aroundu.rest.filters.ResponseHeaderFilter;
import com.aroundu.rest.security.oauth.google.GoogleProfile;

/**
 * @author piergiuseppe82
 *
 */
@Path("/users")
public class UserResource {
    
	private UserServiceBean userServiceBean =  ServiceBeanFactory.getInstance().getUserServiceBean();
	Logger log = LoggerFactory.getLogger(UserResource.class);
	
	
	
    @Path("/{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON}) 
    public Response getUser(@PathParam("id") Long id){
    	try {
			User user = new User();
			user.setId(id);
			user = userServiceBean.getUser(user);
			if(user != null){
				return Response.ok(user).build();
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
    @Produces({MediaType.APPLICATION_JSON}) 
    public Response example(){
    	try {
			User user = new User();
			user.setUsername("piergiuseppe82");
			user.setEmail("piergiuseppe82@gmail.com");
			user.setDisplayName("Piergiuseppe La Cava");
			return Response.ok(user).build();
			
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
    }
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/login")
    public Response login(@QueryParam("username") String username,@QueryParam("password") String password, @Context HttpServletRequest req) throws Exception{
         try {
        	User checkUser = userServiceBean.checkUser(username, password);
			 if(checkUser != null){
				req.getSession().setAttribute("user", checkUser);
				return Response.ok(checkUser).build();
			 }else{
				 req.getSession().invalidate();
				 return  Response.status(Status.UNAUTHORIZED).build();
			 }			 
		} catch (Exception e) {
			log.error("Error", e);
			 req.getSession().invalidate();
			 return Response.serverError().build();
		}
    }
    
    @Path("/profile")
    @GET
    @Produces({MediaType.APPLICATION_JSON}) 
    public Response getProfile(@Context HttpServletRequest req){
    	log.debug("");
    	
    	try {
    		User checkUser = checkUser(req);
 			if(checkUser != null){
 				return Response.ok(checkUser).build();
 			 }else{
 				 req.getSession().invalidate();
 				 return  Response.status(Status.UNAUTHORIZED).build();
 			 }			 
 		} catch (Exception e) {
 			log.error("Error", e);
 			 req.getSession().invalidate();
 			 return Response.serverError().build();
 		}
    }

	private User checkUser(HttpServletRequest req) {
		String token = req.getHeader(ResponseHeaderFilter.X_AUTH_TOKEN);
		log.debug("token "+token);
		if(token == null || token.isEmpty()) return  null;
		User checkUser = userServiceBean.checkUser(token);
		log.debug(" user "+checkUser);
		if(authorizeOAuth(checkUser))
			return checkUser;
		else
			return null;
	}
    
    
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response resgisterUser(User user, @Context HttpServletRequest req) throws Exception{
    	try {
    		String token = req.getHeader(ResponseHeaderFilter.X_AUTH_TOKEN);
    		
    		if(token != null && !token.isEmpty() ){
    			user.setToken(token);
    			log.debug("token "+token);
	    		if("GOOGLE".equalsIgnoreCase(user.getAuth_domain())){
	    			log.debug("DOMAIN GOOGLE");
					completeUserProfileWithGoogle(user);
				}else if("FACEBOOK".equalsIgnoreCase(user.getAuth_domain())){
					log.debug("DOMAIN GOOGLE");
					completeUserProfileWithFacebook(user);
				}else{
					return  Response.status(Status.NOT_ACCEPTABLE).build();
				}
    		}else{
				token = createAppToken(user);
				user.setToken(token);
    		}    		
			User userNew = userServiceBean.addUser(user);
			log.debug("resgisterUser ---> userNew"+userNew);
			if(userNew != null){
				log.debug("invio response "+200);
				 return Response.ok(userNew).header(ResponseHeaderFilter.X_AUTH_TOKEN, token).build();
			 }else{
				 req.getSession().invalidate();
				 log.debug("invio response "+406);
				 return  Response.status(Status.NOT_ACCEPTABLE).build();
			 }			 
			
		} catch (Exception e) {
			 log.error("Error ",e);
			 req.getSession().invalidate();
			 log.debug("invio response "+406);
			 return  Response.status(Status.NOT_ACCEPTABLE).build();
		}
    }

	/**
	 * @param user 
	 * @return 
	 * 
	 */
	private String createAppToken(User user) {
		return new BigInteger(130, new SecureRandom()).toString(8);
	}

	/**
	 * @param user 
	 * @throws Exception 
	 * 
	 */
	private void completeUserProfileWithFacebook(User user) throws Exception {
		
		
	}

	/**
	 * @param user 
	 * @throws Exception 
	 * 
	 */
	private void completeUserProfileWithGoogle(User user) throws Exception {
		log.debug("completeUserProfileWithGoogle --->  avvio chiamata a google per utente "+user);
		Client client = ClientBuilder.newClient();
        client.register(OAuth2ClientSupport.feature(user.getToken()));
        WebTarget baseTarget = client.target("https://www.googleapis.com/oauth2/v1/userinfo?alt=json");
        Response response = baseTarget.request().get();
        if(response.getStatus() == 200 && response.hasEntity()){
			GoogleProfile gooleProfile =  response.readEntity(GoogleProfile.class);
        	log.debug(" google profile "+gooleProfile);
        	user.setThumbnail(gooleProfile.getPicture());
        	user.setLocale(gooleProfile.getLocale());
        	user.setGender(gooleProfile.getGender());
        	log.debug("filled user"+user);
        }else{
        	throw new Exception("Google Response "+response.getStatus());
        }
		
	}
	
	private boolean authorizeOAuth(User user) {
		String auth_domain = user.getAuth_domain();
		log.debug("domain "+auth_domain);
		if(auth_domain == null || auth_domain.isEmpty()) return true;
		if("GOOGLE".equalsIgnoreCase(auth_domain)){
			log.debug("avvio chiamata a google per utente "+user);
			Client client = ClientBuilder.newClient();
	        client.register(OAuth2ClientSupport.feature(user.getToken()));
	        WebTarget baseTarget = client.target("https://www.googleapis.com/oauth2/v1/userinfo?alt=json");
	        Response response = baseTarget.request().get();
	        if(response.getStatus() == 200 && response.hasEntity()){
	        	GoogleProfile gooleProfile =  response.readEntity(GoogleProfile.class);
	        	log.debug("google profile "+gooleProfile);
	        	return gooleProfile != null;
	        }
	        log.debug(" user "+user+" not authorized ");
	        return false;
		}else if("FACEBOOK".equalsIgnoreCase(auth_domain)){
			//TODO DA IMPLEMENTARE
			return false;
		}
		return false;
		
	}

}
