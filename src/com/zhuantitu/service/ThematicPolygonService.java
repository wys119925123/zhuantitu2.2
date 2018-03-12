package com.zhuantitu.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.zhuantitu.dao.ThematicPolygonDao;

@Component("thematicPolygonService")
public class ThematicPolygonService {
	@Resource(name="thematicPolygonDaoImpl")
	private ThematicPolygonDao thematicPolygonDao;
}
