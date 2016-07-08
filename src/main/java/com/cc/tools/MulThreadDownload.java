package com.cc.tools;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
 
/*
 * 多线程下载
 * 
 * no use  不在用  仅供测试
 */
public class MulThreadDownload {
 
    public static void main_mm(String[] args) throws Exception {
        String path = "http://pic.4j4j.cn/upload/pic/20130909/681ebf9d64.jpg";
        new MulThreadDownload().download(path,3);
    }
     
    public void download (String path,int threadsize) throws Exception{
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5000);
        conn.setRequestMethod("GET");
        if(conn.getResponseCode() == 200){
            //获取网络文件长度
            int length = conn.getContentLength();  
            //新建本地文件保存下载数据
            File file = new File(getFilename(path));
            //计算每条线程负责下载的数据量
            int block = length%threadsize==0 ? length/threadsize : length/threadsize+1;
            //开启指定数目的线程同时下载
            for(int threadid = 0; threadid < threadsize; threadid++){
                new DownloadThread(threadid,block,url,file).start();
            }
        }else{
            System.out.println("下载失败！");
        }
    }
     
    private class DownloadThread extends Thread{
        private int threadid;   //线程编号
        private int block;      //下载块大小
        private URL url;        //下载链接
        private File file;      //下载数据保存文件
        public DownloadThread(int threadid, int block, URL url, File file) {
            this.threadid = threadid;
            this.block = block;
            this.url = url;
            this.file = file;
        }
 
        public void run() {
            int start = threadid * block;       //本线程下载数据写入文件开始位置
            int end = (threadid+1) * block - 1; //本线程下载数据写入文件结束位置
            try {
                //创建一个随机访问文件流对象
                RandomAccessFile accessFile = new RandomAccessFile(file, "rwd");
                //文件指针偏移至正确写入位置
                accessFile.seek(start);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(5000);
                conn.setRequestMethod("GET");
                //设置请求数据的范围
                conn.setRequestProperty("Range", "bytes="+start+"-"+end);
                if(conn.getResponseCode() == 206){//状态码206:（部分内容） 服务器成功处理了部分 GET 请求
                    InputStream inStream = conn.getInputStream();
                    byte[] buffer = new byte[1024];
                    int len = 0;
                    while((len = inStream.read(buffer)) != -1){
                        accessFile.write(buffer, 0, len);
                    }
                    accessFile.close();
                    inStream.close();
                }
                System.out.println("第"+(threadid+1)+"部分下载完成");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
 
    private String getFilename(String path) {
        return path.substring(path.lastIndexOf("/")+1);
    }
}