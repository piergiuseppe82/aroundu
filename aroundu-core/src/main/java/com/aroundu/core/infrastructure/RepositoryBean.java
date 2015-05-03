package com.aroundu.core.infrastructure;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.RelationshipType;

import com.aroundu.core.model.NodeEntity;
import com.aroundu.core.repopsitories.IndexRepositoryBean;

/**
 * @author piergiuseppe82
 *
 */
public abstract class RepositoryBean extends Bean{
	
	public static enum RelTypes implements RelationshipType
	{
	    POST,
	    LIKE
	}
	
	private GraphDatabaseService graphDatabaseServices;
	private IndexRepositoryBean indexRepositoryBean;
	
	
	public IndexRepositoryBean getIndexRepositoryBean() {
		return indexRepositoryBean;
	}

	public void setIndexRepositoryBean(IndexRepositoryBean indexRepositoryBean) {
		this.indexRepositoryBean = indexRepositoryBean;
	}

	public GraphDatabaseService getGraphDatabaseServices() {
		return graphDatabaseServices;
	}

	public void setGraphDatabaseServices(GraphDatabaseService graphDatabaseServices) {
		this.graphDatabaseServices = graphDatabaseServices;
	}
	
	public void createRelationshipTo(NodeEntity a, NodeEntity b, RelTypes type) {
		Node nodeA = graphDatabaseServices.getNodeById(a.getId());
		Node nodeB = graphDatabaseServices.getNodeById(b.getId());
		nodeA.createRelationshipTo(nodeB, type);
	}
}
