Width=$(".box").width();
$(".box-right").width(Width-218);

function load(obj){
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
								var html4 ='<li><a href="#" class="f16"><span class="input-r-box"><input class="radio" ip="'+point.ip+'" nvr="'+point.nvr+'" usernume="'+point.videousername+'" pass="'+point.videopassword+'" channel="'+point.channel +'" type="radio" name="camera"></span><span class="neirong">'+point.name+'</span></a></li>';
								$(html4).appendTo($(f1));
							})
							$(".neirong").parent("a").unbind("click");
							$(".neirong").parent("a").click(function(){
								$(".input-r-box").removeClass("input-active").find("input").attr("checked",false);
								$(this).find(".input-r-box").addClass("input-active").find("input").attr("checked",true);
							})
							select_sure(obj);
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
								var html4 ='<li><a href="#" class="f16"><span class="input-r-box"><input class="radio" ip="'+point.ip+'" nvr="'+point.nvr+'" usernume="'+point.videousername+'" pass="'+point.videopassword+'" channel="'+point.channel +'"  type="radio" name="camera"></span><span class="neirong">'+point.name+'</span></a></li>';
								$(html4).appendTo($(f));
							})
							$(".neirong").parent("a").unbind("click");
							$(".neirong").parent("a").click(function(){
								$(".input-r-box").removeClass("input-active").find("input").attr("checked",false);
								$(this).find(".input-r-box").addClass("input-active").find("input").attr("checked",true);
							})
							select_sure(obj);
						}
						
					}
				})
				
			}
		}
	})
}
// 加载选择摄像头

function people_ajax(obj){
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
				load(obj);
				select_cancel();
			}
		}
	
	})
	
}

function burse(){
		
}

// 视频播放
function qiehuan(bb){
  
  var num_block=$(".block").length;
  
    var num=$(bb).find("span").text();
    if (num>=num_block) {
    	for (var i = 0; i < num-num_block; i++) {
        $(".none").eq(0).removeClass("none").addClass("block").addClass("stop").show();
      }
      size(num)
      }else{
      	var play_leng=$(".play").length;
      	if (num>=play_leng) {
         	var cha=num_block-num;
         for (var k = 0; k < cha; k++) {
         	$(".stop").last().removeClass("stop").removeClass("block").addClass("none").width("0").height("0").hide().find("object").width("0").height("0");
            
          }
          size(num)
        }
      }
  
}
    function size(num){
    var width=$(".shipin4").width();
    var height=$(".shipin4").height();
	var n=Math.sqrt(num);
	$(".block").width(width/n-26);
    $(".block").height(height/n-21);
    $(".block").find(".prompt-message").show();
    $(".block").each(function(k,obj){
    	if ($(obj).find(".error-btn").is(":hidden")) {
    		$(obj).find(".add-btn").show();
    	}else{
    		$(obj).find(".add-btn").hide();
    	}
    })
   	$(".none").find(".prompt-message").hide();
    $(".none").find(".add-btn").hide();
    $(".none").find(".error-btn").hide();
    video();
}
// 摄像头选定
function select_sure(obj){
	$(".sure-btn").click(function(){
		if ($(".input-active").length==1) {
			var ip=$(".input-active").find("input").attr("ip");
			var nvr=$(".input-active").find("input").attr("nvr");
			var usernume=$(".input-active").find("input").attr("usernume");
			var pass=$(".input-active").find("input").attr("pass");
			var channel=$(".input-active").find("input").attr("channel");
			var text=$(".input-active").next().text();
			$(obj).find("object").width("100%").height("100%");
		    var index=$(obj).attr("index");
		    $(obj).attr("ip",ip);
		    $(obj).attr("nvr",nvr);
		    $(obj).attr("usernume",usernume);
		    $(obj).attr("pass",pass);
		    $(obj).attr("channel",channel);
		    $(obj).find(".video-address").text(text);
		    ChangeStatus(index,ip,nvr,channel,usernume,pass);
		}
		var arr_mesg=$(".input-active").find("input").attr("arr_mesg");
		$(".revamp").hide();
	    $(".overShaw").hide();
	    $(".nav-ul").html("");
    	$(".sure-btn").unbind("click");
	})
}

select_cancel();
select_close();
// 摄像头选择取消
function select_cancel(){
	$(".cancel-btn").unbind("click");
	$(".cancel-btn").click(function(){
		$(".revamp").hide();
    	$(".overShaw").hide();
    	$(".nav-ul").html("");
    	$(".cancel-btn").unbind("click");
	})
}
function select_close(){
	$(".select-close").unbind("click");
	$(".select-close").click(function(){
		$(".revamp").hide();
    	$(".overShaw").hide();
    	$(".nav-ul").html("");
    	$(".cancel-btn").unbind("click");
	})
}

$(".video-mesg").click(function(){
	$(".video-revamp").show();
	$(".overShaw").show();
})

$(".video-message-close").click(function(){
	$(".video-revamp").hide();
	$(".overShaw").hide();
})

