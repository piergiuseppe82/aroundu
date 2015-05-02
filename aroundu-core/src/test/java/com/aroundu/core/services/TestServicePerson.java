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


/**
 * @author piergiuseppe82
 *
 */
public class TestServicePerson {
	private ServiceBeanFactory factory;
	
	@Before
	public void init(){
		try {
			FileUtils.deleteRecursively(new File("target/graphdb-SERVICE-PERON"));
		} catch (IOException e) {
		}
		factory = ServiceBeanFactory.getInstance("target/graphdb-SERVICE-PERON");
		
	}
	
	@Test
	public void runTest(){
		testAddPerson();
		testGetPerson();
		testCheckPerson();
		testGetAllPerson();
		testPersonsPaginate();
		testDeletePerson();
	}
	
	public void testAddPerson(){
		PersonServiceBean personServiceBean = factory.getPersonServiceBean();
		Person p = new Person();
		p.setAccountId("test12");
		p.setEmail("pippo@pippo.com");
		p.setFullName("Nicola");
		p.setPassword("ciaomondo");
		p.setProfileImage("pippoImage");
		Person addPerson = personServiceBean.addPerson(p);
		Assert.assertTrue(addPerson.getId() > -1);	
	}
	
	
	public void testGetAllPerson(){
		PersonServiceBean personServiceBean = factory.getPersonServiceBean();
		Collection<Person> allPerson = personServiceBean.getAllPerson();
		Assert.assertTrue(allPerson.size() > 0);	
	}
	
	public void testGetPerson(){
		PersonServiceBean personServiceBean = factory.getPersonServiceBean();
		Person p = new Person();	
		p.setAccountId("test12");
		p = personServiceBean.getPerson(p);
		Assert.assertTrue(p!=null && p.getId() > -1);	
	}
	public void testCheckPerson(){
		PersonServiceBean personServiceBean = factory.getPersonServiceBean();		
		Person p = personServiceBean.checkPerson("test12", "ciaomondo");
		Assert.assertTrue(p!=null && p.getId() > -1);	
	}
	
	public void testDeletePerson(){
		PersonServiceBean personServiceBean = factory.getPersonServiceBean();
		Person p = new Person();	
		p.setAccountId("test12");		 
		Assert.assertTrue(personServiceBean.deletePerson(p));	
		p = personServiceBean.getPerson(p);
		Assert.assertTrue(p == null);	
	}
	
	public void testPersonsPaginate(){
		PersonServiceBean personServiceBean = factory.getPersonServiceBean();
		Collection<Person> allPerson = personServiceBean.getPersonsPaginate(0, 10, false);
		Assert.assertTrue(allPerson.size() > 0);	
	}
}
