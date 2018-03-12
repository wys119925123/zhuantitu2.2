package com.zhuantitu.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.zhuantitu.dao.ThematicPolygonImageDao;
@Component("thematicPolygonImageService")
public class ThematicPolygonImageService {
	@Resource(name="thematicPolygonImageDaoImpl")
	private ThematicPolygonImageDao thematicPolygonImageDao;
}
