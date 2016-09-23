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
	
	<script src="<%=path %>/js/sockjs-0.3.4.js"></script>
    <script src="<%=path %>/js/stomp.js"></script>	
    <link rel="stylesheet" href="<%=path %>/css/example.css"/>
    
    <script type="text/javascript">
    var stompClient = null;
    var timestamp = Date.parse(new Date());
    
	callback = function(message) {    
   	   //alert("message---"+message);
   	   //alert("message.body---"+message.body);
       if (message.body == 1) {
			//alert("got message with body " + message.body);
			$("#p1").hide();
			$("#vediocutbutton").attr({"disabled":"disabled"});
			$("#vediocutbutton").removeClass("weui_btn weui_btn_plain_primary").addClass("weui_btn weui_btn_disabled weui_btn_default");
			//$("#vediocutbutton").attr({"class":"weui_btn weui_btn_disabled weui_btn_default"});
			$("#submitbutton").show();
			$("#p2").prepend("视频截取完成,您可以分享朋友圈了");
       } else {
			alert("参数错误 请刷新");
       }
	};

	function connect() {
		$("#p1").prepend("视频截取中。。。请稍候");
		
		var socket = new SockJS('/receivemsg');
		stompClient = Stomp.over(socket);              
		stompClient.connect('', '', function(frame) {  
		//stompClient.connect({}, function(frame) {
       		console.log('*****  Connected  *****');  
			console.log('Connected: ' + frame);
			
        	stompClient.subscribe("/topic/servicemsg"+timestamp+'${vediochannel}', callback);
        	//{ "firstName":"Bill" , "lastName":"Gates" } in M3u8tsdownloadController
        	stompClient.send("/app/tsdownloadstat2ws", {}, JSON.stringify({'vediochannel':'${vediochannel}','timestampws':timestamp,'timelength':'70', 'liveUrl':'http%3A%2F%2F43.224.208.195%2Flive%2Fcoship%2CTWSX1422589417980523.m3u8%3Ffmt%3Dx264_0k_mpegts'}));
   		});
		//alert(3);
		
		
	}   
		
    function disconnect() {
    	$("#submitbutton").hide();

        if (stompClient != null) {
            stompClient.disconnect();
        }
        console.log("Disconnected");       
        
    }
	</script>
	
	
	
	
</head>

<body onload="disconnect()">
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
					  	<input type="hidden" name="vediochannel"  value="${vediochannel}" />
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
			       			class="weui_btn weui_btn_primary"  onclick="connect();"/>	
    			     
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