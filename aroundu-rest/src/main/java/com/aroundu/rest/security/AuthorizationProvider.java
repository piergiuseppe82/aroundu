/**
 * 
 */
package com.aroundu.rest.security;

import com.aroundu.core.model.User;

/**
 * @author piergiuseppe82
 *
 */
public interface AuthorizationProvider {
	public boolean authorize(User user);
	public void completeUserProfile(User user) throws Exception;
}
