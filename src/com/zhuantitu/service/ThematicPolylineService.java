package com.zhuantitu.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.system.utils.GisUtils;
import com.system.utils.PageBean;
import com.system.utils.StringUtil;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.MultiLineString;
import com.zhuantitu.dao.ThematicPolylineDao;
import com.zhuantitu.dao.ThematicPolylineImageDao;
import com.zhuantitu.dao.UserPolylinePermissionDao;
import com.zhuantitu.model.ThematicPolyline;
import com.zhuantitu.model.ThematicPolylineImage;
import com.zhuantitu.model.UserPolylinePermission;
import com.zhuantitu.model.UserPolylinePermissionId;

@Component("thematicPolylineService")
public class ThematicPolylineService {
	@Resource(name="thematicPolylineDaoImpl")
	private ThematicPolylineDao thematicPolylineDao;
	@Resource(name="userPolylinePermissionDaoImpl")
	private UserPolylinePermissionDao userPolylinePermissionDao;
	@Resource(name="thematicPolylineImageDaoImpl")
	private ThematicPolylineImageDao thematicPolylineImageDao;
	public List<ThematicPolyline> getByMenuidAndFloorid(int menuid,String floorid){
		return this.thematicPolylineDao
			.getByHQL("from ThematicPolyline as t where t.thematicPolylineCategory.thematicMapMenu.menuid = " + menuid + " and t.floorid = '" + floorid + "'");
	}
	public ThematicPolyline getById(Integer id) {
		return this.thematicPolylineDao.get(id);
	}
	public void delete(Integer id) {
		this.thematicPolylineDao.delete(id);
	}
	public void add(ThematicPolyline thematicPolyline, String lonlat, String images,String userid) {
		
		MultiLineString geom = GisUtils.createMultiLineString(new LineString[]{GisUtils.createLineStringByWKT(lonlat)});
		thematicPolyline.setGeom(geom);
		thematicPolyline.setPosttime(new Date());
		thematicPolyline = this.thematicPolylineDao.add(thematicPolyline);
		UserPolylinePermissionId id = new UserPolylinePermissionId(userid, thematicPolyline.getPolylineid());
		UserPolylinePermission userPolylinePermission = new UserPolylinePermission(id, thematicPolyline, 1, 1);
		userPolylinePermissionDao.save(userPolylinePermission);
		
		if(StringUtil.isNotEmpty(images)){
			String[] imageArr = images.split(",");
			for(int i=0; i<imageArr.length; i++){
				ThematicPolylineImage thematicPolylineImage = new ThematicPolylineImage();
				thematicPolylineImage.setPath(imageArr[i]);
				thematicPolylineImage.setOrderid(i);
				thematicPolylineImage.setThematicPolyline(thematicPolyline);
				this.thematicPolylineImageDao.save(thematicPolylineImage);
			}
		}
	}
	public void update(ThematicPolyline thematicPolyline, String lonlat,String images, String userid) {
		MultiLineString geom = GisUtils.createMultiLineString(new LineString[]{GisUtils.createLineStringByWKT(lonlat)});
		thematicPolyline.setGeom(geom);
		thematicPolyline.setPosttime(new Date());
		this.thematicPolylineDao.update(thematicPolyline);
		if(StringUtil.isNotEmpty(images)){
			List<ThematicPolylineImage>  thematicPolylineImages = this.thematicPolylineImageDao.getByHQL("from ThematicPolylineImage as t where t.thematicPolyline.polylineid =" + thematicPolyline.getPolylineid());
			for (ThematicPolylineImage thematicPolylineImage : thematicPolylineImages) {
				this.thematicPolylineImageDao.delete(thematicPolylineImage.getImageid());
			}
			String[] imageArr = images.split(",");
			for(int i=0; i<imageArr.length; i++){
				ThematicPolylineImage thematicPolylineImage = new ThematicPolylineImage();
				thematicPolylineImage.setPath(imageArr[i]);
				thematicPolylineImage.setOrderid(i);
				thematicPolylineImage.setThematicPolyline(thematicPolyline);
				this.thematicPolylineImageDao.save(thematicPolylineImage);
			}
		}
	}
	public List<ThematicPolyline> getByHql(String hql) {
		return this.thematicPolylineDao.getByHQL(hql);
	}
	public PageBean<ThematicPolyline> loadPage(String hql, int pageSize, int page) {
		return this.thematicPolylineDao.pageQuery(hql, pageSize, page);
	}
	public void update(ThematicPolyline thematicPolyline) {
		this.thematicPolylineDao.update(thematicPolyline);
	}
	public void bulkDelete(String ids) {
		if(ids.length()>0){
			String[] strs = ids.split(",");
			for (int i=0; i< strs.length; i++) {
				this.thematicPolylineDao.delete(Integer.parseInt(strs[i]));
			}
		}
	}
	public void update(ThematicPolyline thematicPolyline, String images,
			String imageNames) {
		ThematicPolyline polyline = this.thematicPolylineDao.get(thematicPolyline.getPolylineid());
		polyline.setName(thematicPolyline.getName());
		polyline.setColumn1(thematicPolyline.getColumn1());
		polyline.setColumn2(thematicPolyline.getColumn2());
		polyline.setColumn3(thematicPolyline.getColumn3());
		polyline.setColumn4(thematicPolyline.getColumn4());
		polyline.setColumn5(thematicPolyline.getColumn5());
		polyline.setColumn6(thematicPolyline.getColumn6());
		polyline.setColumn7(thematicPolyline.getColumn7());
		polyline.setColumn8(thematicPolyline.getColumn8());
		polyline.setColumn9(thematicPolyline.getColumn9());
		polyline.setColumn10(thematicPolyline.getColumn10());
		polyline.setColumn11(thematicPolyline.getColumn11());
		polyline.setColumn12(thematicPolyline.getColumn12());
		polyline.setColumn13(thematicPolyline.getColumn13());
		polyline.setColumn14(thematicPolyline.getColumn14());
		polyline.setColumn15(thematicPolyline.getColumn15());
		polyline.setColumn16(thematicPolyline.getColumn16());
		polyline.setColumn17(thematicPolyline.getColumn17());
		polyline.setColumn18(thematicPolyline.getColumn18());
		polyline.setColumn19(thematicPolyline.getColumn19());
		polyline.setColumn20(thematicPolyline.getColumn20());
		polyline.setColumn21(thematicPolyline.getColumn21());
		polyline.setColumn22(thematicPolyline.getColumn22());
		polyline.setColumn23(thematicPolyline.getColumn23());
		polyline.setColumn24(thematicPolyline.getColumn24());
		polyline.setColumn25(thematicPolyline.getColumn25());
		polyline.setColumn26(thematicPolyline.getColumn26());
		polyline.setColumn27(thematicPolyline.getColumn27());
		polyline.setColumn28(thematicPolyline.getColumn28());
		polyline.setColumn29(thematicPolyline.getColumn29());
		polyline.setColumn30(thematicPolyline.getColumn30());
		this.thematicPolylineDao.update(polyline);
		if(StringUtil.isNotEmpty(images)){
			List<ThematicPolylineImage> imageList = this.thematicPolylineImageDao
						.getByHQL("from ThematicPolylineImage as t where t.thematicPolyline.polylineid = " + polyline.getPolylineid()+"");
			for (ThematicPolylineImage thematicPolylineImage : imageList) {
				this.thematicPolylineImageDao.delete(thematicPolylineImage.getImageid());
			}
			String[] imagesArr = images.split(",");
			String[] imageNamesArr = imageNames.split(",");
			for(int i = 0 ; i < imagesArr.length; i++){
				ThematicPolylineImage image = new ThematicPolylineImage();
				image.setThematicPolyline(polyline);
				image.setPath(imagesArr[i].trim());
				image.setName(imageNamesArr[i].trim());
				image.setOrderid(999);
				this.thematicPolylineImageDao.save(image);
			}
		}
		
	}
}
