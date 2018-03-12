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

var thematicPointCategoryMap = new HashMap();
var categoryColumnDefineMap = new HashMap();
var videoIconMap = new HashMap();
var map = new LMap.Map2D("map", zoneid);
map.addMeasureAreaControl(function (e) {
    var measure = e.measure;
    var html = "";
	if(measure < 1000) {
		html += "总面积：<font style='color:#ff6319;font-weight: bold;'>" + measure.toFixed(3) + "</font>平方米 *测量数据仅供参考,双击结束";
    }else{
    	html += "总面积：<font style='color:#ff6319;font-weight: bold;'>" + (measure/1000).toFixed(3) + "</font>平方千米 *测量数据仅供参考,双击结束";
    }
    html += "<img onclick='stopMeasureArea()' src='zhuantitu/images/close-measure.png' style='cursor:pointer;display: inline;' title='清除本次测量'/>";
    $(".measureResult").html(html);
})
map.addMeasureDistanceControl(function (e) {
    var units = e.units;
    var measure = e.measure;
    var html = "";
    if(units == 'km'){
    	html += "总长：<font style='color:#ff6319;font-weight: bold;'>" + measure.toFixed(2) + "</font>千米 *测量数据仅供参考,双击结束";
    }else{
    	html += "总长：<font style='color:#ff6319;font-weight: bold;'>" + measure.toFixed(2) + "</font>米 *测量数据仅供参考,双击结束";
    }
    html += "<img onclick='stopMeasureDistance()' src='zhuantitu/images/close-measure.png' style='cursor:pointer;display: inline;' title='清除本次测量'/>";
    	$(".measureResult").html(html);
})

$('.measureDistance').click(function(){
	map.startMeasureDistance();
})
$('.measureArea').click(function(){
	map.startMeasureArea();
})
function stopMeasureDistance(){
	map.stopMeasureDistance();
	$(".measureResult").html("");
}
function stopMeasureArea(){
	map.stopMeasureArea();
	$(".measureResult").html("");
}
function changeFloor(floorid,status){
	map.showFloorMapById(floorid,status);
	if(status){
		showBaseMarker();
	}
	showFloorMarker(floorid);
	map.click(showBulidTotalStatistics);
}

