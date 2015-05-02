package com.aroundu.core.supports;

import java.io.File;
import java.security.MessageDigest;


/**
 * @author piergiuseppe82
 *
 */
public class Utility {
	
	public static String getDataPath(){
		if(System.getenv("OPENSHIFT_DATA_DIR") != null)
			return System.getenv("OPENSHIFT_DATA_DIR");
		else
			return System.getProperty("user.home")+File.separator+"neo4jdb";
	}
	
	
	public static String passwordEncode(String password){
			try {
			 return	new String(MessageDigest.getInstance("MD5").digest(password.getBytes()));
			} catch (Throwable e) {
				e.printStackTrace();
			}
			return null;
		
	}
	
	public static double distance(double lat1, double lon1, double lat2, double lon2, char unit) {
		  double theta = lon1 - lon2;
		  double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
		  dist = Math.acos(dist);
		  dist = rad2deg(dist);
		  dist = dist * 60 * 1.1515;
		  if (unit == 'K') {
		    dist = dist * 1.609344;
		  } else if (unit == 'N') {
		  	dist = dist * 0.8684;
		    }
		  return (dist);
		}
	
	public static double rad2deg(double rad) {
		  return (rad * 180 / Math.PI);
		}
	
	
	public static double deg2rad(double deg) {
		  return (deg * Math.PI / 180.0);
		}

}
