<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>可视化能耗|${sysconfigMap['zhuantituTitle']}</title>
	<!--[if lt IE 9]>
		<script type="text/javascript" src="<%=path%>/zhuantitu/PIE/PIE.js"></script>
	<![endif]-->
	<link rel="stylesheet" href="<%=path%>/zhuantitu/css/common.css">
	<link rel="stylesheet" href="<%=path%>/zhuantitu/css/map.css">
	<link rel="stylesheet" href="<%=path%>/zhuantitu/css/index.css">
	<link rel="stylesheet" href="<%=path%>/zhuantitu/css/page.css">
	<link rel="stylesheet" href="<%=path%>/zhuantitu/css/color.css">
	<link rel="stylesheet" href="<%=path%>/zhuantitu/css/scrollbar.css">
	<script type="text/javascript" src="<%=path%>/zhuantitu/js/jquery.js"></script>
	<script type="text/javascript" src="<%=path%>/zhuantitu/js/scrollbar.js"></script>
	<script type="text/javascript" src="<%=path%>/zhuantitu/layer/layer.js" ></script>
	<script type="text/javascript" src="<%=path%>/zhuantitu/laydate/laydate.js" ></script>
	<script type="text/javascript" src="${sysconfigMap['xygisUrl']}api?v=2.1" ></script>
	<script type="text/javascript">
		LMap.APIURL = "${sysconfigMap['xygisUrl']}";
		LMap.MAP3DTITLETYPE = "jpg";	
		var zoneid = "${to3DZoneid}";
		var floorInfo = [{"floorid":"100010","name":"1F","isDefault":"1"},{"floorid":"100011","name":"2F","isDefault":"0"},{"floorid":"100012","name":"3F","isDefault":"0"},{"floorid":"100013","name":"4F","isDefault":"0"},{"floorid":"100014","name":"5F","isDefault":"0"},{"floorid":"100015","name":"6F","isDefault":"0"},{"floorid":"100016","name":"7F","isDefault":"0"},{"floorid":"100017","name":"8F","isDefault":"0"},{"floorid":"100018","name":"9F","isDefault":"0"}];
	</script>
</head>
<body>
	<div class="box posit-RE">
		<div class="left shadow posit-AB">
			<div class="logo-box">
				<div class="logo" title="返回首页"><a href="<%=path%>/index?zoneid=${zoneid}"><img src="${sysconfigMap['zhuantituLogo']}" alt=""></a></div>
				<div class="title f18 text-CENT">可视化能耗</div>
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
			<div class="map-cut-tool">
				<a class="cut-tool-2d" href="<%=path%>/energyMap_to2D?pid=${pid}&zoneid=${zoneid}&to3DZoneid=${to3DZoneid}">
					<span class="tool-font">微地图</span>
				</a>
				<a class="cut-tool-3d active" href="<%=path%>/energyMap?pid=${pid}&zoneid=${zoneid}&to3DZoneid=${to3DZoneid}">
					<span class="tool-font active">三维</span>
				</a>
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
	
	<div class="flow flow-zongji posit-AB close-flow">
		<div class="flow-top text-CENT">
			<span class="flow1-title">能耗总量统计</span>
			<div class="flow-close float-R" onclick="closeZongji(this)"><img src="<%=path%>/zhuantitu/images/gb.png" alt=""></div>
		</div>
		<div class="flow-content">
			<p class="flow1-address-box"><span class="float-L"><img src="<%=path%>/zhuantitu/images/yuan1.png" alt=""></span><span class="flow1-address"></span></p>
			<div class="time-select1 f16 posit-RE">
				开始时间：<input type="text" id="startDate" class="txtBeginDate c9 f14 search-time laydate-icon" />
				结束时间：<input type="text" id="finishDate" class="txtEndDate c9 f14 search-time laydate-icon" />
			</div>
			<div class="xuan-box">
			</div>
			<div class="btn-box">
				<div class="btn dian-btn1 btn-active">用电查询</div>
				<div class="btn shui-btn1">用水查询</div>
				<div class="btn reset-btn1">重选</div>
				<span style="color:#FA8686;font-size:12px;line-height: 38px;">*选择楼层查看所选楼层能耗总量</span>
			</div>
			<hr style="height:1px;background:#DCDCDC;margin-top:16px;margin-bottom:18px;">
			<table class="flow1-table">
			</table>
		</div>
		<div class="pagin">
	    	<ul class="paginList">
	        </ul>
	    </div>
	</div>
	<div class="flow-mesg posit-AB close-flow">
		<div class="flow-mesg-top text-CENT">
			<span class="flow1-title">能耗总量统计</span>
			<div class="flow-close float-R" onclick="closeMesg(this)"><img src="<%=path%>/zhuantitu/images/gb.png" alt=""></div>
		</div>
		<div class="flow-mesg-content">
			<p class="flow-mesg-address-box"><span class="float-L"><img src="<%=path%>/zhuantitu/images/yuan1.png" alt=""></span><span class="flow-mesg-address"></span></p>
			<ul class="mesg-list">
			</ul>
		</div>
	</div>
	
	<div class="overShaw posit-AB"></div>
	<script src="<%=path%>/zhuantitu/js/index.js"></script>
	<script type="text/javascript" src="<%=path%>/zhuantitu/js/energymap.js"></script>
	<script>
	$(function () {
       	laydate.skin('molv');
       	var start = {
		  elem: '#startDate',
		  event: 'click',
		  format: 'YYYY-MM-DD',
		  istime: false,
		  istoday: true,
		  max: laydate.now(),
		  choose: function(datas){
		     end.min = datas; 
		     end.start = datas;
		  }
		};
		var end = {
		  elem: '#finishDate',
		  event: 'click',
		  format: 'YYYY-MM-DD',
		  istime: false,
		  istoday: true,
		  max: laydate.now(),
		  choose: function(datas){
		    start.max = datas; 
		  }
		};
		laydate(start);
		laydate(end);
		       
		
    });
	function check_selet(aa){
		$(aa).toggleClass("check-selet");
	}
	var flog=true;
	function all_check_selet(bb){
		$(bb).toggleClass("check-selet");
		if (flog) {
			$(".checkbox").addClass("check-selet");
			flog=!flog;
		}else{
			$(".checkbox").removeClass("check-selet");
			flog=!flog;
		}
		
	}
   function closeMesg(cc){
	   $(cc).parents(".close-flow").hide();
   }
   function closeZongji(cc){
	   $(cc).parents(".close-flow").hide();
	   $(".overShaw").hide();
   }
</script>

</body>
</html>