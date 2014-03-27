package com.lj.basic.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

 
import static org.junit.Assert.assertEquals;

import junit.framework.Assert;

import org.junit.Test;

public class JsonUtilTest {
	
	
	
	@Test
	public void Obj2JsonTest(){
		JsonUtil jsonUtil=JsonUtil.getInstance();
		List<String> al=new ArrayList<String>();
		al.add("hello");
		al.add("world");
	//	System.out.println(jonUtil.obj2json(al));
		 assertEquals(jsonUtil.obj2json(al),"[\"hello\",\"world\"]" );
		 
		Map<Integer,String> map=new HashMap<Integer,String>();
		map.put(1, "alleni");
		map.put(2, "eline");
	//	System.out.println(jsonUtil.obj2json(map));
	 	 assertEquals(jsonUtil.obj2json(map),"{\"1\":\"alleni\",\"2\":\"eline\"}");
	 
		
		Object[] objs=new Object[]{"hello","world","!"};
	//	System.out.println(jsonUtil.obj2json(objs));
		assertEquals(jsonUtil.obj2json(objs),"[\"hello\",\"world\",\"!\"]");
		
	}
}
