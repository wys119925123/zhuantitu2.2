package com.zhuantitu.service;
import java.util.List;

import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import com.zhuantitu.dao.ThematicMapMenuDao;
import com.zhuantitu.model.ThematicMapMenu;
@Component("thematicMapMenuService")
public class ThematicMapMenuService {
	@Resource(name="thematicMapMenuDaoImpl")
	private ThematicMapMenuDao thematicMapMenuDao;

	public List<ThematicMapMenu> getByParentMenuid(Integer menuid) {
		return this.thematicMapMenuDao.getByHQL("from ThematicMapMenu as t where t.parentThematicMapMenu.menuid = " + menuid + " order by t.orderid");
	}

	public ThematicMapMenu getById(Integer menuid) {
		return this.thematicMapMenuDao.get(menuid);
	}

	public List<ThematicMapMenu> getByHql(String hql) {
		return this.thematicMapMenuDao.getByHQL(hql);
	}
}
