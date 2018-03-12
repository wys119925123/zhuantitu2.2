package com.system.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter;

public class MyStrutsFilter extends StrutsPrepareAndExecuteFilter {
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException,ServletException{
		HttpServletRequest request = (HttpServletRequest) req;
		String uri = request.getRequestURI();
		if(uri.contains("/jsp/upload_json.jsp")||uri.contains("/jsp/file_manager_json.jsp"))
		{
			chain.doFilter(req, res);
		}else
		{
			super.doFilter(req, res, chain);
		}
	}
}