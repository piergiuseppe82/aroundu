package com.aroundu.core.infrastructure;

import com.aroundu.core.services.PersonServiceBean;
import com.aroundu.core.services.PostServiceBean;
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
	
	private ServiceBeanFactory(String dbpath){
		repositoryBeanFactory = RepositoryBeanFactory.instance(dbpath);            
	}	
	
	public static ServiceBeanFactory getInstance(String dbpath) {
		if(instance==null){
			synchronized (ServiceBeanFactory.class){
				if(instance==null)
					instance = new ServiceBeanFactory(dbpath);
			}
		}			
		return instance;
	}
	
	public static ServiceBeanFactory getInstance() {
		return getInstance(Utility.getDataPath());
	}
	
	
	public PersonServiceBean getPersonServiceBean(){
		return (PersonServiceBean)getBean(PersonServiceBean.class);
		
	}
	public PostServiceBean getPostServiceBean(){
		return (PostServiceBean)getBean(PostServiceBean.class);
	}

	@Override
	void manageInjection(Bean beanInstance) {
		if (beanInstance instanceof PostServiceBean) {
			PostServiceBean bean = (PostServiceBean) beanInstance;
			bean.setPersonRepositoryBean(repositoryBeanFactory.getPersonRepositoryBean());
			bean.setPostRepositoryBean(repositoryBeanFactory.getPostRepositoryBean());
		}
		
		if (beanInstance instanceof PersonServiceBean) {
			PersonServiceBean bean = (PersonServiceBean) beanInstance;
			bean.setPersonRepositoryBean(repositoryBeanFactory.getPersonRepositoryBean());
		}
		
	}
	
	
	

	
}
