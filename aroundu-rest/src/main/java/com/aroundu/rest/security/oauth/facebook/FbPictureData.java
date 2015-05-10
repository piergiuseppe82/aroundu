/**
 * 
 */
package com.aroundu.rest.security.oauth.facebook;

/**
 * @author piergiuseppe82
 *
 */
public class FbPictureData {
	private boolean is_silhouette;
	private String url;
	public boolean isIs_silhouette() {
		return is_silhouette;
	}
	public void setIs_silhouette(boolean is_silhouette) {
		this.is_silhouette = is_silhouette;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		return "FbPictureData [is_silhouette=" + is_silhouette + ", url=" + url
				+ "]";
	}
}
