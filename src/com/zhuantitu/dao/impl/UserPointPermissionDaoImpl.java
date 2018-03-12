package com.zhuantitu.dao.impl;
import org.springframework.stereotype.Component;
import com.system.dao.impl.BaseDaoSupport;
import com.zhuantitu.dao.UserPointPermissionDao;
import com.zhuantitu.model.UserPointPermission;
@Component("userPointPermissionDaoImpl")
public class UserPointPermissionDaoImpl extends BaseDaoSupport<UserPointPermission> implements
		UserPointPermissionDao {
}
