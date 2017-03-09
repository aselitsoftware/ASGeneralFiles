package com.aselitsoftware;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {

	private static String getMD5(byte[] buffer) {
		
		String result = "";
		
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(buffer);
			byte t[] = md.digest();
			for (byte b: t)
				result += String.format("%02x", b & 0xff);
		} catch (Exception ex) {
			
		}
		return result.toUpperCase();
	}
	
	/**
	 * 
	 * @param input
	 * @return
	 */
	public static String getStringHash(String source) {
		
		byte[] buffer;
		
		try {
			
			buffer = source.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			
			buffer = source.getBytes();
		}
		
		return getMD5(buffer);
	}
	
	/**
	 * 
	 * @param file
	 * @param len
	 * @return
	 */
	public static String getFileHash(File source, long len) {
		
		InputStream stream = null;
		byte[] buffer = null;
		long bufLen;
		
		try {
			
			stream = new FileInputStream(source);
			
			if (len > 0)
				bufLen = (len < source.length()) ? len : source.length();
			else
				bufLen = source.length();
			buffer = new byte[(int) bufLen];
			stream.read(buffer, 0, (int) bufLen);
			
			return getMD5(buffer);
			
		} catch (FileNotFoundException ex) {
		
		} catch (IOException ex) {
		
		} catch (Exception ex) {
		
		} finally {
		
			try {
				if (null != stream)
					stream.close();
			} catch (IOException e) {
			}
		}
		
		return "";
	}
}
