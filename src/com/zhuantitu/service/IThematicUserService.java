package com.zhuantitu.service;


import java.util.Map;

import com.common.service.CommonService;
import com.zhuantitu.model.ThematicUser;

public interface IThematicUserService extends CommonService{
	/**
	 * 根据主键获取用户信息
	 * @param userId
	 * @return
	 */
	ThematicUser getByUserid(String userid);
	
	/**
	 * 根据主键获取用户信息
	 * @param userId
	 * @return
	 */
	Map<String, Object> getUserInfo(String userId);
	
	/**
	 * 根据主键更新头像信息
	 * @param userId
	 * @param avatar
	 */
	void initAvatar(String userId, String avatar);
}
