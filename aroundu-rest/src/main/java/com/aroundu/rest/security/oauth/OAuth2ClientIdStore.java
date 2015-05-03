/**
 * 
 */
package com.aroundu.rest.security.oauth;

import org.glassfish.jersey.client.oauth2.ClientIdentifier;

/**
 * @author piergiuseppe82
 *
 */
public class OAuth2ClientIdStore {
	

	    private static ClientIdentifier clientIdentifier;

	    public static  ClientIdentifier getClientIdentifier() {
	        return clientIdentifier;
	    }

	    public static void setClientIdentifier(ClientIdentifier clientIdentifier) {
	    	OAuth2ClientIdStore.clientIdentifier = clientIdentifier;
	    }

}
