package com.cc.tool.uu;



/**
 * <b>function:</b> 下载测试
 * @author hoojo
 * @createDate 2011-9-23 下午05:49:46
 * @file TestDownloadMain.java
 * @package com.hoo.download
 * @project MultiThreadDownLoad
 * @blog http://blog.csdn.net/IBM_hoojo
 * @email hoojo_@126.com
 * @version 1.0
 */
public class TestDownloadMain {
 
    public static void main(String[] args) {
        /*DownloadInfo bean = new DownloadInfo("http://i7.meishichina.com/Health/UploadFiles/201109/2011092116224363.jpg");
        System.out.println(bean);
        BatchDownloadFile down = new BatchDownloadFile(bean);
        new Thread(down).start();*/
        
        //DownloadUtils.download("http://i7.meishichina.com/Health/UploadFiles/201109/2011092116224363.jpg");
        DownloadUtils.download("http://mp3.baidu.com/j?j=2&url=http%3A%2F%2Fzhangmenshiting2.baidu.com%2Fdata%2Fmusic%2F1669425%2F%25E9%2599%25B7%25E5%2585%25A5%25E7%2588%25B1%25E9%2587%258C%25E9%259D%25A2.mp3%3Fxcode%3D2ff36fb70737c816553396c56deab3f1", "aa.mp3", "c:/temp", 5);
    }
}