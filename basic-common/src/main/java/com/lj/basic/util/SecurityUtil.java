package com.lj.basic.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SecurityUtil {
	public static String md5(String password){
		 try {
			MessageDigest md=MessageDigest.getInstance("MD5");
			md.update(password.getBytes());
			return new BigInteger(1, md.digest()).toString(16);
		} catch (NoSuchAlgorithmException e) {
			// XXX Auto-generated catch block
			e.printStackTrace();
		}
		 return null;
		
		
	}
	
	public static String md5(String username,String password){
		 try {
			MessageDigest md=MessageDigest.getInstance("MD5");
			String str=username+password;
			md.update(str.getBytes());
			return new BigInteger(1, md.digest()).toString(16);
		} catch (NoSuchAlgorithmException e) {
			// XXX Auto-generated catch block
			e.printStackTrace();
		}
		 return null;
		
		
	}
	
	public static void main(String[] args)
	{
		System.out.println(md5("admin", "23315"));
	}
	
}
