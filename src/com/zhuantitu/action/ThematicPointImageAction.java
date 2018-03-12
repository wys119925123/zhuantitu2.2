package com.zhuantitu.action;
import javax.annotation.Resource;
import com.system.action.BaseAction;
import com.system.utils.PageBean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.zhuantitu.model.ThematicPointImage;
import com.zhuantitu.service.ThematicPointImageService;
/**
 * 
 * @author LH
 * @since 1.0
 */
@Controller("thematicPointImageAction")
@Scope("prototype")
public class ThematicPointImageAction extends BaseAction {
	@Resource
	private ThematicPointImageService thematicPointImageService;
	private PageBean<ThematicPointImage> thematicPointImagePage;
	private ThematicPointImage thematicPointImage;
}
