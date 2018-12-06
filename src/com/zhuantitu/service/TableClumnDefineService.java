package com.zhuantitu.service;
import java.util.List;

import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import com.zhuantitu.dao.TableClumnDefineDao;
import com.zhuantitu.model.TableClumnDefine;
@Component("tableClumnDefineService")
public class TableClumnDefineService {
	@Resource(name="tableClumnDefineDaoImpl")
	private TableClumnDefineDao tableClumnDefineDao;

	
	public List<TableClumnDefine> loadPointColumn(Integer cid) {
		return this.tableClumnDefineDao.getByHQL("from TableClumnDefine as t where t.id.categoryid = " + cid 
				+ " and t.id.tableName = 'zt_thematic_point' order by t.orderid");
	}
	public List<TableClumnDefine> loadPolylineColumn(Integer cid) {
		return this.tableClumnDefineDao.getByHQL("from TableClumnDefine as t where t.id.categoryid = " + cid 
				+ " and t.id.tableName = 'zt_thematic_polyline' order by t.orderid");
	}
	public List<TableClumnDefine> loadPolygonColumn(Integer cid) {
		return this.tableClumnDefineDao.getByHQL("from TableClumnDefine as t where t.id.categoryid = " + cid 
				+ " and t.id.tableName = 'zt_thematic_polygon' order by t.orderid");
	}
	public List<TableClumnDefine> loadEquipmentColumn(Integer cid) {
		return this.tableClumnDefineDao.getByHQL("from TableClumnDefine as t where t.id.categoryid = " + cid 
				+ " and t.id.tableName = 'zt_thematic_polyline_equipment' order by t.orderid");
	}
	
	public void update(List<TableClumnDefine> tableClumnDefineList) {
		for (TableClumnDefine tableClumnDefine : tableClumnDefineList) {
			this.tableClumnDefineDao.update(tableClumnDefine);
		}
	}

}
