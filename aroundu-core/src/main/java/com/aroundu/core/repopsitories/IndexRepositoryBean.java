package com.aroundu.core.repopsitories;

import java.util.HashMap;
import java.util.Map;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.index.Index;

import com.aroundu.core.infrastructure.RepositoryBean;

public class IndexRepositoryBean extends RepositoryBean{
	
	private Index<Node> geom = null;
	
	
	private Index<Node>  getSpatialindex(){
		if(geom == null){
			boolean existsForNodes = getGraphDatabaseServices().index().existsForNodes("geom");
			if(existsForNodes){
				geom =  getGraphDatabaseServices().index().forNodes("geom");
			}else{
				Map<String, String> map = new HashMap<String, String>();
				map.put("provider", "spatial");
				map.put("geometry_type", "point");
				map.put("lat", "lat");
				map.put("lon", "lon");
				geom =  getGraphDatabaseServices().index().forNodes("geom",map);
			}
		}
		return geom;
	}
	
	public void addToSpatialIndex(Node node){
		Index<Node> index = getSpatialindex();
		index.add(node, "postSpatialId", node.getProperty("postSpatialId"));
	}
}
