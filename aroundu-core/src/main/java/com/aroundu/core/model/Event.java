package com.aroundu.core.model;



/**
 * @author piergiuseppe82
 *
 */
public class Event  extends NodeEntity {

    


	

	/**
	 * 
	 */
	private static final long serialVersionUID = 5020955834409424790L;
	
	private String title;
    private String address;
    private String eventImage;
    private String eventImageUrl;
    private Double latitude;
    private Double longitude;
    private String position;
    private Double distance;
    private User   author;
    private Long hangsNumber;
    private Long aroundEventsNumber;
    
	public Double getDistance() {
		return distance;
	}
	public void setDistance(Double distance) {
		this.distance = distance;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getEventImage() {
		return eventImage;
	}
	public void setEventImage(String eventImage) {
		this.eventImage = eventImage;
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
	
	public String getPosition() {
		if(latitude !=null && longitude != null)
			return latitude+","+longitude;
		return position;
	}
	public void setPosition(String position) {
		if(position != null && position.contains(",") && latitude == null && longitude == null){
			String[] split = position.split(",");
			if(split.length > 1){
				latitude = new Double(split[0]);
				longitude = new Double(split[1]);
			}
		}
		this.position = position;
	}
	public User getAuthor() {
		return author;
	}
	public void setAuthor(User author) {
		this.author = author;
	}
	public String getEventImageUrl() {
		return eventImageUrl;
	}
	public void setEventImageUrl(String eventImageUrl) {
		this.eventImageUrl = eventImageUrl;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Long getAroundEventsNumber() {
		return aroundEventsNumber;
	}
	public void setAroundEventsNumber(Long aroundEventsNumber) {
		this.aroundEventsNumber = aroundEventsNumber;
	}
	public Long getHangsNumber() {
		return hangsNumber;
	}
	public void setHangsNumber(Long hangsNumber) {
		this.hangsNumber = hangsNumber;
	}
	@Override
	public String toString() {
		return "Event [title=" + title + ", address=" + address
				+ ", eventImage=" + eventImage + ", eventImageUrl="
				+ eventImageUrl + ", latitude=" + latitude + ", longitude="
				+ longitude + ", position=" + position + ", distance="
				+ distance + ", author=" + author + ", hangsNumber="
				+ hangsNumber + ", aroundEventsNumber=" + aroundEventsNumber
				+ "]";
	}
	
	
	

   

}
