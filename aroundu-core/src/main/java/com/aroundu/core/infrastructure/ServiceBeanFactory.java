package com.aroundu.core.infrastructure;

import com.aroundu.core.services.UserServiceBean;
import com.aroundu.core.services.EventServiceBean;
import com.aroundu.core.supports.Utility;

/**
 * @author piergiuseppe82
 *
 */
public class ServiceBeanFactory extends Factory{
	
	private static ServiceBeanFactory instance;
	private static RepositoryBeanFactory repositoryBeanFactory;
	
	private ServiceBeanFactory(){
		
	}
	
	private ServiceBeanFactory(String dbpath, boolean destroy){
		repositoryBeanFactory = RepositoryBeanFactory.instance(dbpath,destroy);            
	}	
	
	public static ServiceBeanFactory getInstance(String dbpath) {
		if(instance==null){
			synchronized (ServiceBeanFactory.class){
				if(instance==null)
					instance = new ServiceBeanFactory(dbpath,false);
			}
		}			
		return instance;
	}
	
	public static ServiceBeanFactory getInstance(String dbpath,boolean destroy) {
		if(instance==null){
			synchronized (ServiceBeanFactory.class){
				if(instance==null)
					instance = new ServiceBeanFactory(dbpath,destroy);
			}
		}			
		return instance;
	}
	
	
	public static ServiceBeanFactory getInstance() {
		return getInstance(Utility.getGraphDBPath());
	}
	
	
	public UserServiceBean getUserServiceBean(){
		return (UserServiceBean)getBean(UserServiceBean.class);
		
	}
	public EventServiceBean getEventServiceBean(){
		return (EventServiceBean)getBean(EventServiceBean.class);
	}

	@Override
	void manageInjection(Bean beanInstance) {
		if (beanInstance instanceof EventServiceBean) {
			EventServiceBean bean = (EventServiceBean) beanInstance;
			bean.setUserRepositoryBean(repositoryBeanFactory.getUserRepositoryBean());
			bean.setEventRepositoryBean(repositoryBeanFactory.getEventRepositoryBean());
			bean.setImageRepositoryBean(repositoryBeanFactory.getImageRepositoryBean());
		}
		
		if (beanInstance instanceof UserServiceBean) {
			UserServiceBean bean = (UserServiceBean) beanInstance;
			bean.setUserRepositoryBean(repositoryBeanFactory.getUserRepositoryBean());
			bean.setImageRepositoryBean(repositoryBeanFactory.getImageRepositoryBean());
		}
		
	}
	
	
	

	
}
