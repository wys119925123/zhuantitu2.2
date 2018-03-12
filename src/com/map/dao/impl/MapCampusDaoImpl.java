package com.map.dao.impl;

import org.springframework.stereotype.Component;

import com.map.dao.MapCampusDao;
import com.map.model.MapCampus;
import com.system.dao.impl.BaseDaoSupport;
@Component("mapCampusDaoImpl")
public class MapCampusDaoImpl extends BaseDaoSupport<MapCampus> implements
		MapCampusDao {

}
