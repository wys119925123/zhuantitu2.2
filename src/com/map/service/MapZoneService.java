package com.map.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.map.dao.MapZoneDao;
import com.map.model.MapZone;
@Component("mapZoneService")
public class MapZoneService {
	@Resource(name="mapZoneDaoImpl")
	private MapZoneDao mapZoneDao;
	
	public MapZone getById(String id){
		return this.mapZoneDao.get(id);
	}
	
	public List<MapZone> getByHql(String hql) {
		return this.mapZoneDao.getByHQL(hql);
	}
	public void update(MapZone mapZone){
		this.mapZoneDao.merge(mapZone);
	}
	
	public MapZone getUniqueByPropertys(String[] propertyNames, Object[] values){
		return this.mapZoneDao.getUniqueByPropertys(propertyNames, values);
	}
}
