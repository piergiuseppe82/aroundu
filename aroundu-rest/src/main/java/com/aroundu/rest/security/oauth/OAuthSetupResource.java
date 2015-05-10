/**
 * 
 */
package com.aroundu.rest.security.oauth;

import java.net.URI;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.client.oauth2.ClientIdentifier;

/**
 * @author piergiuseppe82
 *
 */
@Path("setup")
public class OAuthSetupResource {
    @Context
    private UriInfo uriInfo;
    
    

    @GET
    @Produces("text/html")
    @Path("google")
    public Response setupFacebook(@QueryParam("clientId") String consumerKey,
                          @QueryParam("clientSecret") String consumerSecret) {
    	
    	
        OAuth2ClientIdStore.setClientIdentifier(new ClientIdentifier(consumerKey, consumerSecret));
        final URI uri = UriBuilder.fromUri(uriInfo.getBaseUri()).path("google/profile")
                .build();

        return Response.seeOther(uri).build();
    }
    

    @GET
    @Produces("text/html")
    @Path("facebook")
    public Response setupGoogle(@QueryParam("clientId") String consumerKey,
                          @QueryParam("clientSecret") String consumerSecret) {
    	
    	
        OAuth2ClientIdStore.setFbClientIdentifier(new ClientIdentifier(consumerKey, consumerSecret));
        final URI uri = UriBuilder.fromUri(uriInfo.getBaseUri()).path("facebook/profile")
                .build();

        return Response.seeOther(uri).build();
    }
}