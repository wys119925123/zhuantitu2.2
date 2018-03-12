//判断浏览器
BrowserType();
		function BrowserType() 
 { 
   var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串 
   var isOpera = userAgent.indexOf("Opera") > -1; //判断是否Opera浏览器 
   var isIE = userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1 && !isOpera; //判断是否IE浏览器 
   var isEdge = userAgent.indexOf("Windows NT 6.1; Trident/7.0;") > -1 && !isIE; //判断是否IE的Edge浏览器 
   var isFF = userAgent.indexOf("Firefox") > -1; //判断是否Firefox浏览器 
   var isSafari = userAgent.indexOf("Safari") > -1 && userAgent.indexOf("Chrome") == -1; //判断是否Safari浏览器 
   var isChrome = userAgent.indexOf("Chrome") > -1 && userAgent.indexOf("Safari") > -1; //判断Chrome浏览器 
 
   if (isIE)  
   { 
      var reIE = new RegExp("MSIE (\\d+\\.\\d+);"); 
      reIE.test(userAgent); 
      window.location.href="http://192.168.4.244:8020/zhuantitu/Browser.html"
    }else if (isFF) { return "FF";
    } else if (isOpera) { return "Opera";
   }else if (isSafari) { return "Safari";
   }else if (isChrome) { return "Chrome";
   }else{
    	window.location.href="http://192.168.4.244:8020/zhuantitu/Browser.html"
    }
  }
 window.onresize=function(){
 	resize();
 }

var popArr=[],showNum=0;



//地图
//更改楼层
$(".floor-more").click(function(e){
	e.stopPropagation();
	$(".more-popup-box").toggle();
	var len = $('.more-popup>li').length;
	var width=$('.more-popup>li').width();
	$(".more-popup").width(width*len);
})

$(".more-popup").children("li").click(function(e){
	e.stopPropagation();
})

$(".pages-list").click(function(e){
	e.stopPropagation();
})

//鼠标滑过搜索数目
$(".result-num").mouseover(function(){
	$(".pages-box").slideDown(300);
})
//点击周围关闭搜索结果列表
$(document).click(function(){
	$(".pages-box").slideUp(300);
	$(".more-popup-box").fadeOut();
	$(".moreFun-box").slideUp(300);
	$(".moreFunAct").removeClass("moreFunAct");
})
//选择搜索结果
//$(".search-result").click(function(){
//	$(".pages-box").slideUp(300);
//})

//选择统计类别
$(".count-tab li").click(function(){
	$(".chart-big-box").hide();
	$(this).addClass("countAct").siblings().removeClass("countAct");
	$(".nameTwo,.nameOne").addClass("nameAct");
	$(".countAct").prev().removeClass("nameAct");
	var index=$(this).index();
	$(".select-location-box").eq(index).show().siblings(".select-location-box").hide();
})

$(".select-location li").click(function(){
	var text=$(this).find("p").text();
	$(".countAct span").text(text);
	$(".chart-big-box").show();
	$(".select-location-box").hide();
//	firstChart.clear();
	rough();
})

$(".count-tab").children("li").mouseenter(function(){
	var tit=$(this).find("span").text();
	$(this).attr("title",tit);
})


//$(".function-btn").click(function(e){
//	if ($(this).hasClass("add-spot")) {
//		$(this).toggleClass("add-spotAct");
//		$(".add-spot-box").toggleClass("none");
//		$(".add-spot-box").fadeToggle(300);
//		if ($(".add-spot-box").hasClass("none")) {
//			$(".add-spot-input").hide();
//			$("#mask").hide();
//		}else{
//			$(".add-spot-input").show();
////			$(".edit-input").show();
//			$("#mask").show();
//		}
//	} else if($(this).hasClass("check-legend")){
//		$(".legend-show-box").fadeToggle(300,function(){
//			if ($(".legend-show-box").is(":visible")) {
//				$("#mask").show();
//			}else{
//				$("#mask").hide();
//			}
//		});
//	} else if($(this).hasClass("statistic")){
//		$(".count-box").fadeToggle(300);
//	} else if($(this).hasClass("moreFun")){
//		$(".moreFun").toggleClass("moreFunAct");
//		e.stopPropagation();
//		$(".moreFun-box").slideToggle(300);
//
//	} else if($(this).hasClass("system-descrit")){
//		$(".system-descrit-box").fadeIn(300,function(){
//			if ($(".system-descrit-box").is(":visible")) {
//				$("#mask").show();
//			}else{
//				$("#mask").hide();
//			}
//			
//		});
//
//	}
//})

