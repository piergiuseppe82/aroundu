/**
 * 
 */
package com.aroundu.core.services;

import org.junit.Assert;
import org.junit.Before;

import com.aroundu.core.infrastructure.ServiceBeanFactory;
import com.aroundu.core.model.Event;
import com.aroundu.core.model.User;

/**
 * @author piergiuseppe82
 *
 */
public abstract class TestService {
	protected ServiceBeanFactory factory;
	
	@Before
	public void init(){
		factory = ServiceBeanFactory.getInstance("target/graphdb",true);
	}
	
	protected User makeFakeUser(String username) {
		User p = new User();
		p.setUsername(username);
		p.setEmail(username+"@"+username+".com");
		p.setFullName(username+"_fn");
		p.setPassword(username+"_pw");
		p.setDisplayName(username+"_dn");
		p.setProfileImage(MediaFilesMock.IMAGE_64_X_64);
		return p;
	}
	
	
	
	protected Event makeFakeEvent(String title, Double latitude, Double longitude) {
		Event event = new Event();
		User author = addFakeUser(title+"Author");
		event.setAuthor(author);
		if(latitude ==null || longitude ==null){
			event.setPosition("41.9677526,12.6594818");
		}else{
			event.setLatitude(latitude!=null?latitude:41.9677526);
			event.setLongitude(longitude!=null?longitude:12.6594818);
		}	
		event.setEventImageUrl(title+"_image_url");
		event.setEventImage(MediaFilesMock.IMAGE_64_X_64);
		event.setTitle(title);
		event.setAddress(title+"_add");
		return event;
	}
	
	protected Event addfakeEvent(String title,Double latitude, Double longitude) {
		EventServiceBean eventServiceBean = factory.getEventServiceBean();
		Event event = makeFakeEvent(title,latitude,longitude);
		Event addEvent = eventServiceBean.addEvent(event);
		Assert.assertTrue(addEvent.getId() > -1);
		return addEvent;
	}

	/**
	 * @return
	 */
	protected User addFakeUser(String user) {
		UserServiceBean userServiceBean = factory.getUserServiceBean();
		User addUser = userServiceBean.addUser(makeFakeUser(user));
		Assert.assertTrue(addUser.getId() > -1);	
		return addUser;
	}
	

}
