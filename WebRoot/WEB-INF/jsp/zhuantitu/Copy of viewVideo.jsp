<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>视频调阅</title>
  <link rel="stylesheet" href="<%=path%>/zhuantitu/css/common.css">
  <link rel="stylesheet" href="<%=path%>/zhuantitu/css/shipindiaoyue.css">
  <script src="<%=path%>/zhuantitu/js/jquery.js"></script>
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
<body style="background:#5E92F2;">
<div class="box posit-RE">
<div class="box-left posit-AB shadow">
      <div class="logo-box">
        <div class="logo"><img src="${sysconfigMap['zhuantituLogo']}" alt=""></div>
        <div class="title f18 text-CENT">可视化视频调阅</div>
      </div>
      <div class="qiehuan qiehuan1" onclick="qiehuan(this)"><img src="<%=path%>/zhuantitu/images/dj-1.png" alt=""><p><span id="num" class="num">1</span>屏</p></div>
      <div class="qiehuan qiehuan2" onclick="qiehuan(this)"><img src="<%=path%>/zhuantitu/images/dj-2.png" alt=""><p><span class="num">4</span>屏</p></div>
      <div class="qiehuan qiehuan3" onclick="qiehuan(this)"><img src="<%=path%>/zhuantitu/images/dj-3.png" alt=""><p><span class="num">9</span>屏</p></div>
      <div class="qiehuan qiehuan4" onclick="qiehuan(this)"><img src="<%=path%>/zhuantitu/images/dj-4.png" alt=""><p><span class="num">16</span>屏</p></div>
  </div>
