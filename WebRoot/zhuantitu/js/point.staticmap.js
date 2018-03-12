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
$.ajax({
    type: "get",
    url:'cameraIcon_loadIcon',
    async: false,
    success: function(json) {
		if(json.status){
			$.each(json.data, function(i, v){
				videoIconMap.put(v.iconid,{
					"iconid":v.iconid,
					"icon":v.path
				});
			});
		}
	    }
});
$.ajax({
    type: "get",
    url:'thematicPointCategory_loadByMenuid',
    data:{'mid':mid},
    success: function(json) {
		if(json.status){
			$.each(json.data, function(i, v){
				thematicPointCategoryMap.put(v.categoryid,{
					"categoryid":v.categoryid,
					"name":v.name,
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
function showBaseMarker(){
	var layers = map.getLayersByName('baseMarkerLayer');
	if(layers.length > 0){
		layers[0].removeAllFeatures();
		map.removeLayer(layers[0]);
	}
	$.ajax({
	    type: "get",
	    url:'thematicPoint_list',
	    async: false,
	    data:{'mid':mid,'floorid':zoneid + '00'},
	    success: function(d) {
		if(d.status){
			var baseMarkerLayer = new LMap.Layer.Vector('baseMarkerLayer',{
				styleMap:map.iconStyle({'src':'\${icon}','width':28,'height':28,'title':'\${title}'})
			});
			$.each(d.data, function(i, v){
				var coordinate = eval(v.coordinate);
				var markerFeaure = new LMap.Feature.Vector(
					new LMap.Geometry.Point(coordinate[0],coordinate[1]).transform(proj, map.getProjectionObject()),{
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
	    }
	});
	
}
function showFloorMarker(floorid){
	var layers = map.getLayersByName('floorMarkerLayer');
	if(layers.length > 0){
		layers[0].removeAllFeatures();
		map.removeLayer(layers[0]);
	}
	$.ajax({
    type: "get",
    url:'thematicPoint_list',
    async: true,
    data:{'mid':mid,'floorid':floorid},
    success: function(d) {
	if(d.status){
			var floorMarkerLayer = new LMap.Layer.Vector('floorMarkerLayer',{
				styleMap:map.iconStyle({'src':'\${icon}','width':28,'height':28,'title':'\${title}'})
			});
			$.each(d.data, function(i, v){
				var coordinate = eval(v.coordinate);
				var markerFeaure = new LMap.Feature.Vector(
					new LMap.Geometry.Point(coordinate[0],coordinate[1]).transform(proj, map.getProjectionObject()),{
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
function deleteMarker(id){
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
			    url:'thematicPoint_delete',
			    async: true,
			    data:{"id":id},
			    success: function(d) {
				if(d.status){
		    		layer.msg('删除成功', {icon:1,shade:0.3,time: 2000},function(){
            			refreshMap();
						//location.reload();
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
var editMarkerData = null;
function getMarkerContentHTML(data,columnDefineMap){
	var temp = [];
	temp.push("<div><div class=\'xiangxi-box\'>"+
			"<div class=\'xiangxiTop posit-RE\'>"+
			"<div class=\'xiangxi-title\'>" + data.name + "</div><div class=\'revise-box f12\'>");
	if(data.editPermissiont == 1){
		editMarkerData = data;
		temp.push("<span class=\'revise\' onclick=\'showEditMarker()\'>修改</span><span>|</span>");
	}else{
		editMarkerData = null;
	}	
	if(data.deletePermission == 1){
		temp.push("<span class=\'delet\' onclick=\'deleteMarker(" + data.pointid + ")\'>删除</span><span>|</span>");
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
function markerFeatureselected(e){
	var feature = e.feature;
	removeTipMarkerPopup();
	removeAddMarkerLayer();
	
	$.ajax({
	    type: "get",
	    url:'thematicPoint_detail',
	    async: true,
	    data:{'id':feature.attributes.gid},
	    success: function(d) {
		if(d.status){
			if(categoryColumnDefineMap.contains(d.categoryid)){
				markerPopup = createPopup(feature.attributes.gid,feature.geometry.getBounds().getCenterLonLat(),-140,-22);
				markerPopup.setContentHTML(getMarkerContentHTML(d,categoryColumnDefineMap.get(d.categoryid)));
				map.addPopup(markerPopup);
			}else{
				$.ajax({
				    type: "get",
				    url:'tableClumnDefine_loadByCid',
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
									markerPopup = createPopup(feature.attributes.gid,feature.geometry.getBounds().getCenterLonLat(),-140,-22);
									markerPopup.setContentHTML(getMarkerContentHTML(d,columnDefineMap));
									map.addPopup(markerPopup);
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
function markerFeatureunselected(e){
	destroyMarkerPopup();
}
$('.add-new-marker').click(function(e){
	if($(this).hasClass('add-active')){
		$(this).removeClass('add-active');
		$(this).css('background', '#fff');
		removeAddMarkerLayer();
		map.unregisterEvent('click',mapClick);
	}else{
		$(this).addClass('add-active');
		$(this).css('background', '#cacaca');
		map.registerEvent('click',mapClick);
		layer.tips('点击地图选择位置', '.add-new-marker', {
		  tips: [3, '#1E9FFF']
		});
	}
});
function addNewMarker(){
	map.unregisterEvent('click',mapClick);
	map.registerEvent('click',mapClick);
}
var addMarkerPopup = null;
function removeAddMarkerLayer(){
	var layers = map.getLayersByName('addMarkerLayer');
	if(layers.length > 0){
		map.removeLayer(layers[0]);
	}
	if(addMarkerPopup != null){
		map.removePopup(addMarkerPopup);
		addMarkerPopup.destroy();
		addMarkerPopup = null;
	}
}

function mapClick(e){
	removeAddMarkerLayer();
	removeTipMarkerPopup();
	var lonlat = map.getLonLatFromPixel(e.xy);
	var addMarkerLayer = new LMap.Layer.Vector('addMarkerLayer',{
		styleMap:map.iconStyle({'src':'\${icon}','width':31,'height':61,'title':''})
	});
	var markerFeaure = new LMap.Feature.Vector(
			new LMap.Geometry.Point(lonlat.lon,lonlat.lat),{
				icon:'zhuantitu/images/add-marker.png'
			}
		);
	addMarkerLayer.addFeatures(markerFeaure);
	map.addLayer(addMarkerLayer);
	
	
	addMarkerPopup = createPopup(0,lonlat,-100,-61);
	addMarkerPopup.setContentHTML('<div class="add-marker-popup">在此增加点位<span class="add-marker-popup-ok">确定</span></div>');
	map.addPopup(addMarkerPopup);
	$('.add-marker-popup-ok').click(function(){
		var point = new LMap.Geometry.Point(lonlat.lon,lonlat.lat).transform(map.getProjectionObject(),proj);
		showAddPopup(point.x,point.y);
	});
}
function showEditMarker(){
	$('.revamp-title').html('编辑');
	$('.form-content').empty();
	var temp = [];
	temp.push("<div class=\'f14 upload-box\'><span>图片：</span><div>");
	var imgNode = "";
	$.each(editMarkerData.images, function(i, v){
		imgNode += "<div class=\'added-img float-L posit-RE\'>" +
					"<span class=\'add-img-close posit-AB\'>" +
					"<img src=\'zhuantitu/images/del-img.png\' title=\'删除\'>" +
					"</span>" +
					"<img src=\'" + v.path +"\' />" +
					"<input type=\'hidden\' value=\'" + v.path +"\' name=\'images\'/>" +
				"</div>";
	});
	temp.push(imgNode);
	temp.push("<div class=\'upload-btn float-L " + (editMarkerData.images.length > 3?'none':'') + "\'><span class=\'add-img-icon\'>+</span></div></div></div>");
	temp.push("<label class=\'f14\'><span>楼层：</span><select class=\'required\' name=\'thematicPoint.floorid\' >");
	temp.push("<option value=\'" + zoneid + "00\' >室外</option>");
	$.each(floorInfo, function(i, v){
		temp.push("<option value=\'" + v.floorid + "\' " +(v.floorid == editMarkerData.floorid?'selected':'') + ">" + v.name + "</option>");
	});
	temp.push("</select>");
	temp.push("<input type=\'hidden\' value=\'edit\' id=\'submitType\' />");
	temp.push("<input type=\'hidden\' value=\'" + editMarkerData.pointid +"\' name=\'thematicPoint.pointid\' />");
	temp.push("<input type=\'hidden\' value=\'" + editMarkerData.coordinate +"\' name=\'lonlat\' /></label>");
	temp.push("<label class=\'f14\'><span>设备类型：</span><select class=\'required\' name=\'thematicPoint.thematicPointCategory.categoryid\' onchange=\'generatingColumn(this.value)\'>");
	thematicPointCategoryMap.each(function(k,v){
		temp.push("<option value=\'" + v.categoryid + "\' " +(v.categoryid == editMarkerData.categoryid?'selected':'') + ">" + v.name + "</option>");
	});
	temp.push("</select><input type=\'hidden\' name=\'thematicPoint.icon\' value=\'" + editMarkerData.icon + "\' id=\'pointIcon\'/><input type=\'hidden\' value=\'" + mid + "\' name=\'mid\'/></label>");
	
	$('.form-content').append(temp.join(''));
	generatingColumn(editMarkerData.categoryid,editMarkerData);
	$(".revamp").show();
	$(".overShaw").show();
	uploadImage();
	delImg();
}
function showAddPopup(lon,lat){
	$('.revamp-title').html('添加');
	$('.form-content').empty();
	/*var currentFloorid = $('.right-floor-ctns').find('.floor-pic-chosed').attr('floorid');*/
	var temp = [];
	temp.push("<div class=\'f14 upload-box\'><span>图片：</span><div><div class=\'upload-btn float-L\'><span class=\'add-img-icon\'>+</span></div></div></div>");
	temp.push("<label class=\'f14\'><span>楼层：</span><select class=\'required\' name=\'thematicPoint.floorid\' >");
	temp.push("<option value=\'" + zoneid + "00\' >室外</option>");
	$.each(floorInfo, function(i, v){
		/*temp.push("<option value=\'" + v.floorid + "\' " +(v.floorid == currentFloorid?'selected':'') + ">" + v.name + "</option>");*/
		temp.push("<option value=\'" + v.floorid + "\'>" + v.name + "</option>");
	});
	temp.push("</select></label>");
	temp.push("<label class=\'f14\'><span>设备类型：</span><select class=\'required\' name=\'thematicPoint.thematicPointCategory.categoryid\' onchange=\'generatingColumn(this.value)\'>");
	thematicPointCategoryMap.each(function(k,v){
		temp.push("<option value=\'" + v.categoryid + "\'>" + v.name + "</option>");
	});
	temp.push("</select><input type=\'hidden\' name=\'thematicPoint.icon\' id=\'pointIcon\'/>" +
			"<input type=\'hidden\' value=\'" + mid + "\' name=\'mid\'/>" +
			"<input type=\'hidden\' value=\'add\' id=\'submitType\' />" +
			"<input type=\'hidden\' value=\'[" + lon +"," + lat +"]\' name=\'lonlat\' /></label>");
	$('.form-content').append(temp.join(''));
	generatingColumn(thematicPointCategoryMap.keys[0],null);
	$(".revamp").show();
	$(".overShaw").show();
	uploadImage();
}
function uploadImage(){
	$(".upload-btn").upload({
        action: "upload_image",
        fileName: "imageFile",
        params: {"dir":"image","system":"zhuantitu"},
        dataType:'JSON',
        accept: ".jpg,.png,.gif,.jpeg,.bmp",
        complete: function(res){
        	var img = "<div class=\'added-img float-L posit-RE\'>" +
					"<span class=\'add-img-close posit-AB\'>" +
					"<img src=\'zhuantitu/images/del-img.png\' title=\'删除\'>" +
					"</span>" +
					"<img src=\'" + res.path +"\' />" +
					"<input type=\'hidden\' value=\'" + res.path +"\' name=\'images\'/>" +
				"</div>";
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
	$('#pointIcon').val(thematicPointCategoryMap.get(cid).icon);
	if(categoryColumnDefineMap.contains(cid)){
		createColumn(cid, categoryColumnDefineMap.get(cid),data);
	}else{
		showLoadding();
		$.ajax({
		    type: "get",
		    url:'tableClumnDefine_loadByCid',
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
	if(mid == 302){
		if(cid == 4){
			temp.push("<label class=\'f14\'><span>图标：</span><ul class=\'label video-icon\'>");
			
			videoIconMap.each(function(k,v){
				var ischecked = "zhuantitu/images/not-x.png";
				if(data != null){
					if(v.icon == data.icon){
						ischecked = "zhuantitu/images/yes-x.png";
					}
				}
				temp.push("<div class=\'img-box text-CENT posit-RE\'>"+
					"<img src=\'" + v.icon + "\' class=\'qiangji-icon\'>"+
					"<div class=\'check-box posit-AB\'>"+
						"<img class=\'xuanzhong\' src=\'" + ischecked + "\'\'>"+
					"</div>"+
				"</div>");
			});
			temp.push("</ul></label>");
		}
		//添加监控特殊处理
		temp.push("<label class=\'f14\'><span>区域：</span><select class=\'video-location required\' name=\'thematicPoint.thematicLocation.locationid\' >");
		temp.push("</select></label>");
	}
	columnDefineMap.each(function(k,v){
		if(v.isShow == 1){
			temp.push("<label class=\'f14\'><span>" + v.columnCnname+ ":</span>");
			temp.push("<input class=\'" + (v.isRequired == 1?'required':'')+ "\' value=\'" + (data==null?'':data[k]) + "\' type=\'text\' name=\'thematicPoint." + k + "\'>" + (v.isRequired == 1?'<b>*</b>':'')+ "</label>");
		}
	});
	var children = $('.form-content').children();
	for(var k = 3; k < children.length; k++){
		$(children[k]).remove();
	}
	$('.form-content').append(temp.join(''));
	if(mid == 302){
		loadLocation((data != null?data.locationid:''));
	}
	$(".img-box").click(function(){
		$('.video-icon').find(".xuanzhong").attr("src","zhuantitu/images/not-x.png");
		if ($(this).find(".xuanzhong").attr("src")=="zhuantitu/images/not-x.png") {
			$(this).find(".xuanzhong").attr("src","zhuantitu/images/yes-x.png")
			$('#pointIcon').val($(this).find(".qiangji-icon").attr("src"));
		} 
	})
}
function loadLocation(locationid){
	$.ajax({
		type:'get',
		url:'thematicLocation_list?zoneid=' +zoneid,
		dataType:'json',
		success:function(msg){
			if(msg.status){
				var temp = [];
				$.each(msg.data,function(i,i_item){
					temp.push("<option disabled=\'disabled\'>" + i_item.name + "</option>");
					$.each(i_item.child,function(j,j_item){
						temp.push("<option value=\'" + j_item.id + "\' " +(j_item.id == locationid?'selected':'') + ">&nbsp;&nbsp;&nbsp;&nbsp;|- " + j_item.name + "</option>");
					})
				});
				$(temp.join('')).appendTo($('.video-location'));
			}
		}
	});
}
function refreshMap(){
	showBaseMarker();
	showFloorMarker($('.right-floor-ctns').find('.floor-pic-chosed').attr('floorid'));
	removeAddMarkerLayer();
	destroyMarkerPopup();
	$(".revamp").hide();
	$(".overShaw").hide();
}
function savePoint(){
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
			$.ajax({
	            type: "POST",
	            url:'thematicPoint_add',
	            data:$('#save-point-form').serialize(),
	            async: true,
	            error: function(request) {
					closeLoadding();
					$(".revamp").hide();
					$(".overShaw").hide();
	            },
	            success: function(data) {
	            	closeLoadding();
	            	if(data.status){
	            		layer.msg('添加成功', {icon:1,shade:0.3,time: 2000},function(){
	            			refreshMap();
							//location.reload();
						});
	            	}else{
						outError(data);
					}
	            	
	            }
	        });
		}else{
			$.ajax({
	            type: "POST",
	            url:'thematicPoint_edit',
	            data:$('#save-point-form').serialize(),
	            async: true,
	            error: function(request) {
					closeLoadding();
					$(".revamp").hide();
					$(".overShaw").hide();
	            },
	            success: function(data) {
	            	closeLoadding();
	            	if(data.status){
	            		layer.msg('编辑成功', {icon:1,shade:0.3,time: 2000},function(){
	            			refreshMap();
							//location.reload();
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
						removeAddMarkerLayer();
						destroyMarkerPopup();
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


