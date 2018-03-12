package com.zhuantitu.service;
import java.util.List;

import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import com.zhuantitu.dao.ThematicPointImageDao;
import com.zhuantitu.model.ThematicPointImage;
@Component("thematicPointImageService")
public class ThematicPointImageService {
	@Resource(name="thematicPointImageDaoImpl")
	private ThematicPointImageDao thematicPointImageDao;

	public List<ThematicPointImage> getByPointid(Integer id) {
		return this.thematicPointImageDao.getByHQL("from ThematicPointImage as t where t.thematicPoint.pointid = " + id + " order by t.orderid");
	}
}
