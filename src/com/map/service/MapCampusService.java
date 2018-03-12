package com.map.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.map.dao.MapCampusDao;
import com.map.model.MapCampus;
import com.system.utils.PageBean;

@Component("mapCampusService")
public class MapCampusService {
	@Resource(name="mapCampusDaoImpl")
	private MapCampusDao mapCampusDao;
	public List<MapCampus> getByHql(String hql){
		return this.mapCampusDao.getByHQL(hql);
	}
	public MapCampus getById(Integer id){
		return this.mapCampusDao.get(id);
	}
	public PageBean<MapCampus> loadPage(String hql, int pageSize, int page){
		return this.mapCampusDao.pageQuery(hql, pageSize, page);
	}
	public void update(MapCampus mapCampus){
		this.mapCampusDao.update(mapCampus);
	}
	public void add(MapCampus mapCampus){
		this.mapCampusDao.save(mapCampus);
	}
	public MapCampus getDefaultMapCampus(){
		return this.mapCampusDao.getUniqueByProperty("isDefault", 1);
	}
}
