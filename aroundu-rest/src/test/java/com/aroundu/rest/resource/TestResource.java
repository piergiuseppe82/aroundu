/**
 * 
 */
package com.aroundu.rest.resource;

import com.aroundu.core.model.Event;
import com.aroundu.core.model.User;
import com.aroundu.core.services.utilities.MediaFilesMock;

/**
 * @author piergiuseppe82
 *
 */
public abstract class TestResource {
	protected static final String GOOGLE_TOKEN = "";
	protected static final String FACEBOOK_TOKEN = "";
	protected static final String INTERNAL_TOKEN = "cc57a71ba41fe82450b39e15ee68edabdc88a6a39e804b9d510aae948ba09ceb461f49bbfb";
	
	protected User makeFakeUser(String username) {
		User p = new User();
		p.setUsername(username+System.currentTimeMillis());
		p.setEmail(username+"@"+username+".com");
		p.setDisplayName(username+"_fn");
		p.setPassword(username+"_pw");
		p.setDisplayName(username+"_dn");
		p.setImage(MediaFilesMock.IMAGE_64_X_64);
//		p.setToken(new BigInteger(130, new SecureRandom()).toString(8));
		return p;
	}
	
	protected User makeFakeUserGoogleToken() {
		User p = new User();
		p.setUsername(System.currentTimeMillis()+"@GOOGLE");
		p.setEmail("piergiuseppe82@gmail.com");
		p.setDisplayName("piergiuseppe la cava");
		p.setAuth_domain("GOOGLE");
		
//		p.setToken(new BigInteger(130, new SecureRandom()).toString(8));
		return p;
	}
	
	protected User makeFakeUserFacebookToken() {
		User p = new User();
		p.setUsername(System.currentTimeMillis()+"@FACEBOOK");
		p.setEmail("piergiuseppe82@yahoo.com");
		p.setDisplayName("piergiuseppe la cava");
		p.setAuth_domain("FACEBOOK");
		
//		p.setToken(new BigInteger(130, new SecureRandom()).toString(8));
		return p;
	}
	
	protected User makeFakeAuthor(String username) {
		User p = new User();
		p.setUsername(username+System.currentTimeMillis());
		p.setEmail(username+"@"+username+".com");
		p.setDisplayName(username+"_fn");
		p.setPassword(username+"_pw");
		p.setDisplayName(username+"_dn");
		p.setImage(MediaFilesMock.IMAGE_64_X_64);
//		p.setToken(new BigInteger(130, new SecureRandom()).toString(8));
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
		event.setEventImage(MediaFilesMock.IMAGE_64_X_64);
		event.setTitle(title);
		event.setAddress(title+"_add");
		return event;
	}
	
}
