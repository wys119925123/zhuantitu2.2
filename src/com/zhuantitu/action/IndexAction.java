package com.zhuantitu.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.system.action.BaseAction;
import com.zhuantitu.model.ThematicMapMenu;
import com.zhuantitu.model.UserMenuPermission;
import com.zhuantitu.service.ThematicMapMenuService;
@Controller("indexAction")
@Scope("prototype")
public class IndexAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8379372812426104518L;
	private List<ThematicMapMenu>  thematicMapMenus;
	@Resource
	private ThematicMapMenuService thematicMapMenuService;
	@SuppressWarnings("unchecked")
	public String execute(){
		Map<Integer, UserMenuPermission> userMenuPermissionMap = (Map<Integer, UserMenuPermission>) getSession().getAttribute("userMenuPermissionMap");
		List<ThematicMapMenu> list = this.thematicMapMenuService.getByHql("from ThematicMapMenu as t where t.parentThematicMapMenu.menuid is null order by t.orderid");
		thematicMapMenus = new ArrayList<ThematicMapMenu>();
		for (ThematicMapMenu thematicMapMenu : list) {
			if(userMenuPermissionMap.containsKey(thematicMapMenu.getMenuid())){
				thematicMapMenus.add(thematicMapMenu);
			}
		}
		return SUCCESS;
	}
	public List<ThematicMapMenu> getThematicMapMenus() {
		return thematicMapMenus;
	}
	public void setThematicMapMenus(List<ThematicMapMenu> thematicMapMenus) {
		this.thematicMapMenus = thematicMapMenus;
	}
}
