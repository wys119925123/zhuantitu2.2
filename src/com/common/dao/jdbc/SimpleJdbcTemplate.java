package com.common.dao.jdbc;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public class SimpleJdbcTemplate extends JdbcTemplate {
	protected final Log logger = LogFactory.getLog(getClass());

	protected JdbcTemplate  jdbcTemplate;
	protected NamedParameterJdbcTemplate  namedJdbcTemplate;
	
	protected SimpleJdbcInsert simpleJdbcInsert;
	public SimpleJdbcTemplate(DataSource dataSource){
		jdbcTemplate=new JdbcTemplate(dataSource);
		namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		simpleJdbcInsert=new SimpleJdbcInsert(dataSource);
	}
}
