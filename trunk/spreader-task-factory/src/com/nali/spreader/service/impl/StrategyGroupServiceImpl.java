package com.nali.spreader.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nali.common.model.Limit;
import com.nali.common.pagination.PageResult;
import com.nali.spreader.dao.ICrudStrategyGroupDao;
import com.nali.spreader.model.StrategyGroup;
import com.nali.spreader.model.StrategyGroupExample;
import com.nali.spreader.model.StrategyGroupExample.Criteria;
import com.nali.spreader.service.IStrategyGroupService;

/**
 * 
 * @author xiefei
 * 
 */
@Service
public class StrategyGroupServiceImpl implements IStrategyGroupService {
	@Autowired
	private ICrudStrategyGroupDao gcrudDao;

	@Override
	public PageResult<StrategyGroup> findStrategyGroupPageResult(
			StrategyGroup params, Integer pageNum, Integer pageSize) {
		if (params == null) {
			params = new StrategyGroup();
		}
		StrategyGroupExample exp = new StrategyGroupExample();
		Criteria criteria = exp.createCriteria();
		String groupName = params.getGroupName();
		Integer groupType = params.getGroupType();
		if (groupName != null && !"".equals(groupName)) {
			criteria.andGroupNameEqualTo(groupName);
		}
		if (groupType != null && groupType > 0) {
			criteria.andGroupTypeEqualTo(groupType);
		}
		Limit lit = Limit.newInstanceForLimit(pageNum, pageSize);
		exp.setLimit(lit);
		List<StrategyGroup> list = gcrudDao.selectByExample(exp);
		int cnt = gcrudDao.countByExample(exp);
		return new PageResult<StrategyGroup>(list, lit, cnt);
	}

}
