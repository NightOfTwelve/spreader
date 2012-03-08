package com.nali.spreader.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nali.common.model.Limit;
import com.nali.spreader.dao.ICrudClientTaskDao;
import com.nali.spreader.dao.ITaskDao;
import com.nali.spreader.model.ActiveTaskDto;
import com.nali.spreader.model.ClientTask;
import com.nali.spreader.model.ClientTaskExample;
import com.nali.spreader.model.TaskBatch;
import com.nali.spreader.model.UserTaskCount;
import com.nali.spreader.model.UserTaskCount.QueryDto;
import com.nali.spreader.util.PerformanceLogger;
import com.nali.spreader.util.SpecialDateUtil;

@Service
public class UidPoolService implements IUidPoolService {
	@Autowired
	private ICrudClientTaskDao crudClientTaskDao;
	@Autowired
	private ITaskDao taskDao;
	/**
	 * @see ClientTask#BASE_PRIORITY_MAX
	 */
	private String priorityExpression="pow(greatest(floor($T-$end/$n),0),2)+pow($priority, 2)";
	private String finalPriorityExpression;
	
	public UidPoolService() {
		initPriorityExpressionConstant();
		refreshFinalPriorityExpression();
	}
	
	private void refreshFinalPriorityExpression() {
		finalPriorityExpression = priorityExpression.replaceAll("\\$T", "" + getPriorityBaseTime());
	}

	protected void initPriorityExpressionConstant() {
		priorityExpression = priorityExpression.replaceAll("\\$end", "UNIX_TIMESTAMP(expire_time)");
		priorityExpression = priorityExpression.replaceAll("\\$priority", "base_priority");
		priorityExpression = priorityExpression.replaceAll("\\$n", "60");
	}
	
	protected long getPriorityBaseTime() {
		return (SpecialDateUtil.afterToday(2).getTime()/(1000*60));
	}

	@Override
	public void save(ClientTask task) {
		if(task.getStartTime()==null) {
			task.setStartTime(new Date());
		}
		crudClientTaskDao.insertSelective(task);
	}
	
	@Override
	@Transactional
	public List<ClientTask> assignTasks(Long batchId, Long uid, Long actionId, Integer taskType, Integer count) {
		UserTaskCount dto = new UserTaskCount();
		dto.setUid(uid);
		dto.setActionId(actionId);
		dto.setTaskType(taskType);
		dto.setCount(count);
		List<ClientTask> tasks = taskDao.assignTasksSelect(dto);
		assignTasksUpdate(tasks, batchId);
		return tasks;
	}
	
	private void assignTasksUpdate(List<ClientTask> tasks, Long batchId) {
		if(tasks.size()==0) {
			return;
		}
		List<Long> taskIds = new ArrayList<Long>(tasks.size());
		for (ClientTask task : tasks) {
			taskIds.add(task.getId());
		}
		ClientTask record = new ClientTask();
		record.setStatus(ClientTask.STATUS_ASSIGNED);
		record.setBatchId(batchId);
		ClientTaskExample example = new ClientTaskExample();
		example.createCriteria().andStatusNotEqualTo(ClientTask.STATUS_ASSIGNED).andIdIn(taskIds);
		int updateCount = crudClientTaskDao.updateByExampleSelective(record, example);
		if(updateCount!=taskIds.size()) {
			throw new IllegalStateException("transaction error, taskIds:" + taskIds);//TODO log and retry?
		}
	}

	@Override
	public Long getBatchId(Integer taskType, Long clientId) {
		TaskBatch batch = new TaskBatch();
		batch.setClientId(clientId);
		batch.setTaskType(taskType);
		Date current = new Date();
		batch.setAssignTime(current);
		batch.setExpireTime(current);
		return taskDao.insertTaskBatch(batch);
	}

	@Override
	public void expireTasks(Date now) {//ASK is using null for status good?
		taskDao.expireTasks(now);
	}

	@Override
	public void activeTasks(Date now) {
		ActiveTaskDto dto = new ActiveTaskDto();
		dto.setNow(now);
		dto.setPriorityExpression(finalPriorityExpression);
		PerformanceLogger.infoStart();//TODO increase sql performance
		int taskCount = taskDao.activeTasks(dto);
		PerformanceLogger.info("activeTasks[" + taskCount + "]");
	}
	
	@Override
	public void changeDate() {
		//TODO
		refreshFinalPriorityExpression();
		PerformanceLogger.infoStart();
		int refreshCount = taskDao.refreshPriority(finalPriorityExpression);
		PerformanceLogger.info("refreshPriority[" + refreshCount + "]");
	}

	@Override
	public ClientTask getLowestPriorityClientTask(Integer taskType, int topN) {
		ClientTaskExample example = new ClientTaskExample();
		example.setLimit(Limit.newInstanceForLimit(topN-1, 1));
		example.setOrderByClause("priority desc");
		example.createCriteria().andTaskTypeEqualTo(taskType).andStatusEqualTo(ClientTask.STATUS_READY);
		List<ClientTask> rlt = crudClientTaskDao.selectByExampleWithoutBLOBs(example);
		return rlt.size()==0?null:rlt.get(0);
	}

	@Override
	public List<UserTaskCount> countTask(Integer taskType, Long lowestPriority) {
		QueryDto queryDto = new UserTaskCount.QueryDto();
		queryDto.setTaskType(taskType);
		queryDto.setLowestPriority(lowestPriority);
		return taskDao.countTask(queryDto);
	}

	@Override
	public List<UserTaskCount> countTaskAll() {
		return taskDao.countUserTask();
	}
	
}
