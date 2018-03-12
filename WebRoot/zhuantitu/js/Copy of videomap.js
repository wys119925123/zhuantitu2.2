
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
var floorInfo = map.getFloorInfo().floorInfo;
$.each(floorInfo, function(i, v){
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
		layers[0].removeAllFeatures();
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
		layers[0].removeAllFeatures();
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
	if(layers.length > 0){
		if(map.getZoom() > 3){
			layers[0].setVisibility(true);
		}else{
			layers[0].setVisibility(false);
		}
	}
});
var markerPopup = null;
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
function destroyMarkerPopup(){
	if(markerPopup != null){
		map.removePopup(markerPopup);
		markerPopup.destroy();
		markerPopup = null;
	}
}
function onPopupClose(){
	if(selectControl){
		selectControl.unselectAll();
	}
}


function markerFeatureselected(e){
	var feature = e.feature;
	LqUtil.post("thematicPoint_videoDetail",true,{"id":feature.attributes.gid},function(d){
		if(d.status){
			var obj = null;
			layer.open({
			  type: 2,
			  title: ['可视化视频调阅', 'font-size:12px;height:38px'],
			  maxmin: false,
			  shadeClose: true,
			  shade: 0.8,
			  area: ['100%', '100%'],
			  content: "videoMap_viewVideo?t=" + new Date().getTime() + "&mid=" +mid+ "&zoneid=" + zoneid +"&param=" + d.data,
			  success: function(layero, index){
				obj = layero;
			  },
			  cancel:function(index){
				var iframeWin = window[obj.find('iframe')[0]['name']];
   				iframeWin.stopAllVideo();
			  }
			});
		}else{
			outError(d);
		}
		onPopupClose();
	});
	
}
function markerFeatureunselected(e){
	destroyMarkerPopup();
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
var boxLayer = new LMap.Layer.Vector("Box layer");
map.addLayer(boxLayer);
var drawControls = {
    box: new LMap.Control.DrawFeature(boxLayer,
        LMap.Handler.RegularPolygon, {
            handlerOptions: {
                sides: 4,
                irregular: true
            }
        }
    )
};
map.addControl(drawControls['box']);
drawControls['box'].featureAdded = function(e){
	boxLayer.removeAllFeatures();
	layer.confirm('确定按此区域查看吗?', {
		title:'提示',
	  	btn: ['确定','重新框选']
	}, function(){
		layer.closeAll();
		var data = {
				"frameLonlat":e.geometry.toString().replace('POLYGON((','').replace('))',''),
				"floorid":$('.right-floor-ctns').find('.floor-pic-chosed').attr('floorid'),
				"isSearchFloor":(map.getZoom() > 3?1:0)
		};
		LqUtil.post("thematicPoint_videoSearch",true,data,function(d){
			if(d.status){
				var obj = null;
				layer.open({
				  type: 2,
				  title: ['可视化视频调阅', 'font-size:12px;height:38px'],
				  maxmin: false,
				  shadeClose: true,
				  shade: 0.8,
				  area: ['100%', '100%'],
				  content: "videoMap_viewVideo?t=" + new Date().getTime() + "&mid=" +mid+ "&zoneid=" + zoneid +"&param=" + d.data,
				  success: function(layero, index){
					obj = layero;
				  },
				  cancel:function(index){
					var iframeWin = window[obj.find('iframe')[0]['name']];
	   				iframeWin.stopAllVideo();
				  }
				});
			}else{
				outError(d);
			}
			onPopupClose();
		});
	}, function(){
	  
	});
	
}
$('.box-search').click(function(e){
	if($(this).hasClass('add-active')){
		$(this).removeClass('add-active');
		$(this).css('background', '#fff');
		drawControls['box'].deactivate();
	}else{
		$(this).addClass('add-active');
		$(this).css('background', '#cacaca');
		drawControls['box'].activate();
	}
});
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

 


