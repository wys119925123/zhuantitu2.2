package com.map.dao.impl;

import org.springframework.stereotype.Component;

import com.map.dao.MapZoneDao;
import com.map.model.MapZone;
import com.system.dao.impl.BaseDaoSupport;
@Component("mapZoneDaoImpl")
public class MapZoneDaoImpl extends BaseDaoSupport<MapZone> implements
		MapZoneDao {

}
