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

import com.aroundu.core.model.User;
import com.aroundu.rest.filters.ResponseHeaderFilter;
import com.aroundu.rest.provider.JsonMoxyConfigurationContextResolver;

/**
 * 
 */

/**
 * @author piergiuseppe82
 *
 */
public class TestUserResource extends TestResource {
	
	
	Logger log = LoggerFactory.getLogger(TestUserResource.class);
	private Client client;
	
	@Before
	public void init(){
		ClientConfig clientConfig = new ClientConfig();
		clientConfig.register(JsonMoxyConfigurationContextResolver.class);
		client = ClientBuilder.newClient(clientConfig);
		
		
	}
	
	@Test
	public void testStatus(){
		WebTarget target = client.target(getBaseURI());
		Response response = target.path("users").path("example").request().accept(MediaType.APPLICATION_JSON).get();
		Assert.assertTrue(Status.OK.equals(Status.fromStatusCode(response.getStatus())));
	}
	
	@Test
	public void testExample(){
		WebTarget target = client.target(getBaseURI());
		Response response = target.path("users").path("example").request().accept(MediaType.APPLICATION_JSON).get();
		User user = response.readEntity(User.class);
		log.debug(""+user);
		Assert.assertTrue(user != null);
	}
	
	
	
	@Test
	public void testRegistrationGoogle(){
		if(GOOGLE_TOKEN.isEmpty())return;//SKIP TEST WHEN TOKEN NOT SETTED
		WebTarget target = client.target(getBaseURI());
		User user = makeFakeUser("testUserProfile"+System.currentTimeMillis());
		Entity<User> entity = Entity.entity(user, MediaType.APPLICATION_JSON);

		
		Response response = target.path("users").request().header(ResponseHeaderFilter.X_AUTH_TOKEN, GOOGLE_TOKEN).accept(MediaType.APPLICATION_JSON)
				.post(entity);
		log.debug(Status.fromStatusCode(response.getStatus()).toString());
		Assert.assertTrue(Status.OK.equals(Status.fromStatusCode(response.getStatus())));
		Assert.assertTrue(response.getHeaderString(ResponseHeaderFilter.X_AUTH_TOKEN)!= null);
		
	}
	
	@Test
	public void testRegistration(){
		WebTarget target = client.target(getBaseURI());
		User user = makeFakeUser("testUser"+System.currentTimeMillis());
		Entity<User> entity = Entity.entity(user, MediaType.APPLICATION_JSON);

		
		Response response = target.path("users").request().accept(MediaType.APPLICATION_JSON)
				.post(entity);
		
		Assert.assertTrue(Status.OK.equals(Status.fromStatusCode(response.getStatus())));
		Assert.assertTrue(response.getHeaderString(ResponseHeaderFilter.X_AUTH_TOKEN)!= null);
		
	}
	
	@Test
	public void testGetUserProfile(){
		
		WebTarget target = client.target(getBaseURI());
		User user = makeFakeUser("testUserProfile"+System.currentTimeMillis());
		Entity<User> entity = Entity.entity(user, MediaType.APPLICATION_JSON);

		
		Response response = target.path("users").request().accept(MediaType.APPLICATION_JSON)
				.post(entity);
		log.debug(Status.fromStatusCode(response.getStatus()).toString());
		Assert.assertTrue(Status.OK.equals(Status.fromStatusCode(response.getStatus())));
		
		
		String token = response.getHeaderString(ResponseHeaderFilter.X_AUTH_TOKEN);
		Assert.assertTrue(token!= null);
		
		response = target.path("users").path("profile").request().header(ResponseHeaderFilter.X_AUTH_TOKEN, token).accept(MediaType.APPLICATION_JSON)
		.get();
		log.debug(Status.fromStatusCode(response.getStatus()).toString());
		
		Assert.assertTrue(Status.OK.equals(Status.fromStatusCode(response.getStatus())));
		Assert.assertTrue(token!= null);
		user = response.readEntity(User.class);
		log.debug(""+user);
		Assert.assertTrue(user != null);
		
				
	}
	
	
	@Test
	public void testGetUserProfileGoogleToken(){
		if(GOOGLE_TOKEN.isEmpty())return;//SKIP TEST WHEN TOKEN NOT SETTED
		WebTarget target = client.target(getBaseURI());
		
		User user = makeFakeUserGoogleToken();
		Entity<User> entity = Entity.entity(user, MediaType.APPLICATION_JSON);		
		target.path("users").request().header(ResponseHeaderFilter.X_AUTH_TOKEN, GOOGLE_TOKEN).accept(MediaType.APPLICATION_JSON)
				.post(entity);
		
		Response response2 = target.path("users").path("profile").request().header(ResponseHeaderFilter.X_AUTH_TOKEN, GOOGLE_TOKEN).accept(MediaType.APPLICATION_JSON)
		.get();
		log.debug(Status.fromStatusCode(response2.getStatus()).toString());
		Assert.assertTrue(Status.OK.equals(Status.fromStatusCode(response2.getStatus())));
		String token2 = response2.getHeaderString(ResponseHeaderFilter.X_AUTH_TOKEN);
		Assert.assertTrue(token2!= null);
		User user2 = response2.readEntity(User.class);
		log.debug(""+user2);
		Assert.assertTrue(user2 != null);
		
				
	}
	
	@Test
	public void testGetUserProfileFacebookToken(){
		if(FACEBOOK_TOKEN.isEmpty())return;//SKIP TEST WHEN TOKEN NOT SETTED
		WebTarget target = client.target(getBaseURI());
		
		User user = makeFakeUserFacebookToken();
		Entity<User> entity = Entity.entity(user, MediaType.APPLICATION_JSON);		
		target.path("users").request().header(ResponseHeaderFilter.X_AUTH_TOKEN, FACEBOOK_TOKEN).accept(MediaType.APPLICATION_JSON)
				.post(entity);
	
		
		Response response2 = target.path("users").path("profile").request().header(ResponseHeaderFilter.X_AUTH_TOKEN, FACEBOOK_TOKEN).accept(MediaType.APPLICATION_JSON)
		.get();
		log.debug(Status.fromStatusCode(response2.getStatus()).toString());
		Assert.assertTrue(Status.OK.equals(Status.fromStatusCode(response2.getStatus())));
		String token2 = response2.getHeaderString(ResponseHeaderFilter.X_AUTH_TOKEN);
		Assert.assertTrue(token2!= null);
		User user2 = response2.readEntity(User.class);
		log.debug(""+user2);
		Assert.assertTrue(user2 != null);
		
				
	}
	

	/**
	 * @return
	 */
	private URI getBaseURI() {
		return UriBuilder.fromUri("http://localhost:8080/aroundu-rest/service").build();
		
	}
}