$(".moreFun-box li").click(function(e){
	e.stopPropagation();
})

//用户信息框显示隐藏
var flag=true;
$(".person-mesg").hover(function(){
	if (flag) {
		flag=false;
		$(".mesg-list-box").fadeIn(500);
		$(".photo-yuan").fadeIn(500);
		$(".photo").removeClass("photo-act");
	}
},
function(){
	$(".mesg-list-box").fadeOut(500);
	$(".photo-yuan").fadeOut(500,function(){
		flag=true;
	});
	$(".photo").addClass("photo-act");
}
)

//点击新增点位
$(".add-spot").click(function(){
//	$(this).toggleClass("add-spotAct");
//		$(".add-spot-box").toggleClass("none");
		$(".add-spot-box").fadeIn(300);
//		if ($(".add-spot-box").hasClass("none")) {
//			$(".add-spot-input").hide();
//			showNum--;
//			var index=popArr.indexOf(".add-spot-box");
//			popArr.splice(index,index+1);
//			if (popArr.length>1) {
//				$(popArr[popArr.length-2]).hide();
//				$(popArr[popArr.length-1]).show();
//			}else if(popArr.length==1){
//				$(popArr[popArr.length-1]).show();
//			}
//			if (showNum==0) {
//				$("#mask").hide();
//			}
//		}else{
			$(".add-spot-input").show();
			$("#mask").show();
			if (popArr.indexOf(".add-spot-box")<0) {
				showNum++;
			}
			var index=popArr.indexOf(".add-spot-box");
			popArr.splice(index,index+1);
			popArr.push(".add-spot-box");
			if (popArr.length>1) {
				$(popArr[popArr.length-2]).hide();
				$(popArr[popArr.length-1]).show();
			}else if(popArr.length==1){
				$(popArr[popArr.length-1]).show();
			}
//		}
})

//点击关闭新增点位按钮
$(".close-add-box").click(function(){
	$(this).parents(".Popup").fadeOut(300);
	$(".add-spot-box").addClass("none");
	$(".add-spot").removeClass("add-spotAct")
	$(".add-spot-input").hide();
	showNum--;
	var index=popArr.indexOf(".add-spot-box");
	popArr.splice(index,index+1);
	if (popArr.length>1) {
		$(popArr[popArr.length-2]).hide();
		$(popArr[popArr.length-1]).show();
	}else if(popArr.length==1){
		$(popArr[popArr.length-1]).show();
	}
	if (showNum==0) {
		$("#mask").hide();
	}
})

//点击查看图例
$(".check-legend").click(function(){
//	$(".legend-show-box").fadeToggle(300,function(){
//			if ($(".legend-show-box").is(":visible")) {
				$("#mask").show();
				if (popArr.indexOf(".legend-show-box")<0) {
					showNum++;
				}
				var index=popArr.indexOf(".legend-show-box");
				popArr.splice(index,index+1);
				popArr.push(".legend-show-box");
				console.log(popArr);
				if (popArr.length>1) {
					$(popArr[popArr.length-2]).hide();
					$(popArr[popArr.length-1]).show();
				}else if(popArr.length==1){
					$(popArr[popArr.length-1]).show();
				}
//			}else{
//				showNum--;
//				if (showNum==0) {
//					$("#mask").hide();
//				}
//			}
//		});
})

