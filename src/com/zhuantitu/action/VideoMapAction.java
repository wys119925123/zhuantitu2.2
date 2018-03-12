package com.zhuantitu.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.system.action.BaseAction;
import com.system.utils.DESHelper;
import com.zhuantitu.model.ThematicMapMenu;
import com.zhuantitu.model.ThematicPointCategory;
import com.zhuantitu.service.ThematicMapMenuService;
import com.zhuantitu.service.ThematicPointCategoryService;
@Controller("videoMapAction")
@Scope("prototype")
public class VideoMapAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2173663649928862736L;
	private List<ThematicMapMenu> childMenus;
	private ThematicMapMenu thematicMapMenu;
	private List<ThematicPointCategory> thematicPointCategories;
	private String param;
	private String videos;
	@Resource
	private ThematicMapMenuService thematicMapMenuService;
	@Resource
	private ThematicPointCategoryService thematicPointCategoryService;
	
	public String execute(){
		setMid(302);
		thematicMapMenu = this.thematicMapMenuService.getById(getPid());
		thematicPointCategories = this.thematicPointCategoryService.getByMenuid(getMid());
		return "videomap";
	}
	public String viewVideo(){
		setVideos("{\"data\":[" + DESHelper.decryptDES(param.replaceAll(" ", "+"), getEncryptkey()) + "]}");
		return "viewVideo";
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
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	public String getVideos() {
		return videos;
	}
	public void setVideos(String videos) {
		this.videos = videos;
	}
}
