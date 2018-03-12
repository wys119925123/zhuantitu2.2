package com.zhuantitu.mobile.action;
import java.util.List;

import javax.annotation.Resource;
import com.system.action.BaseAction;
import com.system.utils.PageBean;
import com.system.utils.StringUtil;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.zhuantitu.model.ThematicPointCategory;
import com.zhuantitu.service.ThematicPointCategoryService;
/**
 * 
 * @author LH
 * @since 1.0
 */
@Controller("pointCategoryAction")
@Scope("prototype")
public class ThematicPointCategoryAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -451665980351302871L;
	@Resource
	private ThematicPointCategoryService thematicPointCategoryService;
	private PageBean<ThematicPointCategory> thematicPointCategoryPage;
	private ThematicPointCategory thematicPointCategory;
	
	public String loadByMenuid(){
		String userid = validateUser();
		if(userid != null){
			StringBuffer json = new StringBuffer();
			StringBuffer temp = new StringBuffer();
			List<ThematicPointCategory> thematicPointCategories = this.thematicPointCategoryService.getByMenuid(getMid());
			for (ThematicPointCategory thematicPointCategory : thematicPointCategories) {
				temp.append("{")
				.append("\"categoryid\":" + thematicPointCategory.getCategoryid() + ",")
				.append("\"name\":\"" + StringUtil.toJsonText(thematicPointCategory.getName()) + "\",")
				.append("\"icon\":\"" + thematicPointCategory.getIcon() +"\"")
				.append("},");
			}
			json.append("{")
			.append("\"status\":true,")
			.append("\"data\":[")
			.append(StringUtil.deleteLastStr(temp.toString()))
			.append("]}");
			writeJSON(json.toString());
		}
		
		return NONE;
	}

	public PageBean<ThematicPointCategory> getThematicPointCategoryPage() {
		return thematicPointCategoryPage;
	}

	public void setThematicPointCategoryPage(
			PageBean<ThematicPointCategory> thematicPointCategoryPage) {
		this.thematicPointCategoryPage = thematicPointCategoryPage;
	}

	public ThematicPointCategory getThematicPointCategory() {
		return thematicPointCategory;
	}

	public void setThematicPointCategory(ThematicPointCategory thematicPointCategory) {
		this.thematicPointCategory = thematicPointCategory;
	}
}
