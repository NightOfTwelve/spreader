package com.nali.spreader.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.nali.spreader.dao.IRobotContentDao;
import com.nali.spreader.data.KeyValue;

@Repository
public class RobotContentDaoImpl implements IRobotContentDao {
	@Autowired
	private SqlMapClientTemplate sqlMap;

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> queryRobotIdByContentAndType(KeyValue<Long, Integer> params) {
		return sqlMap.queryForList("spreader_robot_content.selectRobotIdByContentId", params);
	}

	@Override
	public List<Long> queryContentIdByUid(KeyValue<Long, Integer> params) {
		return sqlMap.queryForList("spreader_robot_content.selectContentIdByUid", params);
	}
}
