package com.chinobot.aiuas.bot_collect.resource.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class QcloudUtils {

	private static final char[] DIGITS_LOWER =
        {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
	
//	private static long limitTime = 0;
//	
//	private static String SendUrl = "";
	
//	private static String KEY = "";
//	
//	private static String URL = "";

/*
 * KEY+ streamName + txTime
 */
	public static String getSafeUrl(String key, String streamName, long txTime) {
	    String input = new StringBuilder().
	            append(key).
	            append(streamName).
	            append(Long.toHexString(txTime).toUpperCase()).toString();
	
	    String txSecret = null;
	    try {
	        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
	        txSecret  = byteArrayToHexString(
	                messageDigest.digest(input.getBytes("UTF-8")));
	    } catch (NoSuchAlgorithmException e) {
	        e.printStackTrace();
	    } catch (UnsupportedEncodingException e) {
	        e.printStackTrace();
	    }
	
	    return txSecret == null ? "" :
	            new StringBuilder().
	                    append("txSecret=").
	                    append(txSecret).
	                    append("&").
	                    append("txTime=").
	                    append(Long.toHexString(txTime).toUpperCase()).
	                    toString();
	}
	
	private static String byteArrayToHexString(byte[] data) {
	    char[] out = new char[data.length << 1];
	
	    for (int i = 0, j = 0; i < data.length; i++) {
	        out[j++] = DIGITS_LOWER[(0xF0 & data[i]) >>> 4];
	        out[j++] = DIGITS_LOWER[0x0F & data[i]];
	    }
	    return new String(out);
	}
	
	public static String getQcloudUrl(String key, String url, String streamName, int days){
	
	    long now = System.currentTimeMillis()/1000;
//	    long betweenDays = (now - limitTime) / (3600L * 24);
//	
//	    if (SendUrl.equals("") || limitTime == 0 || !KEY.equals(key) || !URL.equals(url) || betweenDays > -3) {
//	    	limitTime = now + 3600L * 24 * 30;
//	    	KEY = key;
//	    	URL = url;
//	        SendUrl = url
//	                + streamName
//	                + "?"
//	                + QcloudUtils.getSafeUrl(key, streamName, limitTime);
//	
//	        
//	    }
	    long limitTime = now + 3600L * 24 * days;
        String sendUrl = "rtmp://" + url + "/live/"
                + streamName
                + "?"
                + QcloudUtils.getSafeUrl(key, streamName, limitTime);
	    return sendUrl;
	}
}
