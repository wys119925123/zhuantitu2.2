<%@ page language="java" import="java.util.*,com.system.utils.DESHelper" pageEncoding="UTF-8"%>
<%@page import="com.alibaba.fastjson.JSONObject"%>
<%@page import="com.alibaba.fastjson.JSON"%>
<%
String path = request.getContextPath();
Cookie[] cookies = request.getCookies();

String rememberUserInfo = null,username="",password="";
if(cookies != null){
	for(Cookie cookie : cookies){
		if(cookie.getName().equals("zhuantiturememberUserInfo")){
			try{
				String value = DESHelper.decryptDES(cookie.getValue(),"lqkjhhxy");
				JSONObject json = JSON.parseObject(value);
				username = json.getString("username");
				password = json.getString("password");
			}catch(Exception e){
				
			}
		}
	}
}

%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>${sysconfigMap['zhuantituTitle']}</title>
	<link rel="stylesheet" href="<%=path%>/zhuantitu/login/css/common.css">
	<link rel="stylesheet" href="<%=path%>/zhuantitu/login/css/login.css">
	<script type="text/javascript" src="<%=path%>/zhuantitu/js/jquery.js"></script>
	<script type="text/javascript" src="<%=path%>/zhuantitu/js/js.cookie.js"></script>
	<script type="text/javascript" src="<%=path%>/zhuantitu/layer/layer.js" ></script>
</head>
<body>
	<img class="bg posit-AB" src="<%=path%>/zhuantitu/login/images/bottom.png" alt="">
	<div class="title-box text-CENT posit-AB">
	<div class="title"><img src="${sysconfigMap['zhuantituLogo']}" alt=""><span>可视化应用登录</span></div>
	</div>
	
	<div class="Box posit-AB">
		<form  method="post" onsubmit="return loginForm();" autocomplete="off">
			<div class="content">
				<input type="hidden" name="zoneid" class="zoneid" value="${zoneid}">
				<li class="mesg posit-RE"><div class="box posit-AB"><div class="box2"></div></div><input class="username" type="text" value="<%=username %>" placeholder="用户名"></li>
				<li class="mesg posit-RE posit-AB"><div class="box posit-AB"><div class="box3"></div></div><input class="password" type="password" value="<%=password %>" placeholder="密码"></li>
				<li class="mesg f14"><input type="checkbox" checked="checked" name="remember" >记住密码</li>
				<li class="mesg"><input type="button" value="登录" onclick="loginForm()"></li>
			</div>
		</form>
	</div>
	<script type="text/javascript">
			
		var loadding = null;
		function showLoadding(){
			loadding = layer.load(1);
		}
		function closeLoadding(){
			layer.close(loadding);
		}
		function loginForm(){
			var username = $('.username').val();
			var password = $('.password').val();
			var zoneid = $('.zoneid').val();
			var remember = $("input[type='checkbox']").is(':checked');
			if(username == ''){
				layer.msg("请输入用户名", function(){});
				return false;
			}
			if(password == ''){
				layer.msg("请输入密码", function(){});
				return false;
			}
			showLoadding();
			try{
				$.ajax({
		            type: "POST",
		            url:'login',
		            data:{"username":username,"password":password,"remember":remember},
		            async: true,
		            error: function(request) {
		            	closeLoadding();
						layer.msg("登录失败，系统错误", function(){});
		            },
		            success: function(data) {
		            	closeLoadding();
		            	if(data.status){
							location.href = "index?zoneid=" + zoneid;
		            	}else{
							layer.msg(data.errorContent, function(){});
						}
		            	
		            }
		        });
			}catch(e){
				closeLoadding();
			}
		}
		$(document).keyup(function(event){  
		    if(event.keyCode ==13){  
		      loginForm();
		    }  
		});
		
	</script>
</body>
</html>