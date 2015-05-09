/**
 * 
 */
package com.aroundu.rest.filters;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aroundu.rest.resource.TestUserResource;

/**
 * @author piergiuseppe82
 *
 */
@Provider
public class ResponseHeaderFilter implements  ContainerResponseFilter{
	Logger log = LoggerFactory.getLogger(ResponseHeaderFilter.class);
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
		if(headerString != null){
			log.debug("TOKEN ON REQUEST "+headerString);
			responseContext.getHeaders().add(X_AUTH_TOKEN, headerString);
		}else{
			log.debug("TOKEN ON RESPONSE "+headerString);
			responseContext.getHeaders().add(X_AUTH_TOKEN, headerString);
		}
		
	}

	
	

}
