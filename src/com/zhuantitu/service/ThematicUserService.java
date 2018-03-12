package com.zhuantitu.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.zhuantitu.dao.ThematicUserDao;
import com.zhuantitu.model.ThematicUser;

@Component("thematicUserService")
public class ThematicUserService {
	@Resource(name="thematicUserDaoImpl")
	private ThematicUserDao thematicUserDao;
	public ThematicUser getByUserid(String name) {
		return this.thematicUserDao.getUniqueByProperty("userid", name);
	}
	
}
