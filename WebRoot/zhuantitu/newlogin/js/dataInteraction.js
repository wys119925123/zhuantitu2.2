//屏幕的高度
var WH=$(window).height();
resize();
function resize(){
	WW=$(window).width();
	WH=$(window).height();
	$(".first-contain-mesg").css({maxHeight:WH-443});
	$(".equipment-list").css({maxHeight:WH-296});
	$(".second-contain-mesg").css({maxHeight:WH-113});
	$(".chart-big-box").css({maxHeight:WH-260});
	$(".result-list").show().css({maxHeight:WH-314});
	$(".chart-box-first").css({maxHeight:WH-300});
	$(".select-location").css({maxHeight:WH-314});
//	document.getElementsByTagName('body')[0].style.zoom=WW/1920;
}

var firstChart;


rough();

function rough(){
	var nameArr=[],numArr=[],barNameArr=[];
	var cList = ['#F64C4C','#F6884C','#F6E04C','#4A97FF','#67F25C',
                 '#FE8463','#9BCA63','#FAD860','#F3A43B','#60C0DD',
                 '#D7504B','#C6E579','#F4E001','#F0805A','#26C0C0'
                ];
    var cList2 = [];
    var app='';
	$.ajax({
		type:"get",
		url:"json/building.json",
		async:true,
		success:function(data){
			if (data.status=="success") {
				var DATA=data.data;
				$.each(DATA, function(i,item) {
					barNameArr.push(item.name+"/"+item.unit);
					numArr.push(item.Number);
					nameArr.push(item.name);
					cList2.push(cList[i%15]);
					app+='<li><div class="color" style="background: '+cList[i%15]+';"></div><span class="annotation-text">'+item.name+'</span><a href="javascript:void(0);" class="detail-a">详情</a></li>'
				});
				$(".annotation-list").html(app);
				detailA();
//				roughChars();
				zichan();
			}
		}
	});
	
	

//function roughChars(){
//if (barNameArr.length>5) {
////	alert("qin");
//	$("#firstChart").width(57*barNameArr.length);
//	$("#qinchengjie").mCustomScrollbar({
//
//		mouseWheel:false,
//
//		horizontalScroll:true,
//
//		scrollButtons:{
//
//		enable:true
//
//	},
//
//	theme:"dark-thin"
//
//});
//}
//if (WH<760) {
//	$(".chart").height(WH-404);
//}
//
//				
//	
//firstChart = echarts.init(document.getElementById('firstChart'));
//// 指定图表的配置项和数据
//
//optionFirst = {
//  color: ['#3398DB'],
//  tooltip : {
//      trigger: 'axis',
//      axisPointer : {            // 坐标轴指示器，坐标轴触发有效
//          type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
//      }
//  },
//  grid: {
//      left: '0%',
//      right: '0%',
//      top:'30',
//      bottom: '10',
//      containLabel: true
//  },
//  xAxis : [
//      {
//          type : 'category',
////          data : ['人员数量/名', '房间数量/间', '房间面积/㎡', '设备数量/件', '设备价值/元','人员数量/名', '房间数量/间', '房间面积/㎡', '设备数量/件', '设备价值/元','人员数量/名', '房间数量/间', '房间面积/㎡', '设备数量/件', '设备价值/元','人员数量/名', '房间数量/间', '房间面积/㎡', '设备数量/件', '设备价值/元'],
//          data : barNameArr,
//          nameLocation:'end',
//          axisTick: {
//              alignWithLabel: true
//          },
//          axisLine:{
//              lineStyle:{
//                  color:'#fff',
//                  width:1,//这里是为了突出显示加上的，可以去掉
//              }
//          },
//          axisLabel:{
//            textStyle:{
//               color:"#fff", //刻度颜色
//               fontSize:14,  //刻度大小
//          	 align:'center'
//            },
//            formatter : function(params){
//                                  var newParamsName = "";
//                                  var paramsNameNumber = params.length;
//                                  var provideNumber = 2;
//                                  var rowNumber = Math.ceil(paramsNameNumber / provideNumber);
//                                  if (paramsNameNumber > provideNumber) {
//                                      for (var p = 0; p < rowNumber; p++) {
//                                          var tempStr = "";
//                                          var start = p * provideNumber;
//                                          var end = start + provideNumber;
//                                          if (p == rowNumber - 1) {
//                                              tempStr = params.substring(start, paramsNameNumber);
//                                          } else {
//                                              tempStr = params.substring(start, end) + "\n";
//                                          }
//                                          newParamsName += tempStr;
//                                      }
//
//                                  } else {
//                                      newParamsName = params;
//                                  }
//                                  return newParamsName
//                              }
//        }
//      }
//  ],
//  yAxis : [
//      {
//          type : 'value',
//          axisLine:{
//              lineStyle:{
//                  color:'#fff',
//                  width:1,//这里是为了突出显示加上的，可以去掉
//              }
//          }
//      }
//  ],
//  series : [
//      {
//          name:'直接访问',
//          type:'bar',
//          barWidth: '30px',
//          data:numArr,
//          itemStyle: {
//		        normal: {
//		            color: function(params) {
//                          var colorList = cList2;
//                          return colorList[params.dataIndex]
//                      }
//		        }
//		    }
//      }
//  ]
//};
//				
//// 使用刚指定的配置项和数据显示图表。
//firstChart.setOption(optionFirst);	
//}


}


