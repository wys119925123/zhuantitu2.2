package com.zhuantitu.service;

import javax.annotation.Resource;

import com.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Component;

import com.zhuantitu.dao.ThematicUserDao;
import com.zhuantitu.model.ThematicUser;

import java.util.List;
import java.util.Map;

@Component("thematicUserService")
public class ThematicUserService extends CommonServiceImpl implements IThematicUserService{
//	@Resource(name="thematicUserDaoImpl")
//	private ThematicUserDao thematicUserDao;
	public ThematicUser getByUserid(String name) {
		//return this.thematicUserDao.getUniqueByProperty("userid", name);
		ThematicUser user = this.getEntity(ThematicUser.class, name);
		return user;
	}

	@Override
	public Map<String, Object> getUserInfo(String userId) {
		String sql = "select * from zt_thematic_user where userid = ?";
		List<Map<String, Object>> list =  this.findForJdbc(sql, userId);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public void initAvatar(String userId, String avatar) {
		String sql = "update zt_thematic_user set avatar = ? where userid = ?";
		this.executeSql(sql, avatar, userId);
	}
}
