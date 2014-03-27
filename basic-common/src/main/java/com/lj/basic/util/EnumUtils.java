package com.lj.basic.util;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;

public class EnumUtils {
	
	/**
	 * 将枚举中的值转换为一组序数列表
	 * @param clz 枚举类名
	 * @return	一组arraylist
	 */
	public static List<Integer> enum2Ordinal(Class<? extends Enum> clz){
		 
		List<Integer>ids=new ArrayList<Integer>();
		for(Enum en:clz.getEnumConstants()){
			ids.add(en.ordinal());
		}
		return ids;
		
	}
	
	/**
	 * 将枚举中的值转换为相应的名称字符串列表
	 * @param clz
	 * @return
	 */
	public static List<String> enum2Name(Class<? extends Enum> clz){
		List<String> names=new ArrayList<String>();
		for(Enum en:clz.getEnumConstants()){
			names.add(en.name());
		}
		return names;
		
	}
	
	/**
	 * 将枚举中的值转换为序号和名称的map
	 * @param clz
	 * @return
	 */
	public static Map<Integer,String> enum2BasicMap(Class<? extends Enum> clz){
		Map<Integer,String> map=new HashMap<Integer, String>();
		for(Enum en:clz.getEnumConstants()){
			map.put(en.ordinal(), en.name());
		}
		
		return map;
	}
	
	/**
	 * 将枚举中的值的某个属性转换为字符串列表
	 * @param clz
	 * @param propName
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static List<String> enumProp2List(Class<? extends Enum> clz,String propName){
		List<String>  rels=new ArrayList<String>();
		if(!clz.isEnum()) return null;
		
		Enum[] enums=clz.getEnumConstants();
		
		for(Enum en:enums){
			try {	
				rels.add((String)PropertyUtils.getProperty(en, propName));
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
		
		return rels;
	}
	
	
	/**
	 * 将枚举中的值转换为序号和字符串的map
	 * @param clz
	 * @return
	 */
	public static Map<Integer,String> enumPropByOrdinalMap(Class<? extends Enum> clz,String propName){
		Map<Integer,String> map=new HashMap<Integer, String>();
		for(Enum en:clz.getEnumConstants()){
			try {	
				map.put(en.ordinal(), (String)PropertyUtils.getProperty(en, propName));
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return map;
	}
	
	public static Map<String,String> enumProp2NameMap(Class<? extends Enum> clz, String propName){
		Map<String , String> map=new HashMap<String,String>();
		for(Enum en:clz.getEnumConstants()){
			try {
				map.put(en.name(), (String)PropertyUtils.getProperty(en, propName));
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return map;
		
	}
	
	
	 
	
	 
}
