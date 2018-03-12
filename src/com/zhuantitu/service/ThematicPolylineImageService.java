package com.zhuantitu.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.zhuantitu.dao.ThematicPolylineImageDao;
import com.zhuantitu.model.ThematicPolylineImage;
@Component("thematicPolylineImageService")
public class ThematicPolylineImageService {
	@Resource(name="thematicPolylineImageDaoImpl")
	private ThematicPolylineImageDao thematicPolylineImageDao;
	public List<ThematicPolylineImage> getByPolylineid(Integer id) {
		return this.thematicPolylineImageDao.getByHQL("from ThematicPolylineImage as t where t.thematicPolyline.polylineid = " + id + " order by t.orderid");
	}
}
