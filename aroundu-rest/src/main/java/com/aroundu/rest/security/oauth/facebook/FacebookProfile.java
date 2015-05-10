/**
 * 
 */
package com.aroundu.rest.security.oauth.facebook;

/**
 * @author piergiuseppe82
 *
 */
public class FacebookProfile {
	
		 
	private String id;
	private String first_name;
	private String last_name;
	private String name;
	private Integer timezone;
	private String updated_time;
	private Boolean verified;
	private String link;
	private FbPicture picture;
	private String locale;
	private String gender;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getTimezone() {
		return timezone;
	}
	public void setTimezone(Integer timezone) {
		this.timezone = timezone;
	}
	public String getUpdated_time() {
		return updated_time;
	}
	public void setUpdated_time(String updated_time) {
		this.updated_time = updated_time;
	}
	public Boolean getVerified() {
		return verified;
	}
	public void setVerified(Boolean verified) {
		this.verified = verified;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public FbPicture getPicture() {
		return picture;
	}
	public void setPicture(FbPicture picture) {
		this.picture = picture;
	}
	public String getLocale() {
		return locale;
	}
	public void setLocale(String locale) {
		this.locale = locale;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	@Override
	public String toString() {
		return "FacebookProfile [id=" + id + ", first_name=" + first_name
				+ ", last_name=" + last_name + ", name=" + name + ", timezone="
				+ timezone + ", updated_time=" + updated_time + ", verified="
				+ verified + ", link=" + link + ", picture=" + picture
				+ ", locale=" + locale + ", gender=" + gender + "]";
	}
	
	
	
}
