package com.aroundu.core.services;

import java.util.Collection;

import org.neo4j.graphdb.Transaction;

import com.aroundu.core.infrastructure.ServiceBean;
import com.aroundu.core.model.User;
import com.aroundu.core.repopsitories.UserRepositoryBean;
import com.aroundu.core.supports.Utility;

/**
 * @author piergiuseppe82
 *
 */
public class UserServiceBean extends ServiceBean {

	private UserRepositoryBean userRepositoryBean;
	
	
	public UserRepositoryBean getUserRepositoryBean() {
		return userRepositoryBean;
	}


	public void setUserRepositoryBean(UserRepositoryBean userRepositoryBean) {
		this.userRepositoryBean = userRepositoryBean;
	}


	
	public User addUser(User p){
		try(Transaction tx = userRepositoryBean.getGraphDatabaseServices().beginTx()){
			p.setPassword(Utility.passwordEncode(p.getPassword()));
			userRepositoryBean.createUser(p);
			tx.success();
			return p;
		}catch(Throwable t){
			t.printStackTrace();
		}	
		return null;
	}
	
	public Collection<User> getAllUser(){
		try(Transaction tx = userRepositoryBean.getGraphDatabaseServices().beginTx()){
			Collection<User> plist = userRepositoryBean.findAll();
			tx.success();
			return plist;
		}catch(Throwable t){
			t.printStackTrace();
		}
		return null;
		
	}
	
	public Collection<User> getUsersPaginate(long from, long to, boolean asc){
		try(Transaction tx = userRepositoryBean.getGraphDatabaseServices().beginTx()){
			Collection<User> plist = userRepositoryBean.findPaginate(from, to, asc);
			tx.success();
			return plist;
		}catch(Throwable t){
			t.printStackTrace();
		}
		return null;
		
	}


	public User getUser(User p) {
		try(Transaction tx = userRepositoryBean.getGraphDatabaseServices().beginTx()){
			User pN = userRepositoryBean.findUser(p.getId());
			tx.success();
			return pN;
		}catch(Throwable t){
			t.printStackTrace();
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
			t.printStackTrace();
		}
		return null;
	}
	
	public boolean deleteUser(User p) {
		try(Transaction tx = userRepositoryBean.getGraphDatabaseServices().beginTx()){
			userRepositoryBean.removeUser(p.getId());
			tx.success();
			return true;
		}catch(Throwable t){
			t.printStackTrace();
		}
		return false;
	}
}
