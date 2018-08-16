package com.common.dao;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;


public interface ICommonDao extends IGenericBaseCommonDao{
	

	
	/**
	 * 解析XML文件
	 * @param fileName XML全路径
	 */
	public void parserXml(String fileName);

}
