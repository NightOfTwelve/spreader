package com.nali.center.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

/**
 * 
 * @author Kenny
 * 
 */
public abstract class AbstractDAO {

	@Autowired
	protected SqlMapClientTemplate dbSdbSqlMap;
}
