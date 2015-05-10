/**
 * 
 */
package com.aroundu.rest.resource;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.NotAuthorizedException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aroundu.core.infrastructure.ServiceBeanFactory;
import com.aroundu.core.model.User;
import com.aroundu.core.services.EventServiceBean;
import com.aroundu.core.services.UserServiceBean;
import com.aroundu.rest.filters.ResponseHeaderFilter;
import com.aroundu.rest.security.ProviderApiClientsServices;

/**
 * @author piergiuseppe82
 *
 */
public abstract class AbstractAppResource {
	protected UserServiceBean userServiceBean =  ServiceBeanFactory.getInstance().getUserServiceBean();
	protected EventServiceBean eventServiceBean =  ServiceBeanFactory.getInstance().getEventServiceBean();
	Logger log = LoggerFactory.getLogger(UserResource.class);
	
	protected User getAuthorizedUser(HttpServletRequest req) {
		try {
			String token = req.getHeader(ResponseHeaderFilter.X_AUTH_TOKEN);
			if(token == null || token.isEmpty()) return  null;
			User checkUser = userServiceBean.checkUser(token);
			if(ProviderApiClientsServices.getProvider(checkUser).authorize(checkUser))
				return checkUser;
			else
				throw new NotAuthorizedException("Not Autorized");
		} catch (Throwable e) {
			log.error("Error",e);
			throw new NotAuthorizedException("Not Autorized");
		}
	}
}
