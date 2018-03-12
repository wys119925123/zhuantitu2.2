package com.zhuantitu.service;
import java.util.List;

import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import com.zhuantitu.dao.ThematicPointCategoryDao;
import com.zhuantitu.model.ThematicPointCategory;
@Component("thematicPointCategoryService")
public class ThematicPointCategoryService {
	@Resource(name="thematicPointCategoryDaoImpl")
	private ThematicPointCategoryDao thematicPointCategoryDao;

	public List<ThematicPointCategory> getByMenuid(Integer menuid) {
		return this.thematicPointCategoryDao
			.getByHQL("from ThematicPointCategory as t where t.thematicMapMenu.menuid = " + menuid + " order by t.orderid");
	}
}
