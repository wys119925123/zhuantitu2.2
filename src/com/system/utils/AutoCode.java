package com.system.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;


public class AutoCode {
	public static void create(String modelName,String desc,String pak) throws IOException
	{
		String rootPath = "F:\\demo\\";
		String _modelName = modelName.substring(0,1).toLowerCase()+modelName.substring(1,modelName.length());
		//建DAO
		File daoFile = new File(rootPath + "dao\\" + modelName+"Dao.java");
		if (!daoFile.getParentFile().exists()) {
			daoFile.getParentFile().mkdirs();
		}
		daoFile.createNewFile();
		BufferedWriter daobw = new BufferedWriter(new OutputStreamWriter(  
				new FileOutputStream(daoFile), "UTF-8"));  
		daoFile.createNewFile();

		daobw.write("package "+pak+".dao;");daobw.write("\r\n");
		daobw.write("import com.system.dao.BaseDao;");daobw.write("\r\n");
		daobw.write("import "+pak+".model."+modelName+";");daobw.write("\r\n");
		daobw.write("public interface "+modelName+"Dao extends BaseDao<"+ modelName +"> {");daobw.write("\r\n");
		daobw.write("}");daobw.write("\r\n");
		daobw.flush();
		daobw.close();
		//建DAOImpl
		File daoImplFile = new File(rootPath  + "dao\\impl\\" + modelName+"DaoImpl.java");
		if (!daoImplFile.getParentFile().exists()) {
			daoImplFile.getParentFile().mkdirs();
		}
		daoImplFile.createNewFile();
		BufferedWriter daoImplbw = new BufferedWriter(new OutputStreamWriter(  
				new FileOutputStream(daoImplFile), "UTF-8"));  
		daoImplFile.createNewFile();
		daoImplbw.write("package "+ pak +".dao.impl;");daoImplbw.write("\r\n");
		daoImplbw.write("import org.springframework.stereotype.Component;");daoImplbw.write("\r\n");
		daoImplbw.write("import com.system.dao.impl.BaseDaoSupport;");daoImplbw.write("\r\n");
		daoImplbw.write("import "+pak+".dao."+modelName+"Dao;");daoImplbw.write("\r\n");
		daoImplbw.write("import "+pak+".model."+modelName+";");daoImplbw.write("\r\n");
		daoImplbw.write("@Component(\""+_modelName+"DaoImpl\")");daoImplbw.write("\r\n");
		daoImplbw.write("public class "+modelName+"DaoImpl extends BaseDaoSupport<"+ modelName +"> implements");daoImplbw.write("\r\n");
		daoImplbw.write("		" + modelName + "Dao {");daoImplbw.write("\r\n");
		daoImplbw.write("}");daoImplbw.write("\r\n");
		daoImplbw.flush();
		daoImplbw.close();
		//建Service
		File serviceFile = new File(rootPath  + "service\\" + modelName+"Service.java");
		if (!serviceFile.getParentFile().exists()) {
			serviceFile.getParentFile().mkdirs();
		}
		serviceFile.createNewFile();
		BufferedWriter servicebw = new BufferedWriter(new OutputStreamWriter(  
				new FileOutputStream(serviceFile), "UTF-8"));  
		servicebw.write("package "+pak+".service;");servicebw.write("\r\n");
		servicebw.write("import org.springframework.stereotype.Component;");servicebw.write("\r\n");

		servicebw.write("import javax.annotation.Resource;");servicebw.write("\r\n");

		servicebw.write("import "+pak+".dao."+modelName+"Dao;");servicebw.write("\r\n");



		servicebw.write("@Component(\""+_modelName+"Service\")");servicebw.write("\r\n");
		servicebw.write("public class "+modelName+"Service {");servicebw.write("\r\n");
		servicebw.write("	@Resource(name=\""+_modelName+"DaoImpl\")");servicebw.write("\r\n");
		servicebw.write("	private "+modelName+"Dao "+_modelName+"Dao;");servicebw.write("\r\n");
	
		servicebw.write("}");servicebw.write("\r\n");
		
		servicebw.flush();
		servicebw.close();
		//建Action
		File actionFile = new File(rootPath  + "action\\" + modelName+"Action.java");
		if (!actionFile.getParentFile().exists()) {
			actionFile.getParentFile().mkdirs();
		}
		actionFile.createNewFile();
		BufferedWriter actionbw = new BufferedWriter(new OutputStreamWriter(  
				new FileOutputStream(actionFile), "UTF-8"));  
		actionbw.write("package "+pak+".action;");
		actionbw.write("\r\n");
		actionbw.write("import javax.annotation.Resource;");
		actionbw.write("\r\n");
		actionbw.write("import com.system.action.BaseAction;");
		actionbw.write("\r\n");
		
		actionbw.write("import com.system.utils.PageBean;");
		actionbw.write("\r\n");
		
		actionbw.write("import org.springframework.context.annotation.Scope;");
		actionbw.write("\r\n");
		actionbw.write("import org.springframework.stereotype.Controller;");
		actionbw.write("\r\n");
		actionbw.write("import "+pak+".model." + modelName +";");
		actionbw.write("\r\n");
		actionbw.write("import "+pak+".service." + modelName +"Service;");
		actionbw.write("\r\n");

		
		actionbw.write("/**");
		actionbw.write("\r\n");
		actionbw.write(" * " + desc + "");
		actionbw.write("\r\n");
		actionbw.write(" * @author LH");
		actionbw.write("\r\n");
		actionbw.write(" * @since 1.0");
		actionbw.write("\r\n");
		actionbw.write(" */");
		actionbw.write("\r\n");
		actionbw.write("@Controller(\"" + _modelName+ "Action\")");
		actionbw.write("\r\n");
		actionbw.write("@Scope(\"prototype\")");
		actionbw.write("\r\n");
		actionbw.write("public class " + modelName + "Action extends BaseAction {");
		actionbw.write("\r\n");
		actionbw.write("	@Resource");actionbw.write("\r\n");
		actionbw.write("	private "+ modelName +"Service "+_modelName+"Service;");actionbw.write("\r\n");
		actionbw.write("	private PageBean<"+modelName+"> "+_modelName+"Page;");actionbw.write("\r\n");
		actionbw.write("	private "+modelName+" "+ _modelName+";");actionbw.write("\r\n");
		
		actionbw.write("}");actionbw.write("\r\n");
		
		
		actionbw.flush();
		actionbw.close();
	}
	public static void main(String[] args) throws IOException {
		AutoCode.create("CameraIcon","","com.zhuantitu");
		AutoCode.create("TableClumnDefine","","com.zhuantitu");
		AutoCode.create("ThematicMapMenu","","com.zhuantitu");
		AutoCode.create("ThematicPoint","","com.zhuantitu");
		AutoCode.create("ThematicPointCategory","","com.zhuantitu");
		AutoCode.create("ThematicPointImage","","com.zhuantitu");
		AutoCode.create("UserMenuPermission","","com.zhuantitu");
		AutoCode.create("UserPointPermission","","com.zhuantitu");
	}
}
