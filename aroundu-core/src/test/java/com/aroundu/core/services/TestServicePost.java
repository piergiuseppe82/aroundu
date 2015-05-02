package com.aroundu.core.services;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.neo4j.io.fs.FileUtils;

import com.aroundu.core.infrastructure.ServiceBeanFactory;
import com.aroundu.core.model.Person;
import com.aroundu.core.model.Post;

/**
 * @author piergiuseppe82
 *
 */
public class TestServicePost {
	private ServiceBeanFactory factory;
	
	@Before
	public void init(){
		try {
			FileUtils.deleteRecursively(new File("target/graphdb-TEST-POST"));
		} catch (IOException e) {
		}
		factory = ServiceBeanFactory.getInstance("target/graphdb-TEST-POST");
	}
	
	@Test
	public void runTest(){
		testAddPerson();
		testAddPost();
		testGetAllPost();
		testGetAllPostPaginate();
		testGetAllPostWhithDistance();
		testGetAllPostWhithDistancePaginate();
	}
	
	public void testGetAllPost() {
		PostServiceBean postServiceBean = factory.getPostServiceBean();
		Collection<Post> posts = postServiceBean.getPosts();
		Assert.assertTrue(posts !=null && posts.size() > -1);	
		for (Post post : posts) {
			System.out.println(post);
		}
	}
	
	public void testGetAllPostPaginate() {
		PostServiceBean postServiceBean = factory.getPostServiceBean();
		Collection<Post> posts = postServiceBean.getPosts(0, 10);
		Assert.assertTrue(posts !=null && posts.size() > -1);	
		for (Post post : posts) {
			System.out.println(post);
		}
	}
	
	public void testGetAllPostWhithDistance() {
		PostServiceBean postServiceBean = factory.getPostServiceBean();
		Collection<Post> posts = postServiceBean.getPosts(41.960747, 12.655900,3.0);
		Assert.assertTrue(posts !=null && posts.size() > -1);	
		for (Post post : posts) {
			System.out.println(post);
		}
	}
	
	public void testGetAllPostWhithDistancePaginate() {
		PostServiceBean postServiceBean = factory.getPostServiceBean();
		Collection<Post> posts = postServiceBean.getPosts(41.960747, 12.655900,1.0,0,10);
		Assert.assertTrue(posts !=null && posts.size() > -1);	
		for (Post post : posts) {
			System.out.println(post);
		}
	}

	public void testAddPost(){
		
		PostServiceBean postServiceBean = factory.getPostServiceBean();
		Post post = new Post();
		post.setAccountId("test1");
		post.setLatitude(41.9677526);
		post.setLongitude(12.6594818);
		post.setPostImage("PippoImage");
		post.setTitle("TitlePost");
		post.setLocationDescription("fortyfive");
		Post addPost = postServiceBean.addPost(post);
		Assert.assertTrue(addPost.getId() > -1);	
		System.out.println(addPost);
	}
	public void testAddPerson(){
		PersonServiceBean personServiceBean = factory.getPersonServiceBean();
		Person p = new Person();
		p.setAccountId("test1");
		p.setEmail("pippo@pippo.com");
		p.setFullName("Nicola");
		p.setPassword("ciaomondo");
		p.setProfileImage("pippoImage");
		Person addPerson = personServiceBean.addPerson(p);
		Assert.assertTrue(addPerson.getId() > -1);	
	}
	
}