function zichan(){
	
	
//	$(".chart-big-box").height()
	$(".chart-box-first").mCustomScrollbar({
	
				horizontalScroll:false,
		
				scrollButtons:{
		
				enable:true
		
			},
		
			theme:"dark-thin"
		
		});
}

//关闭统计详情统计
$(".close-detailChart-box").click(function(){
//	$(".detail-canvas-box").mCustomScrollbar("scrollTo","left");
	$(".detail-chart-box").fadeOut(300);
//	detailChart.clear();
	$("#mask").hide();
	
})

//统计详情
var detailChart;
function detail(){
	var barNameArr=[],numArr=[],cList2 = [];
	var cList = ['#F9884A','#22B2EF'];
	$.ajax({
		type:"get",
		url:"json/buildingDetail.json",
		async:true,
		success:function(data){
			if (data.status=="success") {
				var DATA=data.data;
				$.each(DATA, function(i,item) {
					barNameArr.push(item.name+"("+item.unit+")");
					numArr.push(item.Number);
					cList2.push(cList[i%2]);
				});
				detailCharts();
			}
		}
	});


// 指定图表的配置项和数据
function detailCharts(){
//	alert("cheng")
	if (barNameArr.length>1) {
		$("#detailChart").width(68*barNameArr.length);
//		$(".detail-canvas-box").mCustomScrollbar({
//	
//				mouseWheel:false,
//		
//				horizontalScroll:true,
//		
//				scrollButtons:{
//		
//				enable:true
//		
//			},
//		
//			theme:"dark-thin"
//		
//		});
	}
	
	detailChart = echarts.init(document.getElementById('detailChart'));
	optionDetail = {
	    color: ['#3398DB'],
	    tooltip : {
	        trigger: 'axis',
	        axisPointer : {            
	            type : 'shadow'
	        }
	    },
	    grid: {
	        left: '5px',
	        right: '20px',
	        bottom: '5',
	        top: '45px',
	        containLabel: true
	    },
	    xAxis : [
	        {
	            type : 'category',
	            data : barNameArr,
	            axisTick: {
	                alignWithLabel: true
	            },
	            axisLine:{
	                lineStyle:{
	                    color:'#ADADAD',
	                    width:1,//这里是为了突出显示加上的，可以去掉
	                }
	            },
	            axisLabel:{
		            textStyle:{
		              	color:"#484848",
		                fontSize:12,  //刻度大小
		            	align:'center'
		            },
	            	formatter : function(params){
	                    var newParamsName = "";
	                    var paramsNameNumber = params.length;
	                    var provideNumber = 4;
	                    var rowNumber = Math.ceil(paramsNameNumber / provideNumber);
	                    if (paramsNameNumber > provideNumber) {
	                        for (var p = 0; p < rowNumber; p++) {
	                            var tempStr = "";
	                            var start = p * provideNumber;
	                            var end = start + provideNumber;
	                            if (p == rowNumber - 1) {
	                                tempStr = params.substring(start, paramsNameNumber);
	                            } else {
	                                tempStr = params.substring(start, end) + "\n";
	                            }
	                                newParamsName += tempStr;
	                        }
	
	                    } else {
	                            newParamsName = params;
	                    }
	                    return newParamsName
	                }
	            }
	        }
	    ],
	    yAxis : [
	        {
	        	name : '(青山绿水)',
	            type : 'value'.arguments,
	            axisLine:{
	                lineStyle:{
	                    color:'#ADADAD',
	                    width:1,//这里是为了突出显示加上的，可以去掉
	                }
	            }
	        }
	    ],
	    series : [
	        {
	            name:'直接访问',
	            type:'bar',
	            barWidth: '30px',
				data:numArr,
	            itemStyle: {
			        normal: {
			            color: function(params) {
	                            var colorList = cList2;
	                            return colorList[params.dataIndex]
	                        }
			        }
			    },
			    barGap:10
	        }
	    ]
	};
					
	// 使用刚指定的配置项和数据显示图表。
	detailChart.setOption(optionDetail);
}

}



