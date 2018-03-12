package com.zhuantitu.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.zhuantitu.dao.UserPolygonPermissionDao;
import com.zhuantitu.model.UserPolygonPermission;

@Component("userPolygonPermissionService")
public class UserPolygonPermissionService {
	@Resource(name="userPolygonPermissionDaoImpl")
	private UserPolygonPermissionDao userPolygonPermissionDao;
	public List<UserPolygonPermission> getByUserid(String userid){
		return this.userPolygonPermissionDao.getByHQL("from UserPolygonPermission as t where t.id.userid = '" + userid + "'");
	}
}
