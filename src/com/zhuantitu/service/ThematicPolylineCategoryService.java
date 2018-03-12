package com.zhuantitu.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.system.utils.PageBean;
import com.zhuantitu.dao.TableClumnDefineDao;
import com.zhuantitu.dao.ThematicPolylineCategoryDao;
import com.zhuantitu.model.TableClumnDefine;
import com.zhuantitu.model.TableClumnDefineId;
import com.zhuantitu.model.ThematicPolylineCategory;
@Component("thematicPolylineCategoryService")
public class ThematicPolylineCategoryService {
	@Resource(name="thematicPolylineCategoryDaoImpl")
	private ThematicPolylineCategoryDao thematicPolylineCategoryDao;
	
	private static List<TableClumnDefine> thematicPolylineClumnList = null;
	@Resource(name="tableClumnDefineDaoImpl")
	private TableClumnDefineDao tableClumnDefineDao;
	public List<ThematicPolylineCategory> getByMenuid(Integer menuid) {
		return this.thematicPolylineCategoryDao
			.getByHQL("from ThematicPolylineCategory as t where t.thematicMapMenu.menuid = " + menuid + " order by t.orderid");
	}

	public PageBean<ThematicPolylineCategory> loadPage(String hql, int pageSize,
			int page) {
		return this.thematicPolylineCategoryDao.pageQuery(hql, pageSize, page);
	}

	public void update(ThematicPolylineCategory thematicPolylineCategory) {
		this.thematicPolylineCategoryDao.update(thematicPolylineCategory);
	}

	public ThematicPolylineCategory getById(Integer id) {
		return this.thematicPolylineCategoryDao.get(id);
	}

	public void save(ThematicPolylineCategory thematicPolylineCategory) {
		thematicPolylineCategory = this.thematicPolylineCategoryDao.add(thematicPolylineCategory);
		Integer cid = thematicPolylineCategory.getCategoryid();
		List<TableClumnDefine> tableClumnDefines = this.tableClumnDefineDao.getByHQL("from TableClumnDefine as t where t.id.categoryid = " + cid);
		for (TableClumnDefine tableClumnDefine : tableClumnDefines) {
			this.tableClumnDefineDao.delete(tableClumnDefine.getId());
		}
		for(TableClumnDefine tableClumnDefine :getThematicPolylineClumnList()){
			tableClumnDefine.getId().setCategoryid(cid);
			this.tableClumnDefineDao.merge(tableClumnDefine);
		}
	}

	public void bulkDelete(String ids) {
		if(ids.length()>0){
			String[] strs = ids.split(",");
			for (int i=0; i< strs.length; i++) {
				this.thematicPolylineCategoryDao.delete(Integer.parseInt(strs[i]));
				this.tableClumnDefineDao
					.deleteByHql("delete from TableClumnDefine as t where t.id.categoryid = " +Integer.parseInt(strs[i]) +" and t.id.tableName = 'zt_thematic_polyline'");
			}
		}
	}
	
	private List<TableClumnDefine> getThematicPolylineClumnList(){
		if(thematicPolylineClumnList == null){
			thematicPolylineClumnList = new ArrayList<TableClumnDefine>();
			TableClumnDefine nametableClumnDefine = new TableClumnDefine();
			TableClumnDefineId nameid = new TableClumnDefineId();
			nameid.setTableName("zt_thematic_polyline");
			nameid.setColumnName("name");
			nametableClumnDefine.setId(nameid);
			nametableClumnDefine.setColumnCnname("名称");
			nametableClumnDefine.setColumnType(0);
			nametableClumnDefine.setIsRequired(1);
			nametableClumnDefine.setIsShow(1);
			nametableClumnDefine.setOrderid(1);
			thematicPolylineClumnList.add(nametableClumnDefine);
			
			for(int i = 1 ; i <=30; i++){
				TableClumnDefine tableClumnDefine = new TableClumnDefine();
				TableClumnDefineId id = new TableClumnDefineId();
				id.setTableName("zt_thematic_polyline");
				id.setColumnName("column"+i);
				tableClumnDefine.setId(id);
				tableClumnDefine.setColumnCnname("字段" + i);
				tableClumnDefine.setColumnType(0);
				tableClumnDefine.setIsRequired(0);
				tableClumnDefine.setIsShow(0);
				tableClumnDefine.setOrderid(i + 6);
				thematicPolylineClumnList.add(tableClumnDefine);
			}
		}
		return thematicPolylineClumnList;
	}

	public List<ThematicPolylineCategory> getByHql(String hql) {
		return this.thematicPolylineCategoryDao.getByHQL(hql);
	}
}
