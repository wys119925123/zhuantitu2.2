<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
session.invalidate();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<!-- Mimic Internet Explorer 7 -->
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7"/>
<title>权限不够--系统提示</title>
<style type="text/css">
*{padding:0;margin:0;font:12px/20px Arial,Sans-serif,"宋体";}
a{color:#006ad0;text-decoration:none;}
</style>
<script type="text/javascript">
	var t=-1;
	function testTime() {
		if(t<0) return;
		if(t == 0){
		    location.href = 'https://cas.ctbu.edu.cn/lyuapServer/login?service=http://gis.ctbu.edu.cn/zhuantitu2.2/login_index';
		}
		document.getElementById("view").innerHTML = "<b>"+t+"</b>";
		t--; 
	}
	function offTime() {
		setInterval("testTime()",1000);
		t=2;
	}
	
</script>
</head>

<body onload="offTime();">

<table width="100%" style="height: 100%;" cellpadding="0"
	cellspacing="0">
	<tr align="left" valign="middle">
		<td style="padding-top: 10px;">
			<center>
				<span style="color:red;font-weight:bold;font-size: 16px">没有系统权限，请联系管理员分配权限<br/></span>,3秒后将自动跳转单点登录
				<div id='view' style="color:red;"><b>3</b></div>
				<div id="j">
					<a href="javascript:void(0);" onclick="javascript:location.href='https://cas.ctbu.edu.cn/lyuapServer/login?service=http://gis.ctbu.edu.cn/zhuantitu2.2/login_index';return false;"><span>如果没有跳转，请点击此处</span></a>
				</div>
			</center>
		</td>
	</tr>
</table>

</body>
</html>

