<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
		<title>视频调阅专题图</title>
		<link rel="stylesheet" href="<%=path %>/zhuantitu/video/css/common.css" />
		<link rel="stylesheet" href="<%=path %>/zhuantitu/video/css/shipin.css" />
		<script src="<%=path %>/zhuantitu/video/js/jquery.min.js"></script>
		<script type="text/javascript" src="<%=path%>/zhuantitu/layer/layer.js" ></script>
		<script type="text/javascript">
		var index = parent.layer.getFrameIndex(window.name);
		parent.layer.end =function(){}
		var videos = ${videos};
		var zoneid = "${zoneid}";
		var mid = "${mid}";
		var floorConfig = null;
		var api = "${sysconfigMap['xygisUrl']}";
		$.ajax({
			type:'get',
			url:api+'map_getFloorConfig?zoneid=' +zoneid,
			dataType:'json',
			success:function(msg){
				if(msg.status){
					floorConfig = msg;
				}
			}
		});
		
		
		$(function(){
	
			start_add(videos);
			function start_add(data){
			    var len=data.data.length;
			    var num=0;
			    if(len<2){
			    	tab(1);
			    	$(".tab1").addClass("tab1-act");
					$(".tab4").removeClass("tab4-act");
					$(".tab9").removeClass("tab9-act");
					$(".tab16").removeClass("tab16-act");
			    }else if(len<5){
			    	tab(4);
			    	$(".tab4").addClass("tab4-act");
					$(".tab1").removeClass("tab1-act");
					$(".tab9").removeClass("tab9-act");
					$(".tab16").removeClass("tab16-act");
			    }else if(len<10){
			    	tab(9);
			    	$(".tab9").addClass("tab9-act");
					$(".tab1").removeClass("tab1-act");
					$(".tab4").removeClass("tab4-act");
					$(".tab16").removeClass("tab16-act");
			    }else if(len<16){
			    	tab(16);
			    	$(".tab16").addClass("tab16-act");
					$(".tab1").removeClass("tab1-act");
					$(".tab9").removeClass("tab9-act");
					$(".tab16").removeClass("tab16-act");
			    }
			    $(data.data).each(function(s,s_item){
			    	
			    	var videoObj = [];
			    	videoObj.push("<object type=\'application/x-shockwave-flash\'  ");
					videoObj.push("name=\'SampleMediaPlayback\' align=\'middle\' data=\'zhuantitu/video/swfs/SampleMediaPlayback.swf\' ");
					//videoObj.push("width=\'100%\' height=\'100%\'>");
					videoObj.push("<param name=\'bgcolor\' value=\'#000000\'>");
					videoObj.push("<param name=\'allowscriptaccess\' value=\'sameDomain\'>");
					videoObj.push("<param name=\'allowfullscreen\' value=\'true\'>");
					videoObj.push("<param name=\'flashvars\' value=\'src=" + s_item.videoSource +"&amp;streamType=vod&amp;autoPlay=true&amp;controlBarAutoHide=true&amp;controlBarPosition=bottom\'>");
					videoObj.push("</object>");
			    	
			    	
			    	var video_box=$(".stop").first();
			    	$(video_box).children(".object-box").append(videoObj.join(''));
			    	//var id= $(video_box).find("object").attr("id");
				    //var oBject=document.getElementById(id);
				    $(video_box).children(".add_video").hide();
					$(video_box).children(".object-box").show();
					$(video_box).children(".rush-status").show();
			    	$(video_box).find("object").width("100%").height("100%");
			    	$(video_box).find(".video-address").text(s_item.name);
					$(video_box).children(".rush-status").hide();
					$(video_box).children(".error-status").hide();
					$(video_box).addClass("play").removeClass("stop");
			    })
			}
		
		})
		

		</script>
	</head>
	<body>
		<div class="shipin-box">
		
			
			<!--右侧视频播放-->
			<div class="right-video" style="padding-left: 0;">
				<div class="video-show">
					
					<div class="video-box">
						<div class="video_playback">
							<div class="video block stop" index="1">
								<div class="object-box" id="object_box1">
									<div class="object-top"><span class="video-address"></span><div class="video_close"></div></div>
								</div>
								<div class="add_video"><p class="video-cont">点击添加视频</p></div>
								<div class="error-status"><p class="video-cont">网络错误，请点击重试</p></div>
								<div class="rush-status"></div>
							</div>
							
							<div class="video none " index="2">
								<div class="object-box">
									<div class="object-top"><span class="video-address"></span><div class="video_close"></div></div>
									
								</div>
								<div class="add_video"><p class="video-cont">点击添加视频</p></div>
								<div class="error-status"><p class="video-cont">网络错误，请点击重试</p></div>
								<div class="rush-status"></div>
							</div>
							
							<div class="video none " index="3">
								<div class="object-box">
									<div class="object-top"><span class="video-address"></span><div class="video_close"></div></div>
									
								</div>
								<div class="add_video"><p class="video-cont">点击添加视频</p></div>
								<div class="error-status"><p class="video-cont">网络错误，请点击重试</p></div>
								<div class="rush-status"></div>
							</div>
							
							<div class="video none " index="4">
								<div class="object-box">
									<div class="object-top"><span class="video-address"></span><div class="video_close"></div></div>
									
								</div>
								<div class="add_video"><p class="video-cont">点击添加视频</p></div>
								<div class="error-status"><p class="video-cont">网络错误，请点击重试</p></div>
								<div class="rush-status"></div>
							</div>
							
							<div class="video none " index="5">
								<div class="object-box">
									<div class="object-top"><span class="video-address"></span><div class="video_close"></div></div>
									<object type="application/x-shockwave-flash" id="Voide_ActiveQtServer5" 
										name="SampleMediaPlayback" align="middle" data="zhuantitu/video/swfs/SampleMediaPlayback.swf" 
										width="100%" height="100%">
										<param name="quality" value="high">
										<param name="bgcolor" value="#000000">
										<param name="allowscriptaccess" value="sameDomain">
										<param name="allowfullscreen" value="true">
										<param name="flashvars" value="src=rtmp://192.168.4.200:1935/live/live1&amp;streamType=vod&amp;autoPlay=true&amp;controlBarAutoHide=true&amp;controlBarPosition=bottom">
									</object>
								</div>
								<div class="add_video"><p class="video-cont">点击添加视频</p></div>
								<div class="error-status"><p class="video-cont">网络错误，请点击重试</p></div>
								<div class="rush-status"></div>
							</div>
							
							<div class="video none " index="6">
								<div class="object-box">
									<div class="object-top"><span class="video-address"></span><div class="video_close"></div></div>
								</div>
								<div class="add_video"><p class="video-cont">点击添加视频</p></div>
								<div class="error-status"><p class="video-cont">网络错误，请点击重试</p></div>
								<div class="rush-status"></div>
							</div>
							
							<div class="video none " index="7">
								<div class="object-box">
									<div class="object-top"><span class="video-address"></span><div class="video_close"></div></div>
								</div>
								<div class="add_video"><p class="video-cont">点击添加视频</p></div>
								<div class="error-status"><p class="video-cont">网络错误，请点击重试</p></div>
								<div class="rush-status"></div>
							</div>
							
							<div class="video none " index="8">
								<div class="object-box">
									<div class="object-top"><span class="video-address"></span><div class="video_close"></div></div>
								</div>
								<div class="add_video"><p class="video-cont">点击添加视频</p></div>
								<div class="error-status"><p class="video-cont">网络错误，请点击重试</p></div>
								<div class="rush-status"></div>
							</div>
							
							<div class="video none " index="9">
								<div class="object-box">
									<div class="object-top"><span class="video-address"></span><div class="video_close"></div></div>
								</div>
								<div class="add_video"><p class="video-cont">点击添加视频</p></div>
								<div class="error-status"><p class="video-cont">网络错误，请点击重试</p></div>
								<div class="rush-status"></div>
							</div>
							
							<div class="video none " index="10">
								<div class="object-box">
									<div class="object-top"><span class="video-address"></span><div class="video_close"></div></div>
								</div>
								<div class="add_video"><p class="video-cont">点击添加视频</p></div>
								<div class="error-status"><p class="video-cont">网络错误，请点击重试</p></div>
								<div class="rush-status"></div>
							</div>
							
							<div class="video none " index="11">
								<div class="object-box">
									<div class="object-top"><span class="video-address"></span><div class="video_close"></div></div>
								</div>
								<div class="add_video"><p class="video-cont">点击添加视频</p></div>
								<div class="error-status"><p class="video-cont">网络错误，请点击重试</p></div>
								<div class="rush-status"></div>
							</div>
							
							<div class="video none " index="12">
								<div class="object-box">
									<div class="object-top"><span class="video-address"></span><div class="video_close"></div></div>
								</div>
								<div class="add_video"><p class="video-cont">点击添加视频</p></div>
								<div class="error-status"><p class="video-cont">网络错误，请点击重试</p></div>
								<div class="rush-status"></div>
							</div>
							
							<div class="video none " index="13">
								<div class="object-box">
									<div class="object-top"><span class="video-address"></span><div class="video_close"></div></div>
								</div>
								<div class="add_video"><p class="video-cont">点击添加视频</p></div>
								<div class="error-status"><p class="video-cont">网络错误，请点击重试</p></div>
								<div class="rush-status"></div>
							</div>
							
							<div class="video none " index="14">
								<div class="object-box">
									<div class="object-top"><span class="video-address"></span><div class="video_close"></div></div>
								</div>
								<div class="add_video"><p class="video-cont">点击添加视频</p></div>
								<div class="error-status"><p class="video-cont">网络错误，请点击重试</p></div>
								<div class="rush-status"></div>
							</div>
							
							<div class="video none " index="15">
								<div class="object-box">
									<div class="object-top"><span class="video-address"></span><div class="video_close"></div></div>
								</div>
								<div class="add_video"><p class="video-cont">点击添加视频</p></div>
								<div class="error-status"><p class="video-cont">网络错误，请点击重试</p></div>
								<div class="rush-status"></div>
							</div>
							
							<div class="video none " index="16">
								<div class="object-box">
									<div class="object-top"><span class="video-address"></span><div class="video_close"></div></div>
								</div>
								<div class="add_video"><p class="video-cont">点击添加视频</p></div>
								<div class="error-status"><p class="video-cont">网络错误，请点击重试</p></div>
								<div class="rush-status"></div>
							</div>
							
						</div>
						<div class="video-bottom">
							<div class="video-bottom-btn">
								<div class="video-num-tab tab1 tab1-act"></div>
								<div class="video-num-tab tab4"></div>
								<div class="video-num-tab tab9"></div>
								<div class="video-num-tab tab16"></div>
							</div>
						</div>
					</div>
				</div>
				
				
			</div>
			
			
		</div>
		<div class="overShaw"><iframe  src="about:blank" frameBorder="0" marginHeight="0" marginWidth="0" style="position: absolute; visibility: inherit; top: 0px; left: 0px; width: 100%; height: 100%; z-index: -1; filter: alpha(opacity = 0);"></iframe></div>
			<div class="revamp posit-AB">
			          <div class="revamp-top f18 text-CENT">选择设备<div class="select-close"></div></div>
			            <ul class="form posit-RE">
			              <div class="form-content posit-AB">
			                <div class="nav">
			                  <ul class="nav-ul">
			                    
			                  </ul>
			                </div>
			              </div>
			            </ul>
			            <hr style="height:1px;background:#EBEAEA;margin:15px 0;">
			            <input class="sure-btn" type="button" value="确 定">
			            <iframe  src="about:blank" frameBorder="0" marginHeight="0" marginWidth="0" style="position: absolute; visibility: inherit; top: 0px; left: 0px; width: 100%; height: 100%; z-index: -1; filter: alpha(opacity = 0);"></iframe>
			</div><%--
		<script src="<%=path %>/zhuantitu/video/js/video.js"></script>
	--%>
	<script type="text/javascript">
		var flag=false;
		$(".video-num-tab").click(function(){
			flag=false;
			if($(this).hasClass("tab1")){
				if (tab(1)) {
					$(this).addClass("tab1-act");
					$(".tab4").removeClass("tab4-act");
					$(".tab9").removeClass("tab9-act");
					$(".tab16").removeClass("tab16-act");
				}
			}else if($(this).hasClass("tab4")){
				if (tab(4)) {
					$(this).addClass("tab4-act");
					$(".tab1").removeClass("tab1-act");
					$(".tab9").removeClass("tab9-act");
					$(".tab16").removeClass("tab16-act");
				}
			}else if($(this).hasClass("tab9")){
				if (tab(9)) {
					$(this).addClass("tab9-act");
					$(".tab1").removeClass("tab1-act");
					$(".tab4").removeClass("tab4-act");
					$(".tab16").removeClass("tab16-act");
				}
			}else if($(this).hasClass("tab16")){
				if (tab(16)) {
					$(this).addClass("tab16-act");
					$(".tab1").removeClass("tab1-act");
					$(".tab4").removeClass("tab4-act");
					$(".tab9").removeClass("tab9-act");
				}
			}
		})
		tab(1);
		function tab(num){
			var cha=num-$(".block").length;
			if(num>=$(".block").length){
				for (var i = 0; i < cha; i++) {
					console.log(i);
					$(".none").first().removeClass("none").addClass("block").addClass("stop");
				}
				var n=Math.sqrt(num);
				n=100/n;
				$(".block").css({width:n+"%"});
				$(".block").css({height:n+"%"});
				flag=true;
			}else{
				var lang=$(".block").length;
				if (num>=$(".play").length) {
					for (var j = 0; j < lang-num; j++) {
						$(".stop").last().removeClass("block").addClass("none").removeClass("stop");
						
					}
					var n=Math.sqrt(num);
					n=100/n;
					$(".block").css({width:n+"%"});
					$(".block").css({height:n+"%"});
					flag=true;
				}
			}
			return flag;
		}
		
		$(".video_close").click(function(){
			//var id=$(this).parents(".object-box").find("object").attr("id");
			//var obj=document.getElementById(id);
			$(this).parent().parent().find('object').remove();
			$(this).parents(".video").removeClass("border").addClass("stop").removeClass("play").find(".object-box").hide().next(".add_video").show();
		});
		
		
		
		
		function load(){
			$(".checkbox-parent").parent().unbind("click");
			$(".checkbox-parent").parent().click(function(){
				$(this).next("ul").slideToggle();
				var me=$(this).find("i");
				if ($(me).hasClass("checkbox-parent1")) {
					$(me).toggleClass("ch-p-1");
				}else if($(me).hasClass("checkbox-parent2")){
					$(me).toggleClass("ch-p-2");
					if($(this).parent().attr('rel') == 12){
						var f1 = $(this).parent().find("ul");
						$(f1).empty();
						$.ajax({
							type:'get',
							url:'thematicLocation_loadPointByLocation?mid=' +mid + "&locationid="+$(this).parent().attr('rel'),
							dataType:'json',
							success:function(msg){
								if(msg.status){
									$.each(msg.data,function(a,point){
										var html4 ='<li><a href="#" class="f16"><span class="input-r-box"><input class="radio" vd_name="'+point.name+'" videoSource="' + point.videoSource+'" vd_type="'+point.type+'" ip="'+point.ip+'" nvr="'+point.nvr+'" usernume="'+point.videousername+'" video_mesg="" pass="'+point.videopassword+'" channel="'+point.channel+'" type="radio" name="camera"></span><span class="neirong">'+point.name+'</span></a></li>';
										$(html4).appendTo($(f1));
									})
									$(".neirong").parent("a").unbind("click");
									$(".neirong").parent("a").click(function(){
										$(".input-r-box").removeClass("input-active").find("input").attr("checked",false);
										$(this).find(".input-r-box").addClass("input-active").find("input").attr("checked",true);
									})
								}
							}
						})
					}
				}else if($(me).hasClass("checkbox-parent3")){
					$(me).toggleClass("ch-p-3");
					if($(me).hasClass("ch-p-3")){
						var f = $(this).parent().find("ul");
						$(f).empty();
						$.ajax({
							type:'get',
							url:'thematicLocation_loadPointByLocation?mid=' +mid + "&floorid=" + $(this).parent().attr('rel') + "&locationid="+$(this).parent().parent().parent().attr('rel'),
							dataType:'json',
							success:function(msg){
								if(msg.status){
									$.each(msg.data,function(a,point){
										var html4 ='<li><a href="#" class="f16"><span class="input-r-box"><input class="radio" vd_name='+point.name+' videoSource="' + point.videoSource+'" vd_type='+point.type+' ip="'+point.ip+'" nvr="'+point.nvr+'" usernume="'+point.videousername+'" video_mesg="" pass="'+point.videopassword+'" channel="'+point.channel+'" type="radio" name="camera"></span><span class="neirong">'+point.name+'</span></a></li>';
										$(html4).appendTo($(f));
									})
									$(".neirong").parent("a").unbind("click");
									$(".neirong").parent("a").click(function(){
										$(".input-r-box").removeClass("input-active").find("input").attr("checked",false);
										$(this).find(".input-r-box").addClass("input-active").find("input").attr("checked",true);
									})
								}
								
							}
						})
						
					}
				}
			})
		}
		
		$(".select-close").click(function(){
			$(".child").slideUp();
			$(".ch-p-1").removeClass("ch-p-1");
		    $(".ch-p-2").removeClass("ch-p-2");
		    $(".ch-p-3").removeClass("ch-p-3");
		    $(".input-active").removeClass("input-active");
			$(".overShaw").hide();
			$(".revamp").fadeOut(300);
		})
		
		
		
		//加载摄像头
		function people_ajax(){
			$.ajax({
				type:'get',
				url:'thematicLocation_list?zoneid=' +zoneid,
				dataType:'json',
				success:function(msg){
					if(msg.status){
						$.each(msg.data,function(i,i_item){
							var numa = "a"+i;
							var html1 = '<li><a href="#" class="f18" style="margin-top:34px;"><i class="checkbox-parent checkbox-parent1"></i>'+i_item.name+'</a><ul class="child" id="a'+i+'"></ul></li>';
		
							$(html1).appendTo($(".nav-ul"));
							$.each(i_item.child,function(j,j_item){
								var numb = numa + 'b' + j;
								var html2 ='<li rel="' + j_item.id + '"><a href="#" class="f16"><i class="checkbox-parent checkbox-parent2"></i>'+j_item.name+'</a><ul class="child" id="'+numb+'"></ul></li>';
								$(html2).appendTo($('#a' + i ));
								if(j_item.id != 12){
									$.each(floorConfig.floors,function(k,k_item){
										var numc = numb + 'c' + k;
										var html3 ='<li rel="' + k_item.floorid + '"><a href="#" class="f16"><i class="checkbox-parent checkbox-parent3"></i>'+k_item.name+'</a><ul class="child" id="'+numc+'"></ul></li>';
										$(html3).appendTo($('#' + numb));
									})
								}
								
							})
						})
						load();
						//select_cancel();
					}
				}
			
			})
		}
		people_ajax();
		
		
		$(".add_video").click(function(){
			$(".overShaw").show();
			$(".revamp").fadeIn(300);
			var parent=$(this).parent();
			select_sure(parent);
			
		})
			
					// 摄像头选定
		function select_sure(video_box){
			$(".sure-btn").click(function(){
				if ($(".input-active").length==1) {
					$(video_box).children(".add_video").hide();
					$(video_box).children(".rush-status").show();
					var vd_name=$(".input-active").find("input").attr("vd_name");
					var vd_type=$(".input-active").find("input").attr("vd_type");
					var ip=$(".input-active").find("input").attr("ip");
					var nvr=$(".input-active").find("input").attr("nvr");
					var usernume=$(".input-active").find("input").attr("usernume");
					var pass=$(".input-active").find("input").attr("pass");
					var channel=$(".input-active").find("input").attr("channel");
					var videoSource =$(".input-active").find("input").attr("videoSource");
					var text=$(".input-active").next().text();
					setTimeout(function(){
				    	$(video_box).children(".object-box").show();
						$(video_box).children(".rush-status").hide();
					
					    var index=$(video_box).attr("index");
					    $(video_box).attr("vd_name",vd_name);
					    $(video_box).attr("vd_type",vd_type);
					    $(video_box).attr("ip",ip);
					    $(video_box).attr("nvr",nvr);
					    $(video_box).attr("usernume",usernume);
					    $(video_box).attr("pass",pass);
					    $(video_box).attr("channel",channel);
					    $(video_box).find(".video-address").text(text);
					    
					    //var id= $(video_box).find("object").attr("id");
					    //var oBject=document.getElementById(id);
					    var videoObj = [];
				    	videoObj.push("<object type=\'application/x-shockwave-flash\'  ");
						videoObj.push("name=\'SampleMediaPlayback\' align=\'middle\' data=\'zhuantitu/video/swfs/SampleMediaPlayback.swf\' ");
						//videoObj.push("width=\'100%\' height=\'100%\'>");
						videoObj.push("<param name=\'bgcolor\' value=\'#000000\'>");
						videoObj.push("<param name=\'allowscriptaccess\' value=\'sameDomain\'>");
						videoObj.push("<param name=\'allowfullscreen\' value=\'true\'>");
						videoObj.push("<param name=\'flashvars\' value=\'src=" + videoSource +"&amp;streamType=vod&amp;autoPlay=true&amp;controlBarAutoHide=true&amp;controlBarPosition=bottom\'>");
						videoObj.push("</object>");
				    	
				    	
				    	$(video_box).children(".object-box").append(videoObj.join(''));
					    
			    		$(video_box).find(".video-address").text(vd_name);
			    		$(video_box).find("object").width("100%").height("100%");
						$(video_box).children(".rush-status").hide();
						$(video_box).children(".error-status").hide();
						$(video_box).addClass("play").removeClass("stop");
				    },1000)
					
					
				}
				
				var arr_mesg=$(".input-active").find("input").attr("arr_mesg");
		    	$(".child").slideUp();
		    	$(".ch-p-1").removeClass("ch-p-1");
		    	$(".ch-p-2").removeClass("ch-p-2");
		    	$(".ch-p-3").removeClass("ch-p-3");
		    	$(".input-active").removeClass("input-active");
				$(".revamp").fadeOut(300);
			    $(".overShaw").hide();
		//	    $(".nav-ul").html("");
		    	$(".sure-btn").unbind("click");
		    	
			})
		}
	</script>
	</body>
</html>
