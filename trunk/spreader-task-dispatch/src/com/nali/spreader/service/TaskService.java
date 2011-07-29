package com.nali.spreader.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nali.common.model.Limit;
import com.nali.spreader.dao.ICrudClientTaskDao;
import com.nali.spreader.dao.ICrudTaskBatchDao;
import com.nali.spreader.dao.ITaskDao;
import com.nali.spreader.model.ClientTask;
import com.nali.spreader.model.ClientTaskExample;
import com.nali.spreader.model.TaskBatch;
import com.nali.spreader.model.TaskBatchExample;
import com.nali.spreader.model.TaskResult;
import com.nali.spreader.model.UserTaskCount;
import com.nali.spreader.util.CompareUtil;
import com.nali.spreader.util.avg.ItemCount;

@Service
public class TaskService implements ITaskRepository, ITaskService {//TODO cleanExpireTask
	@Autowired
	private ICrudTaskBatchDao crudTaskBatchDao;
	@Autowired
	private ICrudClientTaskDao crudClientTaskDao;
	@Autowired
	private ITaskDao taskDao;
	@Autowired
	private IResultSender resultSender;

	@Override
	public void save(ClientTask task) {
		task.setId(null);
		task.setBatchId(null);
		crudClientTaskDao.insertSelective(task);
	}

	@Override
	public List<UserTaskCount> countUserTask() {
		return taskDao.countUserTask();
	}

	@Transactional
	@Override
	public void assignToBatch(Long uid, List<ItemCount<Long>> actionCounts) {
		TaskBatch batch = new TaskBatch();
		batch.setUid(uid);
		batch = taskDao.insertTaskBatch(batch);
		Long batchId = batch.getId();
		Date expireTime = null;
		for (ItemCount<Long> averageItem : actionCounts) {
			Long actionId = averageItem.getItem();
			int count = averageItem.getCount();
			Date actionExpireTime = assignTaskAndReturnExpireTime(actionId, uid, count, batchId);
			expireTime = CompareUtil.min(actionExpireTime, expireTime);
		}
		updateBatchExpireTime(batchId, expireTime);
	}

	private void updateBatchExpireTime(Long batchId, Date expireTime) {
		TaskBatch record = new TaskBatch();
		record.setId(batchId);
		record.setExpireTime(expireTime);
		crudTaskBatchDao.updateByPrimaryKeySelective(record);
	}

	private Date assignTaskAndReturnExpireTime(Long actionId, Long uid, int count, Long batchId) {
		ClientTaskExample example = new ClientTaskExample();
		example.createCriteria().andActionIdEqualTo(actionId).andUidEqualTo(uid);
		example.setLimit(Limit.newInstanceForLimit(0, count));
		List<ClientTask> tasks = crudClientTaskDao.selectByExampleWithoutBLOBs(example);
		Date expireTime = null;
		List<Long> taskIds = new ArrayList<Long>();
		for (ClientTask task : tasks) {
			taskIds.add(task.getId());
			Date taskExpireTime = task.getExpireTime();
			expireTime = CompareUtil.min(taskExpireTime, expireTime);
		}
		
		ClientTaskExample updateExample = new ClientTaskExample();
		updateExample.createCriteria().andIdIn(taskIds);
		ClientTask record = new ClientTask();
		record.setBatchId(batchId);
		crudClientTaskDao.updateByExampleSelective(record, updateExample);
		return expireTime;
	}

	@Transactional
	@Override
	public List<ClientTask> assignBatchTaskToClient(Long clientId) {
		TaskBatchExample example = new TaskBatchExample();
		example.createCriteria()
			.andExpireTimeGreaterThan(new Date())
			.andClientIdIsNull()
			//.andAssignTime TODO
			;
		example.setLimit(Limit.newInstanceForLimit(0, 1));
		List<TaskBatch> rltList = crudTaskBatchDao.selectByExample(example);
		if(rltList.size()==0) {
			return Collections.emptyList();
		}
		
		Long batchId = rltList.get(0).getId();
		TaskBatch record = new TaskBatch();
		record.setId(batchId);
		record.setClientId(clientId);
		crudTaskBatchDao.updateByPrimaryKeySelective(record);
		
		ClientTaskExample taskExample = new ClientTaskExample();
		taskExample.createCriteria().andBatchIdEqualTo(batchId);
		return crudClientTaskDao.selectByExampleWithBLOBs(taskExample);
	}

	@Override
	public void reportTask(List<TaskResult> rlts, Long clientId) {
		for (TaskResult taskResult : rlts) {
			taskResult.setClientId(clientId);
			resultSender.send(taskResult);
		}
	}

}
