package com.lj.basic.util;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.sun.image.codec.jpeg.ImageFormatException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import javax.imageio.ImageIO;

public class ImageUtil
{
	private final static ImageUtil imageUtil = new ImageUtil();

	private ImageUtil()
	{

	}

	public static ImageUtil getInstance()
	{
		return imageUtil;
	}
	
	
	/**
	 * 切割图片</br>
	 * width和height是切割后的图片的宽度和长度， 而x和y表示源文件截取的起始坐标.</br>
	 * 比如我设置x为100, y为0，那就相当于从图片顶部(y=0), 左边100(x=100)的位置开始截取</br>
	 * @param os 切割后的输出流
	 * @param is 输入流
	 * @param type 文件的图片类型
	 * @param x  x坐标
	 * @param y  y坐标
	 * @param width 宽度
	 * @param height 高度
	 */
	public void cropImg(OutputStream os, InputStream is, String type, int x,
			int y, int width, int height)
	{
		Image img = null;
		BufferedImage imgBuf = null;
		ImageFilter cropFilter = null;

		try
		{
			imgBuf = ImageIO.read(is);
			cropFilter = new CropImageFilter(x, y, width, height);
			img = Toolkit.getDefaultToolkit().createImage(
					new FilteredImageSource(imgBuf.getSource(), cropFilter));
			BufferedImage tag = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			tag.getGraphics().drawImage(img, 0, 0, null);
			ImageIO.write(tag, type, os);
		}
		catch (IOException e)
		{
			// XXX Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (os != null)
					os.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}

			try
			{
				if (is != null)
				{
					is.close();
				}
			}
			catch (IOException e)
			{
				// XXX Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	
	
	/**
	 * 
	 * @param oPath 输出文件
	 * @param is 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @return
	 */
	public String cropImg(String oPath, InputStream is, int x, int y, int width, int height){
		String type=oPath.substring(oPath.lastIndexOf(".")+1);
		try{
			OutputStream os=new FileOutputStream(oPath);
			cropImg(os,is,type,x,y,width,height);
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	public String cropImg(String oPath, String iPath, int x, int y, int width, int height){
		String type = oPath.substring(oPath.lastIndexOf(".")+1);
			try{
				OutputStream os=new FileOutputStream(oPath);
				InputStream is = new FileInputStream(iPath);
				cropImg(os,is,type,x,y,width,height);
				return oPath;
			} catch(FileNotFoundException e){
				e.printStackTrace();
			}
			
			return null;
	}
	
	public String cropImg(String iPath, int x, int y, int width, int height){
		 String type=iPath.substring(iPath.lastIndexOf(".")+1);
		 String small_name=generateSmallFileName(iPath);
		 
		 try
		{
			InputStream is=new FileInputStream(iPath);
			OutputStream os=new FileOutputStream(small_name);
			cropImg(os,is,type,x,y,width,height);
			return small_name;
		}
		catch (FileNotFoundException e)
		{
			// XXX Auto-generated catch block
			e.printStackTrace();
		}
		 return null;
	}
	
	/**
	 * 生成文件名   hello.txt->hello_small.txt
	 * @param name 原文件名
	 * @return 加入了_small在文件名后面， 后缀前面
	 */
	private String generateSmallFileName(String name){
		String fn=name.substring(0,name.lastIndexOf("."));
		return name.replace(fn, fn+"_small");
	}
	
	
	/*压缩图片开始*/
	/**
	 * 压缩图片操作
	 * @param os 要转换的图片的输出流
	 * @param is 要转换图片的输入流
	 * @param width 要压缩的宽度
	 * @param height 要压缩的高度
	 * @param proportion 是否进行等比例压缩
	 */
	public void compressImg(OutputStream os, InputStream is, int width, int height, boolean proportion){
		compressImg(os,is,width,height,proportion, false);
	}
	
	
	/**
	 * width和height是压缩后的图片的宽度和高度。</br>
	 * 如果想要把图片按照原比例进行压缩， 就设置proportion为true， 然后设置width和height的其中一个为0,即可。</br>
	 * @param os
	 * @param is
	 * @param width
	 * @param height
	 * @param proportion
	 * @param magnify
	 */
	public void compressImg(OutputStream os, InputStream is, int width, int height, boolean proportion, boolean magnify){
		BufferedImage img=null;
		
		try{
			img = ImageIO.read(is);
			int newWidth = 0; int newHeight=0;//孔浩没有写=0
			int oldWidth = img.getWidth(null);
			int oldHeight = img.getHeight();
			boolean isWrite = false;
			if(!magnify){
				boolean iw = width>=height? true:false;
				if(iw){
					if(width>oldWidth) isWrite=true;
				}else{
					if(height>oldHeight) isWrite=true;
				}
				System.out.println(width+" , "+height);
				System.out.println(img.getWidth(null)+","+img.getHeight(null));
				if(isWrite){
					System.out.println("write");
					ImageIO.write(img, "jpg", os);
					os.flush();
				}
				System.out.println(newWidth+","+newHeight);
			}
			if(!isWrite){
				//判断是否是等比缩放
				if(proportion){
					//为等比缩放计算输出的图片宽度及高度
					double rate1=((double)oldWidth)/(double)width+0.1;
					double rate2=((double)oldHeight)/(double)height+0.1;
					System.out.println((rate1+","+rate2));
					//根据缩放比率大的进行缩放控制
					double rate=rate1<rate2?rate1:rate2;
					newWidth=(int)(((double)img.getWidth(null))/rate);
					newHeight=(int)(((double)img.getHeight(null))/rate);
				}else{
					newWidth=width;//输出的图片宽度
					newHeight=height;//输出的图片高度
				}
				BufferedImage tag=new BufferedImage((int)newWidth,(int)newHeight,BufferedImage.TYPE_INT_RGB);
				tag.getGraphics().drawImage(img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0,0,null);
				JPEGImageEncoder encoder=JPEGCodec.createJPEGEncoder(os);
				encoder.encode(tag);
			}
		}catch(ImageFormatException e){
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}finally{
			try {
				if(os!=null) os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if(is!=null) is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 输入File对象压缩文件
	 * @param outputFile 输出文件
	 * @param inputFile  输入文件
	 * @param width   要压缩的宽度
	 * @param height   要压缩的高度
	 * @param proportion  是否进行等比例压缩
	 * @return 
	 */
	public String compressImg(File outputFile, File inputFile, int width,int height,boolean proportion){
		InputStream is=null;
		OutputStream os=null;
		try{
			 is=new FileInputStream(inputFile);
			 os=new FileOutputStream(outputFile);
			 compressImg(os,is,width,height,proportion);
			 return outputFile.getAbsolutePath();
		} catch(FileNotFoundException e){
			e.printStackTrace();
		}
		return null;
	}
	
	public String compressImg(File outputFile, InputStream is, int width, int height,boolean proportion){
		OutputStream os=null;
		try
		{
			os=new FileOutputStream(outputFile);
			compressImg(os, is, width,height,proportion);
			return outputFile.getAbsolutePath();
		}
		catch (FileNotFoundException e)
		{
			// XXX Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	 * @param oPath output path
	 * @param is inputstream
	 * @param width
	 * @param height
	 * @param proportion
	 * @return
	 */
	public String compressImg(String oPath, InputStream is, int width,int height, boolean proportion ){
		OutputStream os = null;
		try{
			os=new FileOutputStream(oPath);
			compressImg(os,is,width,height,proportion);
		}catch (FileNotFoundException  e) {
			e.printStackTrace();
		}
		return oPath;
	}
	
	/**
	 * 
	 * @param oPath output path
	 * @param iPath input path
	 * @param width 
	 * @param height
	 * @param proportion
	 * @return
	 */
	public String compressImg(String oPath, String iPath, int width, int height, boolean proportion){
		InputStream is=null;
		OutputStream os=null;
		try
		{
			is=new FileInputStream(iPath);
			os=new FileOutputStream(oPath);
			compressImg(os, is, width, height, proportion);
		}
		catch (FileNotFoundException e)
		{
			// XXX: handle exception
		}
		return oPath;
	}
	
	/*获取图片宽度开始*/
	/**
	 * 通过InputStream获取图片宽度
	 * @param is
	 * @return
	 */
	public int getWidth(InputStream is)throws IOException{
		BufferedImage img=null;
		img=ImageIO.read(is);
		return img.getWidth();
	}
	
	public int getWidth(String path){
		InputStream is=null;
		try
		{
			is=new FileInputStream(path);
			return getWidth(is);
		}
		catch (FileNotFoundException e)
		{
			// XXX Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// XXX Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	public int getWidth(File file){
		InputStream is=null;
		try
		{
			is=new FileInputStream(file);
			return getWidth(is);
		}
		catch (FileNotFoundException e)
		{
			// XXX Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// XXX Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
		
	}
	/*获取图片宽度结束*/
	
	
	/*获取图片高度开始*/
	
	public int getHeight(InputStream is) throws IOException{
		BufferedImage img=null;
		img = ImageIO.read(is);
		return img.getHeight();
	}
	
	public int getHeight(String path){
		InputStream is=null;
		try
		{
			is=new FileInputStream(path);
			return getHeight(is);
		}
		catch (FileNotFoundException e)
		{
			// XXX Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// XXX Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	public int getHeight(File file){
		InputStream is=null;
		try
		{
			is = new FileInputStream(file);
			return getHeight(is);
		}
		catch (FileNotFoundException e)
		{
			// XXX Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// XXX Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	/*获取图片高度结束*/
	
	
	/*获取图片宽度高度到数组开始*/
	/**
	 * 返回一个数组， 第一个值是宽度，第二个值是高度
	 * @param is 文件输入流
	 * @return 数组，宽度和高度
	 * @throws IOException
	 */
	public int[] getWidthAndHeight(InputStream is) throws IOException{
		Image img=null;
		img=ImageIO.read(is);
		return new int[]{img.getWidth(null),img.getHeight(null)};
	}
	
	
	/**
	 * 返回一个数组， 第一个值是宽度，第二个值是高度
	 * @param is 文件路径
	 * @return 数组，宽度和高度
	 */
	public int[] getWidthAndHeight(String path){
		InputStream is=null;
		try
		{
			is=new FileInputStream(path);
			return getWidthAndHeight(is);
		}
		catch (FileNotFoundException e)
		{
			// XXX Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// XXX Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public int[] getWidthAndHeight(File file){
		InputStream is=null;
		try
		{
			is=new FileInputStream(file);
			return getWidthAndHeight(is);
		}
		catch (FileNotFoundException e)
		{
			// XXX Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// XXX Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/*获取图片宽度和高度结束*/
	
	public static void main(String[] args)
	{
		ImageUtil util=ImageUtil.getInstance();
		String name=util.generateSmallFileName("hell.txt");
		System.out.println(name);
		
	}
	
	
	
}
