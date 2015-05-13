package com.aroundu.rest.resource;
import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aroundu.core.model.Event;
import com.aroundu.rest.filters.ResponseHeaderFilter;
import com.aroundu.rest.provider.JsonMoxyConfigurationContextResolver;

/**
 * 
 */

/**
 * @author piergiuseppe82
 *
 */
public class TestEventResource extends TestResource {
	
	
	Logger log = LoggerFactory.getLogger(TestEventResource.class);
	private Client client;
	
	@Before
	public void init(){
		ClientConfig clientConfig = new ClientConfig();
		clientConfig.register(JsonMoxyConfigurationContextResolver.class);
		client = ClientBuilder.newClient(clientConfig);
		
		
	}
	

	@Test
	public void testCreateEvent(){
		WebTarget target = client.target(getBaseURI());
		Event event = makeFakeEvent("pippo",null,null);
		Entity<Event> entity = Entity.entity(event, MediaType.APPLICATION_JSON);

		
		Response response = target.path("events").request().header(ResponseHeaderFilter.X_AUTH_TOKEN, INTERNAL_TOKEN).accept(MediaType.APPLICATION_JSON)
				.post(entity);
		
		Assert.assertEquals(Status.CREATED,Status.fromStatusCode(response.getStatus()));
	}
	
	@Test
	public void testGetAllEvents(){
		WebTarget target = client.target(getBaseURI());
		
		Response response = target.path("events").request().header(ResponseHeaderFilter.X_AUTH_TOKEN, INTERNAL_TOKEN).accept(MediaType.APPLICATION_JSON)
				.get();
		
		Assert.assertEquals(Status.OK,Status.fromStatusCode(response.getStatus()));
		Object readEntity = response.readEntity(String.class);
		System.out.println(readEntity);
		
	}
	
	
	@Test
	public void testGetAllEventsFromTo(){
		WebTarget target = client.target(getBaseURI());
		
		Response response = target.path("events").queryParam("from", 0).queryParam("to", 3).request().header(ResponseHeaderFilter.X_AUTH_TOKEN, INTERNAL_TOKEN).accept(MediaType.APPLICATION_JSON)
				.get();
		
		Assert.assertEquals(Status.OK,Status.fromStatusCode(response.getStatus()));
		Object readEntity = response.readEntity(String.class);
		System.out.println(readEntity);
		
	}
	

	/**
	 * @return
	 */
	private URI getBaseURI() {
		return UriBuilder.fromUri("http://localhost:8080/aroundu-rest/service").build();
		
	}
}
