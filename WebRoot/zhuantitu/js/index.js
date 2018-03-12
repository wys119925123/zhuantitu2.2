$(function(){
	var h=$(window).height();
	var hc=h/1.5;
	$(".flow-content").height(hc);
	$(".flow-mesg-content").height(hc);
	var flow_h=$(".flow").height();
	var flow_mesg_h=$(".flow-mesg").height();
	var flow_mg_top=0-flow_h/2.3;
	$(".flow").css({marginTop:flow_mg_top});
	var flow_mesg_mg_top=0-flow_mesg_h/2.3;
	$(".flow-mesg").css({marginTop:flow_mesg_mg_top});
	$('.classify').click(function(){
        if($(this).data('href')){
            location.href= $(this).data('href');
        }
    });
	// 地图切换
	$(".qiehuan").mouseenter(function(){
		$(this).find(".qiehuan-start").fadeIn("fast");
	});
	$(".qiehuan").mouseleave(function(){
		$(this).find(".qiehuan-start").fadeOut("fast");
	});
	$(".touxiang").mouseenter(function(){
		$(this).find(".touxiang-start").fadeIn("fast");
	});
	$(".touxiang").mouseleave(function(){
		$(this).find(".touxiang-start").fadeOut("fast");
	});
	// 关闭添加按钮
	$(".close").click(function(){
		$(this).parents(".revamp").hide().prev(".overShaw").hide();
	});
	$('.logout').click(function(){
		layer.confirm('确定要退出吗?', {icon: 3, title:'提示'}, function(index){
		 	location.href = "login_logout";
		});
	});
	
})
