package com.blackberryappframework.Utility;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;

import net.rim.blackberry.api.browser.URLEncodedPostData;
import net.rim.device.api.crypto.CryptoTokenException;
import net.rim.device.api.crypto.CryptoUnsupportedOperationException;
import net.rim.device.api.crypto.Digest;
import net.rim.device.api.crypto.HMAC;
import net.rim.device.api.crypto.HMACKey;
import net.rim.device.api.crypto.MACOutputStream;
import net.rim.device.api.io.Base64OutputStream;

public class EncoderUtil {
    /** Default encoding used for text data */
    public static String DEFAULT_ENCODING = "UTF-8";
    
	public static String urlEncoder(SortedHashtable table) {
		String key;
		
		Enumeration enum = table.keys();
		URLEncodedPostData encoder = new URLEncodedPostData("UTF-8", false);
		
		while(enum.hasMoreElements()) {
			key = (String) enum.nextElement();
			encoder.append(key, (String) table.get(key));
		}
		
		return encoder.toString();
	}
	
	/**
	 * Calculate an RFC2104-compliant HMAC with provided string
	 * @param key the key for HMAC
	 * @param content the string to calculate
	 * @param digest the hash algorithm
	 * @return
	 */
	public static byte[] HMACEncoder(String key, String content, Digest digest) {
		
		try {
			HMACKey hmacKey = new HMACKey(key.getBytes());
			HMAC hMac = new HMAC(hmacKey, digest);
			
			MACOutputStream out = new MACOutputStream(hMac, null);
			out.write(content.getBytes());
			
			return hMac.getMAC();
		} catch (CryptoTokenException e) {
			e.printStackTrace();
		} catch (CryptoUnsupportedOperationException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Encodes the given byte array in base64
	 * @param toEncode
	 * @return
	 */
	public static String encodeBase64(byte[] toEncode) {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(toEncode.length);
		Base64OutputStream base64OutputStream = new Base64OutputStream(byteArrayOutputStream);
		
		try {
			base64OutputStream.write(toEncode, 0, toEncode.length);
			base64OutputStream.flush();
			base64OutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		return byteArrayOutputStream.toString();
	}
	
    /**
     * URL encodes the specified string and returns it.  All keys specified by
     * users need to URL encoded.  The URL encoded key needs to be used in the
     * string to sign (canonical resource path).
     *
     * @param s
     *            The string to URL encode.
     *
     * @return The new, URL encoded, string.
     */
    public static String urlEncode(String s) {
        if (s == null) return null;

            String encodedString = URLUTF8Encoder.encode(s);
            // Web browsers do not always handle '+' characters well, use the
            // well-supported '%20' instead.
            return encodedString;// = GeneralUtils.replace(encodedString, "\\+", "%2B", true);
    }
}
