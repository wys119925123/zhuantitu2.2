package com.zhuantitu.action;
import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.system.action.BaseAction;
import com.zhuantitu.service.ThematicMapMenuService;
/**
 * 
 * @author LH
 * @since 1.0
 */
@Controller("thematicMapMenuAction")
@Scope("prototype")
public class ThematicMapMenuAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2953396515192606029L;
	@Resource
	private ThematicMapMenuService thematicMapMenuService;
	
}
