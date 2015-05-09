/**
 * 
 */
package com.aroundu.rest.resource;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.client.oauth2.OAuth2CodeGrantFlow;
import org.glassfish.jersey.client.oauth2.TokenResult;

/**
 * @author piergiuseppe82
 *
 */
@Path("auth")
public class AuthResource {
	@Context
    private UriInfo uriInfo;
	
    @GET
    @Path("authorize")
    public Response authorize(@QueryParam("code") String code, @QueryParam("state") String state,@Context HttpServletRequest req) {
        OAuth2CodeGrantFlow flow = (OAuth2CodeGrantFlow)req.getSession().getAttribute("flow");
        TokenResult tokenResult = flow.finish(code, state);
        req.getSession().setAttribute("authtoken",tokenResult.getAccessToken());
         // authorization is finished -> now redirect back to the task resource
        req.getSession().removeAttribute("flow");
        final URI uri = UriBuilder.fromUri(uriInfo.getBaseUri()).path("googleinfo/profile").build();
        return Response.seeOther(uri).build();
    }
  
}
