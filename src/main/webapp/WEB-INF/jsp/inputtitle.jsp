<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html lang="utf-8">
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<script src="/js/jquery/jquery-2.2.4.min.js"></script>
	
	
	<script>
		var  downloadstat = 0;
		
		$(document).ready(
			function() {
			 $("#submitbutton").hide();
			 $("#vediocutbutton").click(
			
				 function(){	
						
					 	$("#p1").prepend("视频截取中。。。请稍候");
					 	var ref2 = "";
					 	
						function consoleInfo(){	
							//alert("。。。");	
							$.get("/rest/downloadbeok?vediotimestamp=${vediotimestamp}",function(data,status){
								if(data=="1"){
									//alert("视频截取成功");
									downloadstat =1 ;
								}
								if(data=="0"){
									//alert("视频截取中，请稍等。。。");
								}
							});
						}
					
						//开始和结束定时任务 
						var ref2 = setInterval(function(){
							
				           	if(downloadstat == 1){ 	
				           		$("#p1").hide();
				           		//$("#vediocutbutton").hide();
				           		$("#vediocutbutton").attr({"disabled":"disabled"});
				           		$("#submitbutton").show();
				        	    $("#p2").prepend("视频截取完成,您可以分享朋友圈了");
				        	    clearInterval(ref2); //结束定时任务  			        	    
			                    //alert("视频截取成功");
			                }else{
								consoleInfo();//开始定时任务
							}				
						},4000);
					
					//clearInterval(ref);
				}
			);
		});
	</script>
</head>

<body>
 
	 
	输入标题:<br>
	<form action="/livex/toplayvedio" method="post">
	  <input type="hidden" name="vediotimestamp" value="${vediotimestamp}" />
      <input name="title" type="text"   />    
      <input id="vediocutbutton" name="vediocutbutton"  type="button"  value="截取视频" />     
      <input id="submitbutton" name="submit"  type="submit"  value="生成分享页面分享" />  
	</form>   
	<br>
	<div  id="div123"> 
		<p id="p1"></p><p id="p2"></p>
	</div>
	<!--状态链接： <a href="${doloadstatus}" target="_blank">${doloadstatus}</a>-->
	
</body>	

</html>