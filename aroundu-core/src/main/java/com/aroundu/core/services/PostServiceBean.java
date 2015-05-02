package com.aroundu.core.services;

import java.util.Collection;

import org.neo4j.graphdb.Transaction;

import com.aroundu.core.infrastructure.RepositoryBean;
import com.aroundu.core.infrastructure.ServiceBean;
import com.aroundu.core.model.Person;
import com.aroundu.core.model.Post;
import com.aroundu.core.repopsitories.PersonRepositoryBean;
import com.aroundu.core.repopsitories.PostRepositoryBean;

public class PostServiceBean extends ServiceBean {
	private PostRepositoryBean postRepositoryBean;
	private PersonRepositoryBean personRepositoryBean;
	
	public PersonRepositoryBean getPersonRepositoryBean() {
		return personRepositoryBean;
	}

	public void setPersonRepositoryBean(PersonRepositoryBean personRepositoryBean) {
		this.personRepositoryBean = personRepositoryBean;
	}

	
	public PostRepositoryBean getPostRepositoryBean() {
		return postRepositoryBean;
	}

	public void setPostRepositoryBean(PostRepositoryBean postRepositoryBean) {
		this.postRepositoryBean = postRepositoryBean;
	}

	
	

	
	public Post addPost(Post post){
		try(Transaction tx = personRepositoryBean.getGraphDatabaseServices().beginTx()){
			String accountId = post.getAccountId();
			Person person = personRepositoryBean.findPerson(accountId);
			post = postRepositoryBean.createPost(post);
			personRepositoryBean.createRelationshipTo(person, post, RepositoryBean.RelTypes.POST );
			tx.success();
			return post;
		}catch(Throwable t){
			t.printStackTrace();
		}
		return null;
		
	}
	
	
	public Collection<Post> getPosts(){
		try(Transaction tx = personRepositoryBean.getGraphDatabaseServices().beginTx()){
			Collection<Post> findAll = postRepositoryBean.findAll();
			tx.success();
			return findAll;
		}catch(Throwable t){
			t.printStackTrace();
		}
		return null;
		
	}
	
	public Collection<Post> getPosts(long from, long to, boolean asc){
		try(Transaction tx = personRepositoryBean.getGraphDatabaseServices().beginTx()){
			Collection<Post> findAll = postRepositoryBean.findAllPaginate(from, to, asc);
			tx.success();
			return findAll;
		}catch(Throwable t){
			t.printStackTrace();
		}
		return null;
		
	}
	
	public Collection<Post> getPosts(long from, long to){
		try(Transaction tx = personRepositoryBean.getGraphDatabaseServices().beginTx()){
			Collection<Post> findAll = postRepositoryBean.findAllPaginate(from, to, false);
			tx.success();
			return findAll;
		}catch(Throwable t){
			t.printStackTrace();
		}
		return null;
		
	}
	
	public Collection<Post> getPosts(double latitude, double longitude, double distance){
		try(Transaction tx = personRepositoryBean.getGraphDatabaseServices().beginTx()){
			Collection<Post> findAll = postRepositoryBean.findByDistance(latitude, longitude, distance);
			tx.success();
			return findAll;
		}catch(Throwable t){
			t.printStackTrace();
		}
		return null;
		
	}
	
	public Collection<Post> getPosts(double latitude, double longitude, double distance, long from, long to){
		try(Transaction tx = personRepositoryBean.getGraphDatabaseServices().beginTx()){
			Collection<Post> findAll = postRepositoryBean.findByDistancePaginate(latitude, longitude, distance, from, to);
			tx.success();
			return findAll;
		}catch(Throwable t){
			t.printStackTrace();
		}
		return null;
		
	}
	
	
}
