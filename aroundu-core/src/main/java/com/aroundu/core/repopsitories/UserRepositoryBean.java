package com.aroundu.core.repopsitories;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.ResourceIterator;
import org.neo4j.graphdb.Result;

import com.aroundu.core.infrastructure.RepositoryBean;
import com.aroundu.core.model.User;
import com.aroundu.core.supports.RepoAssemblers;

/**
 * @author piergiuseppe82
 *
 */
public class UserRepositoryBean extends RepositoryBean{
	
	
	
	public User createUser(User user){		
		
		long currentTimeMillis = System.currentTimeMillis();
		user.setCreationTime(currentTimeMillis);
		user.setUpdateTime(currentTimeMillis);
		Node createNode = getGraphDatabaseServices().createNode(DynamicLabel.label(User.class.getSimpleName()));
		RepoAssemblers.beanToNode(user, createNode);		
		user.setId(createNode.getId());
		return user;
	}

	

	public Collection<User> findAll() {
		
		
		ResourceIterator<Node> findNodes = getGraphDatabaseServices().findNodes(DynamicLabel.label(User.class.getSimpleName()));
		Collection<User> pList = RepoAssemblers.toUserCollection(findNodes);
		return pList;
	}

	
	
	
	public Collection<User> findPaginate(long from, long to, boolean desc) {
		
		
		
		Result execute = getGraphDatabaseServices().execute("MATCH (p:User) RETURN p ORDER BY p.creationTime "+(desc?"asc ":"desc ")+"SKIP "+from+" LIMIT "+to);
		Collection<User> pList = RepoAssemblers.toUserCollection(execute);
		return pList;
	}

	

	

	public User findUser(String username) {
		Node node = getGraphDatabaseServices().findNode(DynamicLabel.label(User.class.getSimpleName()), "username", username);
		if(node != null){
			User p = new User();
			RepoAssemblers.nodeToBean(node, p);
			return p;
		}return null;
	}

	public User findUserByToken(String token) {
		Node node = getGraphDatabaseServices().findNode(DynamicLabel.label(User.class.getSimpleName()), "token", token);
		if(node != null){
			User p = new User();
			RepoAssemblers.nodeToBean(node, p);
			return p;
		}return null;
	}
	
	public User findUser(long id) {
		Node nodeById = getGraphDatabaseServices().getNodeById(id);
		if(nodeById != null){
			User p = new User();
			RepoAssemblers.nodeToBean(nodeById, p);
			return p;
		}return null;
	}
	
	public void removeUser(String username) {
		Node node = getGraphDatabaseServices().findNode(DynamicLabel.label(User.class.getSimpleName()), "username", username);
		node.delete();
	}
	
	public void removeUser(long id) {
		Node nodeById = getGraphDatabaseServices().getNodeById(id);
		nodeById.delete();
	}

	public User findUserByAccount(String username, String password) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("username", username);
		map.put("password", password);
		Result execute = getGraphDatabaseServices().execute("MATCH (p:User) WHERE p.username = {username} AND p.password = {password} RETURN p",map);
		Collection<User> collection = RepoAssemblers.toUserCollection(execute);
		for (User user : collection) {
			return user;
		}
		return null;
	}



	
}
