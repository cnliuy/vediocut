<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>    
<!DOCTYPE html lang="utf-8">
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0" />
	<script src="<%=path %>/js/jquery/jquery-2.2.4.min.js"></script>
	<link rel="stylesheet" href="<%=path %>/css/weui.css"/>
    <link rel="stylesheet" href="<%=path %>/css/example.css"/>
</head>
<body>
 	<div class="container"> 	
 		<div class="hd"> 		
	 		<h4 class="page_title">${vediotitle}</h4><br>
		</div>
		
		<div align="center">
			<div class="bd">
				<!--${m3u8str}  -->
			 	<video id="player"  webkit-playsinline="" 
			 		type="m3u8" 
			 		controls="controls" height="80%" width="80%" 
			 		poster="${m3u8str}" 
			 		src="${m3u8str}">
			 		您的浏览器不支持vedio标签
			 	</video>
			 	<!-- autoplay=""  -->
		 	</div>	 
	 	</div>
	 	<br><br>
	 </div>
</body>

</html>