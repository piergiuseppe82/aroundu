package com.aroundu.core.services;

import java.util.Collection;

import org.neo4j.graphdb.Transaction;

import com.aroundu.core.infrastructure.ServiceBean;
import com.aroundu.core.model.Person;
import com.aroundu.core.repopsitories.PersonRepositoryBean;
import com.aroundu.core.supports.Utility;

public class PersonServiceBean extends ServiceBean {

	private PersonRepositoryBean personRepositoryBean;
	
	
	public PersonRepositoryBean getPersonRepositoryBean() {
		return personRepositoryBean;
	}


	public void setPersonRepositoryBean(PersonRepositoryBean personRepositoryBean) {
		this.personRepositoryBean = personRepositoryBean;
	}


	
	public Person addPerson(Person p){
		try(Transaction tx = personRepositoryBean.getGraphDatabaseServices().beginTx()){
			p.setPassword(Utility.passwordEncode(p.getPassword()));
			personRepositoryBean.createPerson(p);
			tx.success();
			return p;
		}catch(Throwable t){
			t.printStackTrace();
		}	
		return null;
	}
	
	public Collection<Person> getAllPerson(){
		try(Transaction tx = personRepositoryBean.getGraphDatabaseServices().beginTx()){
			Collection<Person> plist = personRepositoryBean.findAll();
			tx.success();
			return plist;
		}catch(Throwable t){
			t.printStackTrace();
		}
		return null;
		
	}
	
	public Collection<Person> getPersonsPaginate(long from, long to, boolean asc){
		try(Transaction tx = personRepositoryBean.getGraphDatabaseServices().beginTx()){
			Collection<Person> plist = personRepositoryBean.findPaginate(from, to, asc);
			tx.success();
			return plist;
		}catch(Throwable t){
			t.printStackTrace();
		}
		return null;
		
	}


	public Person getPerson(Person p) {
		try(Transaction tx = personRepositoryBean.getGraphDatabaseServices().beginTx()){
			Person pN = personRepositoryBean.findPerson(p.getAccountId());
			tx.success();
			return pN;
		}catch(Throwable t){
			t.printStackTrace();
		}
		return null;
	}
	
	public Person checkPerson(String accountId, String password) {
		try(Transaction tx = personRepositoryBean.getGraphDatabaseServices().beginTx()){
			password = Utility.passwordEncode(password);
			Person pN = personRepositoryBean.findPersonByAccount(accountId,password);
			tx.success();
			return pN;
		}catch(Throwable t){
			t.printStackTrace();
		}
		return null;
	}
	
	public boolean deletePerson(Person p) {
		try(Transaction tx = personRepositoryBean.getGraphDatabaseServices().beginTx()){
			personRepositoryBean.removePerson(p.getAccountId());
			tx.success();
			return true;
		}catch(Throwable t){
			t.printStackTrace();
		}
		return false;
	}
}
