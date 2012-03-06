package com.nali.spreader.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nali.common.model.Limit;
import com.nali.common.pagination.PageResult;
import com.nali.lts.SchedulerFactory;
import com.nali.lts.exceptions.SchedulerException;
import com.nali.spreader.dao.ICrudRegularJobDao;
import com.nali.spreader.dao.ICrudStrategyGroupDao;
import com.nali.spreader.dao.IStrategyGroupDao;
import com.nali.spreader.model.RegularJob;
import com.nali.spreader.model.RegularJobExample;
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
	private static final Logger logger = Logger.getLogger(StrategyGroupServiceImpl.class);
	@Autowired
	private ICrudStrategyGroupDao gcrudDao;
	@Autowired
	private IStrategyGroupDao groupDao;
	@Autowired
	private ICrudRegularJobDao crudRegDao;
	@Autowired
	private ICrudRegularJobDao crudRegularJobDao;

	@Override
	public PageResult<StrategyGroup> findStrategyGroupPageResult(StrategyGroup params, Integer pageNum,
			Integer pageSize) {
		if (params == null) {
			params = new StrategyGroup();
		}
		StrategyGroupExample exp = new StrategyGroupExample();
		Criteria criteria = exp.createCriteria();
		String groupName = params.getGroupName();
		Integer groupType = params.getGroupType();
		if (groupName != null && !"".equals(groupName)) {
			criteria.andGroupNameLike('%' + groupName + "%");
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
	public void syncRegularJob(Long gid, String groupName, Long regularJobId) {
		if (!checkRegularJobGroupId(gid, regularJobId)) {
			RegularJob rj = crudRegDao.selectByPrimaryKey(regularJobId);
			if (rj != null) {
				rj.setGid(gid);
				rj.setGname(groupName);
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

	@Override
	public void batRemoveStrategyGroup(Long... gids) {
		for (Long gid : gids) {
			if (gid != null) {
				// 首先根据分组ID获取策略实例
				List<RegularJob> jobList = this.findRegularJobListByStrategyGroupId(gid);
				if (jobList.size() > 0) {
					for (RegularJob job : jobList) {
						if (job != null) {
							Long jobId = job.getId();
							String jobName = job.getName();
							try {
								crudRegularJobDao.deleteByPrimaryKey(jobId);
								unSchedule(jobName, jobId);
							} catch (Exception e) {
								logger.error("策略删除失败,策略ID：" + jobId, e);
							}
						}
					}
				}
				// 删除策略组
				try {
					gcrudDao.deleteByPrimaryKey(gid);
				} catch (Exception e) {
					logger.error("策略组删除失败,策略组ID：" + gid, e);
				}
			}
		}
	}

	@Override
	public List<RegularJob> findRegularJobListByStrategyGroupId(Long gid) {
		RegularJobExample example = new RegularJobExample();
		com.nali.spreader.model.RegularJobExample.Criteria c = example.createCriteria();
		c.andGidEqualTo(gid);
		List<RegularJob> list = crudRegularJobDao.selectByExampleWithBLOBs(example);
		return list;
	}

	private void unSchedule(String name, Long id) {
		try {
			SchedulerFactory.getInstance().getScheduler().unscheduleTask(getTriggerName(name, id), name);
		} catch (SchedulerException e) {
			throw new IllegalArgumentException(e);
		}
	}

	private String getTriggerName(String name, Long id) {
		return name + "_" + id;
	}

	@Override
	public void rollBackStrategyGroupByGid(Long gid) {
		if (gid != null) {
			try {
				this.gcrudDao.deleteByPrimaryKey(gid);
			} catch (Exception e) {
				logger.error("删除策略分组失败,分组编号:" + gid, e);
			}
		}
	}
}
