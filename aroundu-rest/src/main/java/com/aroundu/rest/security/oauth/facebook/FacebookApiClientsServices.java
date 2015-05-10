/**
 * 
 */
package com.aroundu.rest.security.oauth.facebook;

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
public class FacebookApiClientsServices extends ProviderApiClientsServices{
	Logger log = LoggerFactory.getLogger(FacebookApiClientsServices.class);
	private static FacebookApiClientsServices instance;
	
	private FacebookApiClientsServices(){
		super();
	}
	
	
	public static FacebookApiClientsServices instance() {
		if(instance==null){
			synchronized (FacebookApiClientsServices.class){
				if(instance==null)
					instance = new FacebookApiClientsServices();
			}
		}			
		return instance;
	}
	
	public FacebookProfile getProfile(String token) {
		   Client client = ClientBuilder.newClient();
	       client.register(OAuth2ClientSupport.feature(token));
	       WebTarget baseTarget = client.target("https://graph.facebook.com/me");
	       Response response = baseTarget.request().get();
	       if(response.getStatus() == 200 && response.hasEntity()){
	    	   FacebookProfile fbprofile = response.readEntity(FacebookProfile.class);
	    	   if(fbprofile != null && fbprofile.getId() != null){
			       baseTarget = client.target("https://graph.facebook.com/"+fbprofile.getId()+"?fields=picture.type(large)");
			       response = baseTarget.request().get();			      
			       if(response.getStatus() == 200 && response.hasEntity()){
			    	   FacebookProfile readimage = response.readEntity(FacebookProfile.class);
			    	   if(readimage != null)
				    	   fbprofile.setPicture(readimage.getPicture());
			       }			       
		    	  return fbprofile; 
	       		}
		        	
	       }
	       return null;
	}
	
	
	@Override
	public boolean authorize(User user) {
		   Client client = ClientBuilder.newClient();
	       client.register(OAuth2ClientSupport.feature(user.getToken()));
	       WebTarget baseTarget = client.target("https://graph.facebook.com/me");
	       Response response = baseTarget.request().get();
	       if(response.getStatus() == 200 && response.hasEntity()){
	    	   FacebookProfile fbprofile = response.readEntity(FacebookProfile.class);
	    	   return (fbprofile.getId()+"@"+user.getAuth_domain()).equals(user.getUsername());
	       }
	       return false;
	}


	/* (non-Javadoc)
	 * @see com.aroundu.rest.security.AuthorizationProvider#completeUserProfile(com.aroundu.core.model.User)
	 */
	@Override
	public void completeUserProfile(User user) throws Exception {
		FacebookProfile profile = this.getProfile(user.getToken());
		if(profile != null){
			try {
				user.setUsername(profile.getId()+"@"+user.getAuth_domain());
				user.setThumbnail(profile.getPicture().getData().getUrl());
			} catch (Exception e) {
				log.warn("No avatar image found for facebook user "+user.getUsername());
			}
        	user.setLocale(profile.getLocale());
        	user.setGender(profile.getGender());
        	log.debug("filled user "+user);
        }else{
        	throw new Exception("Profile not loaded");
        }
		
	}


	
	
	
	
}
