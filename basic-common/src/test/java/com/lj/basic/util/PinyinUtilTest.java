package com.lj.basic.util;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PinyinUtilTest {
	
	 
	  
	  @Before
	  public void setUp(){
		 
	  }
	  
	@Test
	public void str2PinyinTest(){
	//	System.out.println(PinyinUtil.str2Pinyin("你好", null));
		assertEquals(PinyinUtil.str2Pinyin("你好", null), "nihao");
	// 	System.out.println(PinyinUtil.str2Pinyin("吴玲","_"));
		 assertEquals(PinyinUtil.str2Pinyin("吴玲", "_"),"wu_ling");
		 
		 assertEquals(PinyinUtil.str2Pinyin("nihao", null),"nihao");
	}
	
	@Test
	public void getInitialsTest(){
		System.out.println(PinyinUtil.getInitials("李京"));
		assertEquals(PinyinUtil.getInitials("吴玲"), "wl");
	}
	
}
