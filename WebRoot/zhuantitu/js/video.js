var m_iNowChanNo = -1; //当前通道号
var m_iLoginUserId = -1; //注册设备用户ID
var m_iChannelNum = -1; //模拟通道总数
var m_bDVRControl = null; //OCX控件对象
var m_iProtocolType = 0; //协议类型，0 – TCP， 1 - UDP
var m_iStreamType = 0; //码流类型，0 表示主码流， 1 表示子码流
var m_iPlay = 0; //当前是否正在预览
var m_iRecord = 0; //当前是否正在录像
var m_iTalk = 0; //当前是否正在对讲 
var m_iVoice = 0; //当前是否打开声音
var m_iAutoPTZ = 0; //当前云台是否正在自转
var m_iPTZSpeed = 4; //云台速度
var tongdao;
var num_index = 0;
var err_ip,err_nvr,err_usernume,err_pass,err_channel;
var ce = 10;

function stopAllVideo(){
	for(var i=1; i<=16;i++){
		m_bDVRControl = document.getElementById("HIKOBJECT" + i);
		ButtonPress('Preview:stop');
	}
}
function video_play() {

}

window.onload = function() {
	var myDate = new Date();
	var iYear = myDate.getFullYear();
	if (iYear < 1971 || iYear > 2037) {
		alert("为了正常使用本软件，请将系统日期年限设置在1971-2037范围内！");
	}
	if(document.getElementById("HIKOBJECT1").object == null)
	{
		layer.confirm('请先下载控件并注册！', {
			title:'提示',
		  	btn: ['下载','取消'] 
		}, function(){
			window.open("zhuantitu/video/LQActiveX2.0.zip", "_blank");
		}, function(){
		});
		m_bDVRControl = null;
	}
	else
	{
	  	m_bDVRControl = document.getElementById("HIKOBJECT1");
	}
}
function rightclick() {
	return false;
}
function ButtonPress(sKey, szDevIp, szDevPort,szChannel, szDevUser, szDevPwd) {
	try {
		switch (sKey) {
		case "LoginDev": {
			m_iLoginUserId = m_bDVRControl.Login(szDevIp, szDevPort, szDevUser,
					szDevPwd);
			err_ip=szDevIp;
	        err_nvr=szDevPort;
	        err_usernume=szDevUser;
	        err_pass=szDevPwd;
	        err_channel=szChannel;
			if (m_iLoginUserId == -1) {
				$(m_bDVRControl).width("0").height("0");
            $(m_bDVRControl).parent().find(".prompt-message").next().hide().next().show();
            $(m_bDVRControl).parent().attr("ip",err_ip).attr("nvr",err_nvr).attr("usernume",err_usernume).attr("pass",err_pass).attr("channel",err_channel);
            setTimeout(function(){
              $(m_bDVRControl).parent().find(".prompt-message").text("网络错误，请点击重试");
              $(m_bDVRControl).parent().find(".error-btn").children("img").attr("src","zhuantitu/images/refh.png");
            },1000)
            video_retry();
			} else {
				for ( var i = 2; i <= 3; i++) {
					document.getElementById("HIKOBJECT" + i).SetUserID(
							m_iLoginUserId);
				}
				ButtonPress('getDevChan')
			}
			break;
		}
		case "getDevChan": {
			szServerInfo = m_bDVRControl.GetServerInfo();
			var xmlDoc = new ActiveXObject("Microsoft.XMLDOM");
			xmlDoc.async = "false"
			xmlDoc.loadXML(szServerInfo)
			m_iChannelNum = parseInt(xmlDoc.documentElement.childNodes[0].childNodes[0].nodeValue);
			if (m_iChannelNum < 1) {
				$(m_bDVRControl).width("0").height("0");
            $(m_bDVRControl).parent().find(".prompt-message").next().hide().next().show();
            $(m_bDVRControl).parent().attr("ip",err_ip).attr("nvr",err_nvr).attr("usernume",err_usernume).attr("pass",err_pass).attr("channel",err_channel);
            setTimeout(function(){
              $(m_bDVRControl).parent().find(".prompt-message").text("网络错误，请点击重试");
              $(m_bDVRControl).parent().find(".error-btn").children("img").attr("src","zhuantitu/images/refh.png");
            },1000)
            video_retry();
			} else {
				for ( var i = 0; i < m_iChannelNum; i++) {
					var szChannelName = m_bDVRControl.GetChannelName(i);
					if (szChannelName == "") {
						szChannelName = "通道" + (i + 1);
					}
				}
				tongdao = new Option(szChannelName, i)
				ButtonPress('Preview:start');
			}
			break;
		}
		case "Preview:start": {
			m_iNowChanNo = 1
			if (m_iNowChanNo > -1) {

				if (m_iPlay == 1) {
				}
				var bRet = m_bDVRControl.StartRealPlay(szChannel, 0, 0);
				if (bRet) {
					m_iPlay = 1;
					num_index++;
					$(m_bDVRControl).width("100%").height("100%");
					$(m_bDVRControl).parent().removeClass("stop").addClass(
							"play");
					$(m_bDVRControl).parent().find(".video-close").show();
					$(m_bDVRControl).parent().find(".video-bottom").show();
					$(m_bDVRControl).parent().find(".prompt-message").next()
							.show().next().hide();
				} else {
					$(m_bDVRControl).width("0").height("0");
					$(m_bDVRControl).parent().find(".prompt-message").next()
							.hide().next().show();
					$(m_bDVRControl).parent().attr("ip",err_ip).attr("nvr",err_nvr).attr("usernume",err_usernume).attr("pass",err_pass).attr("channel",err_channel);
					setTimeout(function() {
						$(m_bDVRControl).parent().find(".prompt-message").text(
								"网络错误，请点击重试");
						$(m_bDVRControl).parent().find(".error-btn").children(
								"img").attr("src", "zhuantitu/images/refh.png");
					}, 1000)
					video_retry();
				}
			} else {
			}
			break;
		}
		case "Preview:stop": {

			if (m_bDVRControl.StopRealPlay()) {
				m_iPlay = 0;
				$(m_bDVRControl).width("0").height("0").parent().removeClass(
						"play").addClass("stop");
				$(m_bDVRControl).parent().find(".prompt-message").text(
						"点击添加监控视频").next(".add-btn").show();
				$(m_bDVRControl).parent().find(".video-bottom").hide();
				$(m_bDVRControl).parent().find(".video-close").hide();
			} else {
			}
			break;
		}
		default: {
			break;
		}
		}
	} catch (err) {
		//alert(err);
	}
}
$(".video-close").click(function() {
	var i = $(this).parent().attr("index");
	m_bDVRControl = document.getElementById("HIKOBJECT" + i);
	ButtonPress('Preview:stop');
})
function ChangeStatus(iWindowNum, aa, bb, ff,cc, dd) {

	m_bDVRControl = document.getElementById("HIKOBJECT" + iWindowNum);
	ButtonPress('LoginDev', aa, bb, ff,cc, dd)
}
video();
function video() {
	$(".block").find(".add-btn").unbind("click");
	$(".block").each(function() {

		$(this).children(".add-btn").click(function() {
			var parent = $(this).parent();
			$(".revamp").show();
			$(".overShaw").show();
			people_ajax(parent);
		})
	})

}

