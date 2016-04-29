package com.cc.tools;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

//Java最快Append二进制内容到文件的方法
public class Test1 {
	//值得注意的是：需要用二进制比较工具确认大家写的内容都是一样
	public static void main(String[] args) throws Exception {
		//没有缓冲的RandomAccessFile直接写, 因为耗时太长所以直接排除
		//test1("D:/test1.tmp",1000000);
		
		//用ByteBuffer做缓存的 RandomAccessFile写，在windows7 32bit环境测试，最快
		test2("D:/test2.tmp",1000000,1024*150);
		
		//这个和上面一样，用的test2，但缓存size变大，测试证明缓存在大到一定程度后就没有提速的效果
		test2("D:/test3.tmp",1000000,1024*1024);
		
		//用MappedByteBuffer写，测试证明没有test2速度快，可能原因是MappedByteBuffer是内部用的AllowDirect
		//很奇怪，和Thinking in Java 4th 所说不同，用AllowDirect反而更慢，这点可在test2注释行有说明
		//此处用了很大的100M缓存，是为了避免因写入内容超过mapsize, 而要 remap
		//而且MappedByteBuffer有个很重要的特性，只要map()文件就会先固定为和Map Size一样的大小，这不符合Append行为
		test3("D:/test4.tmp",1000000,1024*1024*100);
		
		//用带缓冲的一般流，实际证明这个比test2和test3还要慢		
		test4("D:/test5.tmp",1000000,1024*150);
	}

	public static void test4(String file,int loop,int mapsize) throws Exception {
		//先将上次文件删除
		new File(file).delete();
		DataOutputStream raf = new DataOutputStream(
				new BufferedOutputStream(
						new FileOutputStream(new File(file),true),mapsize));


		byte[] b1 = new byte[]{'a','b','c','d','e','f','g','h'};
		byte[] utfstr = "this is a test".getBytes("UTF-8");
		long s = System.nanoTime();
		for(int i=0;i<loop;i++){
			raf.write(b1);
			raf.writeInt(i);
			raf.writeInt(i+1);
			
			raf.write(utfstr);
			raf.write((byte)'\n');
		}
		//因为close方法可能将缓冲中最后剩余的flush到文件, 所以要纳入计时
		raf.close();

		long d = System.nanoTime() - s;
		System.out.println(file+" spent time="+(d/1000000)+"毫秒");
	}
	
	
	public static void test3(String file,int loop,int mapsize) throws Exception {
		//先将上次文件删除
		new File(file).delete();
		RandomAccessFile raf1 = new RandomAccessFile(file,"rw");

		FileChannel fc = raf1.getChannel();
		MappedByteBuffer raf = fc.map(MapMode.READ_WRITE, 0, mapsize);
		raf.clear();
		//在windows7 32bit 下, allocateDirect反而比allocate慢
		//ByteBuffer raf = ByteBuffer.allocateDirect(mapsize);
		
		byte[] b1 = new byte[]{'a','b','c','d','e','f','g','h'};
		byte[] utfstr = "this is a test".getBytes("UTF-8");
		long s = System.nanoTime();
		long count = 0;
		for(int i=0;i<loop;i++){
			if(raf.remaining()<140){
				System.out.println("remap");
				count += raf.position();  
		        raf = fc.map(MapMode.READ_WRITE, count, mapsize);
				//raf = fc.map(MapMode.READ_WRITE, raf.position(), raf.position()+mapsize); 
			}
			
			raf.put(b1);
			raf.putInt(i);
			raf.putInt(i+1);
			
			raf.put(utfstr);
			raf.put((byte)'\n');
		}
		//因为close方法可能将缓冲中最后剩余的flush到文件, 所以要纳入计时
		fc.close();
		raf1.close();

		long d = System.nanoTime() - s;
		System.out.println(file+" spent time="+(d/1000000)+"毫秒");
	}
	
	
	public static void test2(String file,int loop,int mapsize) throws Exception {
		//先将上次文件删除
		new File(file).delete();
		RandomAccessFile raf1 = new RandomAccessFile(file,"rw");

		FileChannel fc = raf1.getChannel();
		ByteBuffer raf = ByteBuffer.allocate(mapsize);
		raf.clear();
		//在windows7 32bit 下, allocateDirect反而比allocate慢
		//ByteBuffer raf = ByteBuffer.allocateDirect(mapsize);
		
		byte[] b1 = new byte[]{'a','b','c','d','e','f','g','h'};
		byte[] utfstr = "this is a test".getBytes("UTF-8");
		long s = System.nanoTime();
		for(int i=0;i<loop;i++){
			raf.put(b1);
			raf.putInt(i);
			raf.putInt(i+1);
			
			raf.put(utfstr);
			raf.put((byte)'\n');
			
			if(raf.remaining()<140){
				raf.flip();
				fc.write(raf);
				raf.compact();
			}
		}
		raf.flip();
		fc.write(raf);
		//因为close方法可能将缓冲中最后剩余的flush到文件, 所以要纳入计时
		fc.close();
		raf1.close();

		long d = System.nanoTime() - s;
		System.out.println(file+" spent time="+(d/1000000)+"毫秒");
	}
	
	public static void test1(String file, int loop) throws Exception {
		new File(file).delete();
		RandomAccessFile raf = new RandomAccessFile(file,"rw");
		
		
		byte[] b1 = new byte[]{'a','b','c','d','e','f','g','h'};
		byte[] utfstr = "this is a test".getBytes("UTF-8");
		long s = System.nanoTime();
		for(int i=0;i<loop;i++){
			raf.write(b1);
			raf.writeInt(i);
			raf.writeInt(i+1);
			raf.write(utfstr);
			raf.write('\n');
		}
		//因为close方法可能将缓冲中最后剩余的flush到文件, 所以要纳入计时
		raf.close();

		long d = System.nanoTime() - s;
		System.out.println(file+" spent time="+(d/1000000)+"毫秒");
	}

}