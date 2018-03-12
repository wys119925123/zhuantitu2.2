package com.zhuantitu.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.zhuantitu.dao.UserPolylinePermissionDao;
import com.zhuantitu.model.UserPolylinePermission;

@Component("userPolylinePermissionService")
public class UserPolylinePermissionService {
	@Resource(name="userPolylinePermissionDaoImpl")
	private UserPolylinePermissionDao userPolylinePermissionDao;
	public List<UserPolylinePermission> getByUserid(String userid){
		return this.userPolylinePermissionDao.getByHQL("from UserPolylinePermission as t where t.id.userid = '" + userid + "'");
	}
}
