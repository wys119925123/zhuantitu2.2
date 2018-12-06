package com.zhuantitu.action;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.system.action.BaseAction;
import com.system.utils.StringUtil;
import com.zhuantitu.model.ThematicPolylineEquipment;
import com.zhuantitu.model.ThematicPolylineEquipmentImage;
import com.zhuantitu.model.UserMenuPermission;
import com.zhuantitu.service.ThematicPolylineEquipmentImageService;
import com.zhuantitu.service.ThematicPolylineEquipmentService;
@Controller("thematicPolylineEquipmentAction")
@Scope("prototype")
public class ThematicPolylineEquipmentAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2504660848423184139L;
	@Resource
	private ThematicPolylineEquipmentService thematicPolylineEquipmentService;
	@Resource
	private ThematicPolylineEquipmentImageService thematicPolylineEquipmentImageService;
	private ThematicPolylineEquipment thematicPolylineEquipment;
	private String lonlat;
	private String images;
	public String edit(){
		try {
			thematicPolylineEquipmentService.update(thematicPolylineEquipment,
					lonlat.replaceAll("-", " ").replace("POLYGON", "MULTIPOLYGON(") + ")", images);
			writeJSON("{\"status\":true}");
		} catch (Exception e) {
			writeError(203,"更新失败");
		}
		return NONE;
	}
	
	public String add(){
		Map<Integer, UserMenuPermission> userMenuPermissionMap = (Map<Integer, UserMenuPermission>) getSession().getAttribute("userMenuPermissionMap");
		if(userMenuPermissionMap.get(getMid()).getAddcodePermission() == 1){

			thematicPolylineEquipmentService.add(thematicPolylineEquipment, lonlat.replaceAll("-", " ").replace("POLYGON", "MULTIPOLYGON(") + ")", images);
			writeJSON("{\"status\":true}");
		}else{
			writeError(204, "没有操作权限");
		}
		return NONE;
	}
	
	public String list(){
		StringBuffer json = new StringBuffer();
		StringBuffer temp = new StringBuffer();
		List<ThematicPolylineEquipment> thematicPolylineEquipments = this.thematicPolylineEquipmentService.getByMenuidAndFloorid(getMid(), getFloorid());
		for (ThematicPolylineEquipment thematicPolylineEquipment : thematicPolylineEquipments) {
			temp.append("{")
			.append("\"id\":" + thematicPolylineEquipment.getId() + ",")
			.append("\"name\":\"" + StringUtil.toJsonText(thematicPolylineEquipment.getName())+ "\",")
			.append("\"equipmenttype\":\"" + thematicPolylineEquipment.getEquipmenttype() + "\",")
			.append("\"fillcolor\":\"" + thematicPolylineEquipment.getFillcolor() + "\",")
			.append("\"strokecolor\":\"" + thematicPolylineEquipment.getStrokecolor() + "\",")
			.append("\"strokewidth\":\"" + thematicPolylineEquipment.getStrokewidth() + "\",")
			.append("\"coordinates\":\"" + thematicPolylineEquipment.getCoordinates() + "\",")
			.append("\"coordinate\":\"" + thematicPolylineEquipment.getCoordinate() + "\"")
			.append("},");
		}
		json.append("{")
		.append("\"status\":true,")
		.append("\"data\":[")
		.append(StringUtil.deleteLastStr(temp.toString()))
		.append("]}");
	writeJSON(json.toString());
		return NONE;
	}
	
	public String delete(){
		try {
			this.thematicPolylineEquipmentService.delete(getId());
			writeJSON("{\"status\":true}");
		} catch (Exception e) {
			writeError(203,"删除失败");
		}
		return NONE;
	}
	public String detail(){
		try {
			StringBuffer json = new StringBuffer();
			StringBuffer imgJson = new StringBuffer();
			ThematicPolylineEquipment thematicPolylineEquipment = this.thematicPolylineEquipmentService.getById(getId());
			List<ThematicPolylineEquipmentImage> images = this.thematicPolylineEquipmentImageService.getByEquipmentId(getId());
			for (ThematicPolylineEquipmentImage thematicPolylineEquipmentImage : images) {
				imgJson.append("{")
				.append("\"name\":\"" + thematicPolylineEquipmentImage.getName() + "\",")
				.append("\"path\":\"" + thematicPolylineEquipmentImage.getPath() + "\"")
				.append("},");
			}
			json.append("{")
				.append("\"status\":true,")
				.append("\"editPermissiont\":1,")
				.append("\"deletePermission\":1,")
				.append("\"id\":" + thematicPolylineEquipment.getId() + ",")
				.append("\"name\":\"" +  StringUtil.toJsonText(thematicPolylineEquipment.getName()) + "\",")
				.append("\"floorid\":\"" + thematicPolylineEquipment.getFloorid() + "\",")
				.append("\"equipmenttype\":\"" + thematicPolylineEquipment.getEquipmenttype() + "\",")
				.append("\"fillcolor\":\"" + thematicPolylineEquipment.getFillcolor() + "\",")
				.append("\"strokecolor\":\"" + thematicPolylineEquipment.getStrokecolor() + "\",")
				.append("\"strokewidth\":\"" + thematicPolylineEquipment.getStrokewidth() + "\",")
				.append("\"coordinate\":\"" + thematicPolylineEquipment.getCoordinate() + "\",")
				.append("\"coordinates\":\"" + thematicPolylineEquipment.getCoordinates() + "\",")
				.append("\"column1\":\"" + StringUtil.toJsonText(thematicPolylineEquipment.getColumn1()) + "\",")
				.append("\"column2\":\"" + StringUtil.toJsonText(thematicPolylineEquipment.getColumn2()) + "\",")
				.append("\"column3\":\"" + StringUtil.toJsonText(thematicPolylineEquipment.getColumn3()) + "\",")
				.append("\"column4\":\"" + StringUtil.toJsonText(thematicPolylineEquipment.getColumn4()) + "\",")
				.append("\"column5\":\"" + StringUtil.toJsonText(thematicPolylineEquipment.getColumn5()) + "\",")
				.append("\"column6\":\"" + StringUtil.toJsonText(thematicPolylineEquipment.getColumn6()) + "\",")
				.append("\"column7\":\"" + StringUtil.toJsonText(thematicPolylineEquipment.getColumn7()) + "\",")
				.append("\"column8\":\"" + StringUtil.toJsonText(thematicPolylineEquipment.getColumn8()) + "\",")
				.append("\"column9\":\"" + StringUtil.toJsonText(thematicPolylineEquipment.getColumn9()) + "\",")
				.append("\"column10\":\"" + StringUtil.toJsonText(thematicPolylineEquipment.getColumn10()) + "\",")
				.append("\"column11\":\"" + StringUtil.toJsonText(thematicPolylineEquipment.getColumn11()) + "\",")
				.append("\"column12\":\"" + StringUtil.toJsonText(thematicPolylineEquipment.getColumn12()) + "\",")
		
				.append("\"images\":[")
				.append(StringUtil.deleteLastStr(imgJson.toString()))
				.append("]")
				.append("}");
		writeJSON(json.toString());	
		} catch (Exception e) {
			e.printStackTrace();
			writeError(203,"获取数据失败");
		}
		return NONE;
	}

	public ThematicPolylineEquipment getThematicPolylineEquipment() {
		return thematicPolylineEquipment;
	}

	public void setThematicPolylineEquipment(
			ThematicPolylineEquipment thematicPolylineEquipment) {
		this.thematicPolylineEquipment = thematicPolylineEquipment;
	}

	public String getLonlat() {
		return lonlat;
	}

	public void setLonlat(String lonlat) {
		this.lonlat = lonlat;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}
	
	
}
