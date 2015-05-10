/**
 * 
 */
package com.aroundu.rest.security;

import com.aroundu.core.model.User;
import com.aroundu.rest.security.oauth.facebook.FacebookApiClientsServices;
import com.aroundu.rest.security.oauth.google.GoogleApiClientsServices;

/**
 * @author piergiuseppe82
 *
 */
public abstract class ProviderApiClientsServices implements AuthorizationProvider{
	public static synchronized ProviderApiClientsServices getProvider(User user){
		if("GOOGLE".equalsIgnoreCase(user.getAuth_domain())){
			return GoogleApiClientsServices.instance();
		}else if("FACEBOOK".equalsIgnoreCase(user.getAuth_domain())){
			return FacebookApiClientsServices.instance();
		}
		return InternalApiClientsServices.instance();
	}	
}
