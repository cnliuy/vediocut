<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html lang="utf-8">
<title>视频播放</title>
<body>
	播放视频<br>
	<vedio
		src = "http://127.0.0.1:8080/live/TJ2-800-node1.m3u8"
		height= "300"
		width= "400"
	></vedio>
</body>

</html>