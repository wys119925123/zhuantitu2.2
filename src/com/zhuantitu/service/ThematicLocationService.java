package com.zhuantitu.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.zhuantitu.dao.ThematicLocationDao;
import com.zhuantitu.model.ThematicLocation;

@Component("thematicLocationService")
public class ThematicLocationService {
	@Resource(name="thematicLocationDaoImpl")
	private ThematicLocationDao thematicLocationDao;
	
	public List<ThematicLocation> getByHql(String hql){
		return this.thematicLocationDao.getByHQL(hql);
	}
	public List<ThematicLocation> getParentLocation(String zoneid){
		return getByHql("from ThematicLocation as  t where t.zoneid = '" + zoneid + "' and t.parentThematicLocation.locationid is null order by t.orderid ");
	}
	public List<ThematicLocation> getChildLocation(String zoneid){
		return getByHql("from ThematicLocation as  t where t.parentThematicLocation.locationid is not null and t.zoneid = '" + zoneid + "' order by t.orderid ");
	}
	public List<ThematicLocation> getChildLocationByPid(Integer pid) {
		return getByHql("from ThematicLocation as  t where t.parentThematicLocation.locationid = " + pid + " order by t.orderid ");
	}
	
}
