var nowObj;
function stopAllVideo(){
	for(var i=1; i<=16;i++){
		var videoObj =document.getElementById("Voide_ActiveQtServer" + i);
		videoObj.on_Exit_pushButton_clicked();
	}
}
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
	    	var video_box=$(".stop").first();
	    	var id= $(video_box).find("object").attr("ID");
			    var oBject=document.getElementById(id);
			    $(video_box).children(".add_video").hide();
				$(video_box).children(".object-box").show();
				$(video_box).children(".rush-status").show();
		    	var vd_type=s_item.type;
		    	var ip=s_item.ip;
		    	var nvr=s_item.nvr;
		    	var usernume=s_item.videousername;
		    	var pass=s_item.videopassword;
		    	var channel=s_item.channel;
		    	var vd_name = s_item.name;
		    	$(video_box).find("object").width("100%").height("100%");
			    if(oBject.on_Login_pushButton_clicked(vd_type,ip,nvr,usernume,pass,channel)){
			    		$(video_box).find(".video-address").text(vd_name);
						$(video_box).children(".rush-status").hide();
						$(video_box).children(".error-status").hide();
						$(video_box).addClass("play").removeClass("stop");
					}else{
						//alert("播放失败");
						$(video_box).attr("vd_name",vd_name);
					    $(video_box).attr("vd_type",vd_type);
					    $(video_box).attr("ip",ip);
					    $(video_box).attr("nvr",nvr);
					    $(video_box).attr("usernume",usernume);
					    $(video_box).attr("pass",pass);
					    $(video_box).attr("channel",channel);
					    
						$(video_box).addClass("stop").removeClass("play");
						$(video_box).children(".object-box").hide();
						$(video_box).children(".rush-status").hide();
						$(video_box).children(".error-status").show();
					}
	    })
	}

})



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
								var html4 ='<li><a href="#" class="f16"><span class="input-r-box"><input class="radio" vd_name="'+point.name+'" vd_type="'+point.type+'" ip="'+point.ip+'" nvr="'+point.nvr+'" usernume="'+point.videousername+'" video_mesg="" pass="'+point.videopassword+'" channel="'+point.channel+'" type="radio" name="camera"></span><span class="neirong">'+point.name+'</span></a></li>';
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
								var html4 ='<li><a href="#" class="f16"><span class="input-r-box"><input class="radio" vd_name='+point.name+' vd_type='+point.type+' ip="'+point.ip+'" nvr="'+point.nvr+'" usernume="'+point.videousername+'" video_mesg="" pass="'+point.videopassword+'" channel="'+point.channel+'" type="radio" name="camera"></span><span class="neirong">'+point.name+'</span></a></li>';
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
			tab(1)
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
			var text=$(".input-active").next().text();
			setTimeout(function(){
		    	$(video_box).children(".object-box").show();
				$(video_box).children(".rush-status").hide();
				console.log($(".input-active").find("input").attr("vd_type"));
			
			    var index=$(video_box).attr("index");
			    $(video_box).attr("vd_name",vd_name);
			    $(video_box).attr("vd_type",vd_type);
			    $(video_box).attr("ip",ip);
			    $(video_box).attr("nvr",nvr);
			    $(video_box).attr("usernume",usernume);
			    $(video_box).attr("pass",pass);
			    $(video_box).attr("channel",channel);
			    $(video_box).find(".video-address").text(text);
			    
			    var id= $(video_box).find("object").attr("ID");
			    var oBject=document.getElementById(id);
			    console.log(vd_type);
			    if(oBject.on_Login_pushButton_clicked(vd_type,ip,nvr,usernume,pass,channel)){
			    		$(video_box).find(".video-address").text(vd_name);
			    		$(video_box).find("object").width("100%").height("100%");
						$(video_box).children(".rush-status").hide();
						$(video_box).children(".error-status").hide();
						$(video_box).addClass("play").removeClass("stop");
					}else{
						$(video_box).addClass("stop").removeClass("play");
						$(video_box).children(".object-box").hide();
						$(video_box).children(".rush-status").hide();
						$(video_box).children(".error-status").show();
					}
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

//失败后重试
$(".error-status").click(function(){
	
	var video_box=$(this).parent();
	$(video_box).children(".error-status").hide();
	$(video_box).children(".rush-status").show();
//	$(video_box).children(".object-box").show();
	var vd_name=$(".input-active").find("input").attr("vd_name");
	var vd_type=$(video_box).attr("vd_type");
	var ip=$(video_box).attr("ip");
	var nvr=$(video_box).attr("nvr");
	var usernume=$(video_box).attr("usernume");
	var pass=$(video_box).attr("pass");
	var channel=$(video_box).attr("channel");
	var id= $(video_box).find("object").attr("ID");
	var oBject=document.getElementById(id);
	setTimeout(function(){
		if(oBject.on_Login_pushButton_clicked(vd_type,ip,nvr,usernume,pass,channel)){
					$(video_box).find(".video-address").text(vd_name);
					$(video_box).children(".object-box").show();
					$(video_box).children(".rush-status").hide();
					$(video_box).children(".error-status").hide();
					$(video_box).addClass("play").removeClass("stop");
				}else{
					$(video_box).addClass("stop").removeClass("play");
					$(video_box).children(".object-box").hide();
					$(video_box).children(".rush-status").hide();
					$(video_box).children(".error-status").show();
				}
	},1000)
})
			
$(".video_close").click(function(){
	var id=$(this).parents(".object-box").find("object").attr("ID");
	var obj=document.getElementById(id);
	if (obj.on_Exit_pushButton_clicked()) {
		$(this).parents(".video").removeClass("border").addClass("stop").removeClass("play").find(".object-box").hide().next(".add_video").show();
		//alert("退出成功！")
	} else{
		//alert("退出失败！")
	}
	
})

	function Voide_ActiveQtServer1::SelectWindow(){
	  //BoxClicked是Qt中的一个Signal，在mousePressEvent中我们emit这个事件，这样就可以在单击鼠标的时候调用这个JS函数  { 
	  		$("#Voide_ActiveQtServer1").parents(".video").addClass("border").siblings().removeClass("border");
	  		nowObj=Voide_ActiveQtServer1;
		}
		function Voide_ActiveQtServer2::SelectWindow(){
		  //BoxClicked是Qt中的一个Signal，在mousePressEvent中我们emit这个事件，这样就可以在单击鼠标的时候调用这个JS函数  { 
	  		$("#Voide_ActiveQtServer2").parents(".video").addClass("border").siblings().removeClass("border"); 
	  		nowObj=Voide_ActiveQtServer2;
		}
		function Voide_ActiveQtServer3::SelectWindow(){
		  //BoxClicked是Qt中的一个Signal，在mousePressEvent中我们emit这个事件，这样就可以在单击鼠标的时候调用这个JS函数  { 
	  		$("#Voide_ActiveQtServer3").parents(".video").addClass("border").siblings().removeClass("border");  
		nowObj=Voide_ActiveQtServer3;
	}
	function Voide_ActiveQtServer4::SelectWindow(){
  //BoxClicked是Qt中的一个Signal，在mousePressEvent中我们emit这个事件，这样就可以在单击鼠标的时候调用这个JS函数  { 
  		$("#Voide_ActiveQtServer4").parents(".video").addClass("border").siblings().removeClass("border");
  		nowObj=Voide_ActiveQtServer4;
	}
	function Voide_ActiveQtServer5::SelectWindow(){
	  //BoxClicked是Qt中的一个Signal，在mousePressEvent中我们emit这个事件，这样就可以在单击鼠标的时候调用这个JS函数  { 
  		$("#Voide_ActiveQtServer5").parents(".video").addClass("border").siblings().removeClass("border");  
  		nowObj=Voide_ActiveQtServer5;
	}
	function Voide_ActiveQtServer6::SelectWindow(){
	  //BoxClicked是Qt中的一个Signal，在mousePressEvent中我们emit这个事件，这样就可以在单击鼠标的时候调用这个JS函数  { 
  		$("#Voide_ActiveQtServer6").parents(".video").addClass("border").siblings().removeClass("border"); 
  		nowObj=Voide_ActiveQtServer6;
	}
	function Voide_ActiveQtServer7::SelectWindow(){
	  //BoxClicked是Qt中的一个Signal，在mousePressEvent中我们emit这个事件，这样就可以在单击鼠标的时候调用这个JS函数  { 
  		$("#Voide_ActiveQtServer7").parents(".video").addClass("border").siblings().removeClass("border"); 
  		nowObj=Voide_ActiveQtServer7;
	}
	function Voide_ActiveQtServer8::SelectWindow(){
	  //BoxClicked是Qt中的一个Signal，在mousePressEvent中我们emit这个事件，这样就可以在单击鼠标的时候调用这个JS函数  { 
  		$("#Voide_ActiveQtServer8").parents(".video").addClass("border").siblings().removeClass("border"); 
  		nowObj=Voide_ActiveQtServer8;
	}
	function Voide_ActiveQtServer9::SelectWindow(){
	  //BoxClicked是Qt中的一个Signal，在mousePressEvent中我们emit这个事件，这样就可以在单击鼠标的时候调用这个JS函数  { 
  		$("#Voide_ActiveQtServer9").parents(".video").addClass("border").siblings().removeClass("border");  
  		nowObj=Voide_ActiveQtServer9;
	}
	function Voide_ActiveQtServer10::SelectWindow(){
	  //BoxClicked是Qt中的一个Signal，在mousePressEvent中我们emit这个事件，这样就可以在单击鼠标的时候调用这个JS函数  { 
  		$("#Voide_ActiveQtServer10").parents(".video").addClass("border").siblings().removeClass("border");
  		nowObj=Voide_ActiveQtServer10;
	}
	function Voide_ActiveQtServer11::SelectWindow(){
	  //BoxClicked是Qt中的一个Signal，在mousePressEvent中我们emit这个事件，这样就可以在单击鼠标的时候调用这个JS函数  { 
  		$("#Voide_ActiveQtServer11").parents(".video").addClass("border").siblings().removeClass("border");
  		nowObj=Voide_ActiveQtServer11
	}
	function Voide_ActiveQtServer12::SelectWindow(){
	  //BoxClicked是Qt中的一个Signal，在mousePressEvent中我们emit这个事件，这样就可以在单击鼠标的时候调用这个JS函数  { 
  		$("#Voide_ActiveQtServer12").parents(".video").addClass("border").siblings().removeClass("border");
  		nowObj=Voide_ActiveQtServer12;
	}
	function Voide_ActiveQtServer13::SelectWindow(){
	  //BoxClicked是Qt中的一个Signal，在mousePressEvent中我们emit这个事件，这样就可以在单击鼠标的时候调用这个JS函数  { 
  		$("#Voide_ActiveQtServer13").parents(".video").addClass("border").siblings().removeClass("border"); 
  		nowObj=Voide_ActiveQtServer13;
	}
	function Voide_ActiveQtServer14::SelectWindow(){
	  //BoxClicked是Qt中的一个Signal，在mousePressEvent中我们emit这个事件，这样就可以在单击鼠标的时候调用这个JS函数  { 
  		$("#Voide_ActiveQtServer14").parents(".video").addClass("border").siblings().removeClass("border");
  		nowObj=Voide_ActiveQtServer14;
	}
	function Voide_ActiveQtServer15::SelectWindow(){
	  //BoxClicked是Qt中的一个Signal，在mousePressEvent中我们emit这个事件，这样就可以在单击鼠标的时候调用这个JS函数  { 
  		$("#Voide_ActiveQtServer15").parents(".video").addClass("border").siblings().removeClass("border"); 
  		nowObj=Voide_ActiveQtServer15;
	}
	function Voide_ActiveQtServer16::SelectWindow(){
	  //BoxClicked是Qt中的一个Signal，在mousePressEvent中我们emit这个事件，这样就可以在单击鼠标的时候调用这个JS函数  { 
  		$("#Voide_ActiveQtServer16").parents(".video").addClass("border").siblings().removeClass("border");  
  		nowObj=nowObj6;
	}
	

//摄像头上下左右操作

//左上
$(".left-top").mousedown(function(){
	var num=$("#step_select option:selected").val();
	nowObj.on_Up_Left_pushButton_pressed(num);
})
$(".left-top").mouseup(function(){
	var num=$("#step_select option:selected").val();
	nowObj.on_Up_Left_pushButton_released(num);
})

//上
$(".top").mousedown(function(){
	var num=$("#step_select option:selected").val();
	nowObj.on_Up_pushButton_pressed(num);
})
$(".top").mouseup(function(){
	var num=$("#step_select option:selected").val();
	nowObj.on_Up_pushButton_released(num);
})

//右上
$(".right-top").mousedown(function(){
	var num=$("#step_select option:selected").val();
	nowObj.on_Up_Right_pushButton_pressed(num);
})
$(".right-top").mouseup(function(){
	var num=$("#step_select option:selected").val();
	nowObj.on_Up_Right_pushButton_released(num);
})

//左
$(".left").mousedown(function(){
	var num=$("#step_select option:selected").val();
	nowObj.on_Left_pushButton_pressed(num);
})
$(".left").mouseup(function(){
	var num=$("#step_select option:selected").val();
	nowObj.on_Left_pushButton_released(num);
})

//右
$(".right").mousedown(function(){
	var num=$("#step_select option:selected").val();
	nowObj.on_Right_pushButton_pressed(num);
})
$(".right").mouseup(function(){
	var num=$("#step_select option:selected").val();
	nowObj.on_Right_pushButton_released(num);
})

//左下
$(".left-bottom").mousedown(function(){
	var num=$("#step_select option:selected").val();
	nowObj.on_Down_Left_pushButton_pressed(num);
})
$(".left-bottom").mouseup(function(){
	var num=$("#step_select option:selected").val();
	nowObj.on_Down_Left_pushButton_released(num);
})

//下
$(".bottom").mousedown(function(){
	var num=$("#step_select option:selected").val();
	nowObj.on_Focus_Far_pushButton_pressed(num);
})
$(".bottom").mouseup(function(){
	var num=$("#step_select option:selected").val();
	nowObj.on_Focus_Far_pushButton_released(num);
})

//右下
$(".right-bottom").mousedown(function(){
	var num=$("#step_select option:selected").val();
	nowObj.on_Down_Right_pushButton_pressed(num);
})
$(".right-bottom").mouseup(function(){
	var num=$("#step_select option:selected").val();
	nowObj.on_Down_Right_pushButton_released(num);
})


