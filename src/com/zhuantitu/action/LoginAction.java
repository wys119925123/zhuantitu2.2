package com.zhuantitu.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.zhuantitu.service.IThematicUserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.map.model.MapCampus;
import com.map.model.MapZone;
import com.map.service.MapCampusService;
import com.map.service.MapZoneService;
import com.system.action.BaseAction;
import com.system.utils.Configuration;
import com.system.utils.DESHelper;
import com.system.utils.StringUtil;
import com.zhuantitu.model.User;
import com.zhuantitu.service.UserMenuPermissionService;
@Controller("loginAction")
@Scope("prototype")
public class LoginAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -488042178040107244L;
	private static final String encryptKey = Configuration.getInstance().getValue("encryptKey");
	private static final String loginUrl = Configuration.getInstance().getValue("loginUrl");
	private String username;
	private String password;
	private boolean remember;
    @Resource
    private IThematicUserService thematicUserService;
	@Resource
	private UserMenuPermissionService userMenuPermissionService;
	@Resource
	private MapCampusService mapCampusService;
	@Resource
	private MapZoneService mapZoneService;
	public String execute(){
		try {
			if(userMenuPermissionService.hasPermission(username)){
				if(remember){
					addCookie("zhuantiturememberUserInfo", DESHelper.encryptDES("{\"username\":\"" + username+ "\"," +
							"\"password\":\"" + password + "\"}", encryptKey), 60 * 60 * 24 * 7);
				}else{
					addCookie("zhuantiturememberUserInfo", "", -1);
				}
				String result = checkLogin();
				JSONObject json = JSON.parseObject(result);
				if(json.getBooleanValue("status")){
					User user = new User(json.getString("userid"), 
							json.getString("name"), 
							json.getString("nickname"), 
							StringUtil.isNotEmpty(json.getString("avatar"))?json.getString("avatar"):"zhuantitu/images/touxiang.png", 
							json.getString("deptname"));
					initUserPermission(user.getUserid());
					getSession().setAttribute("loginUser", user);
					writeJSON("{\"status\":true}");
				}else{
					writeJSON(result);
				}
			}else{
				writeError(204, "没有系统权限，请联系管理员分配权限");
				return "noPower";
			}
		} catch (Exception e) {
			e.printStackTrace();
			writeError(305, "登录失败");
		}
		return NONE;
	}
	/*public String index(){
		MapCampus mapCampus = this.mapCampusService.getDefaultMapCampus();
		if(mapCampus != null){
			List<MapZone> mapZones = this.mapZoneService.getByHql("from MapZone as t where t.mapCampus.campusid = " + mapCampus.getCampusid());
			for (MapZone mapZone : mapZones) {
				if(mapZone.getMaptype().toUpperCase().equals("2D")){
					setZoneid(mapZone.getZoneid());
				}else{

				}
			}
		}
		return "index";
	}*/
    public String index(){
        MapCampus mapCampus = this.mapCampusService.getDefaultMapCampus();
        if(mapCampus != null){
            List<MapZone> mapZones = this.mapZoneService.getByHql("from MapZone as t where t.mapCampus.campusid = " + mapCampus.getCampusid());
            for (MapZone mapZone : mapZones) {
                if(mapZone.getMaptype().toUpperCase().equals("2D")){
                    setZoneid(mapZone.getZoneid());
                }else{

                }
            }
        }
		HttpServletRequest request = getRequest();
		String uid = request.getRemoteUser();
		AttributePrincipal principal = (AttributePrincipal)request.getUserPrincipal();
		String pusername = null;
		if(principal != null){
			pusername = principal.getName();
		}
        try {
			System.out.println("uid:"+uid);
			System.out.println("pusername:"+pusername);
            if (userMenuPermissionService.hasPermission(uid)){
				System.out.println("1111111");
                if (remember) {
                    addCookie("zhuantiturememberUserInfo", DESHelper.encryptDES("{\"username\":\"" + uid + "\"," + "\"password\":\"" + password + "\"}", encryptKey), 60 * 60 * 24 * 7);
                } else {
                    addCookie("zhuantiturememberUserInfo", "", -1);
                }
                Map<String, Object> thematicUserMap = thematicUserService.getUserInfo(uid);
                if (thematicUserMap != null && thematicUserMap.get("avatar") == null) {
                    thematicUserMap.put("avatar", "zhuantitu/images/touxiang.png");
                }
				System.out.println("2222222");
                initUserPermission(uid);
				User user = new User();
				user.setUserid(uid);
				user.setName(thematicUserMap.get("name")!=null?(String)thematicUserMap.get("name"):null);
				user.setRolename(thematicUserMap.get("deptname")!=null?(String)thematicUserMap.get("deptname"):null);
				user.setAvatar((String)thematicUserMap.get("avatar"));
                getSession().setAttribute("loginUser", thematicUserMap);
				System.out.println("3333333");
            } else {
                return "noPermission";
            }
        } catch (Exception e) {
            e.printStackTrace();
            writeError(305, "登录失败");
        }
        return "main";
    }

	private String checkLogin(){
		String result = null;
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpPost httpPost = new HttpPost(loginUrl);
			long timestamp = System.currentTimeMillis()/1000;
			String secret = "{\"username\":\"" + username + "\"," +
					"\"password\":\"" + DESHelper.encryptDES(password, encryptKey) + "\"," +
					"\"content\":\"" + DigestUtils.md5Hex(username + password + encryptKey + timestamp) + "\"," +
					"\"timestamp\":" + timestamp + "}";
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("secret",DESHelper.encryptDES(secret, encryptKey)));
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			CloseableHttpResponse response = httpclient.execute(httpPost);
			try {
				if(response.getStatusLine().getStatusCode() == 200){
					result = EntityUtils.toString(response.getEntity());
				}
			} catch (Exception e) {
				e.printStackTrace();
				response.close();
			}finally{
				response.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				httpclient.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}finally{
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	public String logout(){
		getSession().invalidate();
		return "logout";
	}

    public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isRemember() {
		return remember;
	}
	public void setRemember(boolean remember) {
		this.remember = remember;
	}
}
