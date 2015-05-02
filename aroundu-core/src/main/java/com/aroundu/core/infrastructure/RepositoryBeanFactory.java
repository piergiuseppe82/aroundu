package com.aroundu.core.infrastructure;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

import com.aroundu.core.repopsitories.IndexRepositoryBean;
import com.aroundu.core.repopsitories.PersonRepositoryBean;
import com.aroundu.core.repopsitories.PostRepositoryBean;

public class RepositoryBeanFactory extends Factory{
	private static GraphDatabaseService graphDb; 
	private static RepositoryBeanFactory factoryInstace;
	
	
	private RepositoryBeanFactory(){
		
	}
	private RepositoryBeanFactory(String dbpath){
		
        synchronized (GraphDatabaseService.class){
        	graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(dbpath);
        }      
	
	
        registerShutdownHook( graphDb );
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

	public PersonRepositoryBean getPersonRepositoryBean(){
		return (PersonRepositoryBean) getBean(PersonRepositoryBean.class);
	}
	
	public IndexRepositoryBean getIndexRepositoryBean(){
		return (IndexRepositoryBean) getBean(IndexRepositoryBean.class);
	}
	
	
	
	public PostRepositoryBean getPostRepositoryBean(){
		return (PostRepositoryBean) getBean( PostRepositoryBean.class);
		
	}
	
	
	
	
	
	private static void registerShutdownHook( final GraphDatabaseService graphDb )
	{
	    Runtime.getRuntime().addShutdownHook( new Thread()
	    {
	        @Override
	        public void run()
	        {
	            graphDb.shutdown();
	        }
	    } );
	}
	
	@Override
	void manageInjection(Bean beanInstance) {
		if (beanInstance instanceof PostRepositoryBean) {
			PostRepositoryBean bean = (PostRepositoryBean) beanInstance;
			bean.setGraphDatabaseServices(graphDb);
			bean.setIndexRepositoryBean(getIndexRepositoryBean());
		}
		
		if (beanInstance instanceof PersonRepositoryBean) {
			PersonRepositoryBean bean = (PersonRepositoryBean) beanInstance;
			bean.setGraphDatabaseServices(graphDb);
			bean.setIndexRepositoryBean(getIndexRepositoryBean());
		}
		
		if (beanInstance instanceof IndexRepositoryBean) {
			IndexRepositoryBean bean = (IndexRepositoryBean) beanInstance;
			bean.setGraphDatabaseServices(graphDb);
		}
		
	}
	
}
