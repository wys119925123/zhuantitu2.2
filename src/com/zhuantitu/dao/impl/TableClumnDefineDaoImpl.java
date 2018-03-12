package com.zhuantitu.dao.impl;
import org.springframework.stereotype.Component;
import com.system.dao.impl.BaseDaoSupport;
import com.zhuantitu.dao.TableClumnDefineDao;
import com.zhuantitu.model.TableClumnDefine;
@Component("tableClumnDefineDaoImpl")
public class TableClumnDefineDaoImpl extends BaseDaoSupport<TableClumnDefine> implements
		TableClumnDefineDao {
}
