<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>${thematicMapMenu.name}|${sysconfigMap['zhuantituTitle']}</title>
	<!--[if lt IE 9]>
		<script type="text/javascript" src="<%=path%>/zhuantitu/PIE/PIE.js"></script>
	<![endif]-->
	<link rel="stylesheet" href="<%=path%>/zhuantitu/css/common.css">
	<link rel="stylesheet" href="<%=path%>/zhuantitu/css/map.css">
	<link rel="stylesheet" href="<%=path%>/zhuantitu/css/index.css">
	<link rel="stylesheet" href="<%=path%>/zhuantitu/css/color.css">
	<link rel="stylesheet" href="<%=path%>/zhuantitu/css/scrollbar.css">
	<script type="text/javascript" src="<%=path%>/zhuantitu/js/jquery.js"></script>
	<script type="text/javascript" src="<%=path%>/zhuantitu/js/scrollbar.js"></script>
	<script type="text/javascript" src="<%=path%>/zhuantitu/layer/layer.js" ></script>
	<script type="text/javascript" src="${sysconfigMap['xygisUrl']}api?v=2.1" ></script>
	<script type="text/javascript">
		var mid = ${mid};
		var zoneid = ${zoneid};
		LMap.APIURL = "${sysconfigMap['xygisUrl']}";
		LMap.MAPSERVERURL = "${sysconfigMap['geoserverUrl']}";
	</script>
</head>
<body>
	<div class="box posit-RE">
		<div class="left shadow posit-AB">
			<div class="logo-box">
				<div class="logo" title="返回首页"><a href="<%=path%>/index?zoneid=${zoneid}"><img src="${sysconfigMap['zhuantituLogo']}" alt=""></a></div>
				<div class="title f18 text-CENT">${thematicMapMenu.name}</div>
			</div>
			<div class="scroll-box posit-RE ">
				<ul class="classify-box ">
					<li class="classify2 posit-RE">
						<p class="tulie posit-RE f18"><img src="<%=path%>/zhuantitu/images/tuli.png" alt="">图例</p>
							<ul class="tushi">
								<s:iterator value="thematicPointCategories" var="c">
									<li class="tuwen"><img src="${c.icon}" alt="">${c.name}</li>
								</s:iterator>
							</ul>
						<div class="triangle-right posit-AB"></div>
					</li>
				</ul>
			</div>
		</div>
		<div class="right posit-AB">
			<div class="map posit-AB" id="map">
				
			</div>
			<div class="zoom">
				<!--放大-->
				<span class="zoom-in"></span>
				<!--缩小-->
				<span class="zoom-out"></span>
			</div>
			
		</div>
	</div>
	<!-- 右上角功能 -->
	<div class="function posit-AB">
		
		<div class="search-box  float-L posit-RE">
			<form onsubmit="return searchForm();" autocomplete="off">
			<input type="text" id="keywords" class="search shadow" placeholder="输入...">
			<div class="input-clear posit-AB" title="清空"></div>
			<div class="start posit-AB search-btn"></div>
			</form>
			<ul class="List shadow none">
			</ul>
		</div>
		
		<%@include file="template/switch-zone.jsp" %>
		<div class="kuangxuan float-L f12 shadow box-search"><img src="<%=path%>/zhuantitu/images/kx.png" alt="">框选查看</div>
		<div class="touxiang float-L posit-RE"><img src="<s:property value="#session.loginUser.avatar"/>" alt="" width="70px" height="70px" >
			<div class="touxiang-start posit-AB">
			<div class="touxiang-start-box posit-RE">
				<div class="touxiang-sanjiao posit-AB"><img src="<%=path%>/zhuantitu/images/jiantou.png" alt=""></div>
				<ul class="people-box">
					<li class="first-li">
						<div class="head-box">
							<div class="head-img-box"><img src="<s:property value="#session.loginUser.avatar"/>" alt="" width="37px" height="37px"></div>
							<p class="f14"><s:property value="#session.loginUser.userid"/></p>
							<p class="f14"><s:property value="#session.loginUser.name"/></p>
						</div>
					</li>
					<li class="f14 second-li"><img src="<%=path%>/zhuantitu/images/baoanshi.png" alt=""><s:property value="#session.loginUser.rolename"/></li>
					<li class="f14 tirth-li logout" ><img src="<%=path%>/zhuantitu/images/eit.png" alt="">退出登录</li>
				</ul>
			</div>

			</div>
		</div>
		
	</div>
	<!--右边楼层选择浮动工具栏-->
	<div class="right-floor-box">
		<ul class="right-floor-ctns">
			<li class="ctns-tool">
				<i class="ctns-tool-pic"></i>
				<span>工具</span>
				<i class="floor-line"></i>
				<!--工具弹出层-->
				<ul class="tool-details bor-2">
					<li class="measureArea">
						<i class="details-pic1"></i>
						<span>测面</span>
					</li>
					<i class="details-line"></i>
					<li class="measureDistance">
						<i class="details-pic2"></i>
						<span>测距</span>
					</li>
				</ul>
			</li>
			
		</ul>
	</div>
	<div class="measureResult" style="position:absolute;bottom:5px;right:20px;font-size: 16px">
		
	</div>
	<script src="<%=path%>/zhuantitu/js/index.js"></script>
	<script type="text/javascript" src="<%=path%>/zhuantitu/js/videomap.js"></script>
	<script type="text/javascript">
	$(window).on("load",function(){
		$(".scroll-box").mCustomScrollbar({
			theme:"minimal-dark"
		});
	});
	</script>
	
</body>
</html>