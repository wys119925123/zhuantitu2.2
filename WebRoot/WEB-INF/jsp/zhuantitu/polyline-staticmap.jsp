<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
String path = request.getContextPath();
String domain = request.getRemoteAddr();
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
	<link rel="stylesheet" href="<%=path%>/zhuantitu/css/line.css">
	<link rel="stylesheet" href="<%=path%>/zhuantitu/css/scrollbar.css">
	<link rel="stylesheet" href="<%=path%>/zhuantitu/fancybox/source/jquery.fancybox.css?v=2.1.4" type="text/css" media="screen" />
	<script type="text/javascript" src="<%=path%>/zhuantitu/js/jquery.js"></script>
	<script type="text/javascript" src="<%=path%>/zhuantitu/js/js.cookie.js"></script>
	<script type="text/javascript" src="<%=path%>/zhuantitu/js/scrollbar.js"></script>
	<script type="text/javascript" src="<%=path%>/zhuantitu/js/upload.js"></script>
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
					<s:iterator value="childMenus" var="m">
						<s:if test="%{#m.menuid == mid}">
						<li class="classify posit-RE active" data-href="staticMap?pid=${pid}&mid=${m.menuid}&zoneid=${zoneid}">
							<p class="content posit-RE f18"><img class="sign zIndex pic" src="${m.selecticon}" alt=""><img src="${m.icon}" alt="">${m.name}</p>
							<div class="triangle-right posit-AB"></div>
						</li>
						</s:if>
						<s:else>
						<li class="classify posit-RE" data-href="staticMap?pid=${pid}&mid=${m.menuid}&zoneid=${zoneid}">
							<p class="content posit-RE f18"><img class="sign" src="${m.selecticon}" alt=""><img src="${m.icon}" alt="">${m.name}</p>
							<div class="triangle-right posit-AB"></div>
						</li>
						</s:else>
					</s:iterator>
					<li class="classify2 posit-RE">
						<p class="tulie posit-RE f18"><img src="<%=path%>/zhuantitu/images/tuli.png" alt="">图例</p>
							<ul class="tushi">
								<s:iterator value="thematicPolylineCategories" var="c">
									<li class="tuwen"><div class="line-box" style="background: #${c.strokecolor}"></div>${c.name}</li>
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
		<div class="left-line posit-AB">
			<div class="left-line-top">
				<span>属性信息</span><div class="close-line-btn float-R"></div>
			</div>
			<div class="line-mesg-box">
				<form method="post" id="save-polyline-form">
				<ul class="line-mesg-ul">
					<div class="line-mesg-ul-top">
						<span class="lint-title"></span>
						<div class="delet-line float-R"></div>
					</div>
					<div class="save-line-box">
						<div class="save-line-btn float-R" onclick="savePolyline()">保存</div>
					</div>
					<ul class="line-form-content">
					</ul>
				</ul>
				</form>
			</div>
		</div>
		<div class="guide-line-use" style="display: none;">
			<div class="guide-line-use-shadow"></div>
			<div class="guide-line-use-bg"></div>
			<div class="know-btn">知道了，开始画线</div>
		</div>
		<script>
			var mid = ${mid};
			$(".know-btn").click(function(event){
				$(".guide-line-use").fadeOut(300);
				event.stopPropagation();
				Cookies.set('tips-know-' +mid, "true",{ expires: 365 ,domain: '<%=domain%>'});
		  	})
			if(!Cookies.get('tips-know-' + mid)){
				$(".guide-line-use").show();
			}
		</script>
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
		
		<s:if test="%{#session.userMenuPermissionMap[mid].addcodePermission == 1}">
			<div class="add float-L shadow f12 posit-RE add-new-polyline"><img src="<%=path%>/zhuantitu/images/add-line.png" alt="">新增线
				<div class="add-over posit-AB"></div>
				<div class="add-start posit-AB">
					<div class="add-start-box posit-RE">
						<div class="add-sanjiao posit-AB"></div>
						<img class="add-close posit-AB" src="<%=path%>/zhuantitu/images/close22.png" alt="">
						<img src="<%=path%>/zhuantitu/images/mouse.png" alt="">
						<p style="margin:10px auto;color:#fff;">可以新增一条线哦~</p>
					</div>
				</div>
			</div>
			<script>
				var mid = ${mid};
				$(".add-close").click(function(event){
					$(this).parents(".add-start").fadeOut("fast");
					$(".add-over").fadeOut("fast");
					event.stopPropagation();
					Cookies.set('tips-' +mid, "true",{ expires: 365 ,domain: '<%=domain%>'});
			  	})
				if(!Cookies.get('tips-' + mid)){
					$(".add-start").show();
					$(".add-over").show();
				}
			</script>
		</s:if>
		<%@include file="template/switch-zone.jsp" %>
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
	<script type="text/javascript" src="<%=path%>/zhuantitu/js/polyline.staticmap.js"></script>
	<script type="text/javascript" src="<%=path%>/zhuantitu/fancybox/lib/jquery.mousewheel-3.0.6.pack.js"></script>
	<script type="text/javascript" src="<%=path%>/zhuantitu/fancybox/source/jquery.fancybox.js?v=2.1.4"></script>
	<script type="text/javascript">
	$(".scroll-box").height($(window).height()-190);
	$(window).on("load",function(){
		$(".scroll-box").mCustomScrollbar({
			theme:"minimal-dark"
		});
	});
	
	
	</script>
	
</body>
</html>