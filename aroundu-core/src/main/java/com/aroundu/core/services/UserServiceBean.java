package com.aroundu.core.services;

import java.util.Collection;

import org.neo4j.graphdb.ConstraintViolationException;
import org.neo4j.graphdb.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aroundu.core.infrastructure.ServiceBean;
import com.aroundu.core.model.User;
import com.aroundu.core.repopsitories.ImageRepositoryBean.ImageDimesionType;
import com.aroundu.core.repopsitories.UserRepositoryBean;
import com.aroundu.core.supports.Utility;

/**
 * @author piergiuseppe82
 *
 */
public class UserServiceBean extends ServiceBean {
	
	Logger log = LoggerFactory.getLogger(UserServiceBean.class);


	private UserRepositoryBean userRepositoryBean;
	
	
	public UserRepositoryBean getUserRepositoryBean() {
		return userRepositoryBean;
	}


	public void setUserRepositoryBean(UserRepositoryBean userRepositoryBean) {
		this.userRepositoryBean = userRepositoryBean;
	}


	
	public User addUser(User user){
		try(Transaction tx = userRepositoryBean.getGraphDatabaseServices().beginTx()){
			if(user.getAuth_domain() == null || user.getAuth_domain().isEmpty()) user.setPassword(Utility.passwordEncode(user.getPassword()));
			
			String image = user.getImage();
			if(image != null && !image.isEmpty()){
				String urlImage = getImageRepositoryBean().saveImage(image, ImageDimesionType.PROFILE_IMAGE, user.getUsername());
				user.setThumbnail(urlImage);
			}
			userRepositoryBean.createUser(user);
			tx.success();
			return user;
		}catch(ConstraintViolationException t){
			log.error("Error on add user",t);
		}catch(Throwable t){
			log.error("Error on add user",t);
		}	
		return null;
	}
	


	public Collection<User> getAllUser(){
		try(Transaction tx = userRepositoryBean.getGraphDatabaseServices().beginTx()){
			Collection<User> plist = userRepositoryBean.findAll();
			tx.success();
			return plist;
		}catch(Throwable t){
			log.error("Error on add user",t);
		}
		return null;
		
	}
	
	public Collection<User> getUsersPaginate(long from, long to, boolean asc){
		try(Transaction tx = userRepositoryBean.getGraphDatabaseServices().beginTx()){
			Collection<User> plist = userRepositoryBean.findPaginate(from, to, asc);
			tx.success();
			return plist;
		}catch(Throwable t){
			log.error("Error ",t);
		}
		return null;
		
	}


	public User getUser(User p) {
		try(Transaction tx = userRepositoryBean.getGraphDatabaseServices().beginTx()){
			User pN = userRepositoryBean.findUser(p.getId());
			tx.success();
			return pN;
		}catch(Throwable t){
			log.error("Error ",t);
		}
		return null;
	}
	
	public User checkUser(String username, String password) {
		try(Transaction tx = userRepositoryBean.getGraphDatabaseServices().beginTx()){
			password = Utility.passwordEncode(password);
			User pN = userRepositoryBean.findUserByAccount(username,password);
			tx.success();
			return pN;
		}catch(Throwable t){
			log.error("Error ",t);
		}
		return null;
	}
	
	public boolean deleteUser(User p) {
		try(Transaction tx = userRepositoryBean.getGraphDatabaseServices().beginTx()){
			userRepositoryBean.removeUser(p.getId());
			tx.success();
			return true;
		}catch(Throwable t){
			log.error("Error ",t);
		}
		return false;
	}


	/**
	 * @param checkUser
	 */
	public User checkUser(String token) {
		try(Transaction tx = userRepositoryBean.getGraphDatabaseServices().beginTx()){
			User pN = userRepositoryBean.findUserByToken(token);
			tx.success();
			return pN;
		}catch(Throwable t){
			log.error("Error ",t);
		}
		return null;
		
	}
}
