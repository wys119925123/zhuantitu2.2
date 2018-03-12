package com.zhuantitu.dao.impl;
import org.springframework.stereotype.Component;
import com.system.dao.impl.BaseDaoSupport;
import com.zhuantitu.dao.UserMenuPermissionDao;
import com.zhuantitu.model.UserMenuPermission;
@Component("userMenuPermissionDaoImpl")
public class UserMenuPermissionDaoImpl extends BaseDaoSupport<UserMenuPermission> implements
		UserMenuPermissionDao {
}
