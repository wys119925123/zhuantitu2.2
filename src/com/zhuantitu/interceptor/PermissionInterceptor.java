package com.zhuantitu.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class PermissionInterceptor extends AbstractInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4970614925860044896L;
	public String intercept(ActionInvocation action) throws Exception {
		HttpSession session = ServletActionContext.getRequest().getSession(true); 
		HttpServletRequest request = ServletActionContext.getRequest();
		String uri = request.getRequestURI();
		System.out.println(uri);
		if(uri.contains("/login") || uri.contains("/mapLogin") || uri.contains("/login_index")){
			return action.invoke();
		}else{
			if(session.getAttribute("loginUser") == null){
				String subUri = uri.substring(uri.lastIndexOf("/") + 1,uri.length());
				if(subUri.equals("energyMap") || subUri.equals("videoMap") || subUri.equals("staticMap") || subUri.equals("index") || subUri.equals("energyMap_to2D")){
					return "nologin";
				}else{
					writeError(404,"登录已过期请重新登录");
					return null;
				}
			}else{
				return action.invoke();
			}
		}
		
	}
	public void writeError(int errorCode,String content){
		writeJSON("{\"status\":false,\"errorCode\":" + errorCode + ",\"errorContent\":\"" + content + "\"}");
	}
	/**
	 * 将数据以JSON格式写入响应中
	 * @param data
	 */
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

}
