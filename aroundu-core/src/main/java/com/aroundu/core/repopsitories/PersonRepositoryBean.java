package com.aroundu.core.repopsitories;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.ResourceIterator;
import org.neo4j.graphdb.Result;

import com.aroundu.core.infrastructure.RepositoryBean;
import com.aroundu.core.model.Person;
import com.aroundu.core.supports.RepoAssemblers;

/**
 * @author piergiuseppe82
 *
 */
public class PersonRepositoryBean extends RepositoryBean{
	
	
	
	public Person createPerson(Person person){		
		
		long currentTimeMillis = System.currentTimeMillis();
		person.setCreationTime(currentTimeMillis);
		person.setUpdateTime(currentTimeMillis);
		Node createNode = getGraphDatabaseServices().createNode(DynamicLabel.label(Person.class.getSimpleName()));
		RepoAssemblers.beanToNode(person, createNode);		
		person.setId(createNode.getId());
		return person;
	}

	

	public Collection<Person> findAll() {
		
		
		ResourceIterator<Node> findNodes = getGraphDatabaseServices().findNodes(DynamicLabel.label(Person.class.getSimpleName()));
		Collection<Person> pList = RepoAssemblers.toPersonCollection(findNodes);
		return pList;
	}

	
	
	
	public Collection<Person> findPaginate(long from, long to, boolean desc) {
		
		
		
		Result execute = getGraphDatabaseServices().execute("MATCH (p:Person) RETURN p ORDER BY p.creationTime "+(desc?"asc ":"desc ")+"SKIP "+from+" LIMIT "+to);
		Collection<Person> pList = RepoAssemblers.toPersonCollection(execute);
		return pList;
	}

	

	

	public Person findPerson(String accountId) {
		Node node = getGraphDatabaseServices().findNode(DynamicLabel.label(Person.class.getSimpleName()), "accountId", accountId);
		if(node != null){
			Person p = new Person();
			RepoAssemblers.nodeToBean(node, p);
			return p;
		}return null;
	}
	
	public Person findPerson(long id) {
		Node nodeById = getGraphDatabaseServices().getNodeById(id);
		if(nodeById != null){
			Person p = new Person();
			RepoAssemblers.nodeToBean(nodeById, p);
			return p;
		}return null;
	}
	
	public void removePerson(String accountId) {
		Node node = getGraphDatabaseServices().findNode(DynamicLabel.label(Person.class.getSimpleName()), "accountId", accountId);
		node.delete();
	}
	
	public void removePerson(long id) {
		Node nodeById = getGraphDatabaseServices().getNodeById(id);
		nodeById.delete();
	}

	public Person findPersonByAccount(String accountId, String password) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("accountId", accountId);
		map.put("password", password);
		Result execute = getGraphDatabaseServices().execute("MATCH (p:Person) WHERE p.accountId = {accountId} AND p.password = {password} RETURN p",map);
		Collection<Person> collection = RepoAssemblers.toPersonCollection(execute);
		for (Person person : collection) {
			return person;
		}
		return null;
	}



	
}