//点击关闭查看图例
$(".close-legend-box").click(function(){
	$(this).parents(".Popup").fadeOut(300);
	showNum--;
	var index=popArr.indexOf(".legend-show-box");
			popArr.splice(index,index+1);
			if (popArr.length>1) {
				$(popArr[popArr.length-2]).hide();
				$(popArr[popArr.length-1]).show();
			}else if(popArr.length==1){
				$(popArr[popArr.length-1]).show();
			}
	if (showNum==0) {
		$("#mask").hide();
	}
})

//点击打开统计详情
$(".statistic").click(function(){
	$(".count-box").fadeToggle(300);
})

////关闭统计详情统计
$(".close-detailChart-box").click(function(){
//	$(".detail-canvas-box").mCustomScrollbar("scrollTo","left");
	$(".detail-chart-box").fadeOut(300);
	showNum--;
	var index=popArr.indexOf(".detail-chart-box");
			popArr.splice(index,index+1);
			if (popArr.length>1) {
				$(popArr[popArr.length-2]).hide();
				$(popArr[popArr.length-1]).show();
			}else if(popArr.length==1){
				$(popArr[popArr.length-1]).show();
			}
	if (showNum==0) {
		$("#mask").hide();
	}
})

//系统说明
$(".system-descrit").click(function(){
	$(".system-descrit-box").fadeIn(300);
//			if ($(".system-descrit-box").is(":visible")) {
				$("#mask").show();
				if (popArr.indexOf(".system-descrit-box")<0) {
					showNum++;
				}
				var index=popArr.indexOf(".system-descrit-box");
				popArr.splice(index,index+1);
				popArr.push(".system-descrit-box");
				console.log(popArr);
				if (popArr.length>1) {
					$(popArr[popArr.length-2]).hide();
					$(popArr[popArr.length-1]).show();
				}else if(popArr.length==1){
					$(popArr[popArr.length-1]).show();
				}
//			}else{
//				showNum--;
//				if (showNum==0) {
//					$("#mask").hide();
//				}
//			}
			
//		});
})

//点击关闭系统说明框
$(".close-systemDescrit-btn").click(function(){
	$(".system-descrit-box").fadeOut(300);
	showNum--;
	var index=popArr.indexOf(".system-descrit-box");
			popArr.splice(index,index+1);
			if (popArr.length>1) {
				$(popArr[popArr.length-2]).hide();
				$(popArr[popArr.length-1]).show();
			}else if(popArr.length==1){
				$(popArr[popArr.length-1]).show();
			}
	if (showNum==0) {
		$("#mask").hide();
	}
})

//查看更多功能
$(".moreFun").click(function(e){
	$(".moreFun").toggleClass("moreFunAct");
	e.stopPropagation();
	$(".moreFun-box").slideToggle(300);
})

//打开统计详情
function detailA(){
	$(".check-detail").click(function(){
//		$(".detail-chart-box").fadeIn(300);
		if (popArr.indexOf(".detail-chart-box")<0) {
					showNum++;
				}
				var index=popArr.indexOf(".detail-chart-box");
				popArr.splice(index,index+1);
				popArr.push(".detail-chart-box");
				console.log(popArr);
				if (popArr.length>1) {
					$(popArr[popArr.length-2]).hide();
					$(popArr[popArr.length-1]).show();
				}else if(popArr.length==1){
					$(popArr[popArr.length-1]).show();
				}
		$("#mask").show();
//		detailChart.clear();
		detail();
	})
}



//图例是否选中
$(".checkbox").click(function(){
	$(this).toggleClass("checked");
})


//搜索功能
$("#search_btn").click(function(e){
	e.stopPropagation();
	search();
})
$("#search").bind('keypress',function(e){
            if(e.keyCode == "13"){
            	search();
            }
        });
