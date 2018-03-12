$(function(){
//	checkCookie()
function getCookie(c_name){
	if (document.cookie.length>0){ 
	c_start=document.cookie.indexOf(c_name + "=")
		if (c_start!=-1){ 
		c_start=c_start + c_name.length+1 
		c_end=document.cookie.indexOf(";",c_start)
			if (c_end==-1) c_end=document.cookie.length
			return unescape(document.cookie.substring(c_start,c_end))
	} 
}
return ""
}

function setCookie(c_name,value,expiredays)
{
var exdate=new Date()
exdate.setDate(exdate.getDate()+expiredays)
document.cookie=c_name+ "=" +escape(value)+
((expiredays==null) ? "" : "; expires="+exdate.toGMTString())
}

function checkCookie(){
username=getCookie('username')
if (username!=null && username!=""){
  $("input[type='text']").val(username)
}

  $(".btn").click(function(){
      username=$("input[type='text']").val();
      if (username!=null && username!=""){
        setCookie('username',username,30)
      }
  })
  $("#name,#pwd").bind('keypress',function(e){
      if(e.keyCode == "13"){
        username=$("input[type='text']").val();
          if (username!=null && username!=""){
           setCookie('username',username,30)
          }
      }
    });
}


checkCookie2()
function getCookie2(c_name){
	if (document.cookie.length>0){ 
	c_start=document.cookie.indexOf(c_name + "=")
		if (c_start!=-1){ 
		c_start=c_start + c_name.length+1 
		c_end=document.cookie.indexOf(";",c_start)
			if (c_end==-1) c_end=document.cookie.length
			return unescape(document.cookie.substring(c_start,c_end))
	} 
}
return ""
}

function setCookie2(c_name,value,expiredays)
{
var exdate=new Date()
exdate.setDate(exdate.getDate()+expiredays)
document.cookie=c_name+ "=" +escape(value)+
((expiredays==null) ? "" : "; expires="+exdate.toGMTString())
}

function checkCookie2(){
pass=getCookie2('pass')
//pass = Base64.decoder(pass);
if (pass!=null && pass!="")
  {$("input[type='password']").val(pass)}
username=getCookie('username')
if (username!=null && username!=""){
  $("input[type='text']").val(username)
}
  $(".sign-btn").click(function(){
  	if ($("input[type='checkbox']").is(':checked')) {
	   pass=$("input[type='password']").val();
  	if (pass!=null && pass!=""){
    	setCookie2('pass',pass,30)
    }
  	}else{
      setCookie2('pass','',-1)
    }
  	
  	username=$("input[type='text']").val();
      if (username!=null && username!=""){
        setCookie('username',username,30)
      }

  })
  
  $("#accountNum,#passtNum").bind('keypress',function(e){
      if(e.keyCode == "13"){
        if ($("input[type='checkbox']").is(':checked')) {
          pass=$("input[type='password']").val()
          if (pass!=null && pass!=""){
              setCookie2('pass',pass,30)
          }
          }else{
            setCookie2('pass','',-1)
          }
          
          
          username=$("input[type='text']").val();
          if (username!=null && username!=""){
           setCookie('username',username,30)
          }
      }
    });
}

})