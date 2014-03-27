package com.lj.basic.util;

import org.junit.Test;

public class SecurityUtilTest {
	
	
	@Test
	public void testPwsMd5(){
		
	}
	
	@Test
	public void testPwsUserNameMd5(){
		String encryp1=SecurityUtil.md5("23315");
		String encryp2=SecurityUtil.md5("alleni", "23315");
		System.out.println(encryp1);
		System.out.println(encryp2);
		
		System.out.println(SecurityUtil.md5("alleni12323315"));
	}
	
	
}
