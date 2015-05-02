package com.aroundu.core.supports;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.ResourceIterator;
import org.neo4j.graphdb.Result;

import com.aroundu.core.model.Person;
import com.aroundu.core.model.Post;

public class RepoAssemblers {
	
	public static Collection<Person> toPersonCollection(ResourceIterator<Node> findNodes) {
		Collection<Person> pList = new ArrayList<Person>();
		if(findNodes !=null){
			while(findNodes.hasNext()){
				Node next = findNodes.next();
				Person p = new Person();
				nodeToBean(next, p);
				pList.add(p);
			}
		}
		return pList;
	}
	
	public static Collection<Person> toPersonCollection(Result execute) {
		Collection<Person> pList = new ArrayList<Person>();
		if(execute !=null){
			while(execute.hasNext()){
				Map<String, Object> map = execute.next();
				Set<String> keySet = map.keySet();
				for (String key : keySet) {
					Node node = (Node)map.get(key);
					Person p = new Person();
					RepoAssemblers.nodeToBean(node, p);
					pList.add(p);
				}
				
			}
		}
		return pList;
	}
	
	public static void nodeToBean(Node node, Person bean) {
		bean.setAccountId((String)node.getProperty("accountId"));
		bean.setCreationTime((long)node.getProperty("creationTime"));
		bean.setEmail((String)node.getProperty("email"));
		bean.setFullName((String)node.getProperty("fullName"));
		bean.setId(node.getId());
		bean.setPassword((String)node.getProperty("password"));
		bean.setUpdateTime((long)node.getProperty("updateTime"));
		bean.setProfileImage((String)node.getProperty("profileImage"));
	}
	
	public static void beanToNode(Person bean, Node node) {
		node.setProperty( "accountId",bean.getAccountId() );
		node.setProperty( "creationTime", bean.getCreationTime());
		node.setProperty( "updateTime", bean.getUpdateTime());
		node.setProperty( "email",  bean.getEmail());		
		node.setProperty( "fullName",  bean.getFullName());		
		node.setProperty( "password", bean.getPassword());
		node.setProperty( "profileImage", bean.getProfileImage());
	}
	
	public static void beanToNode(Post bean, Node node) {
		node.setProperty( "title",bean.getTitle() );
		node.setProperty( "locationDescription", bean.getLocationDescription());
		node.setProperty( "postImage", bean.getPostImage());
		node.setProperty( "lat",  bean.getLatitude());		
		node.setProperty( "lon",  bean.getLongitude());		
		node.setProperty( "accountId",  bean.getAccountId());	
		node.setProperty( "postSpatialId",bean.getPostSpatialId());
		node.setProperty( "creationTime", bean.getCreationTime());
		node.setProperty( "updateTime", bean.getUpdateTime());
	}

	public static Collection<Post> toPostCollection(
			ResourceIterator<Node> findNodes) {
		Collection<Post> pList = new ArrayList<Post>();
		if(findNodes !=null){
			while(findNodes.hasNext()){
				Node next = findNodes.next();
				Post p = new Post();
				nodeToBean(next, p);
				pList.add(p);
			}
		}
		return pList;
	}

	public static void nodeToBean(Node node, Post bean) {
		bean.setTitle((String)node.getProperty("title"));
		bean.setCreationTime((long)node.getProperty("creationTime"));
		bean.setLocationDescription((String)node.getProperty("locationDescription"));
		bean.setPostImage((String)node.getProperty("postImage"));
		bean.setId(node.getId());
		bean.setLatitude((Double)node.getProperty("lat"));
		bean.setLongitude((Double)node.getProperty("lon"));
		bean.setUpdateTime((long)node.getProperty("updateTime"));
		bean.setAccountId((String)node.getProperty("accountId"));
		bean.setPostSpatialId((String)node.getProperty("postSpatialId"));
		
	}

	public static Collection<Post> toPostCollection(Result execute) {
		Collection<Post> pList = new ArrayList<Post>();
		if(execute !=null){
			while(execute.hasNext()){
				Map<String, Object> map = execute.next();
				Set<String> keySet = map.keySet();
				for (String key : keySet) {
					Node node = (Node)map.get(key);
					Post p = new Post();
					RepoAssemblers.nodeToBean(node, p);
					pList.add(p);
				}
				
			}
		}
		return pList;
	}

	public static Collection<Post> toPostCollection(Result execute,
			Double latitute, Double longitude) {
		Collection<Post> pList = new ArrayList<Post>();
		if(execute !=null){
			while(execute.hasNext()){
				Map<String, Object> map = execute.next();
				Set<String> keySet = map.keySet();
				for (String key : keySet) {
					Node node = (Node)map.get(key);
					Post p = new Post();
					RepoAssemblers.nodeToBean(node, p);
					double distance = Utility.distance(latitute, longitude, (Double)node.getProperty("lat"), (Double)node.getProperty("lon"), 'K');
					p.setDistance(distance);
					pList.add(p);
				}
				
			}
		}
		return pList;
	}
}
