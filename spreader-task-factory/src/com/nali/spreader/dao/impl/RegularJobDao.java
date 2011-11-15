package com.nali.spreader.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.nali.spreader.dao.IRegularJobDao;
import com.nali.spreader.model.RegularJob;

@Repository
public class RegularJobDao implements IRegularJobDao {

    @Autowired
    private SqlMapClientTemplate sqlMap;

	@Override
	public Long insert(RegularJob regularJob) {
		return (Long) sqlMap.insert("spreader_regular_job.insert", regularJob);
	}

}
