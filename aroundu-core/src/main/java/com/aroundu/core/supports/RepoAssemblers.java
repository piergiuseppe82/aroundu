package com.aroundu.core.supports;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.ResourceIterator;
import org.neo4j.graphdb.Result;

import com.aroundu.core.infrastructure.RepositoryBean;
import com.aroundu.core.model.Event;
import com.aroundu.core.model.User;

/**
 * @author piergiuseppe82
 *
 */
public class RepoAssemblers {
	
	public static Collection<User> toUserCollection(ResourceIterator<Node> findNodes) {
		Collection<User> pList = new ArrayList<User>();
		if(findNodes !=null){
			while(findNodes.hasNext()){
				Node next = findNodes.next();
				User p = new User();
				nodeToBean(next, p);
				pList.add(p);
			}
		}
		return pList;
	}
	
	public static Collection<User> toUserCollection(Result execute) {
		Collection<User> pList = new ArrayList<User>();
		if(execute !=null){
			while(execute.hasNext()){
				Map<String, Object> map = execute.next();
				Set<String> keySet = map.keySet();
				for (String key : keySet) {
					Node node = (Node)map.get(key);
					User p = new User();
					RepoAssemblers.nodeToBean(node, p);
					pList.add(p);
				}
				
			}
		}
		return pList;
	}
	
	public static void nodeToBean(Node node, User bean) {
		bean.setUsername((String)node.getProperty("username"));
		bean.setDisplayName((String)node.getProperty("displayName"));
		bean.setCreationTime((long)node.getProperty("creationTime"));
		bean.setEmail((String)node.getProperty("email"));
		bean.setId(node.getId());
		bean.setUpdateTime((long)node.getProperty("updateTime"));
		bean.setAuth_domain((String)node.getProperty("auth_domain"));
		bean.setBackground((String)node.getProperty("background"));
		bean.setExpiretime((long)node.getProperty("expiretime"));
		bean.setThumbnail((String)node.getProperty("thumbnail"));
		bean.setToken((String)node.getProperty("token"));
		
	}
	
	public static void nodeToBeanShort(Node node, User bean) {
		bean.setDisplayName((String)node.getProperty("displayName"));
		bean.setId(node.getId());
		bean.setThumbnail((String)node.getProperty("thumbnail"));
	}
	
	public static void beanToNode(User bean, Node node) {
		
		node.setProperty( "username",bean.getUsername() );
		node.setProperty( "creationTime", bean.getCreationTime());
		node.setProperty( "updateTime", bean.getUpdateTime());
		node.setProperty( "email",  bean.getEmail());		
		node.setProperty( "password", bean.getPassword()!=null?bean.getPassword():"");
		node.setProperty( "thumbnail", bean.getThumbnail()!=null?bean.getThumbnail():"");
		node.setProperty( "displayName", bean.getDisplayName());
		node.setProperty( "auth_domain",  bean.getAuth_domain()!=null?bean.getAuth_domain():"");
		node.setProperty( "background",  bean.getBackground()!=null?bean.getBackground():"");
		node.setProperty( "expiretime",  bean.getExpiretime());
		node.setProperty( "token",  bean.getToken());
	}
	
	public static void beanToNode(Event bean, Node node) {
	    
		node.setProperty( "title",bean.getTitle() );
		node.setProperty( "address", bean.getAddress());
		node.setProperty( "eventImageUrl", bean.getEventImageUrl());
		node.setProperty( "lat",  bean.getLatitude());		
		node.setProperty( "lon",  bean.getLongitude());	
		node.setProperty( "eventSpatialId",bean.getEventSpatialId());
		node.setProperty( "creationTime", bean.getCreationTime());
		node.setProperty( "updateTime", bean.getUpdateTime());
	}

	public static Collection<Event> toEventCollection(
			ResourceIterator<Node> findNodes) {
		Collection<Event> pList = new ArrayList<Event>();
		if(findNodes !=null){
			while(findNodes.hasNext()){
				Node next = findNodes.next();
				Event p = new Event();
				nodeToBean(next, p);
				pList.add(p);
			}
		}
		return pList;
	}

	public static void nodeToBean(Node node, Event bean) {
		
		bean.setTitle((String)node.getProperty("title"));
		bean.setAddress((String)node.getProperty("address"));
		bean.setCreationTime((long)node.getProperty("creationTime"));
		bean.setEventImageUrl((String)node.getProperty("eventImageUrl"));
		bean.setId(node.getId());
		bean.setLatitude((Double)node.getProperty("lat"));
		bean.setLongitude((Double)node.getProperty("lon"));
		bean.setUpdateTime((long)node.getProperty("updateTime"));
		bean.setEventSpatialId((String)node.getProperty("eventSpatialId"));		
		
		Iterable<Relationship> relationships = node.getRelationships(RepositoryBean.RelTypes.POST, Direction.INCOMING);
		if(relationships != null){
			for (Relationship relationship : relationships) {
				Node userNode = relationship.getStartNode();
				User user = new User();
				RepoAssemblers.nodeToBeanShort(userNode, user);
				bean.setAuthor(user);
			}
		}
		relationships = node.getRelationships(RepositoryBean.RelTypes.LIKE, Direction.INCOMING);
		if(relationships != null){
			Collection<User> likes = new ArrayList<User>();
			for (Relationship relationship : relationships) {
				Node userNode = relationship.getStartNode();
				User user = new User();
				RepoAssemblers.nodeToBeanShort(userNode, user);
				likes.add(user);
				
			}
			bean.setLikes(likes);
		}
	}

	public static Collection<Event> toEventCollection(Result execute) {
		Collection<Event> pList = new ArrayList<Event>();
		if(execute !=null){
			while(execute.hasNext()){
				Map<String, Object> map = execute.next();
				Set<String> keySet = map.keySet();	
				Event e = new Event();
				for (String key : keySet) {
					Node node = (Node)map.get(key);
					RepoAssemblers.nodeToBean(node, e);					
				}
				pList.add(e);
				
			}
		}
		return pList;
	}

	public static Collection<Event> toEventCollection(Result execute,
			Double latitute, Double longitude) {
		Collection<Event> pList = new ArrayList<Event>();
		if(execute !=null){
			while(execute.hasNext()){
				Map<String, Object> map = execute.next();
				Set<String> keySet = map.keySet();
				for (String key : keySet) {
					Node node = (Node)map.get(key);
					Event p = new Event();
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
