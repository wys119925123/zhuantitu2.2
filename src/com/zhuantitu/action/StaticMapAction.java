package com.zhuantitu.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.system.action.BaseAction;
import com.zhuantitu.model.ThematicMapMenu;
import com.zhuantitu.model.ThematicPointCategory;
import com.zhuantitu.model.ThematicPolygonCategory;
import com.zhuantitu.model.ThematicPolylineCategory;
import com.zhuantitu.model.UserMenuPermission;
import com.zhuantitu.service.ThematicMapMenuService;
import com.zhuantitu.service.ThematicPointCategoryService;
import com.zhuantitu.service.ThematicPolygonCategoryService;
import com.zhuantitu.service.ThematicPolylineCategoryService;
@Controller("staticMapAction")
@Scope("prototype")
public class StaticMapAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2075847411974777748L;
	
	private List<ThematicMapMenu> childMenus;
	private ThematicMapMenu thematicMapMenu;
	private List<ThematicPointCategory> thematicPointCategories;
	private List<ThematicPolylineCategory> thematicPolylineCategories;
	private List<ThematicPolygonCategory> thematicPolygonCategories;
	@Resource
	private ThematicMapMenuService thematicMapMenuService;
	@Resource
	private ThematicPointCategoryService thematicPointCategoryService;
	@Resource
	private ThematicPolylineCategoryService thematicPolylineCategoryService;
	@Resource
	private ThematicPolygonCategoryService thematicPolygonCategoryService;
	
	@SuppressWarnings("unchecked")
	public String execute(){
		childMenus = new ArrayList<ThematicMapMenu>();
		List<ThematicMapMenu> thematicMapMenus = thematicMapMenuService.getByParentMenuid(getPid());
		Map<Integer, UserMenuPermission> userMenuPermissionMap = (Map<Integer, UserMenuPermission>) getSession().getAttribute("userMenuPermissionMap");
		
		for (ThematicMapMenu thematicMapMenu : thematicMapMenus) {
			if(userMenuPermissionMap.containsKey(thematicMapMenu.getMenuid())){
				if(userMenuPermissionMap.get(thematicMapMenu.getMenuid()).getViewPermission() == 1){
					childMenus.add(thematicMapMenu);
				}
			}
		}
		if(childMenus.size() > 0){
			if(getMid() == null && childMenus.size() > 0 ){
				setMid(childMenus.get(0).getMenuid());
			}
			ThematicMapMenu childMenu = this.thematicMapMenuService.getById(getMid());
			thematicMapMenu = this.thematicMapMenuService.getById(getPid());
			if(childMenu.getGeomtype() == 2){//面分类
				thematicPolygonCategories = this.thematicPolygonCategoryService.getByMenuid(getMid());
				return "polygonStaticMap";
			}else if (childMenu.getGeomtype() == 1) {//线分类
				thematicPolylineCategories = this.thematicPolylineCategoryService.getByMenuid(getMid());
				return "polylineStaticMap";
			}else{//点分类
				thematicPointCategories = this.thematicPointCategoryService.getByMenuid(getMid());
				return "pointStaticMap";
			}
		}else{
			return "error";//当前分类没有子分类需要配置
		}
	}
	public List<ThematicMapMenu> getChildMenus() {
		return childMenus;
	}
	public void setChildMenus(List<ThematicMapMenu> childMenus) {
		this.childMenus = childMenus;
	}
	public ThematicMapMenu getThematicMapMenu() {
		return thematicMapMenu;
	}
	public void setThematicMapMenu(ThematicMapMenu thematicMapMenu) {
		this.thematicMapMenu = thematicMapMenu;
	}
	public List<ThematicPointCategory> getThematicPointCategories() {
		return thematicPointCategories;
	}
	public void setThematicPointCategories(
			List<ThematicPointCategory> thematicPointCategories) {
		this.thematicPointCategories = thematicPointCategories;
	}
	public List<ThematicPolylineCategory> getThematicPolylineCategories() {
		return thematicPolylineCategories;
	}
	public void setThematicPolylineCategories(
			List<ThematicPolylineCategory> thematicPolylineCategories) {
		this.thematicPolylineCategories = thematicPolylineCategories;
	}
	public List<ThematicPolygonCategory> getThematicPolygonCategories() {
		return thematicPolygonCategories;
	}
	public void setThematicPolygonCategories(
			List<ThematicPolygonCategory> thematicPolygonCategories) {
		this.thematicPolygonCategories = thematicPolygonCategories;
	}
}
