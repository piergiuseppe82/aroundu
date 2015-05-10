/**
 * 
 */
package com.aroundu.rest.security.oauth;

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
public class OAuthResource {
	@Context
    private UriInfo uriInfo;
	
    @GET
    @Path("google")
    public Response authorize(@QueryParam("code") String code, @QueryParam("state") String state,@Context HttpServletRequest req) {
        OAuth2CodeGrantFlow flow = (OAuth2CodeGrantFlow)req.getSession().getAttribute("googleFlow");
        TokenResult tokenResult = flow.finish(code, state);
        req.getSession().setAttribute("googleAuthToken",tokenResult.getAccessToken());
         // authorization is finished -> now redirect back to the task resource
        req.getSession().removeAttribute("googleFlow");
        final URI uri = UriBuilder.fromUri(uriInfo.getBaseUri()).path("google/profile").build();
        return Response.seeOther(uri).build();
    }
    
    @GET
    @Path("facebook")
    public Response fbauthorize(@QueryParam("code") String code, @QueryParam("state") String state,@Context HttpServletRequest req) {
        OAuth2CodeGrantFlow flow = (OAuth2CodeGrantFlow)req.getSession().getAttribute("facebookFlow");
        TokenResult tokenResult = flow.finish(code, state);
        req.getSession().setAttribute("facebookAuthToken",tokenResult.getAccessToken());
         // authorization is finished -> now redirect back to the task resource
        req.getSession().removeAttribute("facebookFlow");
        final URI uri = UriBuilder.fromUri(uriInfo.getBaseUri()).path("facebook/profile").build();
        return Response.seeOther(uri).build();
    }
  
}
