package com.common.dao.impl;


import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.common.dao.ICommonDao;
import com.common.dao.IGenericBaseCommonDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.*;

/**
 * 公共扩展方法
 * @author 
 *
 */
@Repository
public class CommonDao extends GenericBaseCommonDao implements ICommonDao, IGenericBaseCommonDao {

	
    
	private String toHexString(int index){
        String hexString = Integer.toHexString(index);   
        // 1个byte变成16进制的，只需要2位就可以表示了，取后面两位，去掉前面的符号填充   
        hexString = hexString.substring(hexString.length() -2);  
        return hexString;
	}

	@Override
	public void parserXml(String fileName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object executeSqlReturnKey(String sql, Map<String, Object> param) {
		// TODO Auto-generated method stub
		return null;
	}

}
