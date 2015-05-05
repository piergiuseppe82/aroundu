package com.aroundu.core.infrastructure;

import com.aroundu.core.repopsitories.ImageRepositoryBean;

/**
 * @author piergiuseppe82
 *
 */
public abstract class ServiceBean extends Bean{
	private ImageRepositoryBean imageRepositoryBean;

	protected ImageRepositoryBean getImageRepositoryBean() {
		return imageRepositoryBean;
	}

	protected void setImageRepositoryBean(ImageRepositoryBean imageRepositoryBean) {
		this.imageRepositoryBean = imageRepositoryBean;
	}
	
}
