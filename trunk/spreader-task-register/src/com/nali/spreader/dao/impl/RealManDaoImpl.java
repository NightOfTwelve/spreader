package com.nali.spreader.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.nali.spreader.dao.IRealManDao;
import com.nali.spreader.data.KeyValue;
import com.nali.spreader.data.RealMan;

@Repository
public class RealManDaoImpl implements IRealManDao {
	@Autowired
	private SqlMapClientTemplate sqlMap;

	@Override
	public List<RealMan> getNotVerifiedRealMan(int limit) {
		List<RealMan> list = this.sqlMap.queryForList("spreader_real_man.selectEffRealMan", limit);
		return list;
	}

	@Override
	public int updateSinaUseCount(Long id) {
		return this.sqlMap.update("spreader_real_man.updateSinaUseCount", id);
	}

	@Override
	public Long getRealManIdByRealIdAndName(KeyValue<String, String> param) {
		return (Long) this.sqlMap.queryForObject("spreader_real_man.selectRealManIdByUK", param);
	}
}