package com.zhuantitu.dao;

import com.system.dao.BaseDao;
import com.system.utils.PageBean;
import com.zhuantitu.model.MeterDayStatistics;

public interface MeterDayStatisticsDao extends BaseDao<MeterDayStatistics> {
	public PageBean<?> groupPageQuery(String floorids,String metertype,int locationid,String startTime,String flishTime, int pageSize,int page);
}
