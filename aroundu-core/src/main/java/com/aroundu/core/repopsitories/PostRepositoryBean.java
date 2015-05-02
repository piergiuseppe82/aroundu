package com.aroundu.core.repopsitories;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.ResourceIterator;
import org.neo4j.graphdb.Result;

import com.aroundu.core.infrastructure.RepositoryBean;
import com.aroundu.core.model.Post;
import com.aroundu.core.supports.RepoAssemblers;

public class PostRepositoryBean extends RepositoryBean{
	
	
	
	public Post createPost(Post post){			
		long currentTimeMillis = System.currentTimeMillis();
		post.setCreationTime(currentTimeMillis);
		post.setUpdateTime(currentTimeMillis);
		post.setPostSpatialId(post.getAccountId()+""+currentTimeMillis);
		Node createNode = getGraphDatabaseServices().createNode(DynamicLabel.label(Post.class.getSimpleName()));
		RepoAssemblers.beanToNode(post, createNode);		
		post.setId(createNode.getId());		
		getIndexRepositoryBean().addToSpatialIndex(createNode);
		return post;
	}

	

	public Collection<Post> findAll() {
		
		
		ResourceIterator<Node> findNodes = getGraphDatabaseServices().findNodes(DynamicLabel.label(Post.class.getSimpleName()));
		Collection<Post> pList = RepoAssemblers.toPostCollection(findNodes);
		return pList;
	}

	
	
	
	public Collection<Post> findAllPaginate(long from, long to, boolean asc) {
		Result execute = getGraphDatabaseServices().execute("MATCH (p:Post) RETURN p ORDER BY p.creationTime "+(asc?"asc ":"desc ")+"SKIP "+from+" LIMIT "+to);
		Collection<Post> pList = RepoAssemblers.toPostCollection(execute);
		return pList;
	}
	
	public Collection<Post> findByDistance(Double latitute, Double longitude, Double dist) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("latitude", latitute);
		map.put("longitude", longitude);
		map.put("distance", dist);
		Result execute = getGraphDatabaseServices().execute("START n=node:geom('withinDistance:["+latitute+","+longitude+","+dist+"]') RETURN n");
		Collection<Post> pList = RepoAssemblers.toPostCollection(execute,latitute, longitude);
		return pList;
	}
	
	public Collection<Post> findByDistancePaginate(Double latitute, Double longitude, Double dist,long from, long to) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("from", from);
		map.put("to", to);
		Result execute = getGraphDatabaseServices().execute("START n=node:geom('withinDistance:["+latitute+","+longitude+","+dist+"]') RETURN n SKIP {from} LIMIT {to}",map);
		Collection<Post> pList = RepoAssemblers.toPostCollection(execute,latitute, longitude);
		return pList;
	}

	

	

}
