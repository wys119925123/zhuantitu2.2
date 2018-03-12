package com.zhuantitu.action;
import javax.annotation.Resource;
import com.system.action.BaseAction;
import com.system.utils.PageBean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.zhuantitu.model.UserPointPermission;
import com.zhuantitu.service.UserPointPermissionService;
/**
 * 
 * @author LH
 * @since 1.0
 */
@Controller("userPointPermissionAction")
@Scope("prototype")
public class UserPointPermissionAction extends BaseAction {
	@Resource
	private UserPointPermissionService userPointPermissionService;
	private PageBean<UserPointPermission> userPointPermissionPage;
	private UserPointPermission userPointPermission;
}
