package com.zhuantitu.action;

import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.opensymphony.xwork2.ActionSupport;
import com.system.utils.DESHelper;
import com.zhuantitu.model.ThematicUser;
import com.zhuantitu.service.ThematicUserService;
@Controller("mapLoginAction")
@Scope("prototype")
public class MapLoginAction extends ActionSupport{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8809669225110892427L;
	private static final String encryptKey = "lqkjhhxy";//加解密KEY
	private static final long expires_in = 1800;//连接超时时间
	private String secret;
	@Resource
	private ThematicUserService thematicUserService;
	public String execute(){
		
		long nowtime = System.currentTimeMillis()/1000;
		String result = DESHelper.decryptDES(secret.replaceAll(" ", "+"), encryptKey);
		JSONObject json = JSON.parseObject(result);
		long  timestamp = json.getLongValue("timestamp");
		String userid = json.getString("username");
		String password = DESHelper.decryptDES(json.getString("password").replaceAll(" ", "+"), encryptKey);
		String content = json.getString("content");
		
		if((nowtime - timestamp) > expires_in){
			writeJSON("{\"status\":false,\"errorCode\":300,\"errorContent\":\"登录已过期\"}");
		}else{
			
			if(content.equals(DigestUtils.md5Hex(userid + password + encryptKey + timestamp))){
				ShaPasswordEncoder sp = new ShaPasswordEncoder();
				ThematicUser thematicUser = this.thematicUserService.getByUserid(userid);
				if(thematicUser != null && 
						sp.encodePassword(password, userid).equals(thematicUser.getPassword())){
					writeJSON("{\"status\":true,\"userid\":\"" + thematicUser.getUserid()+ "\",\"name\":\"" + thematicUser.getName() +"\",\"nickname\":\"hhxy\",\"avatar\":\"" + thematicUser.getAvatar() + "\",\"deptname\":\"" + thematicUser.getDeptname() + "\"}");
				}else{
					writeJSON("{\"status\":false,\"errorCode\":302,\"errorContent\":\"用户名或密码错误\"}");
				}
			}else{
				writeJSON("{\"status\":false,\"errorCode\":301,\"errorContent\":\"参数错误\"}");
			}
		}
		
		return NONE;
	}
	public void writeJSON(String json)
	{
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
            // 设置响应头
            response.setContentType("application/json"); // 指定内容类型为 JSON 格式
            response.setCharacterEncoding("UTF-8"); // 防止中文乱码
            // 向响应中写入数据
            PrintWriter writer = response.getWriter();
            writer.write(json.replaceAll("null", "")); 
            writer.flush();
            writer.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
	}
	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}
}
