package com.zhuantitu.mobile.action;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.system.action.BaseAction;
import com.system.utils.StringUtil;
import com.zhuantitu.model.ThematicLocation;
import com.zhuantitu.model.ThematicPoint;
import com.zhuantitu.model.UserPointPermission;
import com.zhuantitu.service.ThematicLocationService;
import com.zhuantitu.service.ThematicPointService;
@Controller("locationAction")
@Scope("prototype")
public class ThematicLocationAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8378012722518004321L;
	@Resource
	private ThematicLocationService thematicLocationService;
	@Resource
	private ThematicPointService thematicPointService;
	@SuppressWarnings("unchecked")
	public String loadPoint(){
		String userid = validateUser();
		if(userid != null){
			StringBuffer json = new StringBuffer();
			StringBuffer temp = new StringBuffer();
			Map<Integer, UserPointPermission> userPointPermissionMap = (Map<Integer, UserPointPermission>) getSession().getAttribute("userPointPermissionMap");
			List<ThematicLocation> thematicLocations = this.thematicLocationService.getChildLocation(getZoneid());
			for (ThematicLocation thematicLocation : thematicLocations) {
				StringBuffer pointJson = new StringBuffer();
				List<ThematicPoint> thematicPoints = this.thematicPointService.getByLoctionidAndMid(thematicLocation.getLocationid(),getMid());
				for (ThematicPoint thematicPoint : thematicPoints) {
					if(userPointPermissionMap.containsKey(thematicPoint.getPointid())){
						pointJson.append("{")
						.append("\"pointid\":" + thematicPoint.getPointid() + ",")
						.append("\"name\":\"" + StringUtil.toJsonText(thematicPoint.getName())+ "\",")
						.append("\"icon\":\"" + thematicPoint.getIcon() + "\",")
						.append("\"floorid\":\"" + thematicPoint.getFloorid() + "\",")
						.append("\"ip\":\"" + thematicPoint.getIp() + "\",")
						.append("\"nvr\":\"" + thematicPoint.getNvr() + "\",")
						.append("\"videousername\":\"" + thematicPoint.getVideousername() + "\",")
						.append("\"videopassword\":\"" + thematicPoint.getVideopassword() + "\",")
						.append("\"coordinate\":\"" + thematicPoint.getCoordinate() + "\",")
						.append("\"videoSource\":\"" + thematicPoint.getColumn30() + "\",")
						.append("\"type\":\"" + thematicPoint.getType() + "\"")
						.append("},");
					}
				}
				temp.append("{")
					.append("\"id\":" + thematicLocation.getLocationid() +",")
					.append("\"name\":\"" + thematicLocation.getName() + "\",")
					.append("\"child\":[")
					.append(StringUtil.deleteLastStr(pointJson.toString()))
					.append("]},");
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
	public String list(){
		String userid = validateUser();
		if(userid != null){
			StringBuffer json = new StringBuffer();
			StringBuffer temp = new StringBuffer();
			List<ThematicLocation> parentLocations = this.thematicLocationService.getParentLocation(getZoneid());
			for (ThematicLocation thematicLocation : parentLocations) {
				List<ThematicLocation> childLocations = this.thematicLocationService.getChildLocationByPid(thematicLocation.getLocationid());
				StringBuffer child = new StringBuffer();
				for (ThematicLocation childLocation : childLocations) {
					child.append("{")
					.append("\"id\":" + childLocation.getLocationid() +",")
					.append("\"name\":\"" + childLocation.getName() + "\"")
					.append("},");
				}
				temp.append("{")
				.append("\"id\":" + thematicLocation.getLocationid() +",")
				.append("\"name\":\"" + thematicLocation.getName() + "\",")
				.append("\"childen\":[")
				.append(StringUtil.deleteLastStr(child.toString()))
				.append("]},");
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
}
