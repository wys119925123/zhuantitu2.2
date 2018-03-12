package com.zhuantitu.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Component;

import com.system.dao.impl.BaseDaoSupport;
import com.system.utils.PageBean;
import com.system.utils.StringUtil;
import com.zhuantitu.dao.MeterDayStatisticsDao;
import com.zhuantitu.model.MeterDayStatistics;
@Component("meterDayStatisticsDaoImpl")
public class MeterDayStatisticsDaoImpl extends
		BaseDaoSupport<MeterDayStatistics> implements MeterDayStatisticsDao {
	@SuppressWarnings({ "unchecked", "static-access" })
	public PageBean<?> groupPageQuery(String floorids,String metertype,int locationid,String startTime,String flishTime, int pageSize,int page) {
		StringBuffer sqlBuf = new StringBuffer();
		StringBuffer hqlBuf = new StringBuffer();
		sqlBuf.append("select count(*) from (select sum(t.energy_value) as data ,t.start_time from zt_meter_day_statistics as t ")
			.append(" where t.metertype = '"  + metertype + "'")
			.append("and t.locationid = " + locationid +" ");
		hqlBuf.append("select sum(t.energyValue) as data ,t.id.startTime from MeterDayStatistics as t ")
			.append(" where t.metertype = '"  + metertype + "'")
			.append("and t.locationid = " + locationid +" ");
		if(StringUtil.isNotEmpty(floorids)){
			sqlBuf.append(" and t.floorid in(" + floorids +  ") ");
			hqlBuf.append(" and t.floorid in(" + floorids +  ") ");
		}
		if(StringUtil.isNotEmpty(startTime)){
			sqlBuf.append("and t.start_time >='" + startTime+"' ");
			hqlBuf.append("and t.id.startTime >='" + startTime+"' ");
		}
		if(StringUtil.isNotEmpty(flishTime)){
			sqlBuf.append("and t.start_time <= '" + flishTime + "' ");
			hqlBuf.append("and t.id.startTime <= '" + flishTime + "' ");
		}
		sqlBuf.append("GROUP BY t.start_time ORDER BY t.start_time desc) as t");
		hqlBuf.append("GROUP BY t.id.startTime ORDER BY t.id.startTime desc");
		final String sql = sqlBuf.toString();
		final String hql = hqlBuf.toString();
		
		System.out.println(sql);
		int allRow = 0;
		List count = (List)getHibernateTemplate().executeFind(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException,SQLException{
				return session.createSQLQuery(sql).list();
			}
		});
		if(count.size() > 0) {
			allRow = Integer.parseInt(count.get(0).toString());
		}
		
		int totalPage = PageBean.countTotalPage(pageSize, allRow);
		if(page > totalPage){page = totalPage;}
		int pageStartR = PageBean.countOffset(pageSize, page);
		if(pageStartR < 0){pageStartR = 0;}
		final int offset = pageStartR;
		final int length = pageSize;
		PageBean pageBean = new PageBean();
		pageBean.setTotalPage(totalPage);
		final int currentPage = PageBean.countCurrentPage(page);
		List<?> list = (List<?>)getHibernateTemplate().executeFind(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException,SQLException{
				List<?> result = (List<?>)session
									.createQuery(hql)
									.setFirstResult(offset)
									.setMaxResults(length)
									.list();
				return result;
			}
		});
		pageBean.setPageSize(pageSize);
		pageBean.setCurrentPage(currentPage);
		pageBean.setAllRow(allRow);
		pageBean.setTotalPage(totalPage);
		pageBean.setList(list);
		pageBean.init();
		return pageBean;
	}
}
