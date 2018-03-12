
var map = new LMap.Map3D("map", zoneid);

var loadding = null;
function showLoadding(){
	loadding = layer.load(1, {
	  shade: [0.1,'#fff']
	});
}
function closeLoadding(){
	layer.close(loadding);
}
function outError(data){
	if(data.errorCode == 404){
		layer.msg(data.errorContent, {icon: 5}, function(){
			location.href = 'login_logout';
		});
	}else{
		layer.msg(data.errorContent, {icon: 5});
	}
}
var floorMap = new HashMap();
$.each(floorInfo, function(i, v){
		floorMap.put(v.floorid,v.name);
});
var fomartPolygon = function(geometry){
	var newStr = geometry.replace('[[','((').replace(']]','))').replace(/\],\[/g,'-').replace(/\[/g,'').replace(/\,/g,' ').replace(/\-/g,',');
	return "POLYGON" + newStr;
}
var selectControl;
var transparentVector = new LMap.Layer.Vector('大楼轮廓',{
		styleMap:new LMap.StyleMap({
			"default":new LMap.Style({
				cursor:'pointer',
				title:'${tooltip}',
				strokeWidth:0,
				fillOpacity:0
			}),
			"select":new LMap.Style({
			}),
		    "temporary":new LMap.Style({
		        fillColor: "#ffffff",
		        fillOpacity: 0.4, 
		        strokeColor: "#fac807",
		        strokeWidth: 1
		    })
		})
	});
LqUtil.get(LMap.APIURL + "map3D_getTransparentPolygons",true,{"zoneid":zoneid},function(d){
	for(var i = 0; i < d.geometry.length; i++){
		var transparentFeaure = new LMap.Feature.Vector(
			LMap.Geometry.fromWKT(fomartPolygon(d.geometry[i].coordinates)),
			{
				tooltip:d.geometry[i].name,
				gid:d.geometry[i].polygonid,
				coordinate:d.geometry[i].coordinate
			}
		);
		transparentVector.addFeatures(transparentFeaure);
	}
	map.addLayer(transparentVector);
	var hoverControl = new LMap.Control.SelectFeature([transparentVector],{
        hover:true,
        highlightOnly:true,
        renderIntent:"temporary"
    });
    map.addControl(hoverControl);
    hoverControl.activate();


    selectControl = new LMap.Control.SelectFeature([transparentVector],{clickout:true});
    map.addControl(selectControl);
    selectControl.activate();
//	var controls = map.getControlsByClass("SelectFeatureControl");
//	for(var n = 0; n< controls.length; n++){
//		controls[n].setLayer([transparentVector]);
//	}
	transparentVector.events.on({
		featureselected:transparentVectorSelect,
		featureunselected:transparentVectorUnselect
	});

});
showBaseMarker();
function transparentVectorSelect(e){
	hightlightFeature = e.feature;
	LqUtil.post('energyMap_bulidTotalStatistics',true,{"buildid":hightlightFeature.attributes.gid},function(d){
		$('.scroll-box').remove();
		var html = [];
		if(d.status){
			html.push("<div class=\'scroll-box form-content posit-AB\' style=\'width: 215px;\'>");
			html.push("<div class=\'address text-CENT f20\'>" + hightlightFeature.attributes.tooltip + "</div>");
			html.push("<ul class=\'count-List text-CENT\'>");
			html.push("<li>");
			html.push("<p class=\'title\'><img class=\'float-L\' src=\'zhuantitu/images/yuanDa.png\' alt=\'\'>今日统计</p>");
			html.push("<ul class=\'count-content\'>");
			html.push("<li>用电<span class=\'dian\'>" + d.today[0].electricValue + "</span>" + d.electricUnit + "</li>");
			html.push("<li>用水<span class=\'shui\'>" + d.today[0].waterValue + "</span>" + d.waterUnit + "</li>");
			html.push("</ul>");
			html.push("</li>");
			html.push("<li>");
			html.push("<p class=\'title\'><img class=\'float-L\' src=\'zhuantitu/images/yuanDa.png\' alt=\'\'>昨日统计</p>");
			html.push("<ul class=\'count-content \' >");
			html.push("<li>用电<span class=\'dian\'>" + d.yesterday[0].electricValue + "</span>" + d.electricUnit + "</li>");
			html.push("<li>用水<span class=\'shui\'>" + d.yesterday[0].waterValue + "</span>" + d.waterUnit + "</li>");
			html.push("</ul>");
			html.push("</li>");
			html.push("<li>");
			html.push("<p class=\'title\'><img class=\'float-L\' src=\'zhuantitu/images/yuanDa.png\' alt=\'\'>本月统计</p>");
			html.push("<ul class=\'count-content \'>");
			html.push("<li>用电<span class=\'dian\'>" + d.month[0].electricValue + "</span>" + d.electricUnit + "</li>");
			html.push("<li>用水<span class=\'shui\'>" + d.month[0].waterValue + "</span>" + d.waterUnit + "</li>");
			html.push("</ul>");
			html.push("</li>");
			html.push("<li>");
			html.push("<p class=\'title\'><img class=\'float-L\' src=\'zhuantitu/images/yuanDa.png\' alt=\'\'>本年统计</p>");
			html.push("<ul class=\'count-content\'>");
			html.push("<li>用电<span class=\'dian\'>" + d.year[0].electricValue + "</span>" + d.electricUnit + "</li>");
			html.push("<li>用水<span class=\'shui\'>" + d.year[0].waterValue + "</span>" + d.waterUnit + "</li>");
			html.push("</ul>");
			html.push("</li>");
			html.push("</ul>");
			html.push("<div class=\'btn-count zongliang text-CENT\'>能耗总量统计</div>");
			html.push("</div>")
			$('.left').append(html.join(''));
			$(".form-content").height($(window).height()-190);
			$(".scroll-box").mCustomScrollbar({
				theme:"minimal-dark"
			});
			$('.zongliang').unbind("click");
			$(".zongliang").click(function(){
				initFloor();
				$(".checkbox").removeClass("check-selet");
				$(".all-checkbox").removeClass("check-selet");
				$(".time-select1 input").val("");
				flog = true;
				$('.flow1-table').empty();
				$('.paginList').empty();
				$(".flow-zongji .flow1-address").text(hightlightFeature.attributes.tooltip);
				$(".flow-mesg-address").text(hightlightFeature.attributes.tooltip);
				$(".flow-zongji").show();
				$(".overShaw").show();
			});
			$('.dian-btn1').unbind("click");
			$(".dian-btn1").click(function(){
				searchEnergy('50107',hightlightFeature.attributes.gid);
			});
			$('.shui-btn1').unbind("click");
			$(".shui-btn1").click(function(){
				searchEnergy('50205',hightlightFeature.attributes.gid);
			});
			
		}else{
			if(d.errorCode == 404){
				layer.msg(d.errorContent, {icon: 5}, function(){
					location.href = 'login_logout';
				});
			}else{
				html.push("<div class=\'scroll-box form-content posit-AB\' style=\'width: 215px;\'>");
				html.push("<div class=\'address text-CENT f20\'>" + hightlightFeature.attributes.tooltip + "</div>");
				html.push("<div class=\'wunenghao\'></div>");
				html.push("<div class=\'text-CENT\' style=\'color: #d4d4d4;\'>当前建筑暂无能耗设备</div>");
				html.push("</div>")
				$('.left').append(html.join(''));
				$(".form-content").height($(window).height()-190);
			}
		}
	});
	onPopupClose();
}
function searchEnergy(type,buildid){
	var floorids = [];
	$('.check-selet').each(function(e){
		if(LqUtil.isNotEmpty($(this).attr("floorid"))){
			floorids.push($(this).attr("floorid"));
		}
	});
	var startTime = $('#startDate').val();
	var flishTime = $('#finishDate').val();
	if(floorids.length > 0){
		pageQuery(type,flishTime,startTime,buildid,1,10,floorids.join(','));
	}else{
		layer.msg("请至少选择一个楼层", {icon: 5});
	}
	
}
function pageQuery(a,b,c,d,e,f,g){
	showLoadding();
	var data ={
		"metertype":a,
		"flishTime":b,
		"startTime":c,
		"buildid":d,
		"page":e,
		"pageSize":f,
		"floorids":g,
		"t":new Date().getTime()
	};
	try{
	LqUtil.post("energyMap_bulidStatistics",true,data,function(json){
		if(json.status){
			$('.flow1-table').empty();
			var html = [];
			if(a == '50107'){
				html.push("<tr style=\'height:46px;background:#FFFED0;\'>");
				html.push("<th>日期</th>");
				html.push("<th>用电量</th>");
				html.push("<th>电费</th>");
				html.push("<th>操作</th>");
				html.push("</tr>");
			}else{
				html.push("<tr style=\'height:46px;background:#FFFED0;\'>");
				html.push("<th>日期</th>");
				html.push("<th>用水量</th>");
				html.push("<th>水费</th>");
				html.push("<th>操作</th>");
				html.push("</tr>");
			}
			$.each(json.data,function(i,v){
				html.push("<tr>");
				html.push("<td>" + v.datetime + "</td>");
				html.push("<td>" + v.value + json.unit +"</td>");
				html.push("<td>￥" + v.totalPrice + "</td>");
				html.push("<td><span class=\'flow1-check\' onclick=\'viewDetail(\"" + d + "\",\"" + v.datetime + "\",\"" + g + "\",\"" + a + "\")\'>查看详情</span></td>");
				html.push("</tr>");
			});
			$('.flow1-table').append(html.join(''));
			createPagin(json.totalPage,json.currentPage,a,b,c,d,e,f,g);
		}else{
			if(data.errorCode == 404){
				layer.msg(data.errorContent, {icon: 5}, function(){
					location.href = 'login_logout';
				});
			}
		}
		closeLoadding();
	});
	}catch(e){
		closeLoadding();
	}
}
function createPagin(totalPage,currentPage,a,b,c,d,e,f,g){
	var html = [];
	var count = 0 , i = 0;
	if(totalPage > 10){
		if(currentPage > 6){
			html.push("<li class=\'paginItem\'><a href=\'javascript:void(0);\' rel=\'1\'><span class=\'pagepre\'></span></a></li>");
			html.push("<li class=\'paginItem more\'><a href=\'javascript:void(0);\'>...</a></li>");
			if(totalPage>currentPage +5){
				count = 0;
				for(i = (currentPage -5); i <= (currentPage +5); i++){
					if(currentPage ==(count + currentPage - 5)){
						html.push("<li class=\'paginItem page-active\'><a href=\'javascript:void(0);\' >" + i + "</a></li>");
					}else{
						html.push("<li class=\'paginItem\'><a href=\'javascript:void(0);\' rel=\'" + i + "\'>" + i + "</a></li>");
					}
					count++;
				}
				html.push("<li class=\'paginItem more\'><a href=\'javascript:void(0);\'>...</a></li>");
				html.push("<li class=\'paginItem\'><a href=\'javascript:void(0);\' rel=\'" + totalPage + "\'><span class=\'pagenxt\'></span></a></li>");
			}else{
				count= 0;
				for(i = (currentPage -5); i <= totalPage; i++){
					if(currentPage ==(count + currentPage - 5)){
						html.push("<li class=\'paginItem page-active\'><a href=\'javascript:void(0);\'>" + i + "</a></li>");
					}else{
						html.push("<li class=\'paginItem\'><a href=\'javascript:void(0);\' rel=\'" + i + "\'>" + i + "</a></li>");
					}
					count++;
				}
			}
		}else{
			count= 0;
			for(i = 1; i <= 10; i++){
				if(currentPage ==(count + 1)){
					html.push("<li class=\'paginItem page-active\'><a href=\'javascript:void(0);\'>" + i + "</a></li>");
				}else{
					html.push("<li class=\'paginItem\'><a href=\'javascript:void(0);\' rel=\'" + i + "\'>" + i + "</a></li>");
				}
				count++;
			}
			html.push("<li class=\'paginItem more\'><a href=\'javascript:void(0);\'>...</a></li>");
				html.push("<li class=\'paginItem\'><a href=\'javascript:void(0);\' rel=\'" + totalPage + "\'><span class=\'pagenxt\'></span></a></li>");
		}
	}else if(totalPage > 1 && 10 >= totalPage){
		count= 0;
		for(i = 1; i <= totalPage; i++){
			if(currentPage ==(count + 1)){
				html.push("<li class=\'paginItem page-active\'><a href=\'javascript:void(0);\'>" + i + "</a></li>");
			}else{
				html.push("<li class=\'paginItem\'><a href=\'javascript:void(0);\' rel=\'" + i + "\'>" + i + "</a></li>");
			}
			count++;
		}
	}
	$('.paginList').empty();
	$('.paginList').append(html.join(""));
	$('.paginItem a').click(function(e){
		if(LqUtil.isNotEmpty($(this).attr('rel'))){
			pageQuery(a,b,c,d,$(this).attr('rel'),f,g);
		}
	})
}
function viewDetail(buildid,datetime,floorids,metertype){
	showLoadding();
	try{
		$(".flow-mesg").show();
		var floorArr = floorids.split(",");
		$('.mesg-list').empty();
		for(var i = 0 ; i< floorArr.length; i++){
			var data = {
				"floorid":floorArr[i],
				"buildid":buildid,
				"datetime":datetime,
				"metertype":metertype
			};
			
			var html = [];
			LqUtil.post("energyMap_bulidStatisticsDetail",false,data,function(json){
				if(json.status){
					$.each(json.data,function(i, v){
						html.push("<li>");
						html.push("<p class=\'shebei-name\'><span class=\'shebei-img float-L\'><img src=\'" + (metertype == '50107'?'zhuantitu/images/ydsb.png':'zhuantitu/images/yssb.png') + "\'></span><span class=\'name\'>" +
							v.name + "</span></p>");
						html.push("<span class=\'count\'>" + (metertype == '50107'?'用电':'用水') + "：<span class=\'electric\'>" + v.value + "</span>" + 
							v.unit + "</span><span class=\'count\'>费用：<span class=\'electric\'>￥" + v.totalPrice + "</span></span>");
						html.push("</li>");
					});
				}else{
					if(data.errorCode == 404){
						layer.msg(data.errorContent, {icon: 5}, function(){
							location.href = 'login_logout';
						});
					}
				}
			});
			$('.mesg-list').append("<li><p class=\'mesg-floor\'>" + floorMap.get(floorArr[i])+ "</p><ul class=\'mesg-box\'>" + html.join("") + "</ul></li>");
		}
		closeLoadding();
	}catch(e){
		closeLoadding();
	}
}
function initFloor(){
	var html = [];
	$.each(floorInfo, function(i, v){
		html.push("<div class=\'xuan xuan1\'>");
		html.push("<div class=\'checkbox\' floorid=\'" + v.floorid+ "\' onclick=\'check_selet(this)\'></div><span>" + v.name + "</span>");
		html.push("</div>");
	});
	$('.xuan-box').empty();
	$('.xuan-box').append(html.join(''));
	$('.xuan-box').append("<div class=\'xuan xuan1 \'><div class=\'all-checkbox\' onclick=\'all_check_selet(this)'\></div><span>全选</span></div>");
	$(".reset-btn1").click(function(){
		$(".checkbox").removeClass("check-selet");
		$(".all-checkbox").removeClass("check-selet");
		$(".time-select1 input").val("");
		flog = true;
	})
}
function transparentVectorUnselect(e){
	onPopupClose();
}
function showBaseMarker(){
	var d = {
		"data":[
			{"coordinate":"[114.00130,33.0085]","icon":"zhuantitu/images/zmzb.png","pointid":1,"name":"电"},
			{"coordinate":"[114.00156,33.0085]","icon":"zhuantitu/images/jszb.png","pointid":2,"name":"水"},
			{"coordinate":"[113.99670,33.00911]","icon":"zhuantitu/images/zmzb.png","pointid":3,"name":"电"},
			{"coordinate":"[113.99699,33.00911]","icon":"zhuantitu/images/jszb.png","pointid":4,"name":"水"},
		]
	};
	var baseMarkerLayer = new LMap.Layer.Vector('baseMarkerLayer',{
		styleMap:map.iconStyle({'src':'\${icon}','width':26,'height':26,'title':'\${title}'})
	});
	$.each(d.data, function(i, v){
		var coordinate = eval(v.coordinate);
		var markerFeaure = new LMap.Feature.Vector(
			new LMap.Geometry.Point(coordinate[0],coordinate[1]),{
				icon:v.icon,
				gid:v.pointid,
				title:v.name
			}
		);
		baseMarkerLayer.addFeatures(markerFeaure);
	});
	map.addLayer(baseMarkerLayer);
}

$('.zoom-in').click(function(e){
	e.stopPropagation();
	map.zoomIn();
});
$('.zoom-out').click(function(e){
	e.stopPropagation();
	map.zoomOut();
});
function createPopup(gid,lonlat,x,y){
	var popup = new LMap.Popup.BlankFramedCloud(
			'g_'+gid,
			lonlat,
			null,
			null,
			null,
			false,
			onPopupClose,
			'tr',
			null,
			x,
			y
		);
	return popup;
}
var markerPopup = null;
function destroyMarkerPopup(){
	if(markerPopup != null){
		map.removePopup(markerPopup);
		markerPopup.destroy();
		markerPopup = null;
	}
}
if(selectControl){
		selectControl.unselectAll();
	}

var tipMarkerPopup = null;
var isClick = true;
function searchForm(){
	var keywords = $('#keywords').val();
	if(LqUtil.isNotEmpty(keywords)){
		if(isClick){
			$('.input-clear').addClass('loading');
			isClick = false;
			try{
			map.search(keywords,function(d){
				if(d.status){
					$('.search-box .List').show();
					var temp = [];
					if(d.result.length > 0){
						$.each(d.result, function(i, v){
							if(v.type=="Polygon"){
								temp.push("<li title=\'" +v.name+ "\' pointid=\'" + v.id + "\' coordinate=\'" + v.coordinate + "\'>" + v.name + "</li>");
							}
						});
					}else{
						temp.push("<li><span>未找到相关地点</span></li>");
					}
					$('.search-box .List').empty();
					$('.search-box .List').append(temp.join(''));
					
					$('.search-box .List li').click(function(e){
						var coordinate = eval($(this).attr('coordinate'));
						map.setCenter(coordinate);
						$('.search-box .List').hide();
						if(tipMarkerPopup != null){
							map.removePopup(tipMarkerPopup);
						}
						tipMarkerPopup = createPopup('tip-marker',new LMap.LonLat(coordinate[0],coordinate[1]),-12,0);
						tipMarkerPopup.setContentHTML('<div style="height:60px;"><img class="blank-marker" style="margin-top:24px;" src="zhuantitu/images/blank-marker.png"/></div>');
						map.addPopup(tipMarkerPopup);
						$('.blank-marker').animate({"margin-top":"0px",},200).animate({"margin-top":"24px",},200)  
					         .animate({"margin-top":"12px",},200).animate({"margin-top":"24px",},200)  
					         .animate({"margin-top":"18px",},200).animate({"margin-top":"24px",},200); 
					});
				}
				isClick = true;
				$('.input-clear').removeClass('loading');
			});
			}catch(e){
				$('.input-clear').addClass('loading');
			}
		}
	}
	return false;
}
$('.search-btn').click(function(e){
	searchForm();
});
$('#keywords').keyup(function(e){
	if(!LqUtil.isNotEmpty($('#keywords').val())){
		$('.search-box .List').empty();
		$('.search-box .List').hide();
		$('.input-clear').hide();
	}else{
		$('.input-clear').show();
	}
});
$('.input-clear').click(function(e){
	$('#keywords').val("");
	$('.search-box .List').empty();
	$('.search-box .List').hide();
	$('.input-clear').hide();
	if(tipMarkerPopup != null){
		map.removePopup(tipMarkerPopup);
	}
});