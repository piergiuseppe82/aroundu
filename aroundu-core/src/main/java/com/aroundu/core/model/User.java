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
	private String displayName;
    private String username;
    private String password;
    private String email;
    private String image;
    private String thumbnail;
    private String background;
    private String auth_domain;
    private String token;
    private String gender;
    private String locale;
    private Long expiretime;
    
	
	
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
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	public String getBackground() {
		return background;
	}
	public void setBackground(String background) {
		this.background = background;
	}
	public String getAuth_domain() {
		return auth_domain;
	}
	public void setAuth_domain(String auth_domain) {
		this.auth_domain = auth_domain;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Long getExpiretime() {
		return expiretime;
	}
	public void setExpiretime(Long expiretime) {
		this.expiretime = expiretime;
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
		return "User [displayName=" + displayName + ", username=" + username
				+ ", password=" + password + ", email=" + email + ", image="
				+ image + ", thumbnail=" + thumbnail + ", background="
				+ background + ", auth_domain=" + auth_domain + ", token="
				+ token + ", gender=" + gender + ", locale=" + locale
				+ ", expiretime=" + expiretime + ", getCreationTime()="
				+ getCreationTime() + ", getUpdateTime()=" + getUpdateTime()
				+ "]";
	}
	
   

}