<div class="box-right float-R">
    <div class="shipin4" id="OCXBody">
      <div class="smallocxdiv none posit-RE" id="NetPlayOCX1" index="1" style="width:0px;height:0px;">
          <object classid="CLSID:CAFCF48D-8E34-4490-8154-026191D73924" standby="Waiting..." 
          	id="HIKOBJECT1" width="0%" height="0%" name="HIKOBJECT1" 
          	codebase="<%=path%>/zhuantitu/video/codebase/NetVideoActiveX23.cab#version=2,3,23,6"></object>
          <div class="video-close posit-AB"><img src="<%=path%>/zhuantitu/images/video-close2.png" alt=""><iframe  src="about:blank" frameBorder="0" marginHeight="0" marginWidth="0" style="position: absolute; visibility: inherit; top: 0px; left: 0px; width: 100%; height: 100%; z-index: -1; filter: alpha(opacity = 20);"></iframe></div>
          <p class="prompt-message">点击添加监控视频</p>
          <div class="add-btn"><img src="<%=path%>/zhuantitu/images/add.png" alt=""></div>
          <div class="error-btn"><img src="<%=path%>/zhuantitu/images/refh.png" alt=""></div>
          <div class="video-bottom"><span class="video-address"></span><iframe  src="about:blank" frameBorder="0" marginHeight="0" marginWidth="0" style="position: absolute; visibility: inherit; top: 0px; left: 0px; width: 100%; height: 100%; z-index: -1; filter: alpha(opacity = 20);"></iframe></div>
      </div>
        <div class="smallocxdiv none" id="NetPlayOCX2" index="2" style="width:0;height:0;">
          <object classid="CLSID:CAFCF48D-8E34-4490-8154-026191D73924" standby="Waiting..." id="HIKOBJECT2" width="0%" height="0%" name="HIKOBJECT2" ></object>
          <div class="video-close posit-AB"><img src="<%=path%>/zhuantitu/images/video-close2.png" alt=""><iframe  src="about:blank" frameBorder="0" marginHeight="0" marginWidth="0" style="position: absolute; visibility: inherit; top: 0px; left: 0px; width: 100%; height: 100%; z-index: -1; filter: alpha(opacity = 20);"></iframe></div>
          <p class="prompt-message">点击添加监控视频</p>
          <div class="add-btn"><img src="<%=path%>/zhuantitu/images/add.png" alt=""></div>
          <div class="error-btn"><img src="<%=path%>/zhuantitu/images/refh.png" alt=""></div>
          <div class="video-bottom"><span class="video-address"></span><iframe  src="about:blank" frameBorder="0" marginHeight="0" marginWidth="0" style="position: absolute; visibility: inherit; top: 0px; left: 0px; width: 100%; height: 100%; z-index: -1; filter: alpha(opacity = 20);"></iframe></div>
        </div>
        <div class="smallocxdiv none" id="NetPlayOCX3" index="3" style="width:0px;height:0px;">
          <object classid="CLSID:CAFCF48D-8E34-4490-8154-026191D73924" standby="Waiting..." id="HIKOBJECT3" width="0%" height="0%" name="HIKOBJECT3" ></object>
          <div class="video-close posit-AB"><img src="<%=path%>/zhuantitu/images/video-close2.png" alt=""><iframe  src="about:blank" frameBorder="0" marginHeight="0" marginWidth="0" style="position: absolute; visibility: inherit; top: 0px; left: 0px; width: 100%; height: 100%; z-index: -1; filter: alpha(opacity = 20);"></iframe></div>
          <p class="prompt-message">点击添加监控视频</p>
          <div class="add-btn"><img src="<%=path%>/zhuantitu/images/add.png" alt=""></div>
          <div class="error-btn"><img src="<%=path%>/zhuantitu/images/refh.png" alt=""></div>
          <div class="video-bottom"><span class="video-address"></span><iframe  src="about:blank" frameBorder="0" marginHeight="0" marginWidth="0" style="position: absolute; visibility: inherit; top: 0px; left: 0px; width: 100%; height: 100%; z-index: -1; filter: alpha(opacity = 20);"></iframe></div>
        </div>
        <div class="smallocxdiv none" id="NetPlayOCX4" index="4" style="width:0px;height:0px;">
          <object classid="CLSID:CAFCF48D-8E34-4490-8154-026191D73924" standby="Waiting..." id="HIKOBJECT4" width="0%" height="0%" name="HIKOBJECT4" ></object>
          <div class="video-close posit-AB"><img src="<%=path%>/zhuantitu/images/video-close2.png" alt=""><iframe  src="about:blank" frameBorder="0" marginHeight="0" marginWidth="0" style="position: absolute; visibility: inherit; top: 0px; left: 0px; width: 100%; height: 100%; z-index: -1; filter: alpha(opacity = 20);"></iframe></div>
          <p class="prompt-message">点击添加监控视频</p>
          <div class="add-btn"><img src="<%=path%>/zhuantitu/images/add.png" alt=""></div>
          <div class="error-btn"><img src="<%=path%>/zhuantitu/images/refh.png" alt=""></div>
          <div class="video-bottom"><span class="video-address"></span><iframe  src="about:blank" frameBorder="0" marginHeight="0" marginWidth="0" style="position: absolute; visibility: inherit; top: 0px; left: 0px; width: 100%; height: 100%; z-index: -1; filter: alpha(opacity = 20);"></iframe></div>
        </div>
        <div class="smallocxdiv none" id="NetPlayOCX5" index="5" style="width:0px;height:0px;">
          <object classid="CLSID:CAFCF48D-8E34-4490-8154-026191D73924" standby="Waiting..." id="HIKOBJECT5" width="0%" height="0%" name="HIKOBJECT5" ></object>
          <div class="video-close posit-AB"><img src="<%=path%>/zhuantitu/images/video-close2.png" alt=""><iframe  src="about:blank" frameBorder="0" marginHeight="0" marginWidth="0" style="position: absolute; visibility: inherit; top: 0px; left: 0px; width: 100%; height: 100%; z-index: -1; filter: alpha(opacity = 20);"></iframe></div>
          <p class="prompt-message">点击添加监控视频</p>
          <div class="add-btn"><img src="<%=path%>/zhuantitu/images/add.png" alt=""></div>
          <div class="error-btn"><img src="<%=path%>/zhuantitu/images/refh.png" alt=""></div>
          <div class="video-bottom"><span class="video-address"></span><iframe  src="about:blank" frameBorder="0" marginHeight="0" marginWidth="0" style="position: absolute; visibility: inherit; top: 0px; left: 0px; width: 100%; height: 100%; z-index: -1; filter: alpha(opacity = 20);"></iframe></div>
        </div>
        <div class="smallocxdiv none" id="NetPlayOCX6" index="6" style="width:0px;height:0px;">
          <object classid="CLSID:CAFCF48D-8E34-4490-8154-026191D73924" standby="Waiting..." id="HIKOBJECT6" width="0%" height="0%" name="HIKOBJECT6" ></object>
          <div class="video-close posit-AB"><img src="<%=path%>/zhuantitu/images/video-close2.png" alt=""><iframe  src="about:blank" frameBorder="0" marginHeight="0" marginWidth="0" style="position: absolute; visibility: inherit; top: 0px; left: 0px; width: 100%; height: 100%; z-index: -1; filter: alpha(opacity = 20);"></iframe></div>
          <p class="prompt-message">点击添加监控视频</p>
          <div class="add-btn"><img src="<%=path%>/zhuantitu/images/add.png" alt=""></div>
          <div class="error-btn"><img src="<%=path%>/zhuantitu/images/refh.png" alt=""></div>
          <div class="video-bottom"><span class="video-address"></span><iframe  src="about:blank" frameBorder="0" marginHeight="0" marginWidth="0" style="position: absolute; visibility: inherit; top: 0px; left: 0px; width: 100%; height: 100%; z-index: -1; filter: alpha(opacity = 20);"></iframe></div>
        </div>
        <div class="smallocxdiv none" id="NetPlayOCX7" index="7" style="width:0px;height:0px;">
          <object classid="CLSID:CAFCF48D-8E34-4490-8154-026191D73924" standby="Waiting..." id="HIKOBJECT7" width="0%" height="0%" name="HIKOBJECT7" ></object>
          <div class="video-close posit-AB"><img src="<%=path%>/zhuantitu/images/video-close2.png" alt=""><iframe  src="about:blank" frameBorder="0" marginHeight="0" marginWidth="0" style="position: absolute; visibility: inherit; top: 0px; left: 0px; width: 100%; height: 100%; z-index: -1; filter: alpha(opacity = 20);"></iframe></div>
          <p class="prompt-message">点击添加监控视频</p>
          <div class="add-btn"><img src="<%=path%>/zhuantitu/images/add.png" alt=""></div>
          <div class="error-btn"><img src="<%=path%>/zhuantitu/images/refh.png" alt=""></div>
          <div class="video-bottom"><span class="video-address"></span><iframe  src="about:blank" frameBorder="0" marginHeight="0" marginWidth="0" style="position: absolute; visibility: inherit; top: 0px; left: 0px; width: 100%; height: 100%; z-index: -1; filter: alpha(opacity = 20);"></iframe></div>
        </div>
        <div class="smallocxdiv none" id="NetPlayOCX8" index="8" style="width:0px;height:0px;">
          <object classid="CLSID:CAFCF48D-8E34-4490-8154-026191D73924" standby="Waiting..." id="HIKOBJECT8" width="0%" height="0%" name="HIKOBJECT8" ></object>
          <div class="video-close posit-AB"><img src="<%=path%>/zhuantitu/images/video-close2.png" alt=""><iframe  src="about:blank" frameBorder="0" marginHeight="0" marginWidth="0" style="position: absolute; visibility: inherit; top: 0px; left: 0px; width: 100%; height: 100%; z-index: -1; filter: alpha(opacity = 20);"></iframe></div>
          <p class="prompt-message">点击添加监控视频</p>
          <div class="add-btn"><img src="<%=path%>/zhuantitu/images/add.png" alt=""></div>
          <div class="error-btn"><img src="<%=path%>/zhuantitu/images/refh.png" alt=""></div>
          <div class="video-bottom"><span class="video-address"></span><iframe  src="about:blank" frameBorder="0" marginHeight="0" marginWidth="0" style="position: absolute; visibility: inherit; top: 0px; left: 0px; width: 100%; height: 100%; z-index: -1; filter: alpha(opacity = 20);"></iframe></div>
        </div>
        <div class="smallocxdiv none" id="NetPlayOCX9" index="9" style="width:0px;height:0px;">
          <object classid="CLSID:CAFCF48D-8E34-4490-8154-026191D73924" standby="Waiting..." id="HIKOBJECT9" width="0%" height="0%" name="HIKOBJECT9" ></object>
          <div class="video-close posit-AB"><img src="<%=path%>/zhuantitu/images/video-close2.png" alt=""><iframe  src="about:blank" frameBorder="0" marginHeight="0" marginWidth="0" style="position: absolute; visibility: inherit; top: 0px; left: 0px; width: 100%; height: 100%; z-index: -1; filter: alpha(opacity = 20);"></iframe></div>
          <p class="prompt-message">点击添加监控视频</p>
          <div class="add-btn"><img src="<%=path%>/zhuantitu/images/add.png" alt=""></div>
          <div class="error-btn"><img src="<%=path%>/zhuantitu/images/refh.png" alt=""></div>
          <div class="video-bottom"><span class="video-address"></span><iframe  src="about:blank" frameBorder="0" marginHeight="0" marginWidth="0" style="position: absolute; visibility: inherit; top: 0px; left: 0px; width: 100%; height: 100%; z-index: -1; filter: alpha(opacity = 20);"></iframe></div>
        </div>
        <div class="smallocxdiv none" id="NetPlayOCX10" index="10" style="width:0px;height:0px;">
          <object classid="CLSID:CAFCF48D-8E34-4490-8154-026191D73924" standby="Waiting..." id="HIKOBJECT10" width="0%" height="0%" name="HIKOBJECT10" ></object>
          <div class="video-close posit-AB"><img src="<%=path%>/zhuantitu/images/video-close2.png" alt=""><iframe  src="about:blank" frameBorder="0" marginHeight="0" marginWidth="0" style="position: absolute; visibility: inherit; top: 0px; left: 0px; width: 100%; height: 100%; z-index: -1; filter: alpha(opacity = 20);"></iframe></div>
          <p class="prompt-message">点击添加监控视频</p>
          <div class="add-btn"><img src="<%=path%>/zhuantitu/images/add.png" alt=""></div>
          <div class="error-btn"><img src="<%=path%>/zhuantitu/images/refh.png" alt=""></div>
          <div class="video-bottom"><span class="video-address"></span><iframe  src="about:blank" frameBorder="0" marginHeight="0" marginWidth="0" style="position: absolute; visibility: inherit; top: 0px; left: 0px; width: 100%; height: 100%; z-index: -1; filter: alpha(opacity = 20);"></iframe></div>
        </div>
        <div class="smallocxdiv none" id="NetPlayOCX11" index="11" style="width:0px;height:0px;">
          <object classid="CLSID:CAFCF48D-8E34-4490-8154-026191D73924" standby="Waiting..." id="HIKOBJECT11" width="0%" height="0%" name="HIKOBJECT11" ></object>
          <div class="video-close posit-AB"><img src="<%=path%>/zhuantitu/images/video-close2.png" alt=""><iframe  src="about:blank" frameBorder="0" marginHeight="0" marginWidth="0" style="position: absolute; visibility: inherit; top: 0px; left: 0px; width: 100%; height: 100%; z-index: -1; filter: alpha(opacity = 20);"></iframe></div>
          <p class="prompt-message">点击添加监控视频</p>
          <div class="add-btn"><img src="<%=path%>/zhuantitu/images/add.png" alt=""></div>
          <div class="error-btn"><img src="<%=path%>/zhuantitu/images/refh.png" alt=""></div>
          <div class="video-bottom"><span class="video-address"></span><iframe  src="about:blank" frameBorder="0" marginHeight="0" marginWidth="0" style="position: absolute; visibility: inherit; top: 0px; left: 0px; width: 100%; height: 100%; z-index: -1; filter: alpha(opacity = 20);"></iframe></div>
        </div>
        <div class="smallocxdiv none" id="NetPlayOCX12" index="12" style="width:0px;height:0px;">
          <object classid="CLSID:CAFCF48D-8E34-4490-8154-026191D73924" standby="Waiting..." id="HIKOBJECT12" width="0%" height="0%" name="HIKOBJECT12" ></object>
          <div class="video-close posit-AB"><img src="<%=path%>/zhuantitu/images/video-close2.png" alt=""><iframe  src="about:blank" frameBorder="0" marginHeight="0" marginWidth="0" style="position: absolute; visibility: inherit; top: 0px; left: 0px; width: 100%; height: 100%; z-index: -1; filter: alpha(opacity = 20);"></iframe></div>
          <p class="prompt-message">点击添加监控视频</p>
          <div class="add-btn"><img src="<%=path%>/zhuantitu/images/add.png" alt=""></div>
          <div class="error-btn"><img src="<%=path%>/zhuantitu/images/refh.png" alt=""></div>
          <div class="video-bottom"><span class="video-address"></span><iframe  src="about:blank" frameBorder="0" marginHeight="0" marginWidth="0" style="position: absolute; visibility: inherit; top: 0px; left: 0px; width: 100%; height: 100%; z-index: -1; filter: alpha(opacity = 20);"></iframe></div>
        </div>
        <div class="smallocxdiv none" id="NetPlayOCX13" index="13" style="width:0px;height:0px;">
          <object classid="CLSID:CAFCF48D-8E34-4490-8154-026191D73924" standby="Waiting..." id="HIKOBJECT13" width="0%" height="0%" name="HIKOBJECT13" ></object>
          <div class="video-close posit-AB"><img src="<%=path%>/zhuantitu/images/video-close2.png" alt=""><iframe  src="about:blank" frameBorder="0" marginHeight="0" marginWidth="0" style="position: absolute; visibility: inherit; top: 0px; left: 0px; width: 100%; height: 100%; z-index: -1; filter: alpha(opacity = 20);"></iframe></div>
          <p class="prompt-message">点击添加监控视频</p>
          <div class="add-btn"><img src="<%=path%>/zhuantitu/images/add.png" alt=""></div>
          <div class="error-btn"><img src="<%=path%>/zhuantitu/images/refh.png" alt=""></div>
          <div class="video-bottom"><span class="video-address"></span><iframe  src="about:blank" frameBorder="0" marginHeight="0" marginWidth="0" style="position: absolute; visibility: inherit; top: 0px; left: 0px; width: 100%; height: 100%; z-index: -1; filter: alpha(opacity = 20);"></iframe></div>
        </div>
        <div class="smallocxdiv none" id="NetPlayOCX14" index="14" style="width:0px;height:0px;">
          <object classid="CLSID:CAFCF48D-8E34-4490-8154-026191D73924" standby="Waiting..." id="HIKOBJECT14" width="0%" height="0%" name="HIKOBJECT14" ></object>
          <div class="video-close posit-AB"><img src="<%=path%>/zhuantitu/images/video-close2.png" alt=""><iframe  src="about:blank" frameBorder="0" marginHeight="0" marginWidth="0" style="position: absolute; visibility: inherit; top: 0px; left: 0px; width: 100%; height: 100%; z-index: -1; filter: alpha(opacity = 20);"></iframe></div>
          <p class="prompt-message">点击添加监控视频</p>
          <div class="add-btn"><img src="<%=path%>/zhuantitu/images/add.png" alt=""></div>
          <div class="error-btn"><img src="<%=path%>/zhuantitu/images/refh.png" alt=""></div>
          <div class="video-bottom"><span class="video-address"></span><iframe  src="about:blank" frameBorder="0" marginHeight="0" marginWidth="0" style="position: absolute; visibility: inherit; top: 0px; left: 0px; width: 100%; height: 100%; z-index: -1; filter: alpha(opacity = 20);"></iframe></div>
        </div>
        <div class="smallocxdiv none" id="NetPlayOCX15" index="15" style="width:0px;height:0px;">
          <object classid="CLSID:CAFCF48D-8E34-4490-8154-026191D73924" standby="Waiting..." id="HIKOBJECT15" width="0%" height="0%" name="HIKOBJECT15" ></object>
          <div class="video-close posit-AB"><img src="<%=path%>/zhuantitu/images/video-close2.png" alt=""><iframe  src="about:blank" frameBorder="0" marginHeight="0" marginWidth="0" style="position: absolute; visibility: inherit; top: 0px; left: 0px; width: 100%; height: 100%; z-index: -1; filter: alpha(opacity = 20);"></iframe></div>
          <p class="prompt-message">点击添加监控视频</p>
          <div class="add-btn"><img src="<%=path%>/zhuantitu/images/add.png" alt=""></div>
          <div class="error-btn"><img src="<%=path%>/zhuantitu/images/refh.png" alt=""></div>
          <div class="video-bottom"><span class="video-address"></span><iframe  src="about:blank" frameBorder="0" marginHeight="0" marginWidth="0" style="position: absolute; visibility: inherit; top: 0px; left: 0px; width: 100%; height: 100%; z-index: -1; filter: alpha(opacity = 20);"></iframe></div>
        </div>
        <div class="smallocxdiv none" id="NetPlayOCX16" index="16" style="width:0px;height:0px;">
          <object classid="CLSID:CAFCF48D-8E34-4490-8154-026191D73924" standby="Waiting..." id="HIKOBJECT16" width="0%" height="0%" name="HIKOBJECT16" ></object>
          <div class="video-close posit-AB"><img src="<%=path%>/zhuantitu/images/video-close2.png" alt=""><iframe  src="about:blank" frameBorder="0" marginHeight="0" marginWidth="0" style="position: absolute; visibility: inherit; top: 0px; left: 0px; width: 100%; height: 100%; z-index: -1; filter: alpha(opacity = 20);"></iframe></div>
          <p class="prompt-message">点击添加监控视频</p>
          <div class="add-btn"><img src="<%=path%>/zhuantitu/images/add.png" alt=""></div>
          <div class="error-btn"><img src="<%=path%>/zhuantitu/images/refh.png" alt=""></div>
          <div class="video-bottom"><span class="video-address"></span><iframe  src="about:blank" frameBorder="0" marginHeight="0" marginWidth="0" style="position: absolute; visibility: inherit; top: 0px; left: 0px; width: 100%; height: 100%; z-index: -1; filter: alpha(opacity = 20);"></iframe></div>
        </div>
    <div id="OperatLogBody" align="left"></div> 
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
            <input class="sure-btn" type="button" value="保 存">
            <input class="cancel-btn" type="button" value="取 消">
            <iframe  src="about:blank" frameBorder="0" marginHeight="0" marginWidth="0" style="position: absolute; visibility: inherit; top: 0px; left: 0px; width: 100%; height: 100%; z-index: -1; filter: alpha(opacity = 0);"></iframe>
      </div>
</div>
<script src="<%=path%>/zhuantitu/js/video.js"></script>
<script src="<%=path%>/zhuantitu/js/viewvideo.js"></script>
</body>
</html>