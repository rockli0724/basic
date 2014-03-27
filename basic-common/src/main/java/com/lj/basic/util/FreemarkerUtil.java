package com.lj.basic.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FreemarkerUtil
{
	private static FreemarkerUtil util;
	private static Configuration cfg;
	
	private FreemarkerUtil(){
		
	}
	
	/**
	 * 获取单例对象
	 * @param pname ftl模板文件所在路径
	 * @return
	 */
	public static FreemarkerUtil getInstance(String pname){
		if(util==null){
			 cfg=new Configuration();
			 cfg.setClassForTemplateLoading(FreemarkerUtil.class, pname);
			 cfg.setDefaultEncoding("utf-8");
			 
			 util=new FreemarkerUtil();
		}
		return util;
	}
	
	
	/**
	 * 获取模板对象
	 * @param fname 模板文件名称
	 * @return
	 */
	private Template getTemplate(String fname){
		try
		{
			return cfg.getTemplate(fname);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 通过标准输出流输出模板的结果
	 * @param root 数据对象
	 * @param fname 模板文件名
	 */
	public void sprint(Map<String,Object>root,String fname){
		try
		{
			getTemplate(fname).process(root, new PrintWriter(System.out));
		}
		catch (TemplateException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 基于文件输出
	 * @param root 数据对象
	 * @param fname 模板文件名
	 * @param outpath 输出文件路径
	 */
	public void fprint(Map<String,Object> root, String fname, String outpath){
		try
		{	
			Template template=getTemplate(fname);
			template.setEncoding("utf-8");
			//template.process(root, new PrintWriter(new File(outpath)));
			
			Writer out=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(outpath)),"utf-8"));
			template.process(root, out);
			
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (TemplateException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
	}
	
	
	
}
