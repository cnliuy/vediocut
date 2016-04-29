package com.cc.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * 二进制模式合并文件文件
 * 相当于 windows 下的 COPY /B 命令
 * 
 * */
public class CopyBFiles {
	
	public static void main(String[] args) throws Exception {
		
		String in_file_Path= "F:"+File.separator+"Java2016"+File.separator+"src"+File.separator+"vediocut"+File.separator+
								"vediocut"+File.separator+"vedios";
		System.out.println(in_file_Path);
		List<String> in_filenames = new ArrayList<String>();
		in_filenames.add("0.TS");
		in_filenames.add("1.TS");
		in_filenames.add("2.TS");
		in_filenames.add("3.TS");
		in_filenames.add("4.TS");
		in_filenames.add("5.TS");//为空指针  测试用
		String output_file_Path = "F:"+File.separator+"Java2016"+File.separator+"src"+File.separator+"vediocut"
									+File.separator+"vediocut"+File.separator+"vedios"+File.separator+"all.TS";
		
		CopyBFiles.combineFile(in_filenames, in_file_Path, output_file_Path);
	}
	
	
		
	/**
	 * 传入 要合并文件的List
	 * 通过二进制合并文件 并保存到相应的输出目录中
	 * 
	 * @author ly
	 * 分割符号 
	 * 	File.separator
	 * 
	 * @param in_filenames 简单的文件名
	 * 		  in_file_Path 文件夹的路径
	 * 		  output_file_Path  输出文件夹路径
	 * */
	public static void combineFile( List <String> in_filenames ,String in_file_Path, String output_file_Path) throws Exception  {  
		OutputStream eachFileOutput = null;		
		eachFileOutput = new FileOutputStream(new File(output_file_Path));  
		Iterator <String>filenamei = in_filenames.iterator();
		while( filenamei.hasNext()){
			  InputStream eachFileInput = null;    
			  String fullpath =in_file_Path+File.separator+filenamei.next();
			  System.out.println(fullpath);			  
              try {
				eachFileInput = new FileInputStream(new File(fullpath));               
				  byte[] buffer = new byte[1024*1024*1];//缓冲区文件大小为1M  
				  int len = 0;  
				  while((len = eachFileInput.read(buffer,0,1024*1024*1)) != -1){  
				      eachFileOutput.write(buffer,0,len);  
				  }
				  eachFileInput.close();  
              } catch (Exception e) {
				e.printStackTrace();
              }           
		}
		eachFileOutput.close();
		System.out.println("输出文件： "+output_file_Path);
	}
	
	
	
	/**
	 * 传入 要合并文件的List
	 * 通过二进制合并文件 并保存到相应的输出目录中
	 * 
	 * @author ly
	 * 分割符号 
	 * 	File.separator
	 * 
	 *  @param in_filenames 全文件路径，包括文件名	 * 		 
	 * 		  output_file_Path  输出文件夹路径
	 * */
	public static void combineFile2( List <String> in_filenames , String output_file_Path) throws Exception  {  
		OutputStream eachFileOutput = null;		
		eachFileOutput = new FileOutputStream(new File(output_file_Path));  
		Iterator <String>filenamei = in_filenames.iterator();
		while( filenamei.hasNext()){
			  InputStream eachFileInput = null;    
			  String fullpath =filenamei.next();
			  System.out.println(fullpath);			  
              try {
				eachFileInput = new FileInputStream(new File(fullpath));               
				  byte[] buffer = new byte[1024*1024*1];//缓冲区文件大小为1M  
				  int len = 0;  
				  while((len = eachFileInput.read(buffer,0,1024*1024*1)) != -1){  
				      eachFileOutput.write(buffer,0,len);  
				  }
				  eachFileInput.close();  
              } catch (Exception e) {
				e.printStackTrace();
              }           
		}
		eachFileOutput.close();
		System.out.println("输出文件： "+output_file_Path);
	}

}