LqUtil.get("cameraIcon_loadIcon",false,null,function(json){
	if(json.status){
		$.each(json.data, function(i, v){
			videoIconMap.put(v.iconid,{
				"iconid":v.iconid,
				"icon":v.path
			});
		});
	}
});
LqUtil.get("thematicPointCategory_loadByMenuid",true,{'mid':mid},function(json){
	if(json.status){
		$.each(json.data, function(i, v){
			thematicPointCategoryMap.put(v.categoryid,{
				"categoryid":v.categoryid,
				"name":v.name,
				"icon":v.icon
			});
		});
	}
});
var floorMap = new HashMap();
var floorInfo = map.getFloorInfo().floorInfo;
$.each(floorInfo, function(i, v){
	floorMap.put(v.floorid,v.name);
	var currentFloorClass = "",li = "";
	if(v.isDefault == 1){
		changeFloor(v.floorid,true);
		currentFloorClass = "floor-pic-chosed";
	}
	
	if(i > 1){
		if(i == 2){
			li = "<li class='floor-more'>"+
					"<i class='floor-more-pic'></i>"+
					"<span>更多</span>"+
					"<i class='floor-line'></i>"+
					"<ul class='more-popup bor-2'>"+
						"<li>"+
							"<i class='floor-pic " + currentFloorClass + "' floorid='" + v.floorid+ "'></i>"+
							"<span>" + v.name +"</span>"+
						"</li>"+
						"<i class='details-line'></i>"+
					"</ul>"+
				"</li>";
				$('.right-floor-ctns').append(li);
		}else{
			li = "<li>"+
					"<i class='floor-pic " + currentFloorClass + "' floorid='" + v.floorid+ "'></i>"+
					"<span>" + v.name +"</span>"+
				"</li>"+
				"<i class='details-line'></i>";
			$('.more-popup').append(li);
			
		}
	}else{
		li = "<li class='floor'>" +
			"<i class='floor-pic " + currentFloorClass + "' floorid='" + v.floorid+ "'></i>" +
			"<span>" + v.name + "</span>" +
			"<i class='floor-line'></i>" +
		"</li>";
		$('.right-floor-ctns').append(li);
	}
});
$('.zoom-in').click(function(e){
	e.stopPropagation();
	map.zoomIn();
});
$('.zoom-out').click(function(e){
	e.stopPropagation();
	map.zoomOut();
});
$('.floor').click(function(e){
	e.stopPropagation();
	$('.floor-pic').removeClass('floor-pic-chosed');
	$(this).find('.floor-pic').addClass('floor-pic-chosed');
	changeFloor($(this).find('.floor-pic').attr('floorid'));
});
$('.ctns-tool').click(function(e){
	e.stopPropagation();
	$('.tool-details').toggle();
});
$('.tool-details').click(function(e){
	e.stopPropagation();
});
$('.floor-more').click(function(e){
	e.stopPropagation();
	$('.more-popup').toggle();
	var len = $('.more-popup>li').length;
	var oo = $('.more-popup>li').width() * len +$('.more-popup').find('.details-line').width() * len;
	var bo = parseInt($('.more-popup').css('border-left-width')) * 2;
	var ow = - (oo + bo);
	$('.more-popup').css('left',ow);
});
$('.more-popup').click(function(e){
	e.stopPropagation();
});
$('.more-popup li').click(function(e){
	e.stopPropagation();
	$('.floor-pic').removeClass('floor-pic-chosed');
	$(this).find('.floor-pic').addClass('floor-pic-chosed');
	changeFloor($(this).find('.floor-pic').attr('floorid'));
});
var selectControl;
function showBaseMarker(){
	var layers = map.getLayersByName('baseMarkerLayer');
	if(layers.length > 0){
		map.removeLayer(layers[0]);
	}
	LqUtil.get("thematicPoint_list",false,{'mid':mid,'floorid':zoneid + '00'},function(d){
		if(d.status){
			var baseMarkerLayer = new LMap.Layer.Vector('baseMarkerLayer',{
				styleMap:map.iconStyle({'src':'\${icon}','width':28,'height':28,'title':'\${title}'})
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
			baseMarkerLayer.events.un({
    			featureselected:markerFeatureselected,
				featureunselected:markerFeatureunselected
    		});
			baseMarkerLayer.events.on({
				featureselected:markerFeatureselected,
				featureunselected:markerFeatureunselected
			});
		}else{
			outError(d);
		}
	});
}
function showFloorMarker(floorid){
	var layers = map.getLayersByName('floorMarkerLayer');
	if(layers.length > 0){
		map.removeLayer(layers[0]);
	}
	LqUtil.get("thematicPoint_list",true,{'mid':mid,'floorid':floorid},function(d){
		if(d.status){
			var floorMarkerLayer = new LMap.Layer.Vector('floorMarkerLayer',{
				styleMap:map.iconStyle({'src':'\${icon}','width':28,'height':28,'title':'\${title}'})
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
				floorMarkerLayer.addFeatures(markerFeaure);
			});
			if(map.getZoom() < 4){
				floorMarkerLayer.setVisibility(false);
			}
			map.addLayer(floorMarkerLayer);
			
			floorMarkerLayer.events.un({
    			featureselected:markerFeatureselected,
				featureunselected:markerFeatureunselected
    		});
			floorMarkerLayer.events.on({
				featureselected:markerFeatureselected,
				featureunselected:markerFeatureunselected
			});
			
			var hoverControl = new LMap.Control.SelectFeature([floorMarkerLayer,map.getLayersByName('baseMarkerLayer')[0]],{
		        hover:true,
		        highlightOnly:true,
		        renderIntent:"temporary"
		    });
		    map.addControl(hoverControl);
		    hoverControl.activate();
		
		
		    selectControl = new LMap.Control.SelectFeature([floorMarkerLayer,map.getLayersByName('baseMarkerLayer')[0]],{clickout:true});
		    map.addControl(selectControl);
		    selectControl.activate();
		}else{
			outError(d);
		}
	});
}
map.registerEvent('moveend',function(e){
	var layers = map.getLayersByName('floorMarkerLayer');
	var energyMarkerLayer = map.getLayersByName('energyMarkerLayer');
	if(layers.length > 0){
		if(map.getZoom() > 3){
			layers[0].setVisibility(true);
			energyMarkerLayer[0].setVisibility(false);
		}else{
			layers[0].setVisibility(false);
			energyMarkerLayer[0].setVisibility(true);
		}
	}
});
function onPopupClose(){
	if(selectControl){
		selectControl.unselectAll();
	}
}
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
function markerFeatureselected(e){
	var feature = e.feature;
	showLoadding();
	removeTipMarkerPopup();
	try{
		LqUtil.get("energyMap_meterDetail",true,{'id':feature.attributes.gid},function(d){
			closeLoadding();
			$('.scroll-box').remove();
			var html = [];
			if(d.status){
				html.push("<div class=\'scroll-box form-content posit-AB\' style=\'width: 215px;\'>");
				html.push("<div class=\'address text-CENT f20\' style=\'padding:0 10px;\'>" + d.name + "</div>");
				html.push("<ul class=\'count-List\'>");
				html.push("<li>");
				html.push("<p class=\'belong-build\'><img class=\'float-L\' style=\'margin-top:8px;\' src=\'zhuantitu/images/yuanDa.png\' alt=\'\'><span class=\'belong\'>从属建筑:</span><span class=\'build\'>" + d.buildName + "</span></p>");
				html.push("</li>");
				if(d.metertype != '50101'){
					html.push("<li>");
					html.push("<p class=\'monitor\'>监测时间</p>");
					html.push("<p class=\'monitor-time\'>" + d.monitorTime + "</p>");
					html.push("</li>");
				}
				html.push("<li>");
				html.push("<span class=\'shebei-mesg mgl20 IP\' style=\'padding:0;\'>ip地址：</span>");
				html.push("<span class=\'shebei-mesg ip\'>" + d.ip + "</span>");
				html.push("</li>");
				if(d.metertype != '50101'){
					html.push("<li>");
					html.push("<span class=\'shebei-mesg mgl20 IP\' style=\'padding:0;\'>当前状态：</span>");
					html.push("<span class=\'shebei-mesg ip\'><span class=\'zhuangtai-img\'><img src=\'zhuantitu/images/yuanDa.png\' alt=\'\'></span><span class=\'zhuangtai-mesg\'>" + d.meterstatus +"</span></span>");
					html.push("</li>");
				}
				html.push("<li>");
				html.push("<span class=\'shebei-mesg mgl20 IP\' style=\'padding:0;\'>设备地址：</span>");
				html.push("<span class=\'shebei-mesg ip\'>" + d.address + "</span>");
				html.push("</li>");
				html.push("<li>");
				html.push("<span class=\'shebei-mesg mgl20 IP\' style=\'padding:0;\'>变比：</span>");
				html.push("<span class=\'shebei-mesg ip\'>" + d.rate + "</span>");
				html.push("</li>");
				html.push("<li>");
				html.push("<span class=\'shebei-mesg mgl20 IP\' style=\'padding:0;\'>获得周期：</span>");
				html.push("<span class=\'shebei-mesg ip\'>" + d.getInterval+ "</span>");
				html.push("</li>");
				html.push("<li>");
				html.push("<span class=\'shebei-mesg mgl20 IP\' style=\'padding:0;\'>设备类型：</span>");
				html.push("<span class=\'shebei-mesg ip\'>" + d.metertypeName + "</span>");
				html.push("</li>");
				html.push("</ul>");
				if(d.metertype == '50107'){
					$('.flow3-title').text("设备用电统计");
					html.push("<div class=\'btn-count metertongji text-CENT\'>设备用电统计</div>");
				}else if(d.metertype == '50205'){
					$('.flow3-title').text("设备用水统计");
					html.push("<div class=\'btn-count metertongji text-CENT\'>设备用水统计</div>");
				}
				html.push("</div>")
				$('.left').append(html.join(''));
				$(".form-content").height($(window).height()-190);
				$(".scroll-box").mCustomScrollbar({
					theme:"minimal-dark"
				});
				$('.metertongji').unbind("click");
				$(".metertongji").click(function(){
					$('.flow3-table').empty();
					$('.flow-zongji-meter .paginList').empty();
					$(".flow-zongji-meter .flow3-address").text(d.name);
					$(".flow-zongji-meter").show();
					$(".overShaw").show();
				});
				$('.dian-btn2').unbind("click");
				$(".dian-btn2").click(function(){
					searchMeterEnergy(feature.attributes.gid);
				});
			}else{
				outError(d);
			}
		});
	}catch(e){
		closeLoadding();
	}
}
function searchMeterEnergy(id){
	var startTime = $('#meterStartTime').val();
	var flishTime = $('#meterFinishTime').val();
	pageQuery3(id,flishTime,startTime,1,10);
	
}
function markerFeatureunselected(e){
	onPopupClose();
}
showEnergyMarker();
function showBulidTotalStatistics(geom){
	if(geom != null && geom.geometrytype == 'outdoor'){
		LqUtil.post('energyMap_bulidTotalStatistics',true,{"buildid":geom.id},function(d){
			$('.scroll-box').remove();
			var html = [];
			if(d.status){
				html.push("<div class=\'scroll-box form-content posit-AB\' style=\'width: 215px;\'>");
				html.push("<div class=\'address text-CENT f20\'>" + geom.name + "</div>");
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
					$('.flow-zongji .paginList').empty();
					$(".flow-zongji .flow1-address").text(geom.name);
					$(".flow-mesg-address").text(geom.name);
					$(".flow-zongji").show();
					$(".overShaw").show();
					
				});
				$('.dian-btn1').unbind("click");
				$(".dian-btn1").click(function(){
					searchEnergy('50107',geom.id);
				});
				$('.shui-btn1').unbind("click");
				$(".shui-btn1").click(function(){
					searchEnergy('50205',geom.id);
				});
				
			}else{
				if(d.errorCode == 404){
					layer.msg(d.errorContent, {icon: 5}, function(){
						location.href = 'login_logout';
					});
				}else{
					html.push("<div class=\'scroll-box form-content posit-AB\' style=\'width: 215px;\'>");
					html.push("<div class=\'address text-CENT f20\'>" + geom.name + "</div>");
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
	$('.flow-zongji .paginList').empty();
	$('.flow-zongji .paginList').append(getPaginHtml(totalPage,currentPage));
	$('.flow-zongji .paginList .paginItem a').click(function(e){
		if(LqUtil.isNotEmpty($(this).attr('rel'))){
			pageQuery(a,b,c,d,$(this).attr('rel'),f,g);
		}
	})
}
function pageQuery3(a,b,c,d,e){
	showLoadding();
	var data ={
		"id":a,
		"flishTime":b,
		"startTime":c,
		"page":d,
		"pageSize":e,
		"t":new Date().getTime()
	};
	try{
		LqUtil.post("energyMap_meterStatistics",true,data,function(json){
			if(json.status){
				$('.flow3-table').empty();
				var html = [];
				if(json.type == '50107'){
					html.push("<tr style=\'height:46px;background:#FFFED0;\'>");
					html.push("<th>日期</th>");
					html.push("<th>用电量</th>");
					html.push("<th>电费</th>");
					html.push("</tr>");
				}else{
					html.push("<tr style=\'height:46px;background:#FFFED0;\'>");
					html.push("<th>日期</th>");
					html.push("<th>用水量</th>");
					html.push("<th>水费</th>");
					html.push("</tr>");
				}
				$.each(json.data,function(i,v){
					html.push("<tr>");
					html.push("<td>" + v.datetime + "</td>");
					html.push("<td>" + v.value + json.unit +"</td>");
					html.push("<td>￥" + v.totalPrice + "</td>");
					html.push("</tr>");
				});
				$('.flow3-table').append(html.join(''));
				createPagin3(json.totalPage,json.currentPage,a,b,c,d,e);
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
function createPagin3(totalPage,currentPage,a,b,c,d,f){
	$('.flow-zongji-meter .paginList').empty();
	$('.flow-zongji-meter .paginList').append(getPaginHtml(totalPage,currentPage));
	$('.flow-zongji-meter .paginList .paginItem a').click(function(e){
		if(LqUtil.isNotEmpty($(this).attr('rel'))){
			pageQuery3(a,b,c,$(this).attr('rel'),f);
		}
	})
}
function getPaginHtml(totalPage,currentPage){
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
	return html.join("")
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
function showEnergyMarker(){
	var d = {
		"data":[
			{"coordinate":"[113.99806914135,33.010036356324]","icon":"zhuantitu/images/zmzb.png","pointid":1,"name":"电"},
			{"coordinate":"[113.99893649438,33.010089375352]","icon":"zhuantitu/images/jszb.png","pointid":2,"name":"水"},
			{"coordinate":"[114.00214609906,33.011967546814]","icon":"zhuantitu/images/zmzb.png","pointid":3,"name":"电"},
			{"coordinate":"[114.00277741769,33.012117125367]","icon":"zhuantitu/images/jszb.png","pointid":4,"name":"水"},
		]
	};
	var energyMarkerLayer = new LMap.Layer.Vector('energyMarkerLayer',{
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
		energyMarkerLayer.addFeatures(markerFeaure);
	});
	map.addLayer(energyMarkerLayer);
}
var tipMarkerPopup = null;
var isClick = true;
function removeTipMarkerPopup(){
	if(tipMarkerPopup != null){
		map.removePopup(tipMarkerPopup);
		tipMarkerPopup.destroy();
		tipMarkerPopup = null;
	}
}
function searchForm(){
	var keywords = $('#keywords').val();
	if(LqUtil.isNotEmpty(keywords)){
		if(isClick){
			$('.input-clear').addClass('loading');
			isClick = false;
			try{
				map.search(keywords,function(d){
					if(d.status){
						removeTipMarkerPopup();
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
							var fid = $(this).attr('pointid').substring(0,6);
							if($(this).attr('pointid').substring(4,6) == '00'){
								map.setCenter(coordinate);
							}else{
								map.setCenter(coordinate);
								$('.floor-pic').removeClass('floor-pic-chosed');
								$('.floor-pic').each(function(){
									if($(this).attr('floorid') == fid){
										$(this).addClass('floor-pic-chosed');
									}
								});
								changeFloor(fid);
							}
							$('.search-box .List').hide();
							
							tipMarkerPopup = createPopup('tip_marker',new LMap.LonLat(coordinate[0],coordinate[1]),-12,0);
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


