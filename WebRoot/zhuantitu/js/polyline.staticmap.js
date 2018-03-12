jQuery.support.cors = true;
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

var thematicPolylineCategoryMap = new HashMap();
var categoryColumnDefineMap = new HashMap();
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
});
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
});

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
		showBasePolyline();
	}
	showFloorPolyline(floorid);
}
$.ajax({
	    type: "get",
	    url:'thematicPolylineCategory_loadByMenuid',
	    async: true,
	    data:{'mid':mid},
	    success: function(json) {
		if(json.status){
		$.each(json.data, function(i, v){
			thematicPolylineCategoryMap.put(v.categoryid,{
				"categoryid":v.categoryid,
				"name":v.name,
				"strokecolor":v.strokecolor,
				"strokewidth":v.strokewidth,
				"minZoom":v.minZoom,
				"maxZoom":v.maxZoom,
				"isClick":v.isClick,
				"icon":v.icon
			});
		});
	}
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
function showBasePolyline(){
	var layers = map.getLayersByName('basePolylineLayer');
	if(layers.length > 0){
		layers[0].removeAllFeatures();
		map.removeLayer(layers[0]);
	}
	$.ajax({
	    type: "get",
	    url:'thematicPolyline_list',
	    async: false,
	    data:{'mid':mid,'floorid':zoneid + '00'},
	    success: function(d) {
		if(d.status){
			var basePolylineLayer = new LMap.Layer.Vector('basePolylineLayer',{
				styleMap:new LMap.StyleMap({
			        "default": new LMap.Style({
			        	cursor:'pointer',
						title:'${title}',
			            strokeWidth: '${strokewidth}',
			            strokeOpacity: 0.8,
			            strokeColor: '${strokecolor}'
			        }),
			        "select": new LMap.Style({
			        }),
			        "temporary": new LMap.Style( {
			            strokeWidth: '${strokewidth}',
			            strokeOpacity: 0.8,
			            strokeColor: "#ff0000"
			        })
			    })
			});
			$.each(d.data, function(i, v){
				//var polylineWkt = "MULTILINESTRING " + v.coordinates.replace('[[','((').replace(']]','))').replace(/\],\[/g,'-').replace(/\[/g,'').replace(/\,/g,' ').replace(/\-/g,',');
				var points = []
				var coordinates = eval(v.coordinates);
				for(var n = 0; n < coordinates.length; n++){
					points.push(new LMap.Geometry.Point(coordinates[n][0],
						coordinates[n][1]).transform(proj, map.getProjectionObject()));
				}
				var polylineFeaure = new LMap.Feature.Vector(
						new LMap.Geometry.LineString(points),{
							gid:v.polylineid,
							strokewidth:v.strokewidth,
							strokecolor:"#"+v.strokecolor,
							title:v.name
						}
			    );
				basePolylineLayer.addFeatures(polylineFeaure);
			});
			map.addLayer(basePolylineLayer);
			basePolylineLayer.events.un({
    			featureselected:polylineFeatureselected,
				featureunselected:polylineFeatureunselected
    		});
			basePolylineLayer.events.on({
				featureselected:polylineFeatureselected,
				featureunselected:polylineFeatureunselected
			});
		}else{
			outError(d);
		}
	    }
	});
}

function showFloorPolyline(floorid){
	var layers = map.getLayersByName('floorPolylineLayer');
	if(layers.length > 0){
		layers[0].removeAllFeatures();
		map.removeLayer(layers[0]);
	}
	$.ajax({
	    type: "get",
	    url:'thematicPolyline_list',
	    async: true,
	    data:{'mid':mid,'floorid':floorid},
	    success: function(d) {
		if(d.status){
			var floorPolylineLayer = new LMap.Layer.Vector('floorPolylineLayer',{
				styleMap:new LMap.StyleMap({
			        "default": new LMap.Style({
			        	cursor:'pointer',
						title:'${title}',
			            strokeWidth: '${strokewidth}',
			            strokeOpacity: 0.8,
			            strokeColor: '${strokecolor}'
			        }),
			        "select": new LMap.Style({
			        }),
			        "temporary": new LMap.Style( {
			            strokeWidth: '${strokewidth}'+2,
			            strokeOpacity: 0.8,
			            strokeColor: "#ff0000"
			        })
			    })
			});
			$.each(d.data, function(i, v){
				//var polylineWkt = "MULTILINESTRING " + v.coordinates.replace('[[','((').replace(']]','))').replace(/\],\[/g,'-').replace(/\[/g,'').replace(/\,/g,' ').replace(/\-/g,',');
				var points = []
				var coordinates = eval(v.coordinates);
				for(var n = 0; n < coordinates.length; n++){
					points.push(new LMap.Geometry.Point(coordinates[n][0],
						coordinates[n][1]).transform(proj, map.getProjectionObject()));
				}
				var polylineFeaure = new LMap.Feature.Vector(
						new LMap.Geometry.LineString(points),{
							gid:v.polylineid,
							strokewidth:v.strokewidth,
							strokecolor:"#"+ v.strokecolor,
							title:v.name
						}
			    );
				floorPolylineLayer.addFeatures(polylineFeaure);
			});
			if(map.getZoom() < 4){
				floorPolylineLayer.setVisibility(false);
			}
			map.addLayer(floorPolylineLayer);
			
			floorPolylineLayer.events.un({
    			featureselected:polylineFeatureselected,
				featureunselected:polylineFeatureunselected
    		});
			floorPolylineLayer.events.on({
				featureselected:polylineFeatureselected,
				featureunselected:polylineFeatureunselected
			});
			var hoverControl = new LMap.Control.SelectFeature([floorPolylineLayer,map.getLayersByName('basePolylineLayer')[0]],{
		        hover:true,
		        highlightOnly:true,
		        renderIntent:"temporary"
		    });
		    map.addControl(hoverControl);
		    hoverControl.activate();
		
		
		    selectControl = new LMap.Control.SelectFeature([floorPolylineLayer,map.getLayersByName('basePolylineLayer')[0]],{clickout:true});
		    map.addControl(selectControl);
		    selectControl.activate();
		}else{
			outError(d);
		}
	    }
	});
}

map.registerEvent('moveend',function(e){
	var layers = map.getLayersByName('floorPolylineLayer');
	if(layers.length > 0){
		if(map.getZoom() > 3){
			layers[0].setVisibility(true);
		}else{
			layers[0].setVisibility(false);
		}
	}
});
var polylinePopup = null;
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
function destroyPolylinePopup(){
	if(polylinePopup != null){
		map.removePopup(polylinePopup);
		polylinePopup.destroy();
		polylinePopup = null;
	}
}
function onPopupClose(){
	if(selectControl){
		selectControl.unselectAll();
	}
}
function deletePolyline(id){
	layer.confirm('你确定要删除吗？', {
		shade:[0.5,'#000'],
		shadeClose: true, 
		title:'提示',
		closeBtn:[1],
		btn: ['确认', '取消'],
		icon: 3,
		fadeIn: 1000,
		yes: function(index){ 
			$.ajax({
	    type: "post",
	    url:'thematicPolyline_delete',
	    async: true,
	    data:{"id":id},
	    success: function(d) {
		if(d.status){
		    		layer.msg('删除成功', {icon:1,shade:0.3,time: 2000},function(){
            			refreshMap();
					});
		    	}
	    }
	});
			
		    layer.close(index);
		},cancel: function(index){ 
		}
	});
}


function goFancyBox(dataObj){
	$.fancybox.open(dataObj);
}
function getImageHtml(images){
	if(images.length > 0){
		var tempImgs = "";
		$.each(images,function(n,v) {
			tempImgs += "{href:'" + v.path +"',title:'" + v.name +"'},";
		});
		var imgsHtml = '<li class=\'img\'><a onclick="goFancyBox([' + LqUtil.deleteLastStr(tempImgs) + '])" href="javascript:void(0)" >' +
					'<img width=\'243px\' height=\'108px\' src="' + images[0].path + '" /></a></li>';
		return imgsHtml;    
	}else{
		return "";
	}
}
var editPolylineData = null;
function getPolylineContentHTML(data,columnDefineMap){
	var temp = [];
	temp.push("<div><div class=\'xiangxi-box\'>"+
			"<div class=\'xiangxiTop posit-RE\'>"+
			"<div class=\'xiangxi-title\'>" + data.name + "</div><div class=\'revise-box f12\'>");
	if(data.editPermissiont == 1){
		editPolylineData = data;
		temp.push("<span class=\'revise\' onclick=\'showEditPopup()\'>修改</span><span>|</span>");
	}else{
		editPolylineData = null;
	}	
	if(data.deletePermission == 1){
		temp.push("<span class=\'delet\' onclick=\'deletePolyline(" + data.polylineid + ")\'>删除</span><span>|</span>");
	}
	temp.push("<span class=\'close-xiangxi\'  onclick=\'onPopupClose()\'><img src=\'zhuantitu/images/close1.png\' style=\'margin-top:2px;\' title=\'关闭\' alt=\'\'></span>");
	temp.push("</div></div><div class=\'xiangqingContent-box posit-RE\'><div class=\'xiangqingContent posit-RE\'><ul class=\'messageBox posit-AB\'>");
	temp.push(getImageHtml(data.images));
	columnDefineMap.each(function(k,v){
		if(v.isShow == 1){
			if(LqUtil.isNotEmpty(data[k])){
				temp.push("<li class=\'message-box\'><span class=\'name\'>" + v.columnCnname + ":</span><span class=\'message\'>" + data[k] + "</span></li>");
			}
		}
	});
	temp.push("</ul>"+
			"</div>"+
			"<div class=\'sanjiao-Box posit-AB\'>"+
			"<div class=\'sanjiao-box posit-RE\'>"+
			"<div class=\'sanjiao posit-AB\'></div>"+
			"<div class=\'sanjiao2 posit-AB\'></div>"+
			"</div>"+
			"</div>"+
			"</div>"+
			"</div></div>");
	return temp.join('');
}
function polylineFeatureselected(e){
	var feature = e.feature;
	$.ajax({
	    type: "get",
	    url:'thematicPolyline_detail',
	    async: true,
	    data:{'id':feature.attributes.gid},
	    success: function(d) {
		if(d.status){
			if(categoryColumnDefineMap.contains(d.categoryid)){
				polylinePopup = createPopup(feature.attributes.gid,feature.geometry.getBounds().getCenterLonLat(),-140,-22);
				polylinePopup.setContentHTML(getPolylineContentHTML(d,categoryColumnDefineMap.get(d.categoryid)));
				map.addPopup(polylinePopup);
			}else{
				$.ajax({
				    type: "get",
				    url:'tableClumnDefine_loadPolylineByCid',
				    async: true,
				    data:{'id':d.categoryid},
				    success: function(json) {
					if(json.status){
						var columnDefineMap = new HashMap();
						$.each(json.data, function(i, v){
							columnDefineMap.put(v.columnName,{
								'columnName':v.columnName,
								'columnCnname':v.columnCnname,
								'columnType':v.columnType,
								'isRequired':v.isRequired,
								'isShow':v.isShow
							});
						});
						categoryColumnDefineMap.put(d.categoryid,columnDefineMap);
						polylinePopup = createPopup(feature.attributes.gid,feature.geometry.getBounds().getCenterLonLat(),-140,-22);
						polylinePopup.setContentHTML(getPolylineContentHTML(d,columnDefineMap));
						map.addPopup(polylinePopup);
					}else{
						outError(json);
					}
				    }
				});
			}
		}else{
			outError(d);
		}
	    }
	});
	
}
function polylineFeatureunselected(e){
	destroyPolylinePopup();
}

var drawLayerStyles = new LMap.StyleMap({
    "default": new LMap.Style(null, {
        rules: [
            new LMap.Rule({
                symbolizer: {
                    "Point": {
                        pointRadius: 6,
                        graphicName: "circle",
                        fillColor: "white",
                        fillOpacity: 1,
                        strokeWidth: 2,
                        strokeOpacity: 1,
                        strokeColor: "#02a6cf"
                    },
                    "Line": {
                        strokeWidth: 4,
                        strokeOpacity: 1,
                        strokeColor: "#00ccff"
                    },
                    "Polygon":{
                        strokeWidth: 2,
                        strokeOpacity: 1,
                        strokeColor: "#ff0000"
                    }
                }
            })
        ]
    }),
    "select": new LMap.Style(null,{
        rules: [
            new LMap.Rule({
                symbolizer: {
                    "Point": {
                        pointRadius: 6,
                        graphicName: "circle",
                        fillColor: "white",
                        fillOpacity: 0.5,
                        strokeWidth: 1,
                        strokeOpacity: 1,
                        strokeColor: "#02a6cf"
                    },
                    "Line": {
                        strokeWidth: 4,
                        strokeOpacity: 1,
                        strokeColor: "#00ccff"
                    },
                    "Polygon":{
                        strokeWidth: 2,
                        strokeOpacity: 1,
                        strokeColor: "#00ccff"
                    }
                }
            })
        ]
    }),
    "temporary": new LMap.Style(null, {
        rules: [
            new LMap.Rule({
                symbolizer: {
                    "Point": {
                        pointRadius: 6,
                        graphicName: "circle",
                        fillColor: "white",
                        fillOpacity: 0.5,
                        strokeWidth: 1,
                        strokeOpacity: 1,
                        strokeColor: "#02a6cf"
                    },
                    "Line": {
                        strokeWidth: 4,
                        strokeOpacity: 1,
                        strokeColor: "#00ccff"
                    },
                    "Polygon":{
                        strokeWidth: 2,
                        strokeOpacity: 1,
                        strokeColor: "#00ccff"
                    }
                }
            })
        ]
    })
});

var drawLayer = new LMap.Layer.Vector("DrawLayer",{styleMap: drawLayerStyles});
map.addLayers([drawLayer]);
var drawControls = {
    point: new LMap.Control.DrawFeature(drawLayer,
        LMap.Handler.Point
    ),
    line: new LMap.Control.DrawFeature(drawLayer,
            LMap.Handler.Path
    ),
    polygon: new LMap.Control.DrawFeature(drawLayer,
            LMap.Handler.Polygon
    ),
    box: new LMap.Control.DrawFeature(drawLayer,
            LMap.Handler.RegularPolygon, {
                handlerOptions: {
                    sides: 4,
                    irregular: true
                }
            }
    ),
    snapping: new LMap.Control.Snapping({layer: drawLayer}),
    modify: new LMap.Control.ModifyFeature(drawLayer)
};
map.addControl(drawControls['line']);
map.addControl(drawControls['modify']);
map.addControl(drawControls['snapping']);
drawControls['snapping'].activate();
LMap.Event.observe(document, "keydown", function(evt) {
    var handled = false;
    switch (evt.keyCode) {
        case 90: // z
            if (evt.metaKey || evt.ctrlKey) {
            	if(drawControls['line'].active){
	                drawControls['line'].undo();
            	}
	            handled = true;
            }
            break;
        case 89: // y
            if (evt.metaKey || evt.ctrlKey) {
            	if(drawControls['line'].active){
                	drawControls['line'].redo();
                }
                handled = true;
            }
            break;
        case 27: // esc
        	if(drawControls['line'].active){
        		drawControls['line'].cancel();
        	}
            handled = true;
            break;
    }
    if (handled) {
        LMap.Event.stop(evt);
    }
});
var startEdit = function(){
    drawControls['modify'].activate();
}
var stopEdit = function(){
    drawControls['modify'].deactivate();
}

var refreshDrawLayer = function(){
	var layers = map.getLayersByName('DrawLayer');
	if(layers.length > 0){
		layers[0].removeAllFeatures();
		map.removeLayer(layers[0]);
	}
	map.addLayer(drawLayer);
}

var startDrawLine = function(){
	refreshDrawLayer();
    drawControls['line'].activate();
}
var stopDrawLine = function(){
    drawControls['line'].deactivate();
}
drawControls['line'].featureAdded = function(e) {
    $('.add-new-polyline').click();
    startEdit();
    drawControls['modify'].selectFeature(e);
    showAddPopup();
}
$('.add-new-polyline').click(function(e){
	if($(this).hasClass('add-active')){
		$(this).removeClass('add-active');
		$(this).css('background', '#fff');
		stopDrawLine();
	}else{
		$(this).addClass('add-active');
		$(this).css('background', '#cacaca');
		hideLeftLine();
		startDrawLine();
		layer.tips('点击地图画线', '.add-new-polyline', {
		  tips: [3, '#1E9FFF']
		});
	}
});
function hideLeftLine(){
	$(".left-line").animate({left:-258},"slow",function(){
	    	$(this).hide();
	});
}
function showLeftLine(){
	$(".left-line").show().animate({left:0},"slow");
}

$('.close-line-btn, .delet-line').click(function(e){
	hideLeftLine();
	refreshDrawLayer();
})

function showEditPopup(){
	$('.lint-title').html('编辑');
	$('.line-form-content').empty();
	var temp = [];
	temp.push("<li class=\"upload-img\"><span>图片：</span><div class=\"add-list\">");
	var imgNode = "";
	$.each(editPolylineData.images, function(i, v){
		imgNode += "<div class=\"added-img float-L posit-RE\">"+
								"<span class=\"add-img-close posit-AB\"><img src=\"zhuantitu/images/close-1.png\" title=\"删除\"></span>"+
								"<img src=\"" + v.path +"\" >" +
								"<input type=\'hidden\' value=\'" + v.path +"\' name=\'images\'/></div>"
	});
	temp.push(imgNode);
	temp.push("<div class=\"upload upload-btn float-L " + (editPolylineData.images.length > 3?'none':'') + "\"><img src=\"zhuantitu/images/004.png\"></div></div></li>");
	temp.push("<li><label><b class=\"requird-logo\"></b>楼层：</label><select class=\'required\' name=\'thematicPolyline.floorid\' >");
	temp.push("<option value=\'" + zoneid + "00\' >室外</option>");
	$.each(floorInfo, function(i, v){
		temp.push("<option value=\'" + v.floorid + "\' " +(v.floorid == editPolylineData.floorid?'selected':'') + ">" + v.name + "</option>");
	});
	temp.push("</select></li>");
	temp.push("<li><label><b class=\"requird-logo\"></b>线路类型：</label><select class=\'required\' name=\'thematicPolyline.thematicPolylineCategory.categoryid\' onchange=\'generatingColumn(this.value)\'>");
	thematicPolylineCategoryMap.each(function(k,v){
		temp.push("<option value=\'" + v.categoryid + "\' " +(v.categoryid == editPolylineData.categoryid?'selected':'') + ">" + v.name + "</option>");
	});
	temp.push("</select>" +
		"<input type=\'hidden\' value=\'edit\' id=\'submitType\' />"+
		"<input type=\'hidden\' name=\'thematicPolyline.strokewidth\' id=\'strokewidth\' value=\'" + editPolylineData.strokewidth + "\'/>" +
		"<input type=\'hidden\' name=\'thematicPolyline.strokecolor\' id=\'strokecolor\' value=\'" + editPolylineData.strokecolor + "\'/>" +
		"<input type=\'hidden\' name=\'thematicPolyline.icon\' value=\'" + editPolylineData.icon + "\' id=\'pointIcon\'/>"+
		"<input type=\'hidden\' value=\'" + "LINESTRING" + editPolylineData.coordinates.replace('[[','(').replace(']]',')').replace(/\],\[/g,'-').replace(/\[/g,'').replace(/\,/g,' ').replace(/\-/g,',').replace(/\s+/g,"-") +"\' name=\'lonlat\' id=\'lonlat\' />"+
		"<input type=\'hidden\' value=\'" + editPolylineData.polylineid +"\' name=\'thematicPolyline.polylineid\' />"+
		"<input type=\'hidden\' value=\'" + mid + "\' name=\'mid\'/></li>");
	$('.line-form-content').append(temp.join(''));
	generatingColumn(editPolylineData.categoryid,editPolylineData);
	uploadImage();
	showLeftLine();
	delImg();
	
	destroyPolylinePopup();

	var basePolylineLayer = map.getLayersByName('basePolylineLayer');
	if(basePolylineLayer.length > 0){
		basePolylineLayer[0].removeAllFeatures();
		map.removeLayer(basePolylineLayer[0]);
	}
	var floorPolylineLayer = map.getLayersByName('floorPolylineLayer');
	if(floorPolylineLayer.length > 0){
		floorPolylineLayer[0].removeAllFeatures();
		map.removeLayer(floorPolylineLayer[0]);
	}
	refreshDrawLayer();
	//var polylineWkt = "MULTILINESTRING " + editPolylineData.coordinates.replace('[[','((').replace(']]','))').replace(/\],\[/g,'-').replace(/\[/g,'').replace(/\,/g,' ').replace(/\-/g,',');
	var points = []
	var coordinates = eval(editPolylineData.coordinates);
	for(var n = 0; n < coordinates.length; n++){
		points.push(new LMap.Geometry.Point(coordinates[n][0],
			coordinates[n][1]).transform(proj, map.getProjectionObject()));
	}
	var polylineFeaure = new LMap.Feature.Vector(
			new LMap.Geometry.LineString(points),{}
    );
	stopEdit();
	startEdit();
	drawControls['modify'].layer.addFeatures(polylineFeaure);
	drawControls['modify'].selectFeature(polylineFeaure);
	

}
function showAddPopup(){
	$('.lint-title').html('添加');
	$('.line-form-content').empty();
	var temp = [];
	temp.push("<li class=\"upload-img\"><span>图片：</span><div class=\"add-list\"><div class=\"upload upload-btn float-L\"><img src=\"zhuantitu/images/004.png\"></div></div></li>");
	temp.push("<li><label><b class=\"requird-logo\"></b>楼层：</label><select class=\'required\' name=\'thematicPolyline.floorid\' >");
	temp.push("<option value=\'" + zoneid + "00\' >室外</option>");
	$.each(floorInfo, function(i, v){
		temp.push("<option value=\'" + v.floorid + "\'>" + v.name + "</option>");
	});
	temp.push("</select></li>");
	temp.push("<li><label><b class=\"requird-logo\"></b>线路类型：</label><select class=\'required\' name=\'thematicPolyline.thematicPolylineCategory.categoryid\' onchange=\'generatingColumn(this.value)\'>");
	thematicPolylineCategoryMap.each(function(k,v){
		temp.push("<option value=\'" + v.categoryid + "\'>" + v.name + "</option>");
	});
	temp.push("</select>" +
			"<input type=\'hidden\' name=\'thematicPolyline.strokewidth\' id=\'strokewidth\'/>" +
			"<input type=\'hidden\' name=\'thematicPolyline.strokecolor\' id=\'strokecolor\'/>" +
			"<input type=\'hidden\' value=\'" + mid + "\' name=\'mid\'/>" +
			"<input type=\'hidden\' value=\'add\' id=\'submitType\' />" +
			"<input type=\'hidden\' name=\'lonlat\' id=\'lonlat\'/></li>");
	$('.line-form-content').append(temp.join(''));
	generatingColumn(thematicPolylineCategoryMap.keys[0],null);
	showLeftLine();
	uploadImage();
	destroyPolylinePopup();
}
function uploadImage(){
	$(".upload-btn").upload({
        action: "upload_image",
        fileName: "imageFile",
        params: {"dir":"image","system":"zhuantitu"},
        dataType:'JSON',
        accept: ".jpg,.png,.gif,.jpeg,.bmp",
        complete: function(res){
        	var img = "<div class=\"added-img float-L posit-RE\">"+
								"<span class=\"add-img-close posit-AB\"><img src=\"zhuantitu/images/close-1.png\" title=\"删除\"></span>"+
								"<img src=\"" + res.path +"\" >" +
								"<input type=\'hidden\' value=\'" + res.path +"\' name=\'images\'/></div>"
			$(".upload-btn").before(img);
			delImg();
			if($('.added-img').length > 3){
				$('.upload-btn').hide();
			}
        	closeLoadding();
        },
        submit: function () {
            showLoadding();
        }
    });
}
function delImg(){
	$(".add-img-close").click(function(){
		$(this).parent().remove();
		if($('.added-img').length < 4){
			$('.upload-btn').show();
		}
	});
}
function generatingColumn(cid,data){
	$('#strokecolor').val(thematicPolylineCategoryMap.get(cid).strokecolor);
	$('#strokewidth').val(thematicPolylineCategoryMap.get(cid).strokewidth);
	if(categoryColumnDefineMap.contains(cid)){
		createColumn(cid, categoryColumnDefineMap.get(cid),data);
	}else{
		showLoadding();
		$.ajax({
	    type: "get",
	    url:'tableClumnDefine_loadPolylineByCid',
	    async: true,
	    data:{'id':cid},
	    success: function(json) {
		if(json.status){
				var columnDefineMap = new HashMap();
				$.each(json.data, function(i, v){
					columnDefineMap.put(v.columnName,{
						'columnName':v.columnName,
						'columnCnname':v.columnCnname,
						'columnType':v.columnType,
						'isRequired':v.isRequired,
						'isShow':v.isShow
					});
				});
				categoryColumnDefineMap.put(cid,columnDefineMap);
				createColumn(cid,columnDefineMap,data);
				closeLoadding();
			}else{
				outError(json);
			}
	    }
	});
		
	}
}
function createColumn(cid,columnDefineMap,data){
	var temp = [];
	columnDefineMap.each(function(k,v){
		if(v.isShow == 1){
			temp.push("<li><label>" + (v.isRequired == 1?'<b class=\"requird-logo\">*</b>':'') + v.columnCnname+ "：</label>");
			temp.push("<input class=\'" + (v.isRequired == 1?'required':'')+ "\' value=\'" + (data==null?'':data[k]) + "\' type=\'text\' name=\'thematicPolyline." + k + "\' /></li>");
		}
	});
	var children = $('.line-form-content').children();
	for(var k = 3; k < children.length; k++){
		$(children[k]).remove();
	}
	$('.line-form-content').append(temp.join(''));
}
function refreshMap(){
	destroyPolylinePopup();
	showBasePolyline();
	showFloorPolyline($('.right-floor-ctns').find('.floor-pic-chosed').attr('floorid'));
	refreshDrawLayer();
}
function savePolyline(){
	var flag = true;
	$(".required").each(function(index,obj){
		if ($(obj).val()=="") {
			flag = false;
			$(this).focus();
			layer.tips($(this).parent().find("span").text().replace(":","") + '不能为空', this, {
			  tips: [2, '#78BA32']
			});
			return false ;
		}else{
			return true;
		}
	});
	if(flag){
		showLoadding();
		if($("#submitType").val() == 'add'){
			for(var i = 0; i< drawLayer.features[0].geometry.components.length; i++){
				 drawLayer.features[0].geometry.components[i] = drawLayer.features[0].geometry.components[i].transform(map.getProjectionObject(), proj);
			}
			$("#lonlat").val(drawLayer.features[0].geometry.toString().replace(/\s+/g,"-"));
			$.ajax({
	            type: "POST",
	            url:'thematicPolyline_add',
	            data:$('#save-polyline-form').serialize(),
	            async: true,
	            error: function(request) {
					closeLoadding();
					layer.msg('添加失败',function(){
	            			refreshMap();
					});
					hideLeftLine();
	            },
	            success: function(data) {
	            	closeLoadding();
	            	hideLeftLine();
	            	if(data.status){
	            		layer.msg('添加成功', {icon:1,shade:0.3,time: 2000},function(){
	            			refreshMap();
						});
	            	}else{
						outError(data);
					}
	            	
	            }
	        });
		}else{
			for(var t = 0; t< drawControls['modify'].layer.features[0].geometry.components.length; t++){
				 drawControls['modify'].layer.features[0].geometry.components[t] = drawControls['modify'].layer.features[0].geometry.components[t].transform(map.getProjectionObject(), proj);
			}
			$("#lonlat").val(drawControls['modify'].layer.features[0].geometry.toString().replace(/\s+/g,"-"));
			
			$.ajax({
	            type: "POST",
	            url:'thematicPolyline_edit',
	            data:$('#save-polyline-form').serialize(),
	            async: true,
	            error: function(request) {
					closeLoadding();
					hideLeftLine();
	            },
	            success: function(data) {
	            	closeLoadding();
	            	hideLeftLine();
	            	if(data.status){
	            		layer.msg('编辑成功', {icon:1,shade:0.3,time: 2000},function(){
	            			refreshMap();
						});
	            	}else{
						outError(data);
					}
	            	
	            }
	        });
		}
	}
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
				$.ajax({
				    type: "post",
				    url:'../map_search',
				    async: true,
				    data:{"keywords":keywords,"zoneid":zoneid,"v":"2.1"},
				    success: function(d) {
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
							
							tipMarkerPopup = createPopup('tip_marker',new LMap.LonLat(coordinate[0],coordinate[1]).transform(proj, map.getProjectionObject()),-12,0);
							tipMarkerPopup.setContentHTML('<div style="height:60px;"><img class="blank-marker" style="margin-top:24px;" src="zhuantitu/images/blank-marker.png"/></div>');
							map.addPopup(tipMarkerPopup);
							$('.blank-marker').animate({"margin-top":"0px",},200).animate({"margin-top":"24px",},200)  
						         .animate({"margin-top":"12px",},200).animate({"margin-top":"24px",},200)  
						         .animate({"margin-top":"18px",},200).animate({"margin-top":"24px",},200); 
						});
					}
					isClick = true;
					$('.input-clear').removeClass('loading');
				    }
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


