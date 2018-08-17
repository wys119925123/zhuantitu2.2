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
<html>
	<head>
		<meta charset="utf-8" />
		<title>${sysconfigMap['zhuantituTitle']}</title>
		<link rel="stylesheet" type="text/css" href="<%=path%>/zhuantitu/newlogin/css/common.css"/>
		<script type="text/javascript" src="<%=path%>/zhuantitu/js/jquery.js"></script>
	<script type="text/javascript" src="<%=path%>/zhuantitu/js/js.cookie.js"></script>
	<script type="text/javascript" src="<%=path%>/zhuantitu/layer/layer.js" ></script>
		<style type="text/css">
			html,body{height: 100%;}
			input[type=checkbox]{vertical-align: middle;margin-right: 5px;}
			@font-face{
			  font-family:'NeuesBauenDemo';
			  font-weight:normal;
			  font-style:normal;
			}
			.signIn-box{height:100%;background-color: #32C2FF;}
			.signIn-top,.signIn-bottom{width:100%;height: 110px;background: #fff;z-index: 10;line-height: 110px;}
			.signIn-top{top: 0;left: 0;font-size: 28px;color: #33C1FF;}
			.signIn-bottom{bottom: 0;left: 0;color: #5E5E5E;}
			.sign-img{width: 50%;height: 100%;background: url(<%=path%>/zhuantitu/newlogin/img/left-img.png) no-repeat 80% center;left: 0;top: 0;}
			.signIn-mesg-box{width: 50%;height: 100%;right: 0;top: 0;}
			.signIn-mesg{width: 372px;height: 358px;background-color: #FFF;border-radius: 5px;left: 50%;top: 50%;margin: -188px 0 0 -186px;padding: 20px 36px 45px;}
			.sign-tit{margin: 28px auto;}
			.signIn-mesg-list{border-radius: 5px;border: 1px solid #A0A0A0;overflow: hidden;margin: 40px 0 20px;}
			.signIn-mesg-list li{height: 48px;border-bottom: 1px solid #A0A0A0;}
			.signIn-mesg-list li input{width: 322px;height: 100%;line-height: 48px;}
			.signIn-mesg-list li:last-of-type{border-bottom: 0;}
			#sign{cursor: pointer;}
			.sign-btn{width: 100%;height: 48px;background: #32C1FF;color: #fff;border-radius: 5px;margin-top: 25px;cursor: pointer;}
			#accountNum{background: url(<%=path%>/zhuantitu/newlogin/img/account.png) no-repeat 15px center;padding-left: 40px;}
			#passtNum{background: url(<%=path%>/zhuantitu/newlogin/img/password.png) no-repeat 15px center;padding-left: 40px;}
			#special-logo{position: absolute;left: 70px;top: 15px;z-index: 999;height: 80px;}
			.versions{margin-right: 67px;}
		</style>
	</head>
	<body>
		<div class="box signIn-box posit-RE">
			<div class="signIn-top posit-AB"></div>
			<img src="<%=path%>/zhuantitu/newlogin/img/logo.png" id="special-logo" alt="" /> 
			<div class="sign-img posit-AB"></div>
			<div class="signIn-mesg-box float-R posit-RE">
				<form  method="post" onsubmit="return loginForm();" autocomplete="off">
				<div class="signIn-mesg posit-AB">
					<img src="<%=path%>/zhuantitu/newlogin/img/keshihua.png" alt="" class="sign-tit">
					<ul class="signIn-mesg-list">
						<li>
							<input type="hidden" name="zoneid" class="zoneid" value="${zoneid}">
							<input type="text" class="username" value="<%=username %>" id="accountNum"  placeholder="请输入账号" />
						</li>
						<li>
							<input type="password"  class="password" id="passtNum"  value="<%=password %>"  placeholder="请输入密码" />
						</li>
					</ul>
					<p><input type="checkbox" checked="checked" name="remember"id="sign" ><label>记住密码</label></p>
					<input class="sign-btn" type="button" value="登录" onclick="loginForm()">
				</div>
				</form>
			</div>
			<div class="signIn-bottom posit-AB text-CENT"><span class="versions">系统版本：<em>V2.2</em></span><span>技术支持：<em>成都市灵奇空间软件有限公司</em></span></div>
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
