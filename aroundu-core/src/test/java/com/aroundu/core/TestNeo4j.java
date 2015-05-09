package com.aroundu.core;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.neo4j.gis.spatial.indexprovider.LayerNodeIndex;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Result;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.index.Index;
import org.neo4j.io.fs.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aroundu.core.services.UserServiceBean;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import com.vividsolutions.jts.operation.distance.DistanceOp;



public class TestNeo4j {
	Logger log = LoggerFactory.getLogger(TestNeo4j.class);
	GraphDatabaseService graphDb;
	Node firstNode;
	Node secondNode;
	Relationship relationship;
	
	private static enum RelTypes implements RelationshipType
	{
	    KNOWS
	}
	
	public void init(){
		try {
			FileUtils.deleteRecursively(new File("target/graphdb-CONFIG"));
		} catch (IOException e) {}
		graphDb = new GraphDatabaseFactory().newEmbeddedDatabase( "graphdb-CONFIG");
		
		registerShutdownHook( graphDb );
	}
	
	public void test() throws ParseException{
		try ( Transaction tx = graphDb.beginTx() )
		{
			
			//CREO L'INDICE
			Map<String, String> map = new HashMap<String, String>();
			map.put("provider", "spatial");
			map.put("geometry_type", "point");
			map.put("lat", "lat");
			map.put("lon", "lon");
			Index<Node> forNodes = graphDb.index().forNodes("geom",map);
			
			//CREATE NODES
			firstNode = graphDb.createNode(DynamicLabel.label("Person"));
			firstNode.setProperty( "message", "Hello, " );
			firstNode.setProperty( "lat", 47.9163 );
			firstNode.setProperty( "lon", -113.0905 );			
			forNodes.add(firstNode, firstNode.toString(), firstNode.toString());
			
			secondNode = graphDb.createNode(DynamicLabel.label("Person"));
			secondNode.setProperty( "message", "World!" );
			secondNode.setProperty( "lat", 46.9263 );
			secondNode.setProperty( "lon", -114.1905 );
			forNodes.add(secondNode, secondNode.toString(), secondNode.toString());

//			
//			
			log.debug(forNodes.getName());
			relationship = firstNode.createRelationshipTo( secondNode, RelTypes.KNOWS );
			relationship.setProperty( "message", "brave Neo4j " );
//			
//			
			
			log.debug(LayerNodeIndex.DISTANCE_IN_KM_PARAMETER);
			log.debug(LayerNodeIndex.WITHIN_DISTANCE_QUERY);
			
			
			Result execute = graphDb.execute("START n=node:geom('withinDistance:[46.9163, -114.0905, 50.0]') RETURN n");
			while(execute.hasNext()){
				Map<String, Object> next = execute.next();
				Set<String> keySet = next.keySet();
				for (String string : keySet) {
					log.debug(string);
					Object object = next.get(string);
					Node node = (Node) object;
					Iterable<String> propertyKeys = node.getPropertyKeys();
					for (String string2 : propertyKeys) {
						log.debug("\t"+string2+":"+node.getProperty(string2));
					}
					WKTReader wktRdr = new WKTReader();
			        Geometry A = wktRdr.read("POINT(46.9163 -114.0905)");
			        Geometry B = wktRdr.read("POINT("+node.getProperty("lat")+" "+node.getProperty("lon")+")");
			        DistanceOp distOp = new DistanceOp(A, B );
			        
			        log.debug("\tdistOp:"+distOp.distance());
					
				}
				
			}
			//SHOW NODES
			System.out.print( firstNode.getProperty( "message" ) );
			System.out.print( relationship.getProperty( "message" ) );
			System.out.print( secondNode.getProperty( "message" ) );
			
				
			//DELETE NODES
			firstNode.getSingleRelationship( RelTypes.KNOWS, Direction.OUTGOING ).delete();
			firstNode.delete();
			secondNode.delete();
			
		
			

			
			
		    tx.success();
		}
	}
	
	private static void registerShutdownHook( final GraphDatabaseService graphDb )
	{
	    // Registers a shutdown hook for the Neo4j instance so that it
	    // shuts down nicely when the VM exits (even if you "Ctrl-C" the
	    // running application).
	    Runtime.getRuntime().addShutdownHook( new Thread()
	    {
	        @Override
	        public void run()
	        {
	            graphDb.shutdown();
	        }
	    } );
	}
}
