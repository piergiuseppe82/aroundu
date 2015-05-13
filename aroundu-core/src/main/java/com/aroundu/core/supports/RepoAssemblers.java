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
		bean.setUsername((String)getProperty("username",node));
		bean.setDisplayName((String)getProperty("displayName",node));
		bean.setCreationTime((Long)getProperty("creationTime",node));
		bean.setEmail((String)getProperty("email",node));
		bean.setId(node.getId());
		bean.setUpdateTime((Long)getProperty("updateTime",node));
		bean.setAuth_domain((String)getProperty("auth_domain",node));
		bean.setBackground((String)getProperty("background",node));
		bean.setExpiretime((Long)getProperty("expiretime",node));
		bean.setThumbnail((String)getProperty("thumbnail",node));
		bean.setToken((String)getProperty("token",node));
		bean.setActive((Boolean)getProperty("active",node));
		
	}
	
	private static Object getProperty(String key, Node node){
		try {
			if(node.hasProperty(key))
				return node.getProperty(key);
		} catch (Exception e) {
			return null;
		}
		return null;
	}
	
	public static void nodeToBeanShort(Node node, User bean) {
		bean.setDisplayName((String)getProperty("displayName",node));
		bean.setId(node.getId());
		bean.setThumbnail((String)getProperty("thumbnail",node));
		bean.setActive((Boolean)getProperty("active",node));
	}
	
	public static void beanToNode(User bean, Node node) {
		
		node.setProperty( "username",bean.getUsername() );
		node.setProperty( "creationTime", bean.getCreationTime());
		node.setProperty( "updateTime", bean.getUpdateTime());
		node.setProperty( "email",  bean.getEmail());		
		if(bean.getPassword() !=null)node.setProperty( "password", bean.getPassword());
		if(bean.getThumbnail() !=null)node.setProperty( "thumbnail", bean.getThumbnail());
		node.setProperty( "displayName", bean.getDisplayName());
		if(bean.getAuth_domain() !=null)node.setProperty( "auth_domain",  bean.getAuth_domain());
		if(bean.getBackground() !=null)node.setProperty( "background",  bean.getBackground());
		if(bean.getExpiretime() !=null)node.setProperty( "expiretime",  bean.getExpiretime());
		node.setProperty( "token",  bean.getToken());
		if(bean.getActive() !=null)node.setProperty( "active",  bean.getActive());
	}
	
	public static void beanToNode(Event bean, Node node) {
	    
		node.setProperty( "title",bean.getTitle() );
		node.setProperty( "address", bean.getAddress());
		node.setProperty( "eventImageUrl", bean.getEventImageUrl());
		node.setProperty( "lat",  bean.getLatitude());		
		node.setProperty( "lon",  bean.getLongitude());	
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
		
		bean.setTitle((String)getProperty("title",node));
		bean.setAddress((String)getProperty("address",node));
		bean.setCreationTime((long)getProperty("creationTime",node));
		bean.setEventImageUrl((String)getProperty("eventImageUrl",node));
		bean.setId(node.getId());
		bean.setLatitude((Double)getProperty("lat",node));
		bean.setLongitude((Double)getProperty("lon",node));
		bean.setUpdateTime((long)getProperty("updateTime",node));
		
		Iterable<Relationship> relationships = node.getRelationships(RepositoryBean.RelTypes.POST, Direction.INCOMING);
		if(relationships != null){
			for (Relationship relationship : relationships) {
				Node userNode = relationship.getStartNode();
				User user = new User();
				RepoAssemblers.nodeToBeanShort(userNode, user);
				bean.setAuthor(user);
			}
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
					double distance = Utility.distance(latitute, longitude, (Double)getProperty("lat",node), (Double)getProperty("lon",node), 'K');
					p.setDistance(distance/1000);
					pList.add(p);
				}
				
			}
		}
		return pList;
	}

	
}
