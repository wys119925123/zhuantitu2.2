package com.zhuantitu.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.zhuantitu.dao.ThematicPolylineEquipmentImageDao;
import com.zhuantitu.model.ThematicPolylineEquipmentImage;
@Component("thematicPolylineEquipmentImageService")
public class ThematicPolylineEquipmentImageService {
	@Resource(name="thematicPolylineEquipmentImageDaoImpl")
	private  ThematicPolylineEquipmentImageDao thematicPolylineEquipmentImageDao;
	public List<ThematicPolylineEquipmentImage> getByEquipmentId(Integer id) {

		return this.thematicPolylineEquipmentImageDao.getByHQL("from ThematicPolylineEquipmentImage as t where t.thematicPolylineEquipment.id = " + id + " order by t.orderid");
	}

}
