package com.nali.spreader.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nali.common.model.Limit;
import com.nali.common.pagination.PageResult;
import com.nali.spreader.dao.ICrudRegularJobDao;
import com.nali.spreader.dao.ICrudStrategyGroupDao;
import com.nali.spreader.dao.IStrategyGroupDao;
import com.nali.spreader.model.RegularJob;
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
	private static final Logger logger = Logger
			.getLogger(StrategyGroupServiceImpl.class);
	@Autowired
	private ICrudStrategyGroupDao gcrudDao;
	@Autowired
	private IStrategyGroupDao groupDao;
	@Autowired
	private ICrudRegularJobDao crudRegDao;

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

	@Override
	public Long saveGroupInfo(StrategyGroup sg) {
		Long key = null;
		if (sg != null) {
			key = groupDao.insertReturnKey(sg);
		} else {
			logger.warn("StrategyGroup为null,无法保存");
		}
		return key;
	}

	@Override
	public void syncRegularJob(Long gid, Long regularJobId) {
		if (!checkRegularJobGroupId(gid, regularJobId)) {
			RegularJob rj = crudRegDao.selectByPrimaryKey(regularJobId);
			if (rj != null) {
				rj.setGid(gid);
				crudRegDao.updateByPrimaryKeyWithBLOBs(rj);
			} else {
				logger.warn("获取RegularJob失败");
			}
		}
	}

	@Override
	public Boolean checkRegularJobGroupId(Long gid, Long regId) {
		Boolean tag = false;
		if (gid != null && regId != null) {
			RegularJob rj = crudRegDao.selectByPrimaryKey(regId);
			if (rj != null) {
				tag = rj.getGid().equals(gid);
			}
		}
		return tag;
	}
}
