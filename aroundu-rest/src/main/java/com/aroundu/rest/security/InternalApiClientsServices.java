/**
 * 
 */
package com.aroundu.rest.security;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.oauth2.OAuth2ClientSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aroundu.core.model.User;
import com.aroundu.rest.security.oauth.facebook.FacebookApiClientsServices;
import com.aroundu.rest.security.oauth.facebook.FacebookProfile;

/**
 * @author piergiuseppe82
 *
 */
public class InternalApiClientsServices extends ProviderApiClientsServices{
	Logger log = LoggerFactory.getLogger(InternalApiClientsServices.class);
	private static InternalApiClientsServices instance;
	
	private InternalApiClientsServices(){
		super();
	}
	
	
	public static InternalApiClientsServices instance() {
		if(instance==null){
			synchronized (InternalApiClientsServices.class){
				if(instance==null)
					instance = new InternalApiClientsServices();
			}
		}			
		return instance;
	}
	
	
	
	
	@Override
	public boolean authorize(User user) {
		   return user != null && user.getId() > -1 && user.getAuth_domain() ==null;
	}


	/* (non-Javadoc)
	 * @see com.aroundu.rest.security.AuthorizationProvider#completeUserProfile(com.aroundu.core.model.User)
	 */
	@Override
	public void completeUserProfile(User user) throws Exception {
	
		
	}


	
	
	
	
}
