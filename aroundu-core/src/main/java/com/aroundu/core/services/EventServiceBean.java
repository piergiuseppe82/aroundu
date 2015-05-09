package com.aroundu.core.services;

import java.util.Collection;

import org.neo4j.graphdb.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aroundu.core.infrastructure.RepositoryBean;
import com.aroundu.core.infrastructure.ServiceBean;
import com.aroundu.core.model.User;
import com.aroundu.core.model.Event;
import com.aroundu.core.repopsitories.UserRepositoryBean;
import com.aroundu.core.repopsitories.EventRepositoryBean;
import com.aroundu.core.repopsitories.ImageRepositoryBean.ImageDimesionType;

/**
 * @author piergiuseppe82
 *
 */
public class EventServiceBean extends ServiceBean {
	private EventRepositoryBean eventRepositoryBean;
	private UserRepositoryBean userRepositoryBean;
	
	Logger log = LoggerFactory.getLogger(EventServiceBean.class);
	
	public UserRepositoryBean getUserRepositoryBean() {
		return userRepositoryBean;
	}

	public void setUserRepositoryBean(UserRepositoryBean personRepositoryBean) {
		this.userRepositoryBean = personRepositoryBean;
	}

	
	public EventRepositoryBean getEventRepositoryBean() {
		return eventRepositoryBean;
	}

	public void setEventRepositoryBean(EventRepositoryBean postRepositoryBean) {
		this.eventRepositoryBean = postRepositoryBean;
	}

	
	

	
	public Event addEvent(Event event){
		try(Transaction tx = userRepositoryBean.getGraphDatabaseServices().beginTx()){
			Long userid = event.getAuthor().getId();
			User user = userRepositoryBean.findUser(userid);			
			if(user != null){
				String image = event.getEventImage();
				String eventUrlThum = getImageRepositoryBean().saveImage(image, ImageDimesionType.THUMBNAILS_IMAGE, user.getUsername());
				event.setEventImageUrl(eventUrlThum);
				event = eventRepositoryBean.createEvent(event);
				userRepositoryBean.createRelationshipTo(user, event, RepositoryBean.RelTypes.POST );
				tx.success();
				return event;
			}
			tx.failure();
		}catch(Throwable t){
			log.error("Error", t);
		}
		return null;
		
	}
	
	
	public Collection<Event> getEvents(){
		try(Transaction tx = userRepositoryBean.getGraphDatabaseServices().beginTx()){
			Collection<Event> findAll = eventRepositoryBean.findAll();
			tx.success();
			return findAll;
		}catch(Throwable t){
			log.error("Error", t);
		}
		return null;
		
	}
	
	public Collection<Event> getEvents(long from, long to, boolean asc){
		try(Transaction tx = userRepositoryBean.getGraphDatabaseServices().beginTx()){
			Collection<Event> findAll = eventRepositoryBean.findAllPaginate(from, to, asc);
			tx.success();
			return findAll;
		}catch(Throwable t){
			log.error("Error", t);
		}
		return null;
		
	}
	
	public Collection<Event> getEvents(long from, long to){
		try(Transaction tx = userRepositoryBean.getGraphDatabaseServices().beginTx()){
			Collection<Event> findAll = eventRepositoryBean.findAllPaginate(from, to, false);
			tx.success();
			return findAll;
		}catch(Throwable t){
			log.error("Error", t);
		}
		return null;
		
	}
	
	public Collection<Event> getEvents(double latitude, double longitude, double distance){
		try(Transaction tx = userRepositoryBean.getGraphDatabaseServices().beginTx()){
			Collection<Event> findAll = eventRepositoryBean.findByDistance(latitude, longitude, distance);
			tx.success();
			return findAll;
		}catch(Throwable t){
			log.error("Error", t);
		}
		return null;
		
	}
	
	public Collection<Event> getEvents(double latitude, double longitude, double distance, long from, long to){
		try(Transaction tx = userRepositoryBean.getGraphDatabaseServices().beginTx()){
			Collection<Event> findAll = eventRepositoryBean.findByDistancePaginate(latitude, longitude, distance, from, to);
			tx.success();
			return findAll;
		}catch(Throwable t){
			log.error("Error", t);
		}
		return null;
		
	}
	
	
}
