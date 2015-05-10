package com.aroundu.rest.resource;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aroundu.core.model.User;
import com.aroundu.core.supports.SecurityTokenProvider;
import com.aroundu.rest.filters.ResponseHeaderFilter;
import com.aroundu.rest.security.ProviderApiClientsServices;

/**
 * @author piergiuseppe82
 *
 */
@Path("/users")
public class UserResource extends AbstractAppResource {
	Logger log = LoggerFactory.getLogger(UserResource.class);
	@Context HttpServletRequest req;
	
    @Path("/{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON}) 
    public Response getUser(@PathParam("id") Long id){
		getAuthorizedUser(req);
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
			log.error("Error",e);
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
			log.error("Error",e);
			return Response.serverError().build();
		}
    }
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/login")
    public Response login(@QueryParam("username") String username,@QueryParam("password") String password ) throws Exception{
         try {
        	User checkUser = userServiceBean.checkUser(username, password);
			 if(checkUser != null){
				return Response.ok(checkUser).header(ResponseHeaderFilter.X_AUTH_TOKEN, checkUser.getToken()).build();
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
    public Response getProfile(){
    	User userFromRequest = getAuthorizedUser(req);    	
    	return Response.ok(userFromRequest).build();
    }

    
    
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response resgisterUser(User user) throws Exception{
    	try {
    		String token = req.getHeader(ResponseHeaderFilter.X_AUTH_TOKEN);
    		
    		if(token != null && !token.isEmpty() ){
    			user.setToken(token);
    			log.debug("token "+token);
	    		ProviderApiClientsServices.getProvider(user).completeUserProfile(user);
    		}else{
				token = SecurityTokenProvider.generateSecret();
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

		
	

}
