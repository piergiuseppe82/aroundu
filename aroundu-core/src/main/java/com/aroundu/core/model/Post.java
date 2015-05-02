package com.aroundu.core.model;


/**
 * @author piergiuseppe82
 *
 */
public class Post  extends AroundUNodeEntity {

    


	

	/**
	 * 
	 */
	private static final long serialVersionUID = 5020955834409424790L;
	
	private String title;
    private String locationDescription;
    private String postImage;
    private Double latitude;
    private Double longitude;
    private String accountId;
    private String postSpatialId;
    private Double distance;
    
	public Double getDistance() {
		return distance;
	}
	public void setDistance(Double distance) {
		this.distance = distance;
	}
	public String getPostSpatialId() {
		return postSpatialId;
	}
	public void setPostSpatialId(String postSpatialId) {
		this.postSpatialId = postSpatialId;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLocationDescription() {
		return locationDescription;
	}
	public void setLocationDescription(String locationDescription) {
		this.locationDescription = locationDescription;
	}
	public String getPostImage() {
		return postImage;
	}
	public void setPostImage(String postImage) {
		this.postImage = postImage;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
    
	public String toWKTPoint(){
		return "POINT("+latitude+" "+longitude+")";
	}
	@Override
	public String toString() {
		return "Post [title=" + title + ", locationDescription="
				+ locationDescription + ", postImage=" + postImage
				+ ", latitude=" + latitude + ", longitude=" + longitude
				+ ", accountId=" + accountId + ", postSpatialId="
				+ postSpatialId + ", distance=" + distance + "]";
	}
    
	

   

}
