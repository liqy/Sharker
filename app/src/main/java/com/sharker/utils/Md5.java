package com.sharker.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class Md5 {



    public static String doubleToMd5(String str){
		return toMd5(toMd5(str));
	}
	public static String toMd5(String str) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			byte[] by = md.digest();// 
			String ss = "";
			String s;
			for (int i = 0; i < by.length; i++) {
				s = Integer.toHexString(0xFF & by[i]);
				if (s.length() == 1) {
					ss = ss + "0" + s;
				} else {
					ss = ss + s;
				}
			}
			return ss;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
}
