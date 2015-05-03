/**
 * 
 */
package com.aroundu.rest.filters;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

/**
 * @author piergiuseppe82
 *
 */
@Provider
public class ResponseHeaderFilter implements  ContainerResponseFilter{

	/**
	 * 
	 */
	public static final String X_AUTH_TOKEN = "X-Auth-Token";

	/* (non-Javadoc)
	 * @see javax.ws.rs.container.ContainerResponseFilter#filter(javax.ws.rs.container.ContainerRequestContext, javax.ws.rs.container.ContainerResponseContext)
	 */
	@Override
	public void filter(ContainerRequestContext requestContext,
			ContainerResponseContext responseContext) throws IOException {
		String headerString = requestContext.getHeaderString(X_AUTH_TOKEN);
		if(headerString != null)
			responseContext.getHeaders().add(X_AUTH_TOKEN, headerString);
		
	}

	
	

}
