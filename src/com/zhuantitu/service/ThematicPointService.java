package com.zhuantitu.service;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.geotools.geometry.jts.JTSFactoryFinder;
import org.springframework.stereotype.Component;

import com.system.utils.StringUtil;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Point;
import com.zhuantitu.dao.ThematicPointDao;
import com.zhuantitu.dao.ThematicPointImageDao;
import com.zhuantitu.dao.UserPointPermissionDao;
import com.zhuantitu.model.ThematicPoint;
import com.zhuantitu.model.ThematicPointImage;
import com.zhuantitu.model.UserPointPermission;
import com.zhuantitu.model.UserPointPermissionId;
@Component("thematicPointService")
public class ThematicPointService {
	@Resource(name="thematicPointDaoImpl")
	private ThematicPointDao thematicPointDao;
	@Resource(name="userPointPermissionDaoImpl")
	private UserPointPermissionDao userPointPermissionDao;
	@Resource(name="thematicPointImageDaoImpl")
	private ThematicPointImageDao thematicPointImageDao;
	public List<ThematicPoint> getByMenuidAndFloorid(int menuid,String floorid){
		return this.thematicPointDao
			.getByHQL("from ThematicPoint as t where t.thematicPointCategory.thematicMapMenu.menuid = " + menuid + " and t.floorid = '" + floorid + "'");
	}
	public ThematicPoint getById(Integer id) {
		return this.thematicPointDao.get(id);
	}
	public void delete(Integer id) {
		this.thematicPointDao.delete(id);
	}
	public void add(ThematicPoint thematicPoint, String lonlat, String images,String userid) {
		String [] temp = lonlat.replace("[", "").replace("]", "").split(",");
		Coordinate coordinate = new Coordinate(Double.parseDouble(temp[0]), Double.parseDouble(temp[1]));
		Point geom =  JTSFactoryFinder.getGeometryFactory( null ).createPoint(coordinate);
		thematicPoint.setGeom(geom);
		thematicPoint.setPosttime(new Date());
		thematicPoint = this.thematicPointDao.add(thematicPoint);
		UserPointPermissionId id = new UserPointPermissionId(userid, thematicPoint.getPointid());
		UserPointPermission userPointPermission = new UserPointPermission(id, thematicPoint, 1, 1);
		userPointPermissionDao.save(userPointPermission);
		
		if(StringUtil.isNotEmpty(images)){
			String[] imageArr = images.split(",");
			for(int i=0; i<imageArr.length; i++){
				ThematicPointImage thematicPointImage = new ThematicPointImage();
				thematicPointImage.setPath(imageArr[i]);
				thematicPointImage.setOrderid(i);
				thematicPointImage.setThematicPoint(thematicPoint);
				this.thematicPointImageDao.save(thematicPointImage);
			}
		}
	}
	public void update(ThematicPoint thematicPoint, String lonlat,String images, String userid) {
		String [] temp = lonlat.replace("[", "").replace("]", "").split(",");
		Coordinate coordinate = new Coordinate(Double.parseDouble(temp[0]), Double.parseDouble(temp[1]));
		Point geom =  JTSFactoryFinder.getGeometryFactory( null ).createPoint(coordinate);
		thematicPoint.setGeom(geom);
		thematicPoint.setPosttime(new Date());
		this.thematicPointDao.update(thematicPoint);
		if(StringUtil.isNotEmpty(images)){
			List<ThematicPointImage>  thematicPointImages = this.thematicPointImageDao.getByHQL("from ThematicPointImage as t where t.thematicPoint.pointid =" + thematicPoint.getPointid());
			for (ThematicPointImage thematicPointImage : thematicPointImages) {
				this.thematicPointImageDao.delete(thematicPointImage.getImageid());
			}
			String[] imageArr = images.split(",");
			for(int i=0; i<imageArr.length; i++){
				ThematicPointImage thematicPointImage = new ThematicPointImage();
				thematicPointImage.setPath(imageArr[i]);
				thematicPointImage.setOrderid(i);
				thematicPointImage.setThematicPoint(thematicPoint);
				this.thematicPointImageDao.save(thematicPointImage);
			}
		}
	}
	public List<ThematicPoint> loadByFidAndLocationid(String floorid, Integer id) {
		return this.thematicPointDao.getByHQL("from ThematicPoint as t where t.floorid = '" + floorid 
				+ "' and t.thematicLocation.locationid = " + id );
	}
	public List<Object[]> slectSearch(String frameLonlat, String floorid, int isSearchFloor) {
		String sql;
		System.out.println(isSearchFloor);
		if(isSearchFloor == 0){//室外
			sql = "select pointid, name, floorid, ip, nvr,channel,videousername,videopassword,type,column30  from zt_thematic_point where floorid = '" + floorid.substring(0, 4) + "00" +  "' and " +
					"(categoryid = 4 or categoryid = 5 ) and " +
					"ST_Contains(ST_MakePolygon(ST_GeomFromText('LINESTRING (" + frameLonlat + ")')) ,geom)";
		}else{//室内外
			sql = "select pointid, name, floorid, ip, nvr,channel,videousername,videopassword,type,column30 from zt_thematic_point where (floorid = '" + floorid.substring(0, 4) + "00" +  "' or floorid = '" + floorid + "') and " +
					"(categoryid = 4 or categoryid = 5 ) and " +
					"ST_Contains(ST_MakePolygon(ST_GeomFromText('LINESTRING (" + frameLonlat + ")')) ,geom)";
		}
		return this.thematicPointDao.getBySql(sql);
	}
	public List<ThematicPoint> getByLoctionidAndMid(Integer locationid, Integer mid) {
		return this.thematicPointDao.getByHQL("from ThematicPoint as t where  t.thematicPointCategory.thematicMapMenu.menuid = " + mid 
				+ " and t.thematicLocation.locationid = " + locationid + " order by t.floorid" );
	}
	public List<ThematicPoint> getByHql(String hql) {
		return this.thematicPointDao.getByHQL(hql);
	}
	public void updateTimeByMeterid(int meterid, String finishTime) {
		ThematicPoint thematicPoint = this.thematicPointDao.getUniqueByProperty("column25", meterid+"");
		thematicPoint.setColumn22(finishTime);
		this.thematicPointDao.update(thematicPoint);
		
	}
}
