package com.system.action;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.system.model.Sysconfig;
import com.system.service.SysconfigService;
import com.system.utils.Configuration;
import com.system.utils.DESHelper;
import com.system.utils.StringUtil;
import com.zhuantitu.model.UserMenuPermission;
import com.zhuantitu.model.UserPointPermission;
import com.zhuantitu.model.UserPolygonPermission;
import com.zhuantitu.model.UserPolylinePermission;
import com.zhuantitu.service.UserMenuPermissionService;
import com.zhuantitu.service.UserPointPermissionService;
import com.zhuantitu.service.UserPolygonPermissionService;
import com.zhuantitu.service.UserPolylinePermissionService;

public class BaseAction extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7817979713297585907L;
	private static final long expirtime = Long.parseLong(Configuration.getInstance().getValue("expirtime"));
	private static final String encryptKey = Configuration.getInstance().getValue("encryptKey");
	private static Map<String, String> configMap = null;
	private Map<String, String> sysconfigMap;
	private String keywords;
	private Integer id;
	private String zoneid;
	private String floorid;
	private int pageSize;
	private int page;
	private String code;
	private Integer mid;
	private Integer pid;
	private Integer pointid;
	private Integer zoom;
	private String to3DZoneid;
	
	@Resource
	private UserMenuPermissionService userMenuPermissionService;
	@Resource
	private UserPointPermissionService userPointPermissionService;
	@Resource
	private UserPolylinePermissionService userPolylinePermissionService;
	@Resource
	private UserPolygonPermissionService userPolygonPermissionService;
	@Resource
	private SysconfigService sysconfigService;
	@PostConstruct
	public void initConfig(){
		setSysconfigMap(initSysConfig());
	}
	public Map<String, String> initSysConfig(){
		configMap = new LinkedHashMap<String, String>();
		List<Sysconfig> sysconfigs = this.sysconfigService.getByHql("from Sysconfig as t order by t.orderid");
		for (Sysconfig sysconfig : sysconfigs) {
			configMap.put(sysconfig.getVarname(), sysconfig.getVarvalue());
		}
		return configMap;
	}
	@SuppressWarnings("unchecked")
	public String validateUser(){
		HttpServletRequest request = ServletActionContext.getRequest();
		Enumeration<String> parameterNames = request.getParameterNames();
		ArrayList<String> parameterNamesArr = Collections.list(parameterNames);
		Collections.sort(parameterNamesArr);
		StringBuffer contentData = new StringBuffer();
		for (String name : parameterNamesArr) {
			if(!name.equals("secret")){
				contentData.append(request.getParameter(name));
			}
		}
		contentData.append(encryptKey);
		String secret = request.getParameter("secret");
		if(StringUtil.isNotEmpty(secret)){
			try {
				String decryptContent = DESHelper.decryptDES(secret.replaceAll(" ", "+"), encryptKey);
				Map<String,String> decryptMap = JSON.parseObject(decryptContent,HashMap.class);
				contentData.append(decryptMap.get("userid"));
				if(decryptMap.get("content").equals(DigestUtils.md5Hex(contentData.toString()))){
					long timestamp = Long.parseLong(decryptMap.get("timestamp"));
					if(validateTimestamp(timestamp)){
						String userid = decryptMap.get("userid");
						if(getSession().getAttribute("userMenuPermissionMap") == null){
							initUserPermission(userid);
						}
						return userid;
					}else{
						writeError(101,"请求已过期");
						return null;
					}
				}else{
					writeError(102,"请求参数签名错误");
					return null;
				}
			} catch (Exception e) {
				e.printStackTrace();
				writeError(103,"接口未知错误");
				return null;
			}
			
		}else{
			writeError(100,"请求参数签名为空");
			return null;
		}
	}
	/**
	 * 初始化用户权限
	 */
	public void initUserPermission(String userid){
		initUserMenuPermission(userid);
		initUserPointPermission(userid);
		initUserPolylinePermission(userid);
		initUserPolygonPermission(userid);
		
	}
	/**
	 * 初始化用户菜单权限
	 * @param userid
	 */
	public void initUserMenuPermission(String userid){
		Map<Integer, UserMenuPermission> userMenuPermissionMap = new HashMap<Integer, UserMenuPermission>();
		List<UserMenuPermission> userMenuPermissions = this.userMenuPermissionService.getByUserid(userid);
		for (UserMenuPermission userMenuPermission : userMenuPermissions) {
			userMenuPermissionMap.put(userMenuPermission.getId().getMenuid(), userMenuPermission);
		}
		getSession().setAttribute("userMenuPermissionMap", userMenuPermissionMap);
	}
	/**
	 * 初始化用户点图元权限
	 * @param userid
	 */
	public void initUserPointPermission(String userid){
		Map<Integer, UserPointPermission> userPointPermissionMap = new HashMap<Integer, UserPointPermission>();
		List<UserPointPermission> userPointPermissions = this.userPointPermissionService.getByUserid(userid);
		for (UserPointPermission userPointPermission : userPointPermissions) {
			userPointPermissionMap.put(userPointPermission.getId().getPointid(), userPointPermission);
		}
		getSession().setAttribute("userPointPermissionMap", userPointPermissionMap);
	}
	/**
	 * 初始化用户线图元权限
	 * @param userid
	 */
	public void initUserPolylinePermission(String userid){
		Map<Integer, UserPolylinePermission> userPolylinePermissionMap = new HashMap<Integer, UserPolylinePermission>();
		List<UserPolylinePermission> userPolylinePermissions = this.userPolylinePermissionService.getByUserid(userid);
		for (UserPolylinePermission userPolylinePermission : userPolylinePermissions) {
			userPolylinePermissionMap.put(userPolylinePermission.getId().getPolylineid(), userPolylinePermission);
		}
		getSession().setAttribute("userPolylinePermissionMap", userPolylinePermissionMap);
	}
	/**
	 * 初始化用户面图元权限
	 * @param userid
	 */
	public void initUserPolygonPermission(String userid){
		Map<Integer, UserPolygonPermission> userPolygonPermissionMap = new HashMap<Integer, UserPolygonPermission>();
		List<UserPolygonPermission> userPolygonPermissions = this.userPolygonPermissionService.getByUserid(userid);
		for (UserPolygonPermission userPolygonPermission : userPolygonPermissions) {
			userPolygonPermissionMap.put(userPolygonPermission.getId().getPolygonid(), userPolygonPermission);
		}
		getSession().setAttribute("userPolygonPermissionMap", userPolygonPermissionMap);
	}
	
	public void addCookie(String name,String value,int expiry){
		HttpServletResponse response = ServletActionContext.getResponse();
		Cookie cookie = new Cookie(name,value);
		cookie.setMaxAge(expiry);
		cookie.setDomain(getRequest().getServerName());
		response.addCookie(cookie);
	}
	/**
	 * 错误输出
	 * 1： 参数错误
	 */
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
	/**
	 * 请求是否过期
	 * @param timestamp
	 * @return
	 */
	private static boolean validateTimestamp(long timestamp){
		long nowtime = System.currentTimeMillis()/1000;
		if((nowtime - timestamp) > expirtime){
			return false;
		}else{
			return true;
		}
	}
	public String getKeywords() {
		String key = "";
		try {
			key = URLDecoder.decode(URLEncoder.encode(this.keywords, "ISO8859_1"), "UTF-8");
		} catch (Exception e) {
		}
		return key;
	}
	/**
	 * 获取request
	 */
	public HttpServletRequest getRequest()
	{
		return ServletActionContext.getRequest();
	}
	/**
	 * 获取response
	 * @return
	 */
	public HttpServletResponse getResponse()
	{
		return ServletActionContext.getResponse();
	}
	/**
	 * 获取session
	 * @return
	 */
	public HttpSession getSession(){
		return ServletActionContext.getRequest().getSession(true);
	}
	public String getRealPath(){
		ActionContext ac = ActionContext.getContext();
		ServletContext sc = (ServletContext) ac.get(ServletActionContext.SERVLET_CONTEXT);
		return sc.getRealPath("/");
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getZoneid() {
		return zoneid;
	}
	public void setZoneid(String zoneid) {
		this.zoneid = zoneid;
	}
	public void setFloorid(String floorid) {
		this.floorid = floorid;
	}
	public int getPageSize() {
		if(pageSize == 0){
			return 10;
		}
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Integer getPointid() {
		return pointid;
	}
	public void setPointid(Integer pointid) {
		this.pointid = pointid;
	}
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public Integer getMid() {
		return mid;
	}
	public void setMid(Integer mid) {
		this.mid = mid;
	}
	public Integer getZoom() {
		return zoom;
	}
	public void setZoom(Integer zoom) {
		this.zoom = zoom;
	}
	public String getFloorid() {
		return floorid;
	}
	public static String getEncryptkey() {
		return encryptKey;
	}
	public String getTo3DZoneid() {
		return to3DZoneid;
	}
	public void setTo3DZoneid(String to3dZoneid) {
		to3DZoneid = to3dZoneid;
	}
	public Map<String, String> getSysconfigMap() {
		return sysconfigMap;
	}
	public void setSysconfigMap(Map<String, String> sysconfigMap) {
		this.sysconfigMap = sysconfigMap;
	}
	public static Map<String, String> getConfigMap() {
		return configMap;
	}
	public static void setConfigMap(Map<String, String> configMap) {
		BaseAction.configMap = configMap;
	}
}
