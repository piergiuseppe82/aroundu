package com.aroundu.core.repopsitories;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Result;

import com.aroundu.core.infrastructure.RepositoryBean;
import com.aroundu.core.model.Event;
import com.aroundu.core.supports.RepoAssemblers;

/**
 * @author piergiuseppe82
 *
 */
public class EventRepositoryBean extends RepositoryBean{
	
	
	
	public Event createEvent(Event event){			
		long currentTimeMillis = System.currentTimeMillis();
		event.setCreationTime(currentTimeMillis);
		event.setUpdateTime(currentTimeMillis);
		event.setEventSpatialId(event.getAuthor().getId()+"_"+currentTimeMillis);
		Node createNode = getGraphDatabaseServices().createNode(DynamicLabel.label(Event.class.getSimpleName()));
		RepoAssemblers.beanToNode(event, createNode);		
		event.setId(createNode.getId());		
		getIndexRepositoryBean().addToSpatialIndex(createNode);
		return event;
	}

	

	public Collection<Event> findAll() {
		
		
		Result execute = getGraphDatabaseServices().execute("MATCH (e:Event) RETURN e ORDER BY e.creationTime");
		Collection<Event> pList = RepoAssemblers.toEventCollection(execute);
		return pList;
	}

	
	
	
	public Collection<Event> findAllPaginate(long from, long to, boolean asc) {
		Result execute = getGraphDatabaseServices().execute("MATCH (e:Event) RETURN e ORDER BY e.creationTime "+(asc?"asc ":"desc ")+"SKIP "+from+" LIMIT "+to);
		Collection<Event> pList = RepoAssemblers.toEventCollection(execute);
		return pList;
	}
	
	public Collection<Event> findByDistance(Double latitute, Double longitude, Double dist) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("latitude", latitute);
		map.put("longitude", longitude);
		map.put("distance", dist);
		Result execute = getGraphDatabaseServices().execute("START n=node:geom('withinDistance:["+latitute+","+longitude+","+dist+"]') RETURN n");
		Collection<Event> pList = RepoAssemblers.toEventCollection(execute,latitute, longitude);
		return pList;
	}
	
	public Collection<Event> findByDistancePaginate(Double latitute, Double longitude, Double dist,long from, long to) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("from", from);
		map.put("to", to);
		Result execute = getGraphDatabaseServices().execute("START n=node:geom('withinDistance:["+latitute+","+longitude+","+dist+"]') RETURN n SKIP {from} LIMIT {to}",map);
		Collection<Event> pList = RepoAssemblers.toEventCollection(execute,latitute, longitude);
		return pList;
	}

	

	

}
