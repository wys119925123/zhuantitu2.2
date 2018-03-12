package com.zhuantitu.dao.impl;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Component;

import com.system.dao.impl.BaseDaoSupport;
import com.zhuantitu.dao.ThematicPointDao;
import com.zhuantitu.model.ThematicPoint;
@Component("thematicPointDaoImpl")
public class ThematicPointDaoImpl extends BaseDaoSupport<ThematicPoint> implements
		ThematicPointDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getBySql(final String sql) {
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public List<Object[]> doInHibernate(Session session) throws HibernateException,
					SQLException {
				return session.createSQLQuery(sql).list();
			}
		});
	}
}
