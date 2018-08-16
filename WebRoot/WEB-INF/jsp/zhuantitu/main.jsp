<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>${sysconfigMap['zhuantituTitle']}</title>
		<link rel="stylesheet" href="<%=path %>/zhuantitu/css/common-PC.css" />
		<link rel="stylesheet" href="<%=path %>/zhuantitu/css/after-login.css" />
		<script src="<%=path %>/zhuantitu/js/jquery.min.js"></script>
		<script type="text/javascript" src="<%=path%>/zhuantitu/layer/layer.js" ></script>
	</head>
	<body>
		<div class="box f14 after-login-box">
			<!--logo-->
			<img src="<%=path%>/zhuantitu/img/logo.png" class="logo-img"/>
			
			<!--用户信息-->
			<div class="user posit-AB">
				<div class="touxiang posit-RE"><div class="touxiang-img-box"><img width="70px" height="70px" src="<s:property value="#session.loginUser.avatar"/>" style="border-radius: 50%;"></div>
					<div class="touxiang-start posit-AB" style="display: block;">
					<ul class="people-box">
							<li class="first-li">
								<div class="head-box">
									<div class="head-img-box"><img width="37px" height="37px" src="<s:property value="#session.loginUser.avatar"/>" style="border-radius: 50%;"></div>
									<p class="f14"><s:property value="#session.loginUser.userid"/></p>
									<p class="f14"><s:property value="#session.loginUser.name"/></p>
								</div>
							</li>
							<li class="f14 second-li"><img src="<%=path %>/zhuantitu/img/bm.png" alt=""><s:property value="#session.loginUser.rolename"/></li>
							<li class="f14 tirth-li logout" style="cursor: pointer;"><img src="<%=path %>/zhuantitu/img/tc.png" alt="">退出登录</li>
						</ul>
					</div>
				</div>
			</div>
			
			<div class="after-login-bg-box">
				<!--左侧提示-->
				<div class="after-login-Prompt-box">
					<div class="Prompt-child-box">
						<img src="<%=path%>/zhuantitu/img/Prompt.png" class="after-login-Prompt"/>
					</div>
				</div>
				<!--右侧样式-->
				<div class="after-login-style-box">
					<div class="style-child-box">
						<img src="<%=path%>/zhuantitu/img/right-style.png" class="after-login-style"/>
					</div>
				</div>
				<!--背景-->
				<img src="<%=path %>/zhuantitu/img/after-login-bg.png" alt="" class="after-login-bg" />
			</div>
			
			<div class="select-zhuanti-box">
				<div class="select-zhuanti-child-box">
					<div class="mySwiper-contenter">
						<ul class="mySwiper-list" style="margin: 0 auto;">
							<s:iterator value="thematicMapMenus" var="t">
								<s:if test="%{#t.menuid==100}">
								<a class="" href="videoMap?pid=${t.menuid }&zoneid=${zoneid}" style="background: #0CD3E3;">
									<div class="mySlide-img-box float-L"><img src="${t.icon}" width="50"/></div>
									<span>${t.name }</span>
								</a>
								</s:if>
								<s:elseif test="%{#t.menuid==200}">
								<a class="" href="energyMap?pid=${t.menuid }&zoneid=${zoneid}&to3DZoneid=1086" style="background: #0CD3E3;">
									<div class="mySlide-img-box float-L"><img src="${t.icon}" width="50"/></div>
									<span>${t.name }</span>
								</a>
								</s:elseif>
								<s:else>
								<a class="" href="staticMap?pid=${t.menuid }&zoneid=${zoneid}" style="background: #0CD3E3;">
									<div class="mySlide-img-box float-L"><img src="${t.icon}" width="50"/></div>
									<span>${t.name }</span>
								</a>
								</s:else>								
							</s:iterator>
						</ul>
					</div>
					
					
					<div class="swiper-button swiper-button-prev"></div>
					<div class="swiper-button swiper-button-next"></div>
				</div>
			</div>
		</div>
		
		<script type="text/javascript">
			$(document).ready(function(){
				swiper();
				function swiper(){
					var num=$(".mySwiper-list").children("a").length;
					if (num>3) {
						$(".mySwiper-list").width(num*314);
						$(".swiper-button").show();
							flog=true;
							$(".swiper-button-next").click(function(){
								if (flog) {
									flog=!flog;
									$(".mySwiper-list").animate({marginLeft: '-314px'},"slow",function(){
										$(this).children().first().appendTo(".mySwiper-list");
										$(".mySwiper-list").css({marginLeft:"0px"});
										flog=!flog;
									});
								};
							})
							$(".swiper-button-prev").click(function(){
								if (flog) {
									flog=!flog;
									var last=$(".mySwiper-list").children().last();
									var first=$(".mySwiper-list").children().first();
									$(last).prependTo(".mySwiper-list")
									$(".mySwiper-list").css({marginLeft:"-314px"});
									$(".mySwiper-list").animate({marginLeft: '0px'},"slow",function(){
										flog=!flog;
									});
								};
							})
					}else{
						$(".swiper-button").hide();
						$(".mySwiper-list").width(num*314);
					}
				}
				
				//用户信息
				$(".touxiang").mouseenter(function(){
					$(".touxiang-start").show();
				})
				$(".touxiang").mouseleave(function(){
					$(".touxiang-start").hide();
				})
				
				$('.logout').click(function(){
					layer.confirm('确定要退出吗?', {icon: 3, title:'提示'}, function(index){
						location.href = "login_logout";
					});
				});
			});
			
		</script>
	</body>
</html>