function search(){
	if ($("#search").val()!='') {
		$("#search_del").show();
		var text=$("#search").val();
		$(".pages-box").show();
		$(".Popup").hide();
		$(".search-result-box").show();
	}else{
		$("#search_del").hide();
		$(".search-result-box").hide();
	}
}

$('#search').keyup(function(){
	if ($("#search").val()!='') {
		$("#search_del").show();
	}else{
		$("#search_del").hide();
	}
})

$("#search_del").click(function(){
	$("#search").val('');
	$("#search_del").hide();
	$(".search-result-box").hide();
})

$(".search-result-box").click(function(e){
	e.stopPropagation();
})

//楼层切换
$(".right-floor-ctns li").click(function(){
	$(".right-floor-ctns li").removeClass("floorAct");
	$(".floor-line").show();
	$(this).addClass("floorAct").prev().find(".floor-line").hide();
})

//切换校区
var flagCampus=true;
$(".campus-tab").hover(function(){
	if (flagCampus) {
		flagCampus=false;
		$(".campus-box").fadeIn(500);
//		$(".photo-yuan").fadeIn(500);
//		$(".photo").removeClass("photo-act");
	}
},
function(){
//	$(".campus-tab").fadeOut(500);
	$(".campus-box").fadeOut(500,function(){
		flagCampus=true;
	});
//	$(".photo").addClass("photo-act");
}
)

$("#containTable tr").click(function(){
	$(".spot-detail").hide();
	$(".equipment-list-box").fadeIn(300);
})

//删除
$(".clear-btn").click(function(){
	_this=$(this)
		layer.confirm('你确定要删除吗？', {
			shade:[0.5,'#000'],
			shadeClose: true, //点击遮罩关闭
			title:'1号教学楼安保点位',
			closeBtn:[1],//右上角关闭按钮
			btn: ['确认', '取消'],
			icon: 3,
			fadeIn: 1000,
			yes: function(index, layero){ //或者使用btn1
			    //按钮【按钮一】的回调
			    _this.parents('.bao2').remove();
			    _this.parents('tr').remove();
			    // alert(2)
			    layer.close(index);//关闭弹出层
			},cancel: function(index){ //或者使用btn2
			    //按钮【按钮二】的回调
			}
		});
		return false;
})


$(".back-btn").click(function(){
	$(this).parents(".back-parent").hide().prev(".back-parent").show();
})

$(".equipment-list li").click(function(){
	$(".equipment-list-box").hide();
	$(".specific-equipment").fadeIn(300);
})

$(".edit-room-btn").click(function(){
	$(".add-spot-box").fadeIn(300);
	$(".edit-room-input").show();
	showNum++;
	$("#mask").show();
})

$(".edit-equipment-btn,.noMesg-edit").click(function(){
	$(".add-spot-box").fadeIn(300);
	$(".edit-equipment-input").show();
	showNum++;
	$("#mask").show();
})

//编辑、添加点位，加图片
$(".upload").click(function(){
	$(this).before('<div class="added-img float-L posit-RE"><span class="add-img-close posit-AB"><img src="img/close-1.png" alt=""></span><img src="img/add.png" alt=""></div>');
	$(".add-img-close").unbind("click");
	delImg();
})

$(".jurisdiction").click(function(){
	$(".back-parent").first().fadeIn(300);
})

$(".add-spot-save").click(function(){
	$(".add-spot-box").fadeOut(300);
	$(".edit-input").hide();
	showNum--;
	if (showNum==0) {
		$("#mask").hide();
	}
})

$("#legendSubmit").click(function(){
	$(".legend-show-box").fadeOut(300);
	$("#legendMask").hide();
})

//删除图片
delImg();
function delImg(){
	$(".add-img-close").click(function(){
		$(this).parent(".added-img").remove();
	})
}


//引导
var guideLeng=$(".guide").length;
$(".guide").click(function(){
	var step=$(this).hide().attr("step");
	if(step<guideLeng){
		$(this).next(".guide").show();
	}else{
		$(".guide").hide();
		$("#maskShow").hide();
	}
})