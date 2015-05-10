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
	    private static ClientIdentifier fbClientIdentifier;

	    public static  ClientIdentifier getClientIdentifier() {
	        return clientIdentifier;
	    }

	    public static void setClientIdentifier(ClientIdentifier clientIdentifier) {
	    	OAuth2ClientIdStore.clientIdentifier = clientIdentifier;
	    }

		public static ClientIdentifier getFbClientIdentifier() {
			return fbClientIdentifier;
		}

		public static void setFbClientIdentifier(ClientIdentifier fbClientIdentifier) {
			OAuth2ClientIdStore.fbClientIdentifier = fbClientIdentifier;
		}

}
