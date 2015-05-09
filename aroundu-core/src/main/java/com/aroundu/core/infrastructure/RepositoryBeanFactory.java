package com.aroundu.core.infrastructure;

import java.io.File;
import java.io.IOException;

import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.io.fs.FileUtils;

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
	
	
	private RepositoryBeanFactory(){
		
	}
	private RepositoryBeanFactory(String dbpath){
		
        synchronized (GraphDatabaseService.class){
        	graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(dbpath);
        	
        	createConstraints(graphDb);
        }      
	
	
        registerShutdownHook( graphDb, false, dbpath );
	}
	
	/**
	 * @param graphDb2
	 */
	private void createConstraints(GraphDatabaseService graphDb2) {
		try ( Transaction tx = graphDb2.beginTx() )
		{
			graphDb2.schema().constraintFor(DynamicLabel.label("User"))
					.assertPropertyIsUnique("username").create();
			graphDb2.schema().constraintFor(DynamicLabel.label("User"))
					.assertPropertyIsUnique("token").create();
			tx.success();
		}		
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
