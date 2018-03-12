package com.zhuantitu.service;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.system.utils.PageBean;
import com.system.utils.StringUtil;
import com.zhuantitu.dao.MeterDayStatisticsDao;
import com.zhuantitu.model.MeterDayStatistics;
@Component("meterDayStatisticsService")
public class MeterDayStatisticsService {
	@Resource(name="meterDayStatisticsDaoImpl")
	private MeterDayStatisticsDao meterDayStatisticsDao;
	
	public void merge(MeterDayStatistics meterDayStatistics){
		this.meterDayStatisticsDao.merge(meterDayStatistics);
	}
	public List<MeterDayStatistics> getByHql(String hql){
		return  this.meterDayStatisticsDao.getByHQL(hql);
	}
	public PageBean<MeterDayStatistics> loadPage(String hql, int pageSize, int page){
		return this.meterDayStatisticsDao.pageQuery(hql, pageSize, page);
	}
	public double sumDay(int locationid, String day,String metertype){
		Object o = this.meterDayStatisticsDao.getByHQL("select sum(t.energyValue) from MeterDayStatistics as t where t.locationid = " + locationid +
				" and t.metertype = '" + metertype + "'" +
				" and t.id.startTime = '" + day +"'").get(0);
		if(o != null){
			BigDecimal b = new BigDecimal(String.valueOf(o));
			return b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(); 
		}else{
			return 0;
		}
	}
	public double sumBetweenDay(int locationid, String startTime, String finishTime,String metertype){
		Object o = this.meterDayStatisticsDao
			.getByHQL("select sum(t.energyValue) from MeterDayStatistics as t where t.locationid = " + locationid + 
				" and t.metertype = '" + metertype + "'" +
				" and t.id.startTime >= '" + startTime +"' and t.finishTime <= '" + finishTime + "'").get(0);
		if(o != null){
			BigDecimal b = new BigDecimal(String.valueOf(o));
			return b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(); 
		}else{
			return 0;
		}
	}
	public PageBean<MeterDayStatistics> sumByMeteridAndBetwenTime(int meterid, String startTime, String finishTime,int pageSize, int page){
		String hql = "from MeterDayStatistics as t where t.id.meterid = " + meterid;
		if(StringUtil.isNotEmpty(startTime)){
			hql += " and t.id.startTime >= '" + startTime + "' ";
		}
		if(StringUtil.isNotEmpty(finishTime)){
			hql += " and t.id.startTime <='" + finishTime + "'";
		}
		hql += " order by t.id.startTime desc";
		return this.meterDayStatisticsDao
			.pageQuery(hql,
					pageSize, page);
	}
	public double sumByMeteridAndTime(int meterid,String time){
		Object o = this.meterDayStatisticsDao
			.getByHQL("select sum(t.energyValue) from MeterDayStatistics as t where t.id.startTime = '" + time +"' " +
					"and t.id.meterid = " + meterid).get(0);
		if(o != null){
			BigDecimal b = new BigDecimal(String.valueOf(o));
			return b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(); 
		}else{
			return 0;
		}
	}
	public PageBean<?> pageByHql(String floorids, int locationid, String metertype,String startTime,String flishTime,int pageSize,int page){
		return this.meterDayStatisticsDao
			.groupPageQuery(floorids,metertype,locationid,startTime,flishTime, pageSize, page);
	}
}
