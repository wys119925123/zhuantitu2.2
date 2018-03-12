package com.zhuantitu.action;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.system.action.BaseAction;
import com.system.utils.PageBean;
import com.system.utils.StringUtil;
import com.zhuantitu.model.ThematicPolylineCategory;
import com.zhuantitu.service.ThematicPolylineCategoryService;
/**
 * 
 * @author LH
 * @since 1.0
 */
@Controller("thematicPolylineCategoryAction")
@Scope("prototype")
public class ThematicPolylineCategoryAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3446895342466891796L;
	/**
	 * 
	 */
	@Resource
	private ThematicPolylineCategoryService thematicPolylineCategoryService;
	private PageBean<ThematicPolylineCategory> thematicPolylineCategoryPage;
	private ThematicPolylineCategory thematicPolylineCategory;
	
	public String loadByMenuid(){
		StringBuffer json = new StringBuffer();
		StringBuffer temp = new StringBuffer();
		List<ThematicPolylineCategory> thematicPolylineCategories = this.thematicPolylineCategoryService.getByMenuid(getMid());
		for (ThematicPolylineCategory thematicPolylineCategory : thematicPolylineCategories) {
			temp.append("{")
				.append("\"categoryid\":" + thematicPolylineCategory.getCategoryid() + ",")
				.append("\"name\":\"" + StringUtil.toJsonText(thematicPolylineCategory.getName()) + "\",")
				.append("\"strokecolor\":\"" + thematicPolylineCategory.getStrokecolor() + "\",")
				.append("\"strokewidth\":\"" + thematicPolylineCategory.getStrokewidth() + "\",")
				.append("\"minZoom\":\"" + thematicPolylineCategory.getMinZoom() + "\",")
				.append("\"maxZoom\":\"" + thematicPolylineCategory.getMaxZoom() + "\",")
				.append("\"isClick\":\"" + thematicPolylineCategory.getIsClick() + "\",")
				.append("\"icon\":\"" + thematicPolylineCategory.getIcon() +"\"")
				.append("},");
		}
		json.append("{")
			.append("\"status\":true,")
			.append("\"data\":[")
			.append(StringUtil.deleteLastStr(temp.toString()))
			.append("]}");
		writeJSON(json.toString());
		return NONE;
	}

	public PageBean<ThematicPolylineCategory> getThematicPolylineCategoryPage() {
		return thematicPolylineCategoryPage;
	}

	public void setThematicPolylineCategoryPage(
			PageBean<ThematicPolylineCategory> thematicPolylineCategoryPage) {
		this.thematicPolylineCategoryPage = thematicPolylineCategoryPage;
	}

	public ThematicPolylineCategory getThematicPolylineCategory() {
		return thematicPolylineCategory;
	}

	public void setThematicPolylineCategory(ThematicPolylineCategory thematicPolylineCategory) {
		this.thematicPolylineCategory = thematicPolylineCategory;
	}
}
