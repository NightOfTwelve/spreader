package com.nali.spreader.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.nali.spreader.dao.IDynamicUserDao;
import com.nali.spreader.group.exp.PropertyExpressionDTO;

@Repository
public class DynamicUserDao implements IDynamicUserDao {
	@Autowired
	private SqlMapClientTemplate sqlMap;

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> queryDynamicUsers(PropertyExpressionDTO dto) {
		return sqlMap.queryForList("spreader_user.queryDynamicUsers", dto);
	}
}
