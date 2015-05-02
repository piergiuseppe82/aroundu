package com.aroundu.core.model;

import java.io.Serializable;

public abstract class AroundUNodeEntity implements Serializable{
	
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Long getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(Long creationTime) {
		this.creationTime = creationTime;
	}
	public Long getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 8519843022177991757L;
	private long id = -1;
	private Long creationTime;
	private Long updateTime;
	
	
	

}
