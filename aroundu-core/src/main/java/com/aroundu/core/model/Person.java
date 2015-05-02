package com.aroundu.core.model;



public class Person  extends AroundUNodeEntity {

    


	

	/**
	 * 
	 */
	private static final long serialVersionUID = 5020955834409424790L;
	private String fullName;
    private String accountId;
    private String password;
    private String email;
    private String profileImage;
    
    
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
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
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
	@Override
	public String toString() {
		return "Person [fullName=" + fullName + ", accountId=" + accountId
				+ ", password=" + password + ", email=" + email
				+ ", profileImage=" + profileImage + "]";
	}
   

}
