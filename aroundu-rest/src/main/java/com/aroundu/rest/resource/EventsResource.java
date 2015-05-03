package com.aroundu.rest.resource;

import java.net.URI;
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
    		author.setUsername("utente1");
    		author.setId(1);
    		author.setDisplayName("Nicola64");
			event.setAuthor(author);
    		event.setLatitude(41.9677526);
    		event.setLongitude(12.6594818);
    		event.setEventImageUrl("http://it.wikipedia.org/wiki/Google#/media/File:Googlelogo1997.jpg");
    		event.setTitle("TitleEvent");
    		event.setAddress("fortyfive");
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
