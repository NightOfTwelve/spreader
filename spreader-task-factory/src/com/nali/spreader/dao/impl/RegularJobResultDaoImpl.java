package com.nali.spreader.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.nali.spreader.dao.IRegularJobResultDao;
import com.nali.spreader.factory.config.RegularJobResultDto;

@Repository
public class RegularJobResultDaoImpl implements IRegularJobResultDao {
	@Autowired
	private SqlMapClientTemplate sqlMap;

	@Override
	public List<RegularJobResultDto> findRegularJobResultDtoByJobId(Map<String, Object> params) {
		return sqlMap.queryForList("spreader_regular_job_result.selectJobResultResult", params);
	}

	@Override
	public int countRegularJobResultDtoByJobId(Long jobId) {
		return (Integer) sqlMap.queryForObject("spreader_regular_job_result.countJobResultResult", jobId);
	}
}
