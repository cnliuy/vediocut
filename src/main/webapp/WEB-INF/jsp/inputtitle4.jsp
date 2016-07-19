<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html lang="utf-8">
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
	<meta http-equiv="Content-Type" content="charset=UTF-8">
	<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0" />
	<link rel="stylesheet" href="<%=path %>/css/weui.css"/>
	<link rel="stylesheet" href="<%=path %>/css/jquery-weui.min.css">
	<script src="<%=path %>/js/jquery/jquery-2.2.4.min.js"></script>
	<script src="<%=path %>/js/jquery/jquery-weui.min.js"></script>
    <link rel="stylesheet" href="<%=path %>/css/example.css"/>
    
	
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
							$.get("<%=path %>/rest/tsdownloadstat?timelength=70&liveUrl=http%3A%2F%2F43.224.208.195%2Flive%2Fcoship%2CTWSX1422589417980523.m3u8%3Ffmt%3Dx264_0k_mpegts",function(data,status){
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
				           		$("#vediocutbutton").removeClass("weui_btn weui_btn_plain_primary").addClass("weui_btn weui_btn_disabled weui_btn_default");
				           		//$("#vediocutbutton").attr({"class":"weui_btn weui_btn_disabled weui_btn_default"});
				           		$("#submitbutton").show();
				        	    $("#p2").prepend("视频截取完成,您可以分享朋友圈了");
				        	    clearInterval(ref2); //结束定时任务  			        	    
			                    //alert("视频截取成功");
			                }else{
								consoleInfo();//开始定时任务
							}				
						},2500);
					
					//clearInterval(ref);
				}
			);
		});
	</script>
	
	
</head>

<body>
 	<div class="container"> 
 	<div class="hd">		
	 	<br><br>
		 <h4 class="page_title" align="center">视频截取</h4><br>
	</div>
	
	<div class="bd">
		
		<div class="weui_cells ">
				<form action="<%=path %>/livex/toplayvedio" method="post">
					  <div class="weui_cell">	
					  	<div class="weui_cell_hd">		  
					  	<input type="hidden" name="vediotimestamp"  value="${vediotimestamp}" />
					  	
					  	</div>					  	
					  </div>
					  <div class="weui_cell">	
					  		<div class="weui_cell_hd">
					  			<label class="weui_label">视频标题</label>
					  		</div>
					 
					  		<div class="weui_cell_bd">               
			                	<input type="text"   name="title"  class="weui_input" placeholder="输入视频的标题" />  
			            	</div>
				      </div>
		</div>
	     
	    
	    
	    
		<div class="weui_btn_area">     	
		      <input id="vediocutbutton" name="vediocutbutton"  type="button"  value="截取视频"
			       			class="weui_btn weui_btn_primary"/>	     
		      <input id="submitbutton" name="submit"  type="submit"  
			      			value="分享视频到朋友圈" class="weui_btn weui_btn_primary"/> 
		 </div> 
				</form>
			
	</div>	<!-- 结束bd -->
	
	<div  id="div123" >   
					<p id="p1" class="weui_media_desc" class="page_desc"></p>
					<p id="p2" class="weui_media_desc" class="page_desc"></p>
	</div>
	
			<!--状态链接： <a href="${doloadstatus}" target="_blank">${doloadstatus}</a>-->
		
		
		
	</div><!-- 结束container -->
	

</body>	

</html>