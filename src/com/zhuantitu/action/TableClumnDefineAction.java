package com.zhuantitu.action;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.system.action.BaseAction;
import com.system.utils.StringUtil;
import com.zhuantitu.model.TableClumnDefine;
import com.zhuantitu.service.TableClumnDefineService;
/**
 * 
 * @author LH
 * @since 1.0
 */
@Controller("tableClumnDefineAction")
@Scope("prototype")
public class TableClumnDefineAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4394153008855244650L;
	@Resource
	private TableClumnDefineService tableClumnDefineService;
	
	public String loadByCid(){
		StringBuffer json = new StringBuffer();
		StringBuffer temp = new StringBuffer();
		List<TableClumnDefine> tableClumnDefines = this.tableClumnDefineService.loadPointColumn(getId());
		for (TableClumnDefine tableClumnDefine : tableClumnDefines) {
			temp.append("{")
				.append("\"columnName\":\"" + tableClumnDefine.getId().getColumnName() + "\",")
				.append("\"columnCnname\":\" " + tableClumnDefine.getColumnCnname() + "\",")
				.append("\"columnType\":\"" + tableClumnDefine.getColumnType() + "\",")
				.append("\"isRequired\":\"" + tableClumnDefine.getIsRequired() + "\",")
				.append("\"isShow\":\"" + tableClumnDefine.getIsShow() + "\"")
				.append("},");
		}
		json.append("{")
			.append("\"status\":true,")
			.append("\"data\":[")
			.append(StringUtil.deleteLastStr(temp.toString()))
			.append("]")
			.append("}");
		writeJSON(json.toString());
		return NONE;
	}
	public String loadPolylineByCid(){
		StringBuffer json = new StringBuffer();
		StringBuffer temp = new StringBuffer();
		List<TableClumnDefine> tableClumnDefines = this.tableClumnDefineService.loadPolylineColumn(getId());
		for (TableClumnDefine tableClumnDefine : tableClumnDefines) {
			temp.append("{")
				.append("\"columnName\":\"" + tableClumnDefine.getId().getColumnName() + "\",")
				.append("\"columnCnname\":\" " + tableClumnDefine.getColumnCnname() + "\",")
				.append("\"columnType\":\"" + tableClumnDefine.getColumnType() + "\",")
				.append("\"isRequired\":\"" + tableClumnDefine.getIsRequired() + "\",")
				.append("\"isShow\":\"" + tableClumnDefine.getIsShow() + "\"")
				.append("},");
		}
		json.append("{")
			.append("\"status\":true,")
			.append("\"data\":[")
			.append(StringUtil.deleteLastStr(temp.toString()))
			.append("]")
			.append("}");
		writeJSON(json.toString());
		return NONE;
	}
	public String loadEquipmentByCid(){
		StringBuffer json = new StringBuffer();
		StringBuffer temp = new StringBuffer();
		List<TableClumnDefine> tableClumnDefines = this.tableClumnDefineService.loadEquipmentColumn(getId());
		for (TableClumnDefine tableClumnDefine : tableClumnDefines) {
			temp.append("{")
				.append("\"columnName\":\"" + tableClumnDefine.getId().getColumnName() + "\",")
				.append("\"columnCnname\":\" " + tableClumnDefine.getColumnCnname() + "\",")
				.append("\"columnType\":\"" + tableClumnDefine.getColumnType() + "\",")
				.append("\"isRequired\":\"" + tableClumnDefine.getIsRequired() + "\",")
				.append("\"isShow\":\"" + tableClumnDefine.getIsShow() + "\"")
				.append("},");
		}
		json.append("{")
			.append("\"status\":true,")
			.append("\"data\":[")
			.append(StringUtil.deleteLastStr(temp.toString()))
			.append("]")
			.append("}");
		writeJSON(json.toString());
		return NONE;
	}
}
