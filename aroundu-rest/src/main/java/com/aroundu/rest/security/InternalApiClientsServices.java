/**
 * 
 */
package com.aroundu.rest.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aroundu.core.model.User;

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
