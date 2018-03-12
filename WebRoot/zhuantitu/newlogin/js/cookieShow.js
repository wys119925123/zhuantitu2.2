var tipVisible = GetCookie("tipVisible");
           if(tipVisible != "yes"){
           		showSearchTip();
			}
			function showSearchTip(){
				$("#addSpotGuid").show();
				$("#maskShow").show();
				SetCookie("tipVisible","yes",365);
			}
			function GetCookie(name){
    			var arr = document.cookie.match(new RegExp("(^| )"+name+"=([^;]*)(;|$)"));
    			if(arr != null) return decodeURIComponent(arr[2]); return null;
			}
			function SetCookie(name,value,exdays){
				var expires = '', path = '', domain = '', secure = ''; 
			    var d = new Date();
			    d.setTime(d.getTime() + (exdays*24*60*60*1000));
			    var expires = "expires="+d.toUTCString();
			    document.cookie = name + "=" + value + "; " + expires;
			}