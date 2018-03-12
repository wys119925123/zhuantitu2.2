package com.system.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import sun.misc.BASE64Decoder;

import com.system.utils.DateUtil;
import com.system.utils.StringUtil;

@Controller("uploadAction")
@Scope("prototype")
public class UploadAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5153120182801019612L;
	private String base64;
	private long size;
	private String type;
	private File imageFile;
	private String imageFileFileName;
	private String dir;
	private String system;
	public String image(){
		try {
			String nowDate = DateUtil.formatDateNoSplit(System.currentTimeMillis());
			String realPath = getRealPath();
			String contextPath = getRequest().getContextPath() + "/";
			if(StringUtil.isNotEmpty(system)){
				realPath = getSysconfigMap().get(system + "Path");
				contextPath = "";
			}
			String savePath = realPath
					+ "attached" + System.getProperty("file.separator") + dir + System.getProperty("file.separator")  
					+ nowDate + System.getProperty("file.separator");
			String saveUrl = contextPath + "attached/" + dir + "/" + nowDate + "/";
			File uploadDir = new File(savePath);
			if(!uploadDir.exists()){
				uploadDir.mkdirs();
			}
			String newName = StringUtil.createUUID() + imageFileFileName.substring(imageFileFileName.lastIndexOf("."),imageFileFileName.length());
			File file = new File(savePath,newName);
		
			FileUtils.copyFile(imageFile, file);
			this.writeJSON("{\"status\":true,\"path\":\"" + saveUrl + newName + "\"}");
		} catch (IOException e) {
			e.printStackTrace();
			this.writeJSON("{\"status\":false}");
		}
		return NONE;
	}
	public String base64Img(){
		String nowDate = DateUtil.formatDateNoSplit(System.currentTimeMillis());
		String savePath = getSession().getServletContext().getRealPath("/") 
						+ "attached" + System.getProperty("file.separator") + "img" + System.getProperty("file.separator")  
						+ nowDate + System.getProperty("file.separator");
		String saveUrl = getRequest().getContextPath() + "/attached/img/" + nowDate + "/";
		File uploadDir = new File(savePath);
		if(!uploadDir.exists()){
			uploadDir.mkdirs();
		}
		String fileExt = type.substring(6).replace("jpeg", "jpg");
		String newName = StringUtil.createUUID() + "." + fileExt;
		String code = base64.replace("jpeg", "jpg").substring(22);
		FileOutputStream out = null;
		try {
			byte[] buffer = new BASE64Decoder().decodeBuffer(code);
			out = new FileOutputStream(savePath + newName);
			out.write(buffer);
			out.close();
			this.writeJSON("{\"status\":true,\"path\":\"" + saveUrl + newName + "\"}");
		} catch (IOException e) {
			e.printStackTrace();
			if(out != null){
				try {
					out.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			this.writeError(203,"接口未知错误");
		}finally{
			if(out != null){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			this.writeError(203,"接口未知错误");
		}
		
		return NONE;
	}
	public void writeJSON(String json)
	{
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
            // 设置响应头
            response.setContentType("application/json"); // 指定内容类型为 JSON 格式
            response.setHeader("Content-Type","text/html");
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
	public void writeError(int errorCode,String content){
		this.writeJSON("{\"status\":false,\"errorCode\":" + errorCode + ",\"errorContent\":\"" + content + "\"}");
	}
	public String getBase64() {
		return base64;
	}
	public void setBase64(String base64) {
		this.base64 = base64;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public File getImageFile() {
		return imageFile;
	}
	public void setImageFile(File imageFile) {
		this.imageFile = imageFile;
	}
	public String getImageFileFileName() {
		return imageFileFileName;
	}
	public void setImageFileFileName(String imageFileFileName) {
		this.imageFileFileName = imageFileFileName;
	}
	public String getDir() {
		return dir;
	}
	public void setDir(String dir) {
		this.dir = dir;
	}
	public String getSystem() {
		return system;
	}
	public void setSystem(String system) {
		this.system = system;
	}
}
