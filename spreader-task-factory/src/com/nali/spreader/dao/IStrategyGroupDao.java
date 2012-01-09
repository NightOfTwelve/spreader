package com.nali.spreader.dao;

import com.nali.spreader.model.StrategyGroup;

/**
 * 自定义的策略分组DAO
 * 
 * @author xiefei
 * 
 */
public interface IStrategyGroupDao {
	/**
	 * 保存记录并返回ID
	 * 
	 * @param sg
	 * @return
	 */
	Long insertReturnKey(StrategyGroup sg);
}
