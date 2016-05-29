<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html lang="utf-8">
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<script src="/js/jquery/jquery-2.2.4.min.js"></script>
	<link rel="stylesheet" href="/css/weui.css"/>
    <link rel="stylesheet" href="/css/example.css"/>
	
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
				           		$("#vediocutbutton").removeClass("weui_btn weui_btn_plain_primary").addClass("weui_btn weui_btn_disabled weui_btn_default");
				           		//$("#vediocutbutton").attr({"class":"weui_btn weui_btn_disabled weui_btn_default"});
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
 	<div class="container"> 		
	 
		<h1 class="page_title">视频截取</h1><br>

		<form action="/livex/toplayvedio" method="post">
		  
		  
		  <input type="hidden" name="vediotimestamp" class="weui_input" value="${vediotimestamp}" />
		  
		  <div class="weui_cell">	
		  	<div class="weui_cell_hd"><label class="weui_label">视频标题:</label></div>
		  	<div class="weui_cell_bd weui_cell_primary">               
                <input type="text"   name="title"  class="weui_input" placeholder="在此输入视频的标题" />  
            </div>
	      </div>
	      	<br>
	      	<div class="button_sp_area">	      	
		      <input id="vediocutbutton" name="vediocutbutton"  type="button"  value="截取视频" class="weui_btn weui_btn_plain_primary"/>	     
		      <input id="submitbutton" name="submit"  type="submit"  value="生成分享页面分享" class="weui_btn weui_btn_plain_primary"/> 
		    </div> 
		</form>   
		<br>
		<div  id="div123">   
			<p id="p1" class="weui_media_desc"></p><p id="p2" class="weui_media_desc"></p>
		</div>
		<!--状态链接： <a href="${doloadstatus}" target="_blank">${doloadstatus}</a>-->
	</div>
	
</body>	

</html>