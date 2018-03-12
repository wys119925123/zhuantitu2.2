package com.zhuantitu.mobile.action;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.system.action.BaseAction;
import com.system.utils.StringUtil;
import com.zhuantitu.model.ThematicPoint;
import com.zhuantitu.model.ThematicPointImage;
import com.zhuantitu.model.UserPointPermission;
import com.zhuantitu.service.ThematicPointImageService;
import com.zhuantitu.service.ThematicPointService;
/**
 * 
 * @author LH
 * @since 1.0
 */
@Controller("pointAction")
@Scope("prototype")
public class ThematicPointAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9220808004135878349L;
	@Resource
	private ThematicPointService thematicPointService;
	@Resource
	private ThematicPointImageService thematicPointImageService;
	private ThematicPoint thematicPoint;
	@SuppressWarnings("unchecked")
	public String list(){
		String userid = validateUser();
		if(userid != null){
			StringBuffer json = new StringBuffer();
			StringBuffer temp = new StringBuffer();
			Map<Integer, UserPointPermission> userPointPermissionMap = (Map<Integer, UserPointPermission>) getSession().getAttribute("userPointPermissionMap");
			List<ThematicPoint> thematicPoints = this.thematicPointService.getByMenuidAndFloorid(getMid(),getFloorid());
			for (ThematicPoint thematicPoint : thematicPoints) {
				if(userPointPermissionMap.containsKey(thematicPoint.getPointid())){
					temp.append("{")
						.append("\"pointid\":" + thematicPoint.getPointid() + ",")
						.append("\"name\":\"" + thematicPoint.getName() + "\",")
						.append("\"icon\":\"" + thematicPoint.getIcon() + "\",")
						.append("\"coordinate\":\"" + thematicPoint.getCoordinate() + "\"")
						.append("},");
				}
			}
			json.append("{")
				.append("\"status\":true,")
				.append("\"data\":[")
				.append(StringUtil.deleteLastStr(temp.toString()))
				.append("]}");
			writeJSON(json.toString());
		}
		return NONE;
	}
	@SuppressWarnings("unchecked")
	public String detial(){
		String userid = validateUser();
		if(userid != null){
			Map<Integer, UserPointPermission> userPointPermissionMap = (Map<Integer, UserPointPermission>) getSession().getAttribute("userPointPermissionMap");
			if(userPointPermissionMap.containsKey(getId())){
				try {
					StringBuffer json = new StringBuffer();
					StringBuffer imgJson = new StringBuffer();
					ThematicPoint thematicPoint = this.thematicPointService.getById(getId());
					List<ThematicPointImage> thematicPointImages = this.thematicPointImageService.getByPointid(getId());
					for (ThematicPointImage thematicPointImage : thematicPointImages) {
						imgJson.append("{")
							.append("\"name\":\"" + thematicPointImage.getName() + "\",")
							.append("\"path\":\"" + thematicPointImage.getPath() + "\"")
							.append("},");
					}
					
					json.append("{")
						.append("\"status\":true,")
						.append("\"editPermissiont\":" + userPointPermissionMap.get(getId()).getEditPermission()+ ",")
						.append("\"deletePermission\":" + userPointPermissionMap.get(getId()).getDeletePermission()+ ",")
						.append("\"pointid\":" + thematicPoint.getPointid() + ",")
						.append("\"categoryid\":" + thematicPoint.getThematicPointCategory().getCategoryid() + ",")
						.append("\"name\":\"" +  StringUtil.toJsonText(thematicPoint.getName()) + "\",")
						.append("\"floorid\":\"" + thematicPoint.getFloorid() + "\",")
						.append("\"coordinate\":\"" + thematicPoint.getCoordinate() + "\",")
						.append("\"ip\":\"" + thematicPoint.getIp() + "\",")
						.append("\"nvr\":\"" + thematicPoint.getNvr() + "\",")
						.append("\"channel\":\"" + StringUtil.toJsonText(thematicPoint.getChannel()) + "\",")
						.append("\"videousername\":\"" + thematicPoint.getVideousername() + "\",")
						.append("\"videopassword\":\"" + thematicPoint.getVideopassword() + "\",")
						.append("\"type\":\"" + thematicPoint.getType() + "\",")
						.append("\"column1\":\"" + StringUtil.toJsonText(thematicPoint.getColumn1()) + "\",")
						.append("\"column2\":\"" + StringUtil.toJsonText(thematicPoint.getColumn2()) + "\",")
						.append("\"column3\":\"" + StringUtil.toJsonText(thematicPoint.getColumn3()) + "\",")
						.append("\"column4\":\"" + StringUtil.toJsonText(thematicPoint.getColumn4()) + "\",")
						.append("\"column5\":\"" + StringUtil.toJsonText(thematicPoint.getColumn5()) + "\",")
						.append("\"column6\":\"" + StringUtil.toJsonText(thematicPoint.getColumn6()) + "\",")
						.append("\"column7\":\"" + StringUtil.toJsonText(thematicPoint.getColumn7()) + "\",")
						.append("\"column8\":\"" + StringUtil.toJsonText(thematicPoint.getColumn8()) + "\",")
						.append("\"column9\":\"" + StringUtil.toJsonText(thematicPoint.getColumn9()) + "\",")
						.append("\"column10\":\"" + StringUtil.toJsonText(thematicPoint.getColumn10()) + "\",")
						.append("\"column11\":\"" + StringUtil.toJsonText(thematicPoint.getColumn11()) + "\",")
						.append("\"column12\":\"" + StringUtil.toJsonText(thematicPoint.getColumn12()) + "\",")
						.append("\"column13\":\"" + StringUtil.toJsonText(thematicPoint.getColumn13()) + "\",")
						.append("\"column14\":\"" + StringUtil.toJsonText(thematicPoint.getColumn14()) + "\",")
						.append("\"column15\":\"" + StringUtil.toJsonText(thematicPoint.getColumn15()) + "\",")
						.append("\"column16\":\"" + StringUtil.toJsonText(thematicPoint.getColumn16()) + "\",")
						.append("\"column17\":\"" + StringUtil.toJsonText(thematicPoint.getColumn17()) + "\",")
						.append("\"column18\":\"" + StringUtil.toJsonText(thematicPoint.getColumn18()) + "\",")
						.append("\"column19\":\"" + StringUtil.toJsonText(thematicPoint.getColumn19()) + "\",")
						.append("\"column20\":\"" + StringUtil.toJsonText(thematicPoint.getColumn20()) + "\",")
						.append("\"column21\":\"" + StringUtil.toJsonText(thematicPoint.getColumn21()) + "\",")
						.append("\"column22\":\"" + StringUtil.toJsonText(thematicPoint.getColumn22()) + "\",")
						.append("\"column23\":\"" + StringUtil.toJsonText(thematicPoint.getColumn23()) + "\",")
						.append("\"column24\":\"" + StringUtil.toJsonText(thematicPoint.getColumn24()) + "\",")
						.append("\"column25\":\"" + StringUtil.toJsonText(thematicPoint.getColumn25()) + "\",")
						.append("\"column26\":\"" + StringUtil.toJsonText(thematicPoint.getColumn26()) + "\",")
						.append("\"column27\":\"" + StringUtil.toJsonText(thematicPoint.getColumn27()) + "\",")
						.append("\"column28\":\"" + StringUtil.toJsonText(thematicPoint.getColumn28()) + "\",")
						.append("\"column29\":\"" + StringUtil.toJsonText(thematicPoint.getColumn29()) + "\",")
						.append("\"column30\":\"" + StringUtil.toJsonText(thematicPoint.getColumn30()) + "\",")
						.append("\"videoSource\":\"" + thematicPoint.getColumn30() + "\",")
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
		}
		return NONE;
	}

	@SuppressWarnings("unchecked")
	public String loadByFidAndLocationid(){
		String userid = validateUser();
		if(userid != null){
			StringBuffer json = new StringBuffer();
			StringBuffer temp = new StringBuffer();
			Map<Integer, UserPointPermission> userPointPermissionMap = (Map<Integer, UserPointPermission>) getSession().getAttribute("userPointPermissionMap");
			List<ThematicPoint> thematicPoints = this.thematicPointService.loadByFidAndLocationid(getFloorid(),getId());
			for (ThematicPoint thematicPoint : thematicPoints) {
				if(userPointPermissionMap.containsKey(thematicPoint.getPointid())){
					temp.append("{")
						.append("\"pointid\":" + thematicPoint.getPointid() + ",")
						.append("\"name\":\"" + thematicPoint.getName() + "\",")
						.append("\"icon\":\"" + thematicPoint.getIcon() + "\",")
						.append("\"ip\":\"" + thematicPoint.getIp() + "\",")
						.append("\"nvr\":\"" + thematicPoint.getNvr() + "\",")
						.append("\"videousername\":\"" + thematicPoint.getVideousername() + "\",")
						.append("\"videopassword\":\"" + thematicPoint.getVideopassword() + "\",")
						.append("\"channel\":\"" + StringUtil.toJsonText(thematicPoint.getChannel()) + "\",")
						.append("\"type\":\"" + thematicPoint.getType() + "\",")
						.append("\"videoSource\":\"" + thematicPoint.getColumn30() + "\",")
						//rtmp://192.168.75.101:1935/live/pag/192.168.75.100/7302/003542/0/MAIN/TCP
						.append("\"coordinate\":\"" + thematicPoint.getCoordinate() + "\"")
						.append("},");
				}
			}
			json.append("{")
				.append("\"status\":true,")
				.append("\"data\":[")
				.append(StringUtil.deleteLastStr(temp.toString()))
				.append("]}");
			writeJSON(json.toString());
		}
		return NONE;
	}
	public ThematicPoint getThematicPoint() {
		return thematicPoint;
	}

	public void setThematicPoint(ThematicPoint thematicPoint) {
		this.thematicPoint = thematicPoint;
	}
}
