package com.zhuantitu.service;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.zhuantitu.dao.UserPointPermissionDao;
import com.zhuantitu.model.UserPointPermission;
@Component("userPointPermissionService")
public class UserPointPermissionService {
	@Resource(name="userPointPermissionDaoImpl")
	private UserPointPermissionDao userPointPermissionDao;
	public List<UserPointPermission> getByUserid(String userid){
		return this.userPointPermissionDao.getByHQL("from UserPointPermission as t where t.id.userid = '" + userid + "'");
	}
}
