package com.zhuantitu.mobile.action;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.system.action.BaseAction;
import com.system.utils.StringUtil;
import com.zhuantitu.model.ThematicMapMenu;
import com.zhuantitu.model.UserMenuPermission;
import com.zhuantitu.service.ThematicMapMenuService;
/**
 * 
 * @author LH
 * @since 1.0
 */
@Controller("mapMenuAction")
@Scope("prototype")
public class ThematicMapMenuAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -51344353105712987L;
	@Resource
	private ThematicMapMenuService thematicMapMenuService;
	
	@SuppressWarnings("unchecked")
	public String loadChildMenu(){
		String userid = validateUser();
		if(userid != null){
			StringBuffer json = new StringBuffer();
			StringBuffer temp = new StringBuffer();
			Map<Integer, UserMenuPermission> userMenuPermissionMap = (Map<Integer, UserMenuPermission>) getSession().getAttribute("userMenuPermissionMap");
			List<ThematicMapMenu> thematicMapMenus = this.thematicMapMenuService.getByHql("from ThematicMapMenu as t where t.geomtype = 0 and t.parentThematicMapMenu.menuid is not null order by t.orderid");
			for (ThematicMapMenu thematicMapMenu : thematicMapMenus) {
				if(userMenuPermissionMap.containsKey(thematicMapMenu.getMenuid())){
					temp.append("{")
						.append("\"mid\":" + thematicMapMenu.getMenuid() + ",")
						.append("\"name\":\"" + StringUtil.toJsonText(thematicMapMenu.getName()) + "\",")
						.append("\"appIcon\":\"" + thematicMapMenu.getAppIcon() + "\"")
						.append("},");
				}
			}
			json.append("{")
				.append("\"status\":true,")
				.append("\"data\":[")
				.append(StringUtil.deleteLastStr(temp.toString()))
				.append("]")
				.append("}");
			
			writeJSON(json.toString());
		}
		return NONE;
	}
	@SuppressWarnings("unchecked")
	public String loadMenu(){
		String userid = validateUser();
		if(userid != null){
			StringBuffer json = new StringBuffer();
			StringBuffer temp = new StringBuffer();
			Map<Integer, UserMenuPermission> userMenuPermissionMap = (Map<Integer, UserMenuPermission>) getSession().getAttribute("userMenuPermissionMap");
			List<ThematicMapMenu> list = this.thematicMapMenuService.getByHql("from ThematicMapMenu as t where t.geomtype = 0 and t.parentThematicMapMenu.menuid is null order by t.orderid");
			for (ThematicMapMenu thematicMapMenu : list) {
				if(userMenuPermissionMap.containsKey(thematicMapMenu.getMenuid())){
					StringBuffer child = new StringBuffer();
					List<ThematicMapMenu> thematicMapMenus = this.thematicMapMenuService.getByHql("from ThematicMapMenu as t where t.geomtype = 0 and t.parentThematicMapMenu.menuid = " + thematicMapMenu.getMenuid() + " order by t.orderid");
					for (ThematicMapMenu menu : thematicMapMenus) {
						if(userMenuPermissionMap.containsKey(menu.getMenuid())){
							child.append("{")
								.append("\"mid\":" + menu.getMenuid() + ",")
								.append("\"name\":\"" + StringUtil.toJsonText(menu.getName()) + "\",")
								.append("\"appIcon\":\"" + menu.getAppIcon() + "\"")
								.append("},");
						}
					}
					temp.append("{")
						.append("\"mid\":" + thematicMapMenu.getMenuid() + ",")
						.append("\"name\":\"" + StringUtil.toJsonText(thematicMapMenu.getName()) + "\",")
						.append("\"appIcon\":\"" + thematicMapMenu.getAppIcon() + "\",")
						.append("\"childMenu\":[")
						.append(StringUtil.deleteLastStr(child.toString()))
						.append("]},");
				}
			}
			json.append("{")
				.append("\"status\":true,")
				.append("\"data\":[")
				.append(StringUtil.deleteLastStr(temp.toString()))
				.append("]")
				.append("}");
			
			writeJSON(json.toString());
		}
		return NONE;
	}
}
