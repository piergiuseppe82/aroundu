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
			 e.printStackTrace();
			 req.getSession().invalidate();
			 return Response.serverError().build();
		}
    }
    
    @Path("/profile")
    @GET
    @Produces({MediaType.APPLICATION_JSON}) 
    public Response getProfile(@Context HttpServletRequest req){
    	System.out.println("getProfile --->");
    	
    	try {
    		User checkUser = checkUser(req);
 			if(checkUser != null){
 				return Response.ok(checkUser).build();
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

	private User checkUser(HttpServletRequest req) {
		System.out.println("checkUser --->");
		String token = req.getHeader(ResponseHeaderFilter.X_AUTH_TOKEN);
		System.out.println("checkUser ---> token"+token);
		if(token == null || token.isEmpty()) return  null;
		User checkUser = userServiceBean.checkUser(token);
		System.out.println("checkUser ---> user"+checkUser);
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
    			System.out.println("resgisterUser ---> TOKEN ON REQUEST"+token);
	    		if("GOOGLE".equalsIgnoreCase(user.getAuth_domain())){
	    			System.out.println("resgisterUser ---> DOMAIN GOOGLE");
					completeUserProfileWithGoogle(user);
				}else if("FACEBOOK".equalsIgnoreCase(user.getAuth_domain())){
					System.out.println("resgisterUser ---> DOMAIN FACEBOOK");
					completeUserProfileWithFacebook(user);
				}else{
					return  Response.status(Status.NOT_ACCEPTABLE).build();
				}
    		}else{
				token = createAppToken(user);
				user.setToken(token);
    		}    		
			User userNew = userServiceBean.addUser(user);
			System.out.println("resgisterUser ---> userNew"+userNew);
			if(userNew != null){
				System.out.println("resgisterUser ---> invio response"+200);
				 return Response.ok(userNew).header(ResponseHeaderFilter.X_AUTH_TOKEN, token).build();
			 }else{
				 req.getSession().invalidate();
				 System.out.println("resgisterUser ---> invio response"+406);
				 return  Response.status(Status.NOT_ACCEPTABLE).build();
			 }			 
			
		} catch (Exception e) {
			 e.printStackTrace();
			 req.getSession().invalidate();
			 System.out.println("resgisterUser ---> invio response"+406);
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
		System.out.println("completeUserProfileWithGoogle --->  avvio chiamata a google per utente "+user);
		Client client = ClientBuilder.newClient();
        client.register(OAuth2ClientSupport.feature(user.getToken()));
        WebTarget baseTarget = client.target("https://www.googleapis.com/oauth2/v1/userinfo?alt=json");
        Response response = baseTarget.request().get();
        if(response.getStatus() == 200 && response.hasEntity()){
			GoogleProfile gooleProfile =  response.readEntity(GoogleProfile.class);
        	System.out.println("completeUserProfileWithGoogle ---> profile "+gooleProfile);
        	System.out.println(gooleProfile);
        	user.setThumbnail(gooleProfile.getPicture());
        	user.setLocale(gooleProfile.getLocale());
        	user.setGender(gooleProfile.getGender());
        	System.out.println("completeUserProfileWithGoogle ---> user filled "+user);
        }else{
        	throw new Exception("Response in error --->"+response.getStatus());
        }
		
	}
	
	private boolean authorizeOAuth(User user) {
		String auth_domain = user.getAuth_domain();
		System.out.println("authorizeOAuth ---> auth_domain"+auth_domain);
		if(auth_domain == null || auth_domain.isEmpty()) return true;
		if("GOOGLE".equalsIgnoreCase(auth_domain)){
			System.out.println("authorizeOuth --->  avvio chiamata a google per utente "+user);
			Client client = ClientBuilder.newClient();
	        client.register(OAuth2ClientSupport.feature(user.getToken()));
	        WebTarget baseTarget = client.target("https://www.googleapis.com/oauth2/v1/userinfo?alt=json");
	        Response response = baseTarget.request().get();
	        if(response.getStatus() == 200 && response.hasEntity()){
	        	GoogleProfile gooleProfile =  response.readEntity(GoogleProfile.class);
	        	System.out.println("authorizeOuth ---> profile "+gooleProfile);
	        	return gooleProfile != null;
	        }
	        System.out.println("authorizeOuth ---> user "+user+" not valid!!! ");
	        return false;
		}else if("FACEBOOK".equalsIgnoreCase(auth_domain)){
			//TODO DA IMPLEMENTARE
			return false;
		}
		return false;
		
	}

}
