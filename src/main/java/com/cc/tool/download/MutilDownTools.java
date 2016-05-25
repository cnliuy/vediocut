package com.cc.tool.download;

import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.URL;
import java.net.URLConnection;

public class MutilDownTools {
	/**
	 * 
	 * 单个文件的 多线程下载。  在用  --- using
	 * 
	 * srcurl  : http://43.224.208.195/live/live2/TJ2/800/TJ2-800-node1_20160427113618_1460041664.ts 
	 * 
	 * */
	
	public static String MutilDown(int down_thread_num,String out_file_name ,String srcurl) {
		//定义几个线程去下载
				int DOWN_THREAD_NUM = down_thread_num;
				String OUT_FILE_NAME = out_file_name ;
				InputStream[] isArr = new InputStream[DOWN_THREAD_NUM];
				RandomAccessFile[] outArr = new RandomAccessFile[DOWN_THREAD_NUM];
				try {
					// 创建一个URL对象
					URL url = new URL(srcurl);
					// 以此URL对象打开第一个输入流
					try {
						isArr[0] = url.openStream();
					} catch (Exception e) {
						OUT_FILE_NAME ="";
						e.printStackTrace();
					}
					long fileLen = getFileLength(url);
					System.out.println("网络资源的大小" + fileLen);
					//以输出文件名创建第一个RandomAccessFile输出流
					//创建从中读取和向其中写入（可选）的随机存取文件流，第一个参数：文件名，第二个参数是：参数指定用以打开文件的访问模式
					//"rw"可能是可读可写，
					outArr[0] = new RandomAccessFile(OUT_FILE_NAME, "rw");
					// 创建一个与下载资源相同大小的空文件
					for (int i = 0; i < fileLen; i++) {
						outArr[0].write(0);
					}
					// 每线程应该下载的字节数
					long numPerThred = fileLen / DOWN_THREAD_NUM;
					// 整个下载资源整除后剩下的余数取模
					long left = fileLen % DOWN_THREAD_NUM;
					for (int i = 0; i < DOWN_THREAD_NUM; i++) {
						// 为每个线程打开一个输入流、一个RandomAccessFile对象，
						// 让每个线程分别负责下载资源的不同部分。
						//isArr[0]和outArr[0]已经使用，从不为0开始
						if (i != 0) {
							// 以URL打开多个输入流
							isArr[i] = url.openStream();
							// 以指定输出文件创建多个RandomAccessFile对象
							outArr[i] = new RandomAccessFile(OUT_FILE_NAME, "rw");
						}
						// 分别启动多个线程来下载网络资源
						if (i == DOWN_THREAD_NUM - 1) {
							// 最后一个线程下载指定numPerThred+left个字节
							new DownThread(i * numPerThred, (i + 1) * numPerThred
									+ left, isArr[i], outArr[i]).start();
						} else {
							// 每个线程负责下载一定的numPerThred个字节
							new DownThread(i * numPerThred, (i + 1) * numPerThred,
									isArr[i], outArr[i]).start();
						}
					}
				} catch (Exception ex) {
					OUT_FILE_NAME ="";
					ex.printStackTrace();
					
				}
				
				return  OUT_FILE_NAME  ;
	}
	

	// 定义获取指定网络资源的长度的方法
	public static long getFileLength(URL url) throws Exception {
		long length = 0;
		// 打开该URL对应的URLConnection
		URLConnection con = url.openConnection();
		con.setConnectTimeout(7600);  
		con.setReadTimeout(7600);  
		// 获取连接URL资源的长度
		long size = con.getContentLength();
		length = size;		
		return length;
	}
}
