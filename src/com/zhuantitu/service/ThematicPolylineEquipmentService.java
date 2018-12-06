package com.zhuantitu.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.system.utils.GisUtils;
import com.system.utils.StringUtil;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.MultiLineString;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.zhuantitu.dao.ThematicPolylineEquipmentDao;
import com.zhuantitu.dao.ThematicPolylineEquipmentImageDao;
import com.zhuantitu.model.ThematicPolylineEquipment;
import com.zhuantitu.model.ThematicPolylineEquipmentImage;
import com.zhuantitu.model.ThematicPolylineImage;

@Component("thematicPolylineEquipmentService")
public class ThematicPolylineEquipmentService {
	@Resource(name="thematicPolylineEquipmentDaoImpl")
	private ThematicPolylineEquipmentDao thematicPolylineEquipmentDao;
	@Resource(name="thematicPolylineEquipmentImageDaoImpl")
	private ThematicPolylineEquipmentImageDao thematicPolylineEquipmentImageDao;
	
	public List<ThematicPolylineEquipment> getByMenuidAndFloorid(int menuid,String floorid){
		return this.thematicPolylineEquipmentDao
			.getByHQL("from ThematicPolylineEquipment as t where t.thematicMapMenu.menuid = " + menuid + " and t.floorid = '" + floorid + "'");
	}
	public ThematicPolylineEquipment getById(Integer id) {
		return this.thematicPolylineEquipmentDao.get(id);
	}
	public void delete(Integer id) {
		this.thematicPolylineEquipmentDao.delete(id);
	}
	public void add(ThematicPolylineEquipment thematicPolylineEquipment,
			String lonlat, String images) {
		MultiPolygon geom = GisUtils.createMultiPolygonByWKT(lonlat);
		thematicPolylineEquipment.setGeom(geom);
		thematicPolylineEquipment.setPosttime(new Date());
		thematicPolylineEquipment = this.thematicPolylineEquipmentDao.add(thematicPolylineEquipment);
		if(StringUtil.isNotEmpty(images)){
			String[] imageArr = images.split(",");
			for(int i=0; i<imageArr.length; i++){
				ThematicPolylineEquipmentImage thematicPolylineEquipmentImage = new ThematicPolylineEquipmentImage();
				thematicPolylineEquipmentImage.setPath(imageArr[i]);
				thematicPolylineEquipmentImage.setOrderid(i);
				thematicPolylineEquipmentImage.setThematicPolylineEquipment(thematicPolylineEquipment);
				this.thematicPolylineEquipmentImageDao.save(thematicPolylineEquipmentImage);
			}
		}
		
		
	}
	public void update(ThematicPolylineEquipment thematicPolylineEquipment,
			String lonlat, String images) {
		MultiPolygon geom = GisUtils.createMultiPolygonByWKT(lonlat);
		thematicPolylineEquipment.setGeom(geom);
		thematicPolylineEquipment.setPosttime(new Date());
		this.thematicPolylineEquipmentDao.update(thematicPolylineEquipment);
		if(StringUtil.isNotEmpty(images)){
			List<ThematicPolylineEquipmentImage>  thematicPolylineEquipmentImages = this.thematicPolylineEquipmentImageDao.getByHQL("from ThematicPolylineEquipmentImage as t where t.thematicPolylineEquipment.id =" + thematicPolylineEquipment.getId());
			for (ThematicPolylineEquipmentImage thematicPolylineEquipmentImage : thematicPolylineEquipmentImages) {
				this.thematicPolylineEquipmentImageDao.delete(thematicPolylineEquipmentImage.getImageid());
			}
			String[] imageArr = images.split(",");
			for(int i=0; i<imageArr.length; i++){
				ThematicPolylineEquipmentImage thematicPolylineEquipmentImage = new ThematicPolylineEquipmentImage();
				thematicPolylineEquipmentImage.setPath(imageArr[i]);
				thematicPolylineEquipmentImage.setOrderid(i);
				thematicPolylineEquipmentImage.setThematicPolylineEquipment(thematicPolylineEquipment);
				this.thematicPolylineEquipmentImageDao.save(thematicPolylineEquipmentImage);
			}
		}
		
	}


}
