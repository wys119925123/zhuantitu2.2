<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>${sysconfigMap['zhuantituTitle']}</title>
	<link rel="stylesheet" href="<%=path %>/zhuantitu/login/css/common.css">
	<link rel="stylesheet" href="<%=path %>/zhuantitu/login/css/xuanze.css">
	<script src="<%=path %>/zhuantitu/js/jquery.js"></script>
	<script type="text/javascript" src="<%=path%>/zhuantitu/layer/layer.js" ></script>
	<style>
	.logo-title{width: 535px;height: 105px; color:#fff;font-size: 36px;line-height: 105px;font-weight: 500;padding-left: 120px;}
	</style>
</head>
<body>
	<div class="box posit-RE">
	<div class="bg"><img src="<%=path %>/zhuantitu/login/images/biejing.png" alt=""></div>
		<div class="logo-title posit-AB" style="background: url('${sysconfigMap['zhuantituLogo']}')  no-repeat left; ">
		可视化应用统一登录平台
		</div>
			<s:if test="%{thematicMapMenus.size == 1}">
			<div class="option-box-out posit-AB" >
				<s:iterator value="thematicMapMenus" var="t">
					<s:if test="%{#t.menuid==100}">
					<div class="option-box posit-RE forward" data-href="videoMap?pid=${t.menuid }&zoneid=${zoneid}">
						<div class="option posit-AB" id="op1">
							<div class="img"><img src="${t.icon}" alt=""></div>
							<span>${t.name }</span>
						</div>
					</div>
					</s:if>
					<s:elseif test="%{#t.menuid==200}">
					<div class="option-box posit-RE forward" data-href="energyMap?pid=${t.menuid }&zoneid=${zoneid}&to3DZoneid=1009">
						<div class="option posit-AB" id="op1">
							<div class="img"><img src="${t.icon}" alt=""></div>
							<span>${t.name }</span>
						</div>
					</div>
					</s:elseif>
					<s:else>
					<div class="option-box posit-RE forward" data-href="staticMap?pid=${t.menuid }&zoneid=${zoneid}">
						<div class="option posit-AB" id="op1">
							<div class="img"><img src="${t.icon}" alt=""></div>
							<span>${t.name }</span>
						</div>
					</div>
					</s:else>
				</s:iterator>
			</div>			
			</s:if>
			<s:elseif test="%{thematicMapMenus.size == 2}">
			<div class="option-box-out posit-AB">
			<div class="option-box posit-RE">
				<s:iterator value="thematicMapMenus" var="t" status="s">
					<s:if test="%{#t.menuid==100}">
					<div class="option float-L forward" id="op2-<s:property value="#s.index+1"/>" 
						data-href="videoMap?pid=${t.menuid }&zoneid=${zoneid}">
						<div class="img"><img src="${t.icon}" alt=""></div>
						<span>${t.name }</span>
					</div>
					</s:if>
					<s:elseif test="%{#t.menuid==200}">
					<div class="option float-L forward" id="op2-<s:property value="#s.index+1"/>" 
						data-href="energyMap?pid=${t.menuid }&zoneid=${zoneid}&to3DZoneid=1009">
						<div class="img"><img src="${t.icon}" alt=""></div>
						<span>${t.name }</span>
					</div>
					</s:elseif>
					<s:else>
					<div class="option float-L forward" id="op2-<s:property value="#s.index+1"/>" 
						data-href="staticMap?pid=${t.menuid }&zoneid=${zoneid}">
						<div class="img"><img src="${t.icon}" alt=""></div>
						<span>${t.name }</span>
					</div>
					</s:else>
				</s:iterator>
			</div>
			</div>
			</s:elseif>
			<s:elseif test="%{thematicMapMenus.size >2}">
			<div class="option-box-out posit-AB">
			<div class="btn-box posit-RE">
				<div class="btn btn-L posit-AB"><img src="<%=path %>/zhuantitu/login/images/zb.png" alt=""></div>
				<div class="btn btn-R posit-AB"><img src="<%=path %>/zhuantitu/login/images/yb.png" alt=""></div>
				<div class="option-box posit-RE">
					<div class="option-out posit-RE">
						<s:iterator value="thematicMapMenus" var="t">
							<s:if test="%{#t.menuid==100}">
							<div class="option op1 posit-RE forward" data-href="videoMap?pid=${t.menuid }&zoneid=${zoneid}">
								<div class="img"><img src="${t.icon}" alt=""></div>
								<span >${t.name }</span>
							</div>
							</s:if>
							<s:elseif test="%{#t.menuid==200}">
							<div class="option op1 posit-RE forward" data-href="energyMap?pid=${t.menuid }&zoneid=${zoneid}&to3DZoneid=1009">
								<div class="img"><img src="${t.icon}" alt=""></div>
								<span >${t.name }</span>
							</div>
							</s:elseif>
							<s:else>
							<div class="option op1 posit-RE forward" data-href="staticMap?pid=${t.menuid }&zoneid=${zoneid}">
								<div class="img"><img src="${t.icon}" alt=""></div>
								<span >${t.name }</span>
							</div>
							</s:else>
							
						</s:iterator>
					</div>
				</div>
			</div>
				
			</div>
			</s:elseif>
			<s:else></s:else>
			
			<div class="user posit-AB">
				<div class="touxiang posit-RE"><div class="touxiang-img-box"><img width="70px" height="70px" src="<s:property value="#session.loginUser.avatar"/>" style="border-radius: 50%;"></div>
				<p class="posit-AB zhaungtai">已登录</p>
					<div class="touxiang-start posit-AB">
					<ul class="people-box">
							<li class="first-li">
								<div class="head-box">
									<div class="head-img-box"><img width="37px" height="37px" src="<s:property value="#session.loginUser.avatar"/>" style="border-radius: 50%;"></div>
									<p class="f14"><s:property value="#session.loginUser.userid"/></p>
									<p class="f14"><s:property value="#session.loginUser.name"/></p>
								</div>
							</li>
							<li class="f14 second-li"><img src="<%=path %>/zhuantitu/login/images/bm.png" alt=""><s:property value="#session.loginUser.deptname"/></li>
							<li class="f14 tirth-li logout" style="cursor: pointer;"><img src="<%=path %>/zhuantitu/login/images/tc.png" alt="">退出登录</li>
						</ul>
					</div>
				</div>
			</div>
		
	</div>
	<script src="<%=path %>/zhuantitu/login/js/xuanze.js"></script>
	<script>
	$(function(){
		$('.logout').click(function(){
			layer.confirm('确定要退出吗?', {icon: 3, title:'提示'}, function(index){
			 	location.href = "http://dm.haut.edu.cn/dmm_cas/logout?service=http://gis.haut.edu.cn/zhuantitu/login_index";
			});
		});
		$('.forward').click(function(){
			if($(this).data('href')){
				location.href= $(this).data('href');
			}
		});
	});
	</script>
</body>
</html>