package com.zhuantitu.action;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.system.action.BaseAction;
import com.system.utils.PageBean;
import com.system.utils.StringUtil;
import com.zhuantitu.model.CameraIcon;
import com.zhuantitu.service.CameraIconService;
/**
 * 
 * @author LH
 * @since 1.0
 */
@Controller("cameraIconAction")
@Scope("prototype")
public class CameraIconAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3223896108733720414L;
	@Resource
	private CameraIconService cameraIconService;
	private PageBean<CameraIcon> cameraIconPage;
	private CameraIcon cameraIcon;
	
	public String loadIcon(){
		StringBuffer json = new StringBuffer();
		StringBuffer temp = new StringBuffer();
		List<CameraIcon> cameraIcons = this.cameraIconService.getAll(); 
		for (CameraIcon cameraIcon : cameraIcons) {
			temp.append("{")
				.append("\"iconid\":" + cameraIcon.getIconid() + ",")
				.append("\"path\":\"" + cameraIcon.getPath() + "\"")
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

	public PageBean<CameraIcon> getCameraIconPage() {
		return cameraIconPage;
	}

	public void setCameraIconPage(PageBean<CameraIcon> cameraIconPage) {
		this.cameraIconPage = cameraIconPage;
	}

	public CameraIcon getCameraIcon() {
		return cameraIcon;
	}

	public void setCameraIcon(CameraIcon cameraIcon) {
		this.cameraIcon = cameraIcon;
	}

}
