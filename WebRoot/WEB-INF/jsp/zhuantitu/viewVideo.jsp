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
		</script>
	</head>
	<body>
		<div class="shipin-box">
			<div class="shipin-left-console float-L">
				<div class="shipin-logo">
					<img src="${sysconfigMap['zhuantituLogo']}" alt="" />
				</div>
				
				<ul class="direction-console">
					<li class="direction-btn left-top" title="左上转"></li>
					<li class="direction-btn top"  title="上转"></li>
					<li class="direction-btn right-top" title="右上转"></li>
					<li class="direction-btn left" title="左转"></li>
					<li class="direction-btn middle" title="复位"></li>
					<li class="direction-btn right" title="右转"></li>
					<li class="direction-btn left-bottom" title="左下转"></li>
					<li class="direction-btn bottom" title="右转" ></li>
					<li class="direction-btn right-bottom" title="右下转"></li>
				</ul>
				
				<div class="step-box">步长（1-7）
					<select name="" id="step_select" title="旋转速度">
						<option value="1">1</option>
						<option value="2">2</option>
						<option value="3">3</option>
						<option value="4">4</option>
						<option value="5">5</option>
						<option value="6">6</option>
						<option value="7">7</option>
					</select>
				</div>
				
				<ul class="camera">
					<li class="camera-con"><div class="increase increase-zoom" title="变倍加"></div><p class="camera-content">变倍</p><div class="reduce reduce-zoom" title="变倍减"></div></li>
					<li class="camera-con"><div class="increase increase-focus" title="变焦加"></div><p class="camera-content">变焦</p><div class="reduce reduce-focus" title="变焦减"></div></li>
					<li class="camera-con"><div class="increase increase-circle" title="光圈加"></div><p class="camera-content">光圈</p><div class="reduce reduce-circle" title="光圈减"></div></li>
				</ul>
				
				<a href="<%=path %>/zhuantitu/video/RegActivex.exe" class="download"></a>
			</div>
			
			<!--右侧视频播放-->
			<div class="right-video">
				<div class="video-show">
					<div class="video-top">
						
					</div>
					
					<div class="video-box">
						<div class="video_playback">
							<div class="video block stop" index="1">
								<div class="object-box" id="object_box1">
									<div class="object-top"><span class="video-address">第一教学楼3F设备1</span><div class="video_close"></div></div>
									<object ID="Voide_ActiveQtServer1" width="100%" height="100%" CLASSID="CLSID:FD6DD048-5325-41D5-9E28-EC0E8B957B1E">
									</object>
								</div>
								<div class="add_video"><p class="video-cont">点击添加视频</p></div>
								<div class="error-status"><p class="video-cont">网络错误，请点击重试</p></div>
								<div class="rush-status"></div>
							</div>
							
							<div class="video none " index="2">
								<div class="object-box">
									<div class="object-top"><span class="video-address">第一教学楼3F设备1</span><div class="video_close"></div></div>
									<object ID="Voide_ActiveQtServer2" width="100%" height="100%" CLASSID="CLSID:FD6DD048-5325-41D5-9E28-EC0E8B957B1E">
									</object>
								</div>
								<div class="add_video"><p class="video-cont">点击添加视频</p></div>
								<div class="error-status"><p class="video-cont">网络错误，请点击重试</p></div>
								<div class="rush-status"></div>
							</div>
							
							<div class="video none " index="3">
								<div class="object-box">
									<div class="object-top"><span class="video-address">第一教学楼3F设备1</span><div class="video_close"></div></div>
									<object ID="Voide_ActiveQtServer3" width="100%" height="100%" CLASSID="CLSID:FD6DD048-5325-41D5-9E28-EC0E8B957B1E">
									</object>
								</div>
								<div class="add_video"><p class="video-cont">点击添加视频</p></div>
								<div class="error-status"><p class="video-cont">网络错误，请点击重试</p></div>
								<div class="rush-status"></div>
							</div>
							
							<div class="video none " index="4">
								<div class="object-box">
									<div class="object-top"><span class="video-address">第一教学楼3F设备1</span><div class="video_close"></div></div>
									<object ID="Voide_ActiveQtServer4" width="100%" height="100%" CLASSID="CLSID:FD6DD048-5325-41D5-9E28-EC0E8B957B1E">
									</object>
								</div>
								<div class="add_video"><p class="video-cont">点击添加视频</p></div>
								<div class="error-status"><p class="video-cont">网络错误，请点击重试</p></div>
								<div class="rush-status"></div>
							</div>
							
							<div class="video none " index="5">
								<div class="object-box">
									<div class="object-top"><span class="video-address">第一教学楼3F设备1</span><div class="video_close"></div></div>
									<object ID="Voide_ActiveQtServer5" width="100%" height="100%" CLASSID="CLSID:FD6DD048-5325-41D5-9E28-EC0E8B957B1E">
									</object>
								</div>
								<div class="add_video"><p class="video-cont">点击添加视频</p></div>
								<div class="error-status"><p class="video-cont">网络错误，请点击重试</p></div>
								<div class="rush-status"></div>
							</div>
							
							<div class="video none " index="6">
								<div class="object-box">
									<div class="object-top"><span class="video-address">第一教学楼3F设备1</span><div class="video_close"></div></div>
									<object ID="Voide_ActiveQtServer6" width="100%" height="100%" CLASSID="CLSID:FD6DD048-5325-41D5-9E28-EC0E8B957B1E">
									</object>
								</div>
								<div class="add_video"><p class="video-cont">点击添加视频</p></div>
								<div class="error-status"><p class="video-cont">网络错误，请点击重试</p></div>
								<div class="rush-status"></div>
							</div>
							
							<div class="video none " index="7">
								<div class="object-box">
									<div class="object-top"><span class="video-address">第一教学楼3F设备1</span><div class="video_close"></div></div>
									<object ID="Voide_ActiveQtServer7" width="100%" height="100%" CLASSID="CLSID:FD6DD048-5325-41D5-9E28-EC0E8B957B1E">
									</object>
								</div>
								<div class="add_video"><p class="video-cont">点击添加视频</p></div>
								<div class="error-status"><p class="video-cont">网络错误，请点击重试</p></div>
								<div class="rush-status"></div>
							</div>
							
							<div class="video none " index="8">
								<div class="object-box">
									<div class="object-top"><span class="video-address">第一教学楼3F设备1</span><div class="video_close"></div></div>
									<object ID="Voide_ActiveQtServer8" width="100%" height="100%" CLASSID="CLSID:FD6DD048-5325-41D5-9E28-EC0E8B957B1E">
									</object>
								</div>
								<div class="add_video"><p class="video-cont">点击添加视频</p></div>
								<div class="error-status"><p class="video-cont">网络错误，请点击重试</p></div>
								<div class="rush-status"></div>
							</div>
							
							<div class="video none " index="9">
								<div class="object-box">
									<div class="object-top"><span class="video-address">第一教学楼3F设备1</span><div class="video_close"></div></div>
									<object ID="Voide_ActiveQtServer9" width="100%" height="100%" CLASSID="CLSID:FD6DD048-5325-41D5-9E28-EC0E8B957B1E">
									</object>
								</div>
								<div class="add_video"><p class="video-cont">点击添加视频</p></div>
								<div class="error-status"><p class="video-cont">网络错误，请点击重试</p></div>
								<div class="rush-status"></div>
							</div>
							
							<div class="video none " index="10">
								<div class="object-box">
									<div class="object-top"><span class="video-address">第一教学楼3F设备1</span><div class="video_close"></div></div>
									<object ID="Voide_ActiveQtServer10" width="100%" height="100%" CLASSID="CLSID:FD6DD048-5325-41D5-9E28-EC0E8B957B1E">
									</object>
								</div>
								<div class="add_video"><p class="video-cont">点击添加视频</p></div>
								<div class="error-status"><p class="video-cont">网络错误，请点击重试</p></div>
								<div class="rush-status"></div>
							</div>
							
							<div class="video none " index="11">
								<div class="object-box">
									<div class="object-top"><span class="video-address">第一教学楼3F设备1</span><div class="video_close"></div></div>
									<object ID="Voide_ActiveQtServer11" width="100%" height="100%" CLASSID="CLSID:FD6DD048-5325-41D5-9E28-EC0E8B957B1E">
									</object>
								</div>
								<div class="add_video"><p class="video-cont">点击添加视频</p></div>
								<div class="error-status"><p class="video-cont">网络错误，请点击重试</p></div>
								<div class="rush-status"></div>
							</div>
							
							<div class="video none " index="12">
								<div class="object-box">
									<div class="object-top"><span class="video-address">第一教学楼3F设备1</span><div class="video_close"></div></div>
									<object ID="Voide_ActiveQtServer12" width="100%" height="100%" CLASSID="CLSID:FD6DD048-5325-41D5-9E28-EC0E8B957B1E">
									</object>
								</div>
								<div class="add_video"><p class="video-cont">点击添加视频</p></div>
								<div class="error-status"><p class="video-cont">网络错误，请点击重试</p></div>
								<div class="rush-status"></div>
							</div>
							
							<div class="video none " index="13">
								<div class="object-box">
									<div class="object-top"><span class="video-address">第一教学楼3F设备1</span><div class="video_close"></div></div>
									<object ID="Voide_ActiveQtServer13" width="100%" height="100%" CLASSID="CLSID:FD6DD048-5325-41D5-9E28-EC0E8B957B1E">
									</object>
								</div>
								<div class="add_video"><p class="video-cont">点击添加视频</p></div>
								<div class="error-status"><p class="video-cont">网络错误，请点击重试</p></div>
								<div class="rush-status"></div>
							</div>
							
							<div class="video none " index="14">
								<div class="object-box">
									<div class="object-top"><span class="video-address">第一教学楼3F设备1</span><div class="video_close"></div></div>
									<object ID="Voide_ActiveQtServer14" width="100%" height="100%" CLASSID="CLSID:FD6DD048-5325-41D5-9E28-EC0E8B957B1E">
									</object>
								</div>
								<div class="add_video"><p class="video-cont">点击添加视频</p></div>
								<div class="error-status"><p class="video-cont">网络错误，请点击重试</p></div>
								<div class="rush-status"></div>
							</div>
							
							<div class="video none " index="15">
								<div class="object-box">
									<div class="object-top"><span class="video-address">第一教学楼3F设备1</span><div class="video_close"></div></div>
									<object ID="Voide_ActiveQtServer15" width="100%" height="100%" CLASSID="CLSID:FD6DD048-5325-41D5-9E28-EC0E8B957B1E">
									</object>
								</div>
								<div class="add_video"><p class="video-cont">点击添加视频</p></div>
								<div class="error-status"><p class="video-cont">网络错误，请点击重试</p></div>
								<div class="rush-status"></div>
							</div>
							
							<div class="video none " index="16">
								<div class="object-box">
									<div class="object-top"><span class="video-address">第一教学楼3F设备1</span><div class="video_close"></div></div>
									<object ID="Voide_ActiveQtServer16" width="100%" height="100%" CLASSID="CLSID:FD6DD048-5325-41D5-9E28-EC0E8B957B1E">
									</object>
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
			</div>
		<script src="<%=path %>/zhuantitu/video/js/video.js"></script>
	</body>
</html>
