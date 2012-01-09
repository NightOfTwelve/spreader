package com.nali.spreader.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.nali.spreader.dao.IStrategyGroupDao;
import com.nali.spreader.model.StrategyGroup;


/**
 * DAO实现
 * 
 * @author xiefei
 * 
 */
@Repository
public class StrategyGroupDaoImpl implements IStrategyGroupDao {
	@Autowired
	private SqlMapClientTemplate sqlMap;

	@Override
	public Long insertReturnKey(StrategyGroup sg) {
		return (Long) sqlMap.insert("spreader_strategy_group.insertReturnKey",
				sg);
	}
}
