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
		System.out.println(user);
		Assert.assertTrue(user != null);
	}
	
	@Test
	public void testToken(){
		WebTarget target = client.target(getBaseURI());
		Response response = target.path("users").path("example").request()
				.header(ResponseHeaderFilter.X_AUTH_TOKEN, "jhgasjdghjlawufcduuua73862478qgdgdqdhhaqdahdUISHDUHW").accept(MediaType.APPLICATION_JSON)
				.get();
		
		Assert.assertTrue(response.getHeaderString(ResponseHeaderFilter.X_AUTH_TOKEN)!= null);
	}
	
	@Test
	public void testRegistration(){
		WebTarget target = client.target(getBaseURI());
		User user = makeFakeUser("testUser"+System.currentTimeMillis());
		Entity<User> entity = Entity.entity(user, MediaType.APPLICATION_JSON);

		
		Response response = target.path("users").request()
				.header(ResponseHeaderFilter.X_AUTH_TOKEN, "jhgasjdghjlawufcduuua73862478qgdgdqdhhaqdahdUISHDUHW").accept(MediaType.APPLICATION_JSON)
				.post(entity);
		
		Assert.assertTrue(Status.CREATED.equals(Status.fromStatusCode(response.getStatus())));
		Assert.assertTrue(response.getHeaderString(ResponseHeaderFilter.X_AUTH_TOKEN)!= null);
		
	}
	
	@Test
	public void testGetUserProfile(){
		String username = "userProfile"+System.currentTimeMillis();
		User user = makeFakeUser(username);
		Entity<User> entity = Entity.entity(user, MediaType.APPLICATION_JSON);

		WebTarget target = client.target(getBaseURI());
		Response response = target.path("users").request()
				.header(ResponseHeaderFilter.X_AUTH_TOKEN, "jhgasjdghjlawufcduuua73862478qgdgdqdhhaqdahdUISHDUHW").accept(MediaType.APPLICATION_JSON)
				.post(entity);
		
		Assert.assertTrue(Status.CREATED.equals(Status.fromStatusCode(response.getStatus())));
		Assert.assertTrue(response.getHeaderString(ResponseHeaderFilter.X_AUTH_TOKEN)!= null);
		
		String headerString = response.getHeaderString("Location");
		System.out.println(headerString);
		Assert.assertTrue(headerString != null && !headerString.isEmpty());
		target = client.target(headerString);
		response = target.request()
				.header(ResponseHeaderFilter.X_AUTH_TOKEN, "jhgasjdghjlawufcduuua73862478qgdgdqdhhaqdahdUISHDUHW").accept(MediaType.APPLICATION_JSON)
				.get();
		Assert.assertTrue(Status.OK.equals(Status.fromStatusCode(response.getStatus())));
		user = response.readEntity(User.class);
		System.out.println(user);
		Assert.assertTrue(user != null);
		Assert.assertTrue(user.getId()>-1);
		Assert.assertTrue(username.equals(user.getUsername()));
		
	}

	/**
	 * @return
	 */
	private URI getBaseURI() {
//		return UriBuilder.fromUri("http://localhost:8080/aroundu-rest/service").build();
		return UriBuilder.fromUri("http://hangapp-placava.rhcloud.com/service").build();
		
	}
}
