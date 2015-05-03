package com.aroundu.core.model;



/**
 * @author piergiuseppe82
 *
 */
public class User  extends NodeEntity {

    


	

	/**
	 * 
	 */
	private static final long serialVersionUID = 5020955834409424790L;
	private String fullName;
	private String displayName;
    private String username;
    private String password;
    private String email;
    private String profileImage;
    private String profileImageUrl;
    
    
	public String getProfileImage() {
		return profileImage;
	}
	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getProfileImageUrl() {
		return profileImageUrl;
	}
	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}
	@Override
	public String toString() {
		return "User [fullName=" + fullName + ", displayName=" + displayName
				+ ", username=" + username + ", password=" + password
				+ ", email=" + email + ", profileImage=" + profileImage
				+ ", profileImageUrl=" + profileImageUrl + ", getId()="
				+ getId() + ", getCreationTime()=" + getCreationTime()
				+ ", getUpdateTime()=" + getUpdateTime() + "]";
	}
   

}
