package com.zhuantitu.service;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.zhuantitu.dao.UserMenuPermissionDao;
import com.zhuantitu.model.UserMenuPermission;
@Component("userMenuPermissionService")
public class UserMenuPermissionService {
	@Resource(name="userMenuPermissionDaoImpl")
	private UserMenuPermissionDao userMenuPermissionDao;
	public List<UserMenuPermission> getByUserid(String userid){
		return this.userMenuPermissionDao.getByHQL("from UserMenuPermission as t where t.id.userid = '" + userid +"'");
	}
	public boolean hasPermission(String username) {
		List<UserMenuPermission> list= this.userMenuPermissionDao.getByHQL("from UserMenuPermission as t where t.id.userid = '" + username +"' and (t.addcodePermission = 1 or t.viewPermission = 1)");
		if(list.size() > 0){
			return true;
		}else{
			return false;
		}
	}
}
