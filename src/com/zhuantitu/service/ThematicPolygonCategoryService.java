package com.zhuantitu.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.system.utils.PageBean;
import com.zhuantitu.dao.TableClumnDefineDao;
import com.zhuantitu.dao.ThematicPolygonCategoryDao;
import com.zhuantitu.model.TableClumnDefine;
import com.zhuantitu.model.TableClumnDefineId;
import com.zhuantitu.model.ThematicPolygonCategory;

@Component("thematicPolygonCategoryService")
public class ThematicPolygonCategoryService {
	@Resource(name="thematicPolygonCategoryDaoImpl")
	private ThematicPolygonCategoryDao thematicPolygonCategoryDao;
	
	private static List<TableClumnDefine> thematicPolygonClumnList = null;
	@Resource(name="tableClumnDefineDaoImpl")
	private TableClumnDefineDao tableClumnDefineDao;
	public List<ThematicPolygonCategory> getByMenuid(Integer menuid) {
		return this.thematicPolygonCategoryDao
			.getByHQL("from ThematicPolygonCategory as t where t.thematicMapMenu.menuid = " + menuid + " order by t.orderid");
	}

	public PageBean<ThematicPolygonCategory> loadPage(String hql, int pageSize,
			int page) {
		return this.thematicPolygonCategoryDao.pageQuery(hql, pageSize, page);
	}

	public void update(ThematicPolygonCategory thematicPolygonCategory) {
		this.thematicPolygonCategoryDao.update(thematicPolygonCategory);
	}

	public ThematicPolygonCategory getById(Integer id) {
		return this.thematicPolygonCategoryDao.get(id);
	}

	public void save(ThematicPolygonCategory thematicPolygonCategory) {
		thematicPolygonCategory = this.thematicPolygonCategoryDao.add(thematicPolygonCategory);
		Integer cid = thematicPolygonCategory.getCategoryid();
		List<TableClumnDefine> tableClumnDefines = this.tableClumnDefineDao.getByHQL("from TableClumnDefine as t where t.id.categoryid = " + cid);
		for (TableClumnDefine tableClumnDefine : tableClumnDefines) {
			this.tableClumnDefineDao.delete(tableClumnDefine.getId());
		}
		for(TableClumnDefine tableClumnDefine :getThematicPolygonClumnList()){
			tableClumnDefine.getId().setCategoryid(cid);
			this.tableClumnDefineDao.merge(tableClumnDefine);
		}
	}

	public void bulkDelete(String ids) {
		if(ids.length()>0){
			String[] strs = ids.split(",");
			for (int i=0; i< strs.length; i++) {
				this.thematicPolygonCategoryDao.delete(Integer.parseInt(strs[i]));
				this.tableClumnDefineDao
					.deleteByHql("delete from TableClumnDefine as t where t.id.categoryid = " +Integer.parseInt(strs[i]) +" and t.id.tableName = 'zt_thematic_polygon'");
			}
		}
	}
	
	private List<TableClumnDefine> getThematicPolygonClumnList(){
		if(thematicPolygonClumnList == null){
			thematicPolygonClumnList = new ArrayList<TableClumnDefine>();
			TableClumnDefine nametableClumnDefine = new TableClumnDefine();
			TableClumnDefineId nameid = new TableClumnDefineId();
			nameid.setTableName("zt_thematic_polygon");
			nameid.setColumnName("name");
			nametableClumnDefine.setId(nameid);
			nametableClumnDefine.setColumnCnname("名称");
			nametableClumnDefine.setColumnType(0);
			nametableClumnDefine.setIsRequired(1);
			nametableClumnDefine.setIsShow(1);
			nametableClumnDefine.setOrderid(1);
			thematicPolygonClumnList.add(nametableClumnDefine);
			
			for(int i = 1 ; i <=30; i++){
				TableClumnDefine tableClumnDefine = new TableClumnDefine();
				TableClumnDefineId id = new TableClumnDefineId();
				id.setTableName("zt_thematic_polygon");
				id.setColumnName("column"+i);
				tableClumnDefine.setId(id);
				tableClumnDefine.setColumnCnname("字段" + i);
				tableClumnDefine.setColumnType(0);
				tableClumnDefine.setIsRequired(0);
				tableClumnDefine.setIsShow(0);
				tableClumnDefine.setOrderid(i + 6);
				thematicPolygonClumnList.add(tableClumnDefine);
			}
		}
		return thematicPolygonClumnList;
	}

	public List<ThematicPolygonCategory> getByHql(String hql) {
		return this.thematicPolygonCategoryDao.getByHQL(hql);
	}
	
}
