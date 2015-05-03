package com.aroundu.core.services;

import java.util.Collection;

import org.neo4j.graphdb.Transaction;

import com.aroundu.core.infrastructure.RepositoryBean;
import com.aroundu.core.infrastructure.ServiceBean;
import com.aroundu.core.model.User;
import com.aroundu.core.model.Event;
import com.aroundu.core.repopsitories.UserRepositoryBean;
import com.aroundu.core.repopsitories.EventRepositoryBean;

/**
 * @author piergiuseppe82
 *
 */
public class EventServiceBean extends ServiceBean {
	private EventRepositoryBean eventRepositoryBean;
	private UserRepositoryBean userRepositoryBean;
	
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
				event = eventRepositoryBean.createEvent(event);
				userRepositoryBean.createRelationshipTo(user, event, RepositoryBean.RelTypes.POST );
				tx.success();
				return event;
			}
			tx.failure();
		}catch(Throwable t){
			t.printStackTrace();
		}
		return null;
		
	}
	
	
	public Collection<Event> getEvents(){
		try(Transaction tx = userRepositoryBean.getGraphDatabaseServices().beginTx()){
			Collection<Event> findAll = eventRepositoryBean.findAll();
			tx.success();
			return findAll;
		}catch(Throwable t){
			t.printStackTrace();
		}
		return null;
		
	}
	
	public Collection<Event> getEvents(long from, long to, boolean asc){
		try(Transaction tx = userRepositoryBean.getGraphDatabaseServices().beginTx()){
			Collection<Event> findAll = eventRepositoryBean.findAllPaginate(from, to, asc);
			tx.success();
			return findAll;
		}catch(Throwable t){
			t.printStackTrace();
		}
		return null;
		
	}
	
	public Collection<Event> getEvents(long from, long to){
		try(Transaction tx = userRepositoryBean.getGraphDatabaseServices().beginTx()){
			Collection<Event> findAll = eventRepositoryBean.findAllPaginate(from, to, false);
			tx.success();
			return findAll;
		}catch(Throwable t){
			t.printStackTrace();
		}
		return null;
		
	}
	
	public Collection<Event> getEvents(double latitude, double longitude, double distance){
		try(Transaction tx = userRepositoryBean.getGraphDatabaseServices().beginTx()){
			Collection<Event> findAll = eventRepositoryBean.findByDistance(latitude, longitude, distance);
			tx.success();
			return findAll;
		}catch(Throwable t){
			t.printStackTrace();
		}
		return null;
		
	}
	
	public Collection<Event> getEvents(double latitude, double longitude, double distance, long from, long to){
		try(Transaction tx = userRepositoryBean.getGraphDatabaseServices().beginTx()){
			Collection<Event> findAll = eventRepositoryBean.findByDistancePaginate(latitude, longitude, distance, from, to);
			tx.success();
			return findAll;
		}catch(Throwable t){
			t.printStackTrace();
		}
		return null;
		
	}
	
	
}
