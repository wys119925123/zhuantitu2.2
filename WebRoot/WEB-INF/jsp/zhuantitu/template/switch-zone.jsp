<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String switchpath = request.getContextPath();
%>
<div class="qiehuan shadow float-L f12 posit-RE"><img src="<%=switchpath%>/zhuantitu/images/qh.png" alt="">切换校区
	<div class="qiehuan-start  posit-AB">
		<div class="qiehuan-start-box posit-RE">
			<div class="sanjiao-Box-i posit-AB">
				<div class="sanjiao-box-i posit-RE">
					<div class="sanjiao2-i posit-AB"></div>
					<div class="sanjiao-i posit-AB"></div>
				</div>
			</div>
			<div class="fun-sanjiao posit-AB"></div>
			<div class="map-option"  zoneid="1000">
				<img class="${zoneid eq '1000'?'map-option-active':''}" src="http://gis.sicnu.edu.cn/mapstatic/3d/img/cl.jpg" title="校区1">
			</div>
			<div class="map-option" zoneid="1000">
				<img class="${zoneid eq '1001'?'map-option-active':''}" src="http://gis.sicnu.edu.cn/mapstatic/3d/img/szs.png" title="校区2">
			</div>
		</div>
	</div>
</div>
<script>
$('.map-option').click(function(){
	var link = location.href;
	if(link.indexOf('?') > 0){
		location.href = changeURLArg(link,"zoneid",$(this).attr('zoneid'));
	}else{
		location.href = lnk + "?zoneid=" + $(this).attr('zoneid');
	}
})
function changeURLArg(url,arg,arg_val){ 
    var pattern=arg+'=([^&]*)'; 
    var replaceText=arg+'='+arg_val; 
    if(url.match(pattern)){ 
        var tmp='/('+ arg+'=)([^&]*)/gi'; 
        tmp=url.replace(eval(tmp),replaceText); 
        return tmp; 
    }else{ 
        if(url.match('[\?]')){ 
            return url+'&'+replaceText; 
        }else{ 
            return url+'?'+replaceText; 
        } 
    } 
    return url+'\n'+arg+'\n'+arg_val; 
} 
</script>