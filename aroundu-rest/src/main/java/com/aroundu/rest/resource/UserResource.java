package com.aroundu.rest.resource;

import java.net.URI;

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

import com.aroundu.core.infrastructure.ServiceBeanFactory;
import com.aroundu.core.model.User;
import com.aroundu.core.services.UserServiceBean;

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
			user.setFullName("Piergiuseppe La Cava");
			user.setPassword("secret1");
			user.setProfileImage("-");
			return Response.ok(user).build();
			
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
    }
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/auth")
    public Response auth(@QueryParam("username") String username,@QueryParam("password") String password, @Context HttpServletRequest req) throws Exception{
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
    
    @Path("/myprofile")
    @GET
    @Produces({MediaType.APPLICATION_JSON}) 
    public Response getpingUser(@Context HttpServletRequest req){
    	 try {
    		 User checkUser = (User) req.getSession().getAttribute("user");
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
    
    @Path("/myprofile/logout")
    @GET
    @Produces({MediaType.APPLICATION_JSON})  
    public Response logout(@Context HttpServletRequest req){
    	 try {
    		
			 req.getSession().invalidate();
			 return  Response.status(Status.UNAUTHORIZED).build();
 						 
 		} catch (Exception e) {
 			 e.printStackTrace();
 			 req.getSession().invalidate();
 			 return Response.serverError().build();
 		}
    }
    
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response resgisterUser(User user, @Context HttpServletRequest req) throws Exception{
    	System.out.println("Invocated resgisterUser"+user);
         try {
			User userNew = userServiceBean.addUser(user);
			 if(userNew != null){
				 URI location = new URI("users/"+userNew.getId());
				 return Response.created(location).build();
			 }else{
				 req.getSession().invalidate();
				 return  Response.status(Status.NOT_ACCEPTABLE).build();
			 }			 
		} catch (Exception e) {
			 e.printStackTrace();
			 req.getSession().invalidate();
			 return Response.serverError().build();
		}
    }

}
