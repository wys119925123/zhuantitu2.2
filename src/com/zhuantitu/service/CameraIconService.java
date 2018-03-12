package com.zhuantitu.service;
import java.util.List;

import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import com.zhuantitu.dao.CameraIconDao;
import com.zhuantitu.model.CameraIcon;
@Component("cameraIconService")
public class CameraIconService {
	@Resource(name="cameraIconDaoImpl")
	private CameraIconDao cameraIconDao;

	public List<CameraIcon> getAll() {
		return this.cameraIconDao.getAll("orderid",true);
	}
}
