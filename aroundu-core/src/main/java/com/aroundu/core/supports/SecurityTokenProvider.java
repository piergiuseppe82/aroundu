/**
 * 
 */
package com.aroundu.core.supports;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;


/**
 * @author piergiuseppe82
 *
 */
public class SecurityTokenProvider {
	public static String generateToken(String secret,String data) throws NoSuchAlgorithmException, InvalidKeyException{
		     Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
		     SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
		     sha256_HMAC.init(secret_key);
		     byte[] doFinal = sha256_HMAC.doFinal(data.getBytes());
		     System.out.println(new String(doFinal));
		     String hash = Base64.encodeBase64String(doFinal);
		     return hash;
	}
	
	public static boolean checkToken(String secret,String data, String token) throws NoSuchAlgorithmException, InvalidKeyException{
		byte[] decodedToken = Base64.decodeBase64(token);
		Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
		SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
	    sha256_HMAC.init(secret_key);
	    byte[] doFinal = sha256_HMAC.doFinal(data.getBytes());
	    return Arrays.equals(decodedToken, doFinal);
	}
	
	public static String generateSecret(){
		return new BigInteger(300, new SecureRandom()).toString(16);
	}
	
	
	

}
