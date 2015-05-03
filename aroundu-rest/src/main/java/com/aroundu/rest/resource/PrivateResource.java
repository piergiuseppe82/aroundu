/**
 * 
 */
package com.aroundu.rest.resource;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.client.oauth2.OAuth2ClientSupport;
import org.glassfish.jersey.client.oauth2.OAuth2CodeGrantFlow;
import org.glassfish.jersey.client.oauth2.OAuth2FlowGoogleBuilder;

import com.aroundu.rest.security.oauth.OAuth2ClientIdStore;

/**
 * @author piergiuseppe82
 *
 */
@Path("private")
public class PrivateResource {
   
    @Context
    private UriInfo uriInfo;

   
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTasks( @Context HttpServletRequest req) {
    	String token = (String)req.getSession().getAttribute("authtoken");
        if (token == null) {
            String redirectURI = UriBuilder.fromUri(uriInfo.getBaseUri())
                    .path("auth/authorize").build().toString();

            OAuth2CodeGrantFlow flow = OAuth2ClientSupport.googleFlowBuilder(
            		OAuth2ClientIdStore.getClientIdentifier(),
                    redirectURI,
                    "profile")
                    .prompt(OAuth2FlowGoogleBuilder.Prompt.CONSENT).build();

           

            // start the flow
           String googleAuthURI = flow.start();

           req.getSession().setAttribute("flow", flow);
            // redirect user to Google Authorization URI.
            return Response.seeOther(UriBuilder.fromUri(googleAuthURI).build()).build();
        }
        
        Client client = ClientBuilder.newClient();
        client.register(OAuth2ClientSupport.feature(token));
        WebTarget baseTarget = client.target("https://www.googleapis.com/oauth2/v1/userinfo?alt=json");
        Response response = baseTarget.request().get();
        return Response.ok(response.readEntity(new GenericType(String.class))).build(); 
    }

   
}