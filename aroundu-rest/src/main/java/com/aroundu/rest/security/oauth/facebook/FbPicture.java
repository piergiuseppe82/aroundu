/**
 * 
 */
package com.aroundu.rest.security.oauth.facebook;

/**
 * @author piergiuseppe82
 *
 */
public class FbPicture {
	private Long id;
	private FbPictureData data;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public FbPictureData getData() {
		return data;
	}
	public void setData(FbPictureData data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "FbPicture [id=" + id + ", data=" + data + "]";
	}
}