function video_retry() {
	$(".block").find(".error-btn").unbind("click");
	$(".error-btn").click(
			function() {
				$(this).find("img").attr("src", "zhuantitu/images/5-121204194032-50.gif")
						.css( {
							marginTop : "-4px"
						});
				$(this).prev().prev().text("正在重试...");
				var parent = $(this).parent();
				var retry_ip = $(parent).attr("ip");
				var retry_nvr = $(parent).attr("nvr");
				var retry_usernume = $(parent).attr("usernume");
				var retry_pass = $(parent).attr("pass");
				var retry_index = $(parent).attr("index");
				var retry_channel = $(parent).attr("channel");
				ChangeStatus(retry_index, retry_ip, retry_nvr, retry_channel,retry_usernume,
						retry_pass);
					
	})
}

$(function(){
	start_add(videos);
	function start_add(data){
	    var len=data.data.length;
	    var num=0;
	    if(len<2){
	      $(".smallocxdiv").eq(0).removeClass("none").show().addClass("block").addClass("stop");
	      num++;
	    }else if(len<5){
	      for (var i = 0; i < 4; i++) {
	        $(".smallocxdiv").eq(i).removeClass("none").show().addClass("block").addClass("stop");
	        num++;
	      }
	    }else if(len<10){
	      for (var i = 0; i < 9; i++) {
	        $(".smallocxdiv").eq(i).removeClass("none").show().addClass("block").addClass("stop");
	        num++;
	      }
	    }else if(len<16){
	      for (var i = 0; i < 16; i++) {
	        $(".smallocxdiv").eq(i).removeClass("none").show().addClass("block").addClass("stop");
	        num++;
	      }
	    }
	    size(num);
	    $(data.data).each(function(s,s_item){
	        index=s+1;
	        $("#HIKOBJECT"+index).width("100%").height("100%");

	        ChangeStatus(index,s_item.ip,s_item.nvr,s_item.channel,s_item.videousername,s_item.videopassword);
	        $("#NetPlayOCX"+index).find(".video-address").html(s_item.name)
	    })
	}
})