package com.aroundu.core.infrastructure;

import java.io.File;
import java.io.IOException;

import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.schema.ConstraintDefinition;
import org.neo4j.graphdb.schema.ConstraintType;
import org.neo4j.io.fs.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aroundu.core.repopsitories.EventRepositoryBean;
import com.aroundu.core.repopsitories.ImageRepositoryBean;
import com.aroundu.core.repopsitories.IndexRepositoryBean;
import com.aroundu.core.repopsitories.UserRepositoryBean;
import com.aroundu.core.supports.Utility;

/**
 * @author piergiuseppe82
 *
 */
public class RepositoryBeanFactory extends Factory{
	private static GraphDatabaseService graphDb; 
	private static RepositoryBeanFactory factoryInstace;
	Logger log = LoggerFactory.getLogger(RepositoryBeanFactory.class);
	
	private RepositoryBeanFactory(){
		
	}
	private RepositoryBeanFactory(String dbpath){
		
        synchronized (GraphDatabaseService.class){
        	graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(dbpath);
        	log.debug("Inizializing graphdb");
        	createConstraints(graphDb);
        }      
	
	
        registerShutdownHook( graphDb, !Utility.isServerRuntime(), dbpath );
	}
	
	/**
	 * @param graphDb2
	 */
	private void createConstraints(GraphDatabaseService graphDb2) {
		try ( Transaction tx = graphDb2.beginTx() )
		{
			
			addUniqueConstraints(graphDb2,"User","username");
			addUniqueConstraints(graphDb2,"User","token");
			
			tx.success();
		}		
	}
	private void addUniqueConstraints(GraphDatabaseService graphDb2,String label,String property) {
		log.debug("Request unique constarint fro "+label+" on property "+property);
		Iterable<ConstraintDefinition> constraints = graphDb2.schema().getConstraints(DynamicLabel.label(label));
		if(constraints != null){
			for (ConstraintDefinition constraintDefinition : constraints) {
				if(constraintDefinition.isConstraintType(ConstraintType.UNIQUENESS)){
					Iterable<String> propertyKeys = constraintDefinition.getPropertyKeys();
					if(propertyKeys != null){
						for (String string : propertyKeys) {
							string.equalsIgnoreCase(property);
							log.debug("Unique constraints for "+label+" on property "+property+" already exist");
							return;
						}
					}
				}
			}
		}
		log.debug("Add unique constraints for "+label+" on property "+property);
		graphDb2.schema().constraintFor(DynamicLabel.label(label))
				.assertPropertyIsUnique(property).create();
	}
	
	
	
	public static RepositoryBeanFactory instance(String dbpath) {
		if(factoryInstace==null){
			synchronized (RepositoryBeanFactory.class){
				if(factoryInstace==null)
					factoryInstace = new RepositoryBeanFactory(dbpath);
			}
		}			
		return factoryInstace;
	}
	

	public UserRepositoryBean getUserRepositoryBean(){
		return (UserRepositoryBean) getBean(UserRepositoryBean.class);
	}
	
	public IndexRepositoryBean getIndexRepositoryBean(){
		return (IndexRepositoryBean) getBean(IndexRepositoryBean.class);
	}
	
	public ImageRepositoryBean getImageRepositoryBean(){
		return (ImageRepositoryBean) getBean(ImageRepositoryBean.class);
	}
	
	
	
	public EventRepositoryBean getEventRepositoryBean(){
		return (EventRepositoryBean) getBean( EventRepositoryBean.class);
		
	}
	
	
	
	
	
	private static void registerShutdownHook( final GraphDatabaseService graphDb, final boolean destroy, final String dbPath )
	{
	    Runtime.getRuntime().addShutdownHook( new Thread()
	    {
	        @Override
	        public void run()
	        {
	            graphDb.shutdown();
	            if(destroy){
	            	try {
	        			FileUtils.deleteRecursively(new File(dbPath));
	        		} catch (IOException e) {
	        		}
	            }
	        }
	    } );
	}
	
	@Override
	void manageInjection(Bean beanInstance) {
		if (beanInstance instanceof EventRepositoryBean) {
			EventRepositoryBean bean = (EventRepositoryBean) beanInstance;
			bean.setGraphDatabaseServices(graphDb);
			bean.setIndexRepositoryBean(getIndexRepositoryBean());
		}
		
		if (beanInstance instanceof UserRepositoryBean) {
			UserRepositoryBean bean = (UserRepositoryBean) beanInstance;
			bean.setGraphDatabaseServices(graphDb);
			bean.setIndexRepositoryBean(getIndexRepositoryBean());
		}
		
		if (beanInstance instanceof IndexRepositoryBean) {
			IndexRepositoryBean bean = (IndexRepositoryBean) beanInstance;
			bean.setGraphDatabaseServices(graphDb);
		}
		
	}
	
}
