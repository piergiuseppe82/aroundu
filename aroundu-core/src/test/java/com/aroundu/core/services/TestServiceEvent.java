package com.aroundu.core.services;

import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aroundu.core.model.Event;

/**
 * @author piergiuseppe82
 *
 */
public class TestServiceEvent extends TestService{
	Logger log = LoggerFactory.getLogger(TestServiceEvent.class);
	
	
	
	@Test
	public void testGetAllEvent() {
		EventServiceBean eventServiceBean = factory.getEventServiceBean();
		addfakeEvent("testGetAllEvent1",null,null);
		addfakeEvent("testGetAllEvent2",null,null);
		addfakeEvent("testGetAllEvent3",null,null);
		Collection<Event> events = eventServiceBean.getEvents();
		Assert.assertTrue(events !=null && events.size() >= 3);	
		for (Event event : events) {
			log.debug(""+event);
		}
	}
	
	@Test
	public void testGetAllEventPaginate() {
		EventServiceBean eventServiceBean = factory.getEventServiceBean();
		addfakeEvent("testGetAllEventPaginate1",null,null);
		addfakeEvent("testGetAllEventPaginate2",null,null);
		addfakeEvent("testGetAllEventPaginate3",null,null);
		addfakeEvent("testGetAllEventPaginate4",null,null);
		addfakeEvent("testGetAllEventPaginate5",null,null);
		addfakeEvent("testGetAllEventPaginate6",null,null);
		Collection<Event> events = eventServiceBean.getEvents(0, 2);
		Assert.assertTrue(events !=null && events.size() == 2);	
		for (Event event : events) {
			log.debug(""+event);
		}
	}
	
	@Test
	public void testGetAllEventWhithDistance() {
		EventServiceBean eventServiceBean = factory.getEventServiceBean();
		addfakeEvent("1 ROMA - GIARDINI VATICANI",41.903609, 12.450288);
		addfakeEvent("1 ROMA - ISOLA TIBERINA",41.890828, 12.477046);
		addfakeEvent("1 ROMA - PIAZZA DEI CINQUECENTO",41.900794, 12.502023);
		addfakeEvent("1 ROMA - TERME DI CARACALLA",41.879262, 12.492324);
		addfakeEvent("1 ROMA - COLOSSEO",41.890380, 12.492410);
		addfakeEvent("1 ROMA - UNIVERSITA' LA SAPIENZA",41.903541, 12.514554);
		Collection<Event> events = eventServiceBean.getEvents(41.902235, 12.456800/*ROMA - P.ZZA S.Pietro*/,3.0);
		for (Event event : events) {
			Assert.assertTrue(event.getDistance() != null && event.getDistance().doubleValue() <= 3.0);	
			log.debug(""+event);
		}
	}
	
	@Test
	public void testGetAllEventWhithDistancePaginate() {
		EventServiceBean eventServiceBean = factory.getEventServiceBean();
		addfakeEvent("ROMA - GIARDINI VATICANI",41.903609, 12.450288);
		addfakeEvent("ROMA - ISOLA TIBERINA",41.890828, 12.477046);
		addfakeEvent("ROMA - PIAZZA DEI CINQUECENTO",41.900794, 12.502023);
		addfakeEvent("ROMA - TERME DI CARACALLA",41.879262, 12.492324);
		addfakeEvent("ROMA - COLOSSEO",41.890380, 12.492410);
		addfakeEvent("ROMA - UNIVERSITA' LA SAPIENZA",41.903541, 12.514554);
		Collection<Event> events = eventServiceBean.getEvents(41.902235, 12.456800/*ROMA - P.ZZA S.Pietro*/,3.0,0,1);
		Assert.assertTrue(events !=null && events.size() ==1);	
		for (Event event : events) {
			Assert.assertTrue(event.getDistance() != null && event.getDistance().doubleValue() <= 3.0);	
			log.debug(""+event);
		}
	}

	@Test
	public void testAddEvent(){
		Event addEvent = addfakeEvent("testAddEvent", null, null);
		Assert.assertTrue(addEvent.getId() > -1);
		log.debug(""+addEvent);
	}

	

	
	
	
}
