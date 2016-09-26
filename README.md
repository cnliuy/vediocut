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
          在com.cc.VediocutApplicationde下 打开@EnableScheduling注释 开始ts片段的下载操作
        
          
          
###版本0.11：
----视频下载后存放位置 \vediocut\vediocut\src\main\resources\static\live\live2\TJ2\800\TJ2-800-node1_20160504102158_1460161734.ts<br>
----视频通过 com.cc.web.LiveController test1vs() 生成截取播放串。经vlc测试可以正常播放。<br>
----视频通过  http://127.0.0.1:8080/live/TJ2-800-vedioclip.m3u8?timelength=60&timestamp=1462331693523 播放 <br>
----src/test/java下的  com.cc.testTimestampe 类  根据时分秒 生成  timestampe

----创建war包 ，当项目中存在多个带 main() 方法的类，打包war时，会出现问题。<br>
----" Your existing archive will be enhanced by Spring Boot during the package phase. The main class that you want<br>
      to launch can either be specified using a configuration option, or by adding a Main-Class attribute to the <br>
      manifest in the usual way. If you don’t specify a main class the plugin will search for a class with a public<br>
      static void main(String[] args) method.<br><br>
	To build and run a project artifact, you can type the following:<br>
	$ mvn package<br>
	$ java -jar target/mymodule-0.0.1-SNAPSHOT.jar<br>

    " 
   
###版本0.12 
    以 Live2Controller liveclip() 异步下载处理  ，使用 <br>
    	http://127.0.0.1:8080/livex/liveclip?timelength=60&liveUrl=http%3A%2F%2F43.224.208.195%2Flive%2Fcoship%2CTWSX1422589417980523.m3u8%3Ffmt%3Dx264_0k_mpegts
    	<br>
    访问入口。
    
    注意系统时间的同步问题，时间必须统一，才能正确截取视频。
    

 <br>
   传到服务器需要调整的内容<br>
   1.服务器系统时间需要同步<br>
   2.tools下 M3u8Download GoGetFileSavePath() 文件的存放目录<br>
   3.application.properties 中数据库的端口和密码<br>
   4.入口   Live2Controller<br> 
   
 <br>  
 

###版本0.12.1
 	可以打成war包 部署。
 	注意以下： 
		<!--  部署war包时 需要这个  correct  千万不要注释掉-->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-tomcat</artifactId>
			<scope>provided</scope>
		</dependency>
		
--------------------------------
pom.xml不加上述内容，对应报错如下：<br>
2016-07-08 17:38:46.502 ERROR 102760 --- [nio-8080-exec-1] o.s.boot.context.web.ErrorPageFilter     : Forwarding to error page from request [/wel] due to exception [java.lang.LinkageError: loader constraint violation: when resolving method
"org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(Ljavax/servlet/ServletConfig;)Lorg/apache/tomcat/InstanceManager;" the class loader (instance of org/apache/jasper/servlet/JasperLoader) of the current class, org/apache/jsp/WEB_002dINF/jsp/welcome_jsp, and the class loader (instance of java/net/URLClassLoader) for the method's defining class, org/apache/jasper/runtime/InstanceManagerFactory, have different Class objects for the type org/apache/tomcat/InstanceManager used in the signature]

javax.servlet.ServletException: java.lang.LinkageError: loader constraint violation: when resolving method "org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(Ljavax/servlet/ServletConfig;)Lorg/apache/tomcat/InstanceManager;" the class loader (instance of org/apache/jasper/servlet/JasperLoader) of the
current class, org/apache/jsp/WEB_002dINF/jsp/welcome_jsp, and the class loader
(instance of java/net/URLClassLoader) for the method's defining class, org/apache/jasper/runtime/InstanceManagerFactory, have different Class objects for the type org/apache/tomcat/InstanceManager used in the signature
        at org.apache.jasper.servlet.JspServlet.service(JspServlet.java:349) ~[jasper.jar:8.0.14]
        at javax.servlet.http.HttpServlet.service(HttpServlet.java:725) ~[servlet-api.jar:na]
        at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:291) [catalina.jar:8.0.14]
        at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:206) [catalina.jar:8.0.14]
<br>  。。。。。<br>
        at java.lang.Thread.run(Thread.java:745) [na:1.8.0_25]
Caused by: java.lang.LinkageError: loader constraint violation: when resolving method "org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(Ljavax/servlet/ServletConfig;)Lorg/apache/tomcat/InstanceManager;" the class loader (instance of org/apache/jasper/servlet/JasperLoader) of the current class, org/apache/jsp/WEB_002dINF/jsp/welcome_jsp, and the class loader (instance of java/net/URLClassLoader) for the method's defining class, org/apache/jasper/runtime/InstanceManagerFactory, have different Class objects for the type org/apache/tomcat/InstanceManager used in the signature
        at org.apache.jsp.WEB_002dINF.jsp.welcome_jsp._jspInit(welcome_jsp.java:45) ~[na:na]
        at org.apache.jasper.runtime.HttpJspBase.init(HttpJspBase.java:49) ~[jasper.jar:8.0.14]
        at org.apache.jasper.servlet.JspServletWrapper.getServlet(JspServletWrapper.java:180) ~[jasper.jar:8.0.14]
        at org.apache.jasper.servlet.JspServletWrapper.service(JspServletWrapper.java:369) ~[jasper.jar:8.0.14]
        at org.apache.jasper.servlet.JspServlet.serviceJspFile(JspServlet.java:396) ~[jasper.jar:8.0.14]
        at org.apache.jasper.servlet.JspServlet.service(JspServlet.java:340) ~[jasper.jar:8.0.14]
        ... 82 common frames omitted

		
----------------------
	打成war，部署到新环境时 需要更改的内容 ，详细参见  com.cc.ChangeEnvReadMe 中描述
----------------------

---------------------------------
###版本0.12.2
 	socket2.html 前后端通信 
 	
###版本0.13
	在tvchannelpindaopojo 中增加对应关系 ，可以进行频道的配置。注: 该表不可以有重复项
	SQL 语句：
	---- INSERT INTO tvchannelpindaopojo(
            id, pindaostr, vediochannel)
         VALUES (1, 'TJ2-800-node1', 'live_live2_TJ2_800');

	
   
--EOF--