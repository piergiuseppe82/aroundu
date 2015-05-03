package com.aroundu.core.infrastructure;

import java.io.File;
import java.io.IOException;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.io.fs.FileUtils;

import com.aroundu.core.repopsitories.IndexRepositoryBean;
import com.aroundu.core.repopsitories.UserRepositoryBean;
import com.aroundu.core.repopsitories.EventRepositoryBean;

/**
 * @author piergiuseppe82
 *
 */
public class RepositoryBeanFactory extends Factory{
	private static GraphDatabaseService graphDb; 
	private static RepositoryBeanFactory factoryInstace;
	
	
	private RepositoryBeanFactory(){
		
	}
	private RepositoryBeanFactory(String dbpath,boolean destroy){
		
        synchronized (GraphDatabaseService.class){
        	graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(dbpath);
        }      
	
	
        registerShutdownHook( graphDb, destroy, dbpath );
	}
	
	public static RepositoryBeanFactory instance(String dbpath) {
		if(factoryInstace==null){
			synchronized (RepositoryBeanFactory.class){
				if(factoryInstace==null)
					factoryInstace = instance(dbpath, false);
			}
		}			
		return factoryInstace;
		
	}
	
	
	public static RepositoryBeanFactory instance(String dbpath, boolean destroy) {
		if(factoryInstace==null){
			synchronized (RepositoryBeanFactory.class){
				if(factoryInstace==null)
					factoryInstace = new RepositoryBeanFactory(dbpath,destroy);
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
