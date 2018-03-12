package com.zhuantitu.dao;
import java.util.List;

import com.system.dao.BaseDao;
import com.zhuantitu.model.ThematicPoint;
public interface ThematicPointDao extends BaseDao<ThematicPoint> {
	public List<Object[]> getBySql(String sql);
}
