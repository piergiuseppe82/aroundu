/**
 * 
 */
package com.aroundu.rest.security.oauth.google;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.oauth2.OAuth2ClientSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aroundu.core.model.User;
import com.aroundu.rest.security.ProviderApiClientsServices;

/**
 * @author piergiuseppe82
 *
 */
public class GoogleApiClientsServices extends ProviderApiClientsServices{
	Logger log = LoggerFactory.getLogger(GoogleApiClientsServices.class);
	
	private static GoogleApiClientsServices instance;
	
	private GoogleApiClientsServices(){
		super();
	}
	
	
	public static GoogleApiClientsServices instance() {
		if(instance==null){
			synchronized (GoogleApiClientsServices.class){
				if(instance==null)
					instance = new GoogleApiClientsServices();
			}
		}			
		return instance;
	}
	
	public GoogleProfile getProfile(String token) {
		 	Client client = ClientBuilder.newClient();
	        client.register(OAuth2ClientSupport.feature(token));
	        WebTarget baseTarget = client.target("https://www.googleapis.com/oauth2/v1/userinfo?alt=json");
	        Response response = baseTarget.request().get();
	        if(response.getStatus() == 200 && response.hasEntity()){
	        	return response.readEntity(GoogleProfile.class);
	        }
	        return null;
	}
	
	
	@Override
	public boolean authorize(User user) {
		  GoogleProfile profile = getProfile(user.getToken());
			if(profile != null){
	        	return (profile.getId()+"@"+user.getAuth_domain()).equals(user.getUsername());
	        }
	        log.warn(" user "+user+" not authorized ");
	        return false;
	}


	
	@Override
	public void completeUserProfile(User user) throws Exception {
				GoogleProfile profile = GoogleApiClientsServices.instance().getProfile(user.getToken());
			if(profile != null){
				user.setUsername(profile.getId()+"@"+user.getAuth_domain());
				user.setThumbnail(profile.getPicture());
	        	user.setLocale(profile.getLocale());
	        	user.setGender(profile.getGender());
	        	log.debug("filled user"+user);
	        }else{
	        	throw new Exception("Profile not loaded");
	        }
			
		
	}
	
	
}
