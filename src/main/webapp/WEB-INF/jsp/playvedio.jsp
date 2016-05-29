<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html lang="utf-8">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<script src="/js/jquery/jquery-2.2.4.min.js"></script>
	<link rel="stylesheet" href="/css/weui.css"/>
    <link rel="stylesheet" href="/css/example.css"/>
</head>
<body>
 	<div class="container"> 	
	 	<h4 class="page_title">${vediotitle}:</h4><br>
		
		<br>
		<!--${m3u8str}  --><br><br>
	 	<video id="player"  webkit-playsinline="" 
	 		autoplay="" type="m3u8" 
	 		poster="${m3u8str}" 
	 		src="${m3u8str}">
	 	</video>
	 	
	 </div>
</body>

</html>