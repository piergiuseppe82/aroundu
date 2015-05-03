/**
 * 
 */
package com.aroundu.rest.resource;

import com.aroundu.core.model.Event;
import com.aroundu.core.model.User;

/**
 * @author piergiuseppe82
 *
 */
public abstract class TestResource {
	protected User makeFakeUser(String username) {
		User p = new User();
		p.setUsername(username);
		p.setEmail(username+"@"+username+".com");
		p.setFullName(username+"_fn");
		p.setPassword(username+"_pw");
		p.setDisplayName(username+"_dn");
		p.setProfileImageUrl(username+"_img_url");
		return p;
	}
	
	protected User makeFakeAuthor(String username) {
		User p = new User();
		p.setUsername(username);
		p.setEmail(username+"@"+username+".com");
		p.setFullName(username+"_fn");
		p.setPassword(username+"_pw");
		p.setDisplayName(username+"_dn");
		p.setProfileImageUrl(username+"_img_url");
		return p;
	}
	
	
	
	protected Event makeFakeEvent(String title, Double latitude, Double longitude) {
		Event event = new Event();
		User author = makeFakeAuthor(title+"Author");
		event.setAuthor(author);
		if(latitude ==null || longitude ==null){
			event.setPosition("41.9677526,12.6594818");
		}else{
			event.setLatitude(latitude!=null?latitude:41.9677526);
			event.setLongitude(longitude!=null?longitude:12.6594818);
		}	
		event.setEventImageUrl(title+"_image_url");
		event.setTitle(title);
		event.setAddress(title+"_add");
		return event;
	}
	
}
