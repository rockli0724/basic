package com.lj.basic.util;

import org.apache.log4j.Logger;


/**
 * 这里把log4j.properties放到了basic-common中，此时如果调用basic-common的maven项目中没有log4j.properties文件<br/>
 * 那么就会调用这里的log4j.properties来读取信息<br/>
 * 但是如果maven项目中建立了log4j.properties，那么程序就不会再读取这里的property文件了。
 * @author Administrator
 *
 */
public final class MyLog4jLogger
{
	private static Logger logger=Logger.getLogger(MyLog4jLogger.class);
	
	private MyLog4jLogger(){}
	
	public  static void debug(Object obj){
		logger.debug( obj);
		 
		
	}
	
	
	public static void info(Object obj){
		logger.info(obj);
	}
	
	public static void warn(Object obj){
		logger.warn(obj);
	}
}
