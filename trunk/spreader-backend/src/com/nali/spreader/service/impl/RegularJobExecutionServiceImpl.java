package com.nali.spreader.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nali.common.model.Limit;
import com.nali.common.pagination.PageResult;
import com.nali.common.util.CollectionUtils;
import com.nali.spreader.dao.ICrudTaskDao;
import com.nali.spreader.dao.IRegularJobResultDao;
import com.nali.spreader.dao.ITaskResultQueryDao;
import com.nali.spreader.factory.config.RegularJobResultDto;
import com.nali.spreader.factory.config.TaskResultInfoQueryDto;
import com.nali.spreader.model.Task;
import com.nali.spreader.model.TaskExample;
import com.nali.spreader.model.TaskExample.Criteria;
import com.nali.spreader.service.IRegularJobExecutionService;

/**
 * 执行结果
 * 
 * @author xiefei
 * 
 */
@Service
public class RegularJobExecutionServiceImpl implements IRegularJobExecutionService {
	@Autowired
	private IRegularJobResultDao resultDao;
	@Autowired
	private ITaskResultQueryDao taskResultDao;
	@Autowired
	private ICrudTaskDao curdTaskDao;

	@Override
	public PageResult<RegularJobResultDto> findRegularJobResultByJobId(Long jobId, Limit limit) {
		Map<String, Object> params = CollectionUtils.newHashMap(2);
		params.put("jobId", jobId);
		params.put("limit", limit);
		List<RegularJobResultDto> list = this.resultDao.findRegularJobResultDtoByJobId(params);
		int count = this.resultDao.countRegularJobResultDtoByJobId(jobId);
		return new PageResult<RegularJobResultDto>(list, limit, count);
	}

	@Override
	public List<TaskResultInfoQueryDto> findTaskResultInfoByResultId(Long resultId) {
		List<TaskResultInfoQueryDto> list = Collections.emptyList();
		if (resultId != null) {
			list = this.taskResultDao.queryTaskResultStatusList(resultId);
		}
		return list;
	}

	@Override
	public List<Task> findTaskInfoByResultId(Long resultId) {
		List<Task> list = Collections.emptyList();
		TaskExample te = new TaskExample();
		te.setOrderByClause("id desc");
		Criteria c = te.createCriteria();
		if (resultId != null) {
			c.andResultIdEqualTo(resultId);
			list = this.curdTaskDao.selectByExample(te);
		}
		return list;
	}

	@Override
	public PageResult<Task> findPageResultTaskByResultId(Long resultId, Limit limit) {
		TaskExample te = new TaskExample();
		te.setLimit(limit);
		Criteria c = te.createCriteria();
		if (resultId != null) {
			c.andResultIdEqualTo(resultId);
			List<Task> list = this.curdTaskDao.selectByExample(te);
			int count = this.curdTaskDao.countByExample(te);
			return new PageResult<Task>(list, limit, count);
		}
		return null;
	}
}
