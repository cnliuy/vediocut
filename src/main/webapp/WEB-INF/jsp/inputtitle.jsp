<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html lang="utf-8">

<body>
 
	 
	输入标题:<br>
	<form action="/livex/toplayvedio" method="post">
	  <input type="hidden" name="vediotimestamp" value="${vediotimestamp}" />
      <input name="title" type="text"   />       
      <input name="submit"  type="submit"  value="提交" />  
	</form>   
	<br>
 
</body>

</html>