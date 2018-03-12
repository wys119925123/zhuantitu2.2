package com.zhuantitu.action;
import javax.annotation.Resource;
import com.system.action.BaseAction;
import com.system.utils.PageBean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.zhuantitu.model.UserMenuPermission;
import com.zhuantitu.service.UserMenuPermissionService;
/**
 * 
 * @author LH
 * @since 1.0
 */
@Controller("userMenuPermissionAction")
@Scope("prototype")
public class UserMenuPermissionAction extends BaseAction {
	@Resource
	private UserMenuPermissionService userMenuPermissionService;
	private PageBean<UserMenuPermission> userMenuPermissionPage;
	private UserMenuPermission userMenuPermission;
}
