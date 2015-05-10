/**
 * 
 */
package com.aroundu.core.services.utilities;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import org.junit.Test;

import com.aroundu.core.supports.SecurityTokenProvider;

/**
 * @author piergiuseppe82
 *
 */
public class SecurityTest {
	
	@Test
	public void testTokengeneration() throws InvalidKeyException, NoSuchAlgorithmException{
		String secret = SecurityTokenProvider.generateSecret();
		System.out.println(secret);
		String generateToken = SecurityTokenProvider.generateToken(secret, "BLBNLJSJSJSOPISUJIOPSJIO");
		System.out.println(generateToken);
	}
}
