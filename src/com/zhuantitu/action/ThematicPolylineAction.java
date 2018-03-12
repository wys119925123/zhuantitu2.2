package com.zhuantitu.action;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.system.action.BaseAction;
import com.system.utils.PageBean;
import com.system.utils.StringUtil;
import com.zhuantitu.model.ThematicPolyline;
import com.zhuantitu.model.ThematicPolylineImage;
import com.zhuantitu.model.User;
import com.zhuantitu.model.UserMenuPermission;
import com.zhuantitu.model.UserPolylinePermission;
import com.zhuantitu.service.ThematicPolylineImageService;
import com.zhuantitu.service.ThematicPolylineService;
/**
 * 
 * @author LH
 * @since 1.0
 */
@Controller("thematicPolylineAction")
@Scope("prototype")
public class ThematicPolylineAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7662907056668082408L;
	@Resource
	private ThematicPolylineService thematicPolylineService;
	@Resource
	private ThematicPolylineImageService thematicPolylineImageService;
	private PageBean<ThematicPolyline> thematicPolylinePage;
	private ThematicPolyline thematicPolyline;
	private String lonlat;
	private String images;
	private String frameLonlat;
	private Integer isSearchFloor;
	@SuppressWarnings("unchecked")
	public String edit(){
		Map<Integer, UserPolylinePermission> userPolylinePermissionMap  = (Map<Integer, UserPolylinePermission>) getSession().getAttribute("userPolylinePermissionMap");
		if(userPolylinePermissionMap.get(thematicPolyline.getPolylineid()).getEditPermission() == 1){
			User loginUser = (User) getSession().getAttribute("loginUser");
			thematicPolylineService.update(thematicPolyline,lonlat.replaceAll("-", " "),images,loginUser.getUserid());
			writeJSON("{\"status\":true}");
		}else{
			writeError(204, "没有操作权限");
		}
		return NONE;
	}
	@SuppressWarnings("unchecked")
	public String add(){
		Map<Integer, UserMenuPermission> userMenuPermissionMap = (Map<Integer, UserMenuPermission>) getSession().getAttribute("userMenuPermissionMap");
		if(userMenuPermissionMap.get(getMid()).getAddcodePermission() == 1){
			User loginUser = (User) getSession().getAttribute("loginUser");
			thematicPolylineService.add(thematicPolyline,lonlat.replaceAll("-", " "),images,loginUser.getUserid());
			initUserPolylinePermission(loginUser.getUserid());
			writeJSON("{\"status\":true}");
		}else{
			writeError(204, "没有操作权限");
		}
		return NONE;
	}
	@SuppressWarnings("unchecked")
	public String list(){
		StringBuffer json = new StringBuffer();
		StringBuffer temp = new StringBuffer();
		Map<Integer, UserPolylinePermission> userPolylinePermissionMap = (Map<Integer, UserPolylinePermission>) getSession().getAttribute("userPolylinePermissionMap");
		List<ThematicPolyline> thematicPolylines = this.thematicPolylineService.getByMenuidAndFloorid(getMid(),getFloorid());
		for (ThematicPolyline thematicPolyline : thematicPolylines) {
			if(userPolylinePermissionMap.containsKey(thematicPolyline.getPolylineid())){
				temp.append("{")
					.append("\"polylineid\":" + thematicPolyline.getPolylineid() + ",")
					.append("\"name\":\"" + StringUtil.toJsonText(thematicPolyline.getName())+ "\",")
					.append("\"categoryid\":\"" + thematicPolyline.getThematicPolylineCategory().getCategoryid() + "\",")
					.append("\"strokecolor\":\"" + thematicPolyline.getStrokecolor() + "\",")
					.append("\"strokewidth\":\"" + thematicPolyline.getStrokewidth() + "\",")
					.append("\"coordinates\":\"" + thematicPolyline.getCoordinates() + "\",")
					.append("\"coordinate\":\"" + thematicPolyline.getCoordinate() + "\"")
					.append("},");
			}
		}
		json.append("{")
			.append("\"status\":true,")
			.append("\"data\":[")
			.append(StringUtil.deleteLastStr(temp.toString()))
			.append("]}");
		writeJSON(json.toString());
		return NONE;
	}

	@SuppressWarnings("unchecked")
	public String delete(){
		Map<Integer, UserPolylinePermission> userPolylinePermissionMap = (Map<Integer, UserPolylinePermission>) getSession().getAttribute("userPolylinePermissionMap");
		if(userPolylinePermissionMap.containsKey(getId())){
			this.thematicPolylineService.delete(getId());
			writeJSON("{\"status\":true}");
		}else{
			writeError(203,"删除失败");
		}
		return NONE;
	}
	@SuppressWarnings("unchecked")
	public String detail(){
		Map<Integer, UserPolylinePermission> userPolylinePermissionMap = (Map<Integer, UserPolylinePermission>) getSession().getAttribute("userPolylinePermissionMap");
		if(userPolylinePermissionMap.containsKey(getId())){
			try {
				StringBuffer json = new StringBuffer();
				StringBuffer imgJson = new StringBuffer();
				ThematicPolyline thematicPolyline = this.thematicPolylineService.getById(getId());
				List<ThematicPolylineImage> thematicPolylineImages = this.thematicPolylineImageService.getByPolylineid(getId());
				for (ThematicPolylineImage thematicPolylineImage : thematicPolylineImages) {
					imgJson.append("{")
						.append("\"name\":\"" + thematicPolylineImage.getName() + "\",")
						.append("\"path\":\"" + thematicPolylineImage.getPath() + "\"")
						.append("},");
				}
				
				json.append("{")
					.append("\"status\":true,")
					.append("\"editPermissiont\":" + userPolylinePermissionMap.get(getId()).getEditPermission()+ ",")
					.append("\"deletePermission\":" + userPolylinePermissionMap.get(getId()).getDeletePermission()+ ",")
					.append("\"polylineid\":" + thematicPolyline.getPolylineid() + ",")
					.append("\"categoryid\":" + thematicPolyline.getThematicPolylineCategory().getCategoryid() + ",")
					.append("\"name\":\"" +  StringUtil.toJsonText(thematicPolyline.getName()) + "\",")
					.append("\"floorid\":\"" + thematicPolyline.getFloorid() + "\",")
					.append("\"length\":\"" + thematicPolyline.getLength() + "\",")
					.append("\"strokecolor\":\"" + thematicPolyline.getStrokecolor() + "\",")
					.append("\"strokewidth\":\"" + thematicPolyline.getStrokewidth() + "\",")
					.append("\"coordinate\":\"" + thematicPolyline.getCoordinate() + "\",")
					.append("\"coordinates\":\"" + thematicPolyline.getCoordinates() + "\",")
					.append("\"column1\":\"" + StringUtil.toJsonText(thematicPolyline.getColumn1()) + "\",")
					.append("\"column2\":\"" + StringUtil.toJsonText(thematicPolyline.getColumn2()) + "\",")
					.append("\"column3\":\"" + StringUtil.toJsonText(thematicPolyline.getColumn3()) + "\",")
					.append("\"column4\":\"" + StringUtil.toJsonText(thematicPolyline.getColumn4()) + "\",")
					.append("\"column5\":\"" + StringUtil.toJsonText(thematicPolyline.getColumn5()) + "\",")
					.append("\"column6\":\"" + StringUtil.toJsonText(thematicPolyline.getColumn6()) + "\",")
					.append("\"column7\":\"" + StringUtil.toJsonText(thematicPolyline.getColumn7()) + "\",")
					.append("\"column8\":\"" + StringUtil.toJsonText(thematicPolyline.getColumn8()) + "\",")
					.append("\"column9\":\"" + StringUtil.toJsonText(thematicPolyline.getColumn9()) + "\",")
					.append("\"column10\":\"" + StringUtil.toJsonText(thematicPolyline.getColumn10()) + "\",")
					.append("\"column11\":\"" + StringUtil.toJsonText(thematicPolyline.getColumn11()) + "\",")
					.append("\"column12\":\"" + StringUtil.toJsonText(thematicPolyline.getColumn12()) + "\",")
					.append("\"column13\":\"" + StringUtil.toJsonText(thematicPolyline.getColumn13()) + "\",")
					.append("\"column14\":\"" + StringUtil.toJsonText(thematicPolyline.getColumn14()) + "\",")
					.append("\"column15\":\"" + StringUtil.toJsonText(thematicPolyline.getColumn15()) + "\",")
					.append("\"column16\":\"" + StringUtil.toJsonText(thematicPolyline.getColumn16()) + "\",")
					.append("\"column17\":\"" + StringUtil.toJsonText(thematicPolyline.getColumn17()) + "\",")
					.append("\"column18\":\"" + StringUtil.toJsonText(thematicPolyline.getColumn18()) + "\",")
					.append("\"column19\":\"" + StringUtil.toJsonText(thematicPolyline.getColumn19()) + "\",")
					.append("\"column20\":\"" + StringUtil.toJsonText(thematicPolyline.getColumn20()) + "\",")
					.append("\"column21\":\"" + StringUtil.toJsonText(thematicPolyline.getColumn21()) + "\",")
					.append("\"column22\":\"" + StringUtil.toJsonText(thematicPolyline.getColumn22()) + "\",")
					.append("\"column23\":\"" + StringUtil.toJsonText(thematicPolyline.getColumn23()) + "\",")
					.append("\"column24\":\"" + StringUtil.toJsonText(thematicPolyline.getColumn24()) + "\",")
					.append("\"column25\":\"" + StringUtil.toJsonText(thematicPolyline.getColumn25()) + "\",")
					.append("\"column26\":\"" + StringUtil.toJsonText(thematicPolyline.getColumn26()) + "\",")
					.append("\"column27\":\"" + StringUtil.toJsonText(thematicPolyline.getColumn27()) + "\",")
					.append("\"column28\":\"" + StringUtil.toJsonText(thematicPolyline.getColumn28()) + "\",")
					.append("\"column29\":\"" + StringUtil.toJsonText(thematicPolyline.getColumn29()) + "\",")
					.append("\"column30\":\"" + StringUtil.toJsonText(thematicPolyline.getColumn30()) + "\",")
					.append("\"images\":[")
					.append(StringUtil.deleteLastStr(imgJson.toString()))
					.append("]")
					.append("}");
				writeJSON(json.toString());
			} catch (Exception e) {
				e.printStackTrace();
				writeError(203,"获取数据失败");
			}
		}else{
			writeError(204,"没有操作权限");
		}
		return NONE;
	}
	public PageBean<ThematicPolyline> getThematicPolylinePage() {
		return thematicPolylinePage;
	}

	public void setThematicPolylinePage(PageBean<ThematicPolyline> thematicPolylinePage) {
		this.thematicPolylinePage = thematicPolylinePage;
	}

	public ThematicPolyline getThematicPolyline() {
		return thematicPolyline;
	}

	public void setThematicPolyline(ThematicPolyline thematicPolyline) {
		this.thematicPolyline = thematicPolyline;
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
	public String getFrameLonlat() {
		return frameLonlat;
	}
	public void setFrameLonlat(String frameLonlat) {
		this.frameLonlat = frameLonlat;
	}
	public Integer getIsSearchFloor() {
		return isSearchFloor;
	}
	public void setIsSearchFloor(Integer isSearchFloor) {
		this.isSearchFloor = isSearchFloor;
	}
}
