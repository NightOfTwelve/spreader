package com.nali.spreader.dao;

import java.util.List;

import com.nali.spreader.group.exp.PropertyExpressionDTO;

public interface IDynamicUserDao {
	/**
	 * 查询动态分组中的用户
	 * 
	 * @param dto
	 * @return
	 */
	List<Long> queryDynamicUsers(PropertyExpressionDTO dto);
}
