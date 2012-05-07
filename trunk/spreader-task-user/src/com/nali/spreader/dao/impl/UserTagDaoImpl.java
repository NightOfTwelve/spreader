package com.nali.spreader.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.nali.spreader.dao.IUserTagDao;
import com.nali.spreader.data.UserTag;

/**
 * dao实现
 * 
 * @author xiefei
 * 
 */
@Repository
public class UserTagDaoImpl implements IUserTagDao {
	@Autowired
	private SqlMapClientTemplate sqlMap;

	@Override
	public int updateCategoryIdByTagId(UserTag userTag) {
		int rows = this.sqlMap.update("spreader_user_tag.updateCategoryByKeywordId", userTag);
		return rows;
	}
}
