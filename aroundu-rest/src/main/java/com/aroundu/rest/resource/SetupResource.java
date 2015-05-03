/**
 * 
 */
package com.aroundu.rest.resource;

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

import com.aroundu.rest.security.oauth.OAuth2ClientIdStore;

/**
 * @author piergiuseppe82
 *
 */
@Path("setup")
public class SetupResource {
    @Context
    private UriInfo uriInfo;
    
    

    @GET
    @Produces("text/html")
    public Response setup(@QueryParam("clientId") String consumerKey,
                          @QueryParam("clientSecret") String consumerSecret) {
    	
    	
        OAuth2ClientIdStore.setClientIdentifier(new ClientIdentifier(consumerKey, consumerSecret));
        final URI uri = UriBuilder.fromUri(uriInfo.getBaseUri()).path("private")
                .build();

        return Response.seeOther(uri).build();
    }
}