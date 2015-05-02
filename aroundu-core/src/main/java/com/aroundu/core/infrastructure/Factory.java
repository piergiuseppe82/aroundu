package com.aroundu.core.infrastructure;

import java.util.HashMap;
import java.util.Map;

public abstract class Factory {
	private static Map<String,Bean> beanMap =new HashMap<String,Bean>();
	
	protected  Object getBean( Class<? extends Bean> clazz){
		try {
			Bean beanInstance = beanMap.get(clazz.getSimpleName());
			if(beanInstance == null){
				synchronized (clazz){
					if(beanInstance==null){
						beanInstance = clazz.cast(clazz.newInstance());
						manageInjection(beanInstance);
					}
				}
				
			}
			return beanInstance;
		} catch (Exception e) {
			e.printStackTrace();
		}return null;
	}

	abstract void manageInjection(Bean beanInstance);
	
	

	
	

}
