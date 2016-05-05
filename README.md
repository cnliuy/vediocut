#vediocut
##基于 Spring Boot 构建

spring boot 快速构建代码网站 http://start.spring.io/ （spring boot 代码生成 ，附上所需构建的内容）。
<br>
<br>


###版本0.1：

----自动开启定时任务，每隔20s，ScheduledTasks类 下载指定m3u8文件，和其中包含的视频文件，<br>
          并将对应数据存入数据库。已经下载的数据，会先检索数据库，不做重复下载工<br>
          作。<br>   
          随后实现，自动生成m3u8的工作<br>
          
###版本0.11：
----视频下载后存放位置 \vediocut\vediocut\src\main\resources\static\live\live2\TJ2\800\TJ2-800-node1_20160504102158_1460161734.ts<br>
----视频通过 com.cc.web.LiveController test1vs() 生成截取播放串。经vlc测试可以正常播放。<br>

----创建war包 ，当项目中存在多个带 main() 方法的类，打包war时，会出现问题。<br>
----" Your existing archive will be enhanced by Spring Boot during the package phase. The main class that you want<br>
      to launch can either be specified using a configuration option, or by adding a Main-Class attribute to the <br>
      manifest in the usual way. If you don’t specify a main class the plugin will search for a class with a public<br>
      static void main(String[] args) method.<br><br>
	To build and run a project artifact, you can type the following:<br>
	$ mvn package<br>
	$ java -jar target/mymodule-0.0.1-SNAPSHOT.jar<br>

