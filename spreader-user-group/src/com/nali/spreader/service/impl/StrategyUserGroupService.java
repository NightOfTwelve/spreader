package com.nali.spreader.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nali.spreader.dao.ICrudStrategyUserGroupDao;
import com.nali.spreader.model.StrategyUserGroup;
import com.nali.spreader.service.IStrategyUserGroupService;

@Service
public class StrategyUserGroupService implements IStrategyUserGroupService {
	@Autowired
	private ICrudStrategyUserGroupDao crudStrategyUserGroupDao;

	@Override
	public StrategyUserGroup get(Long sid) {
		return crudStrategyUserGroupDao.selectByPrimaryKey(sid);
	}

	@Override
	public void save(StrategyUserGroup extendConfig) {
		int updateCount = crudStrategyUserGroupDao.updateByPrimaryKey(extendConfig);
		if(updateCount==0) {
			crudStrategyUserGroupDao.insertSelective(extendConfig);		
		}
	}

}
