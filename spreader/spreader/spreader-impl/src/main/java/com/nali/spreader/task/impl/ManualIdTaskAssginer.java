package com.nali.spreader.task.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.nali.spreader.content.IContentAssembler;
import com.nali.spreader.model.Client;
import com.nali.spreader.model.Task;
import com.nali.spreader.model.TaskAssign;
import com.nali.spreader.model.TaskType;
import com.nali.spreader.repo.ClientIdRepository;
import com.nali.spreader.task.ITaskAssginService;
import com.nali.spreader.task.ITaskAssigner;
import com.nali.spreader.task.ITaskService;
import com.nali.spreader.task.TaskStatus;
import com.nali.spreader.util.RandomUtil;

public class ManualIdTaskAssginer implements ITaskAssigner {
	@Autowired
	private ITaskAssginService taskAssginService;

	@Autowired
	private ITaskService taskService;

	@Autowired
	private ClientIdRepository clientIdRepository;

	@Autowired
	private IContentAssembler contentAssembler;

	@Override
	public void assignTasks(TaskType taskType) {
		int batchCount = taskType.getBatchSize();
		int count = this.taskAssginService.getUnAssignTaskCount(taskType
				.getId());
		int assigned = 0;
		int taskTypeId = taskType.getId();

		// 已经分配的assign数小于总数
		while (assigned < count) {
			// 批量取出待分配的任务列表，小于每批任务的个数
			List<TaskAssign> taskAssigns = this.taskAssginService
					.getUnAssignedTasks(taskType.getId(), assigned + 1,
							batchCount);
			int perTaskAssgined = 0;
			if (batchCount > 1) {
				List<String> contentSlices = new ArrayList<String>();
				for (TaskAssign taskAssign : taskAssigns) {
					long fromId = taskAssign.getFromId();
					long toId = taskAssign.getToId();
					int websiteId = taskAssign.getWebsiteId();
					int taskCount = (int) (toId - fromId + 1);
					// 产生的Id的数
					if (taskCount > 0) {

						// 前面有剩余未处理的Id分配
						if (perTaskAssgined > 0) {
							Long fromIdLong = Long.valueOf(fromId);
							Integer perTaskAssginedInteger = Integer
									.valueOf(perTaskAssgined);
							Integer taskCountInteger = Integer.valueOf(taskCount);

							this.handleCrossTaskAssign(batchCount,
									perTaskAssginedInteger, fromIdLong,
									taskCountInteger, contentSlices, taskAssign, taskType);

							perTaskAssgined = perTaskAssginedInteger.intValue();
							fromId = fromIdLong.longValue();
							taskCount = taskCountInteger.intValue();
						}
						
						if (taskCount > batchCount) {
							// 按id数分批
							int batches = (int) (taskCount / batchCount);
							int batchLeft = (int) (taskCount % batchCount);
							int i = 0;
							while (i++ < batches) {
								long batchEndId = fromId + batchCount - 1;
								String content = this.contentAssembler
										.assembleContent(taskTypeId, websiteId,
												fromId, batchEndId);
								this.handleTask(taskType, taskAssign, content);
								fromId = batchEndId + 1;
							}

							if (batchLeft > 0) {
								Long fromIdLong = Long.valueOf(fromId);
								Integer perTaskAssginedInteger = Integer
										.valueOf(perTaskAssgined);
								this.handleCrossTaskAssign(batchCount,
										perTaskAssginedInteger, fromIdLong,
										batchLeft, contentSlices, taskAssign, taskType);
								perTaskAssgined = perTaskAssginedInteger.intValue();
								fromId = fromIdLong.longValue();
							}
						}
					}
				}
			}else if(batchCount == 1){
				if(taskAssigns.size() == 1) {
					TaskAssign taskAssign = taskAssigns.get(0);
					Long fromId = taskAssign.getFromId();
					Long toId = taskAssign.getToId();
					if(toId != null) {
						if(fromId != toId) {
							throw new IllegalArgumentException("");
						}
					}
					
					this.handleTask(taskType, taskAssign, fromId);
				}
			}
		}
	}

	private void handleCrossTaskAssign(int batchCount,
			Integer perTaskAssginedInteger, Long fromIdLong,
			Integer taskCountInteger, List<String> contentSlices,
			TaskAssign taskAssign, TaskType taskType) {
		long fromId = fromIdLong.longValue();
		int perTaskAssgined = perTaskAssginedInteger.intValue();
		int taskCount = taskCountInteger.intValue();
		int duration = batchCount - perTaskAssgined;
		int taskTypeId = taskAssign.getTaskType();
		int websiteId = taskAssign.getWebsiteId();
		if (taskCount >= duration) {
			int thisBatch = batchCount - perTaskAssgined;
			long endId = fromId + thisBatch - 1;
			String thisContent = this.contentAssembler.assembleContent(
					taskTypeId, websiteId, fromId, endId);
			contentSlices.add(thisContent);
			String content = this.contentAssembler.assembleContent(taskTypeId,
					websiteId, contentSlices);
			this.handleTask(taskType, taskAssign, content);
			contentSlices = new ArrayList<String>();
			fromId = endId + 1;

			int nextBatch = taskCount - thisBatch;
			String nextContent = this.contentAssembler.assembleContent(
					taskTypeId, websiteId, fromId, fromId + nextBatch - 1);
			contentSlices.add(nextContent);
			perTaskAssgined = nextBatch;
			taskCount -= duration;
		} else {
			String content = this.contentAssembler.assembleContent(taskTypeId,
					websiteId, fromId, fromId + taskCount - 1);
			contentSlices.add(content);
			perTaskAssgined += taskCount;
			taskCount = 0;
		}

		perTaskAssginedInteger = Integer.valueOf(perTaskAssgined);
		fromIdLong = Long.valueOf(fromId);
		taskCountInteger = Integer.valueOf(taskCount);
	}

	private void handleTask(TaskType taskType, TaskAssign taskAssign, String content) {
		Task task = new Task();
		task.setContent(content);
		this.assembleTask(task, taskType, taskAssign);
		
		this.taskService.generateTask(task);
	}
	
	private void handleTask(TaskType taskType, TaskAssign taskAssign, Long id) {
		Task task = new Task();
		this.assembleTask(task, taskType, taskAssign);
		
		if(taskType.getHasWebContent()) {
			task.setContentId(id);
		}else{
			task.setContent(String.valueOf(id));
		}
		this.taskService.generateTask(task);
	}
	
	private void assembleTask(Task task, TaskType taskType, TaskAssign taskAssign) {
		List<Client> allClients = this.clientIdRepository.getAll();
		Client client = RandomUtil.getRandomObject(allClients);
		task.setClientId(client.getId());
		int websiteId = taskAssign.getWebsiteId();
		task.setHasSubTask(false);
		task.setCreatedTime(new Date());
		task.setStatus(TaskStatus.assigned.getStatusVal());
		task.setUseCookie(true);
		task.setWebsiteId(websiteId);
		task.setBatchCount(taskType.getBatchSize());
	}

	public IContentAssembler getContentAssembler() {
		return contentAssembler;
	}

	public void setContentAssembler(IContentAssembler contentAssembler) {
		this.contentAssembler = contentAssembler;
	}
}
