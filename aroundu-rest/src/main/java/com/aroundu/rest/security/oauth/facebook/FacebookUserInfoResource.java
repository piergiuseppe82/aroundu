/**
 * 
 */
package com.aroundu.rest.security.oauth.facebook;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.client.oauth2.OAuth2ClientSupport;
import org.glassfish.jersey.client.oauth2.OAuth2CodeGrantFlow;

import com.aroundu.rest.security.oauth.OAuth2ClientIdStore;

/**
 * @author piergiuseppe82
 *
 */
@Path("facebook")
public class FacebookUserInfoResource {
   
    @Context
    private UriInfo uriInfo;

   
    
   @GET
   @Path("profile")
   @Produces(MediaType.APPLICATION_JSON)
   public Response getProfile( @Context HttpServletRequest req) {
   	  String token = (String)req.getSession().getAttribute("facebookAuthToken");
   	  
   	  if (token == null) {
      	//Start authentication flow - Only Work whit session cookie enabled (not work in classic rest process)
           String redirectURI = UriBuilder.fromUri(uriInfo.getBaseUri())
                   .path("auth/facebook").build().toString();
           OAuth2CodeGrantFlow flow = OAuth2ClientSupport.facebookFlowBuilder(
           		OAuth2ClientIdStore.getFbClientIdentifier(),
                   redirectURI).build();
          String facebookAuthURI = flow.start();
          req.getSession().setAttribute("facebookFlow", flow);
          return Response.seeOther(UriBuilder.fromUri(facebookAuthURI).build()).build();
       }
       
       FacebookProfile facebookProfile = FacebookApiClientsServices.instance().getProfile(token);
       return Response.ok(facebookProfile).header("X-Auth-Token", token).build(); 
   }
   
   


	
    
   
}