package com.nali.spreader.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
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
	private static Logger logger = Logger.getLogger(TaskService.class);
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
		try {
			crudClientTaskDao.insertSelective(task);
		} catch (Exception e) {
			logger.error("receive task error, taskCode:" + task.getTaskCode(), e);
		}
	}

	@Override
	public List<UserTaskCount> countUserTask() {
		return taskDao.countUserTask();
	}

	@Transactional
	@Override
	public void assignToBatch(Integer taskType, Long uid, List<ItemCount<Long>> actionCounts) {
		TaskBatch batch = new TaskBatch();
		batch.setTaskType(taskType);
		Long batchId = taskDao.insertTaskBatch(batch);
		Date expireTime = null;
		for (ItemCount<Long> averageItem : actionCounts) {
			Long actionId = averageItem.getItem();
			int count = averageItem.getCount();
			if(count!=0) {
				Date actionExpireTime = assignTaskAndReturnExpireTime(actionId, taskType, uid, count, batchId);
				expireTime = CompareUtil.min(actionExpireTime, expireTime);
			}
		}
		updateBatchExpireTime(batchId, expireTime);
	}

	@Transactional
	@Override
	public void assignToBatch(List<ClientTask> tasks, Integer taskType, Long clientId) {
		TaskBatch batch = new TaskBatch();
		batch.setClientId(clientId);
		batch.setTaskType(taskType);
		Date current = new Date();
		batch.setAssignTime(current);
		batch.setExpireTime(current);
		assignToBatch(batch, tasks);
	}

	@Transactional
	@Override
	public void assignToBatch(List<ClientTask> tasks, Integer taskType, Date expireTime) {
		TaskBatch batch = new TaskBatch();
		batch.setTaskType(taskType);
		batch.setExpireTime(expireTime);
		assignToBatch(batch, tasks);
	}

	private void assignToBatch(TaskBatch batch, List<ClientTask> tasks) {
		Long batchId = taskDao.insertTaskBatch(batch);
		for (ClientTask task : tasks) {
			task.setBatchId(batchId);
			crudClientTaskDao.insertSelective(task);
		}
	}

	private void updateBatchExpireTime(Long batchId, Date expireTime) {
		TaskBatch record = new TaskBatch();
		record.setId(batchId);
		record.setExpireTime(expireTime);
		crudTaskBatchDao.updateByPrimaryKeySelective(record);
	}

	private Date assignTaskAndReturnExpireTime(Long actionId, Integer taskType, Long uid, int count, Long batchId) {
		ClientTaskExample example = new ClientTaskExample();
		example.createCriteria()
			.andActionIdEqualTo(actionId)
			.andUidEqualTo(uid).andTaskTypeEqualTo(taskType)
			.andBatchIdEqualTo(ClientTask.DEFAULT_BATCH_ID);
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
	public List<ClientTask> assignBatchTaskToClient(Integer taskType, Long clientId) {//TODO 锁问题
		Long batchId;
		synchronized(this) {
			TaskBatchExample example = new TaskBatchExample();
			example.createCriteria()
			.andTaskTypeEqualTo(taskType)
			.andExpireTimeGreaterThan(new Date())
			.andClientIdIsNull();
			example.setLimit(Limit.newInstanceForLimit(0, 1));
			List<TaskBatch> rltList = crudTaskBatchDao.selectByExample(example);
			if(rltList.size()==0) {
				return Collections.emptyList();
			}
			
			batchId = rltList.get(0).getId();
			TaskBatch record = new TaskBatch();
			record.setId(batchId);
			record.setClientId(clientId);
			record.setAssignTime(new Date());
			crudTaskBatchDao.updateByPrimaryKeySelective(record);
		}
		
		ClientTaskExample taskExample = new ClientTaskExample();
		taskExample.createCriteria().andBatchIdEqualTo(batchId);
		return crudClientTaskDao.selectByExampleWithBLOBs(taskExample);
	}

	@Override
	public void reportTask(List<TaskResult> rlts, Integer taskType, Long clientId) {
		for (TaskResult taskResult : rlts) {
			taskResult.setClientId(clientId);
			//TODO mark success&report fail
			if(TaskResult.STATUS_SUCCESS.equals(taskResult.getStatus())) {
				try {
					resultSender.send(taskResult);
				} catch (Exception e) {
					logger.error("handle result error, taskCode:" + taskResult.getTaskCode(), e);
				}
			} else {
				logger.error("task failed, id:" + taskResult.getTaskId()
								+ ", code:" + taskResult.getTaskCode()
								+ ", status:" + taskResult.getStatus()
								+ ", clientId:" + clientId
								+ "\r\n" + taskResult.getResult()
								);
			}
		}
	}

}