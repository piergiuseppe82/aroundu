package com.aroundu.rest.resource;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.aroundu.core.infrastructure.ServiceBeanFactory;
import com.aroundu.core.model.Event;
import com.aroundu.core.model.User;
import com.aroundu.core.services.EventServiceBean;

/**
 * @author piergiuseppe82
 *
 */
@Path("/events")
public class EventsResource {
    
	private EventServiceBean eventServiceBean =  ServiceBeanFactory.getInstance().getEventServiceBean();
	
    @GET
    @Produces({MediaType.APPLICATION_JSON})  //add MediaType.APPLICATION_XML if you want XML as well (don't forget @XmlRootElement)
    public Response getEvents(){
    	try {
    		Collection<Event> events = eventServiceBean.getEvents();
			
			if(events != null){
				GenericEntity<Collection<Event>> list = new GenericEntity<Collection<Event>>(events) {};
				return Response.ok(list).build();
			}else{
				return Response.status(Status.NOT_FOUND).build();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
    }
    
    @GET
    @Path("example")
    @Produces({MediaType.APPLICATION_JSON})
    public Response example(){
    	try {
    		Event event = new Event();
    		User author = new User();
    		author.setId(1);
    		author.setDisplayName("AuthorName");
    		author.setThumbnail("http://it.wikipedia.org/wiki/Google#/media/File:Googlelogo1997.jpg");
			event.setId(1);
    		event.setAuthor(author);
    		event.setLatitude(41.9677526);
    		event.setLongitude(12.6594818);
    		event.setDistance(300.0);
    		event.setEventImageUrl("http://it.wikipedia.org/wiki/Google#/media/File:Googlelogo1997.jpg");
    		event.setTitle("TitleEvent");
    		event.setAddress("fortyfive");
    		event.setAroundEventsNumber(10L);
    		event.setLikesNumber(10L);
    		event.setCreationTime(System.currentTimeMillis());
			event.setUpdateTime(System.currentTimeMillis());
			
    		
    		Collection<User> likes = new ArrayList<User>();
    		User like1 = new User();
    		like1.setId(2);
    		like1.setDisplayName("Userlike1");
    		like1.setThumbnail("http://it.wikipedia.org/wiki/Google#/media/File:Googlelogo1997.jpg");
    		likes.add(like1);
    		User like2 = new User();
    		like2.setId(3);
    		like2.setDisplayName("Userlike2");
    		like2.setThumbnail("http://it.wikipedia.org/wiki/Google#/media/File:Googlelogo1997.jpg");
    		likes.add(like2);
    		User like3 = new User();
    		like3.setId(4);
    		like3.setDisplayName("Userlike3");
    		like3.setThumbnail("http://it.wikipedia.org/wiki/Google#/media/File:Googlelogo1997.jpg");
    		likes.add(like3);
    		event.setLikes(likes);
			
    		Collection<Event> aroundEvents = new ArrayList<Event>();
    		Event event2 = new Event();
    		User author2 = new User();
    		author2.setId(2);
    		author2.setDisplayName("AuthorName2");
    		author2.setThumbnail("http://it.wikipedia.org/wiki/Google#/media/File:Googlelogo1997.jpg");
			event2.setId(2);
    		event2.setAuthor(author2);
    		event2.setDistance(300.0);
    		event2.setEventImageUrl("http://it.wikipedia.org/wiki/Google#/media/File:Googlelogo1997.jpg");
    		event2.setTitle("TitleEvent2");
    		aroundEvents.add(event2);
    		Event event3 = new Event();
    		User author3 = new User();
    		author3.setId(3);
    		author3.setDisplayName("AuthorName3");
    		author3.setThumbnail("http://it.wikipedia.org/wiki/Google#/media/File:Googlelogo1997.jpg");
			event3.setId(3);
    		event3.setAuthor(author3);
    		event3.setDistance(300.0);
    		event3.setEventImageUrl("http://it.wikipedia.org/wiki/Google#/media/File:Googlelogo1997.jpg");
    		event3.setTitle("TitleEvent3");
    		aroundEvents.add(event3);
    		Event event4 = new Event();
    		User author4 = new User();
    		author4.setId(4);
    		author4.setDisplayName("AuthorName4");
    		author4.setThumbnail("http://it.wikipedia.org/wiki/Google#/media/File:Googlelogo1997.jpg");
			event4.setId(4);
    		event4.setAuthor(author4);
    		event4.setDistance(300.0);
    		event4.setEventImageUrl("http://it.wikipedia.org/wiki/Google#/media/File:Googlelogo1997.jpg");
    		event4.setTitle("TitleEvent4");
    		aroundEvents.add(event4);
    		event.setAroundEvents(aroundEvents);
			return Response.ok(event).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
    }
    
    
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response createEvent(Event event, @Context HttpServletRequest req) throws Exception{
    	
         try {
        	 Event eventNew = eventServiceBean.addEvent(event);
			 if(eventNew != null){
				URI location = new URI("/events/"+eventNew.getId());
				return Response.created(location).build();
			 }else{
				 req.getSession().invalidate();
				 return  Response.status(Status.NOT_ACCEPTABLE).build();
			 }			 
		} catch (Exception e) {
			 e.printStackTrace();
			 req.getSession().invalidate();
			 return Response.serverError().build();
		}
    }

}
