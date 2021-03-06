package com.aroundu.core.services;

import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aroundu.core.model.User;


/**
 * @author piergiuseppe82
 *
 */
public class TestServiceUser extends TestService{
	Logger log = LoggerFactory.getLogger(TestServiceUser.class);
	@Test
	public void testAddUser(){
		User addUser = addFakeUser("userForTestAddUser");
		Assert.assertTrue(addUser.getId() > -1);	
		log.debug(""+addUser);
	}
	
	@Test
	public void testGetAllUser(){
		UserServiceBean userServiceBean = factory.getUserServiceBean();
		addFakeUser("userForGetAllUser1");
		addFakeUser("userForGetAllUser2");
		addFakeUser("userForGetAllUser3");
		Collection<User> allUser = userServiceBean.getAllUser();
		Assert.assertTrue(allUser.size() >= 3);	
		for (User user : allUser) {
			log.debug(""+user);
		}
	}	
	
	@Test
	public void testGetUser(){				
		UserServiceBean userServiceBean = factory.getUserServiceBean();		
		User addUser = addFakeUser("userForTestGetUser");		
		User p = userServiceBean.getUser(addUser);
		Assert.assertTrue(p!=null && p.getId() == addUser.getId());		
		log.debug(""+p);
	}
	
	@Test
	public void testCheckUser(){
		UserServiceBean userServiceBean = factory.getUserServiceBean();	
		User createUser = makeFakeUser("testCheckUser");
		String username = createUser.getUsername();
		String pwd = createUser.getPassword();
		User addUser = userServiceBean.addUser(createUser);		
		User p = userServiceBean.checkUser(username,pwd);
		Assert.assertTrue(p!=null && p.getId() == addUser.getId());	
	}
	
	@Test
	public void testDeleteUser(){
		UserServiceBean userServiceBean = factory.getUserServiceBean();
		User addUser = addFakeUser("testDeleteUser");				 
		Assert.assertTrue(userServiceBean.deleteUser(addUser));	
		User p = userServiceBean.getUser(addUser);
		Assert.assertTrue(p == null);	
	}
	
	@Test
	public void testUsersPaginate(){
		UserServiceBean userServiceBean = factory.getUserServiceBean();
		addFakeUser("testUsersPaginate1");
		addFakeUser("testUsersPaginate2");
		addFakeUser("testUsersPaginate3");
		Collection<User> allUser = userServiceBean.getUsersPaginate(0, 2, false);
		Assert.assertTrue(allUser.size() == 2);	
	}
	
	@Test
	public void testConstraintsAddUser(){
		User addUser = addFakeUser("userForTestAddUserConst");
		Assert.assertTrue(addUser.getId() > -1);	
		log.debug(""+addUser);
		addUser = addFakeUser("userForTestAddUserConst");
		Assert.assertNull(addUser);
	}

	
}
