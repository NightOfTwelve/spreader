package com.nali.spreader.task.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.nali.spreader.content.IContentAssembler;
import com.nali.spreader.model.Client;
import com.nali.spreader.model.Task;
import com.nali.spreader.model.TaskAssign;
import com.nali.spreader.model.TaskType;
import com.nali.spreader.repo.ClientIdRepository;
import com.nali.spreader.repo.TaskTypeRepository;
import com.nali.spreader.task.ITaskAssginService;
import com.nali.spreader.task.ITaskAssigner;
import com.nali.spreader.task.ITaskService;
import com.nali.spreader.util.RandomUtil;

public class ManualIdTaskAssginer implements ITaskAssigner {
	@Autowired
	private ITaskAssginService taskAssginService;

	@Autowired
	private TaskTypeRepository taskTypeRepository;

	@Autowired
	private ITaskService taskService;

	@Autowired
	private ClientIdRepository clientIdRepository;
	
	private IContentAssembler contentAssembler;

	// private int taskAssignCount = 50;
	//	
	// public int getTaskAssignCount() {
	// return taskAssignCount;
	// }
	// public void setTaskAssignCount(int taskAssignCount) {
	// this.taskAssignCount = taskAssignCount;
	// }
	
	@Override
	public void assignTasks(TaskType taskType) {
		List<TaskType> types = this.taskTypeRepository.getAll();
		List<Client> allClients = this.clientIdRepository.getAll();
		int batchCount = taskType.getBatchSize();
		int count = this.taskAssginService.getUnAssignTaskCount(taskType
				.getBatchSize());
		int assigned = 0;
		int taskAssigned = 0;
		int taskTypeId = taskType.getId();
		while (assigned < count) {
			List<TaskAssign> taskAssigns = this.taskAssginService
					.getUnAssignedTasks(taskType.getId(), assigned + 1,
							batchCount);
			for (TaskAssign taskAssign : taskAssigns) {
				long fromId = taskAssign.getFromId();
				long toId = taskAssign.getToId();

				long taskCount = toId - fromId + 1;
				if (taskCount > 0) {
					if (taskCount > batchCount) {
						int perTaskAssgined = 0;
						while (perTaskAssgined < taskCount) {
							Task task = new Task();
							Client client = RandomUtil
									.getRandomObject(allClients);
							task.setClientId(client.getId());
							String content = "";
							boolean hasSubTask = false;
							int websiteId = taskAssign.getWebsiteId();
							if(fromId != toId) {
								 content = this.contentAssembler.assembleContent(taskTypeId, websiteId, fromId, toId);
								 hasSubTask = this.contentAssembler.hasSubTask(taskTypeId, websiteId, fromId, toId);
							}else{
								content = this.contentAssembler.assembleContent(taskTypeId, websiteId, fromId);
								 hasSubTask = this.contentAssembler.hasSubTask(taskTypeId, websiteId, fromId);
							}
							
							task.setContent(content);
							task.setHasSubTask(hasSubTask);
							task.setCreatedTime(new Date());
							task.setStatus()
							this.taskService.generateTask(task);
						}
					} else {

					}
				}
			}
		}
	}

	public IContentAssembler getContentAssembler() {
		return contentAssembler;
	}

	public void setContentAssembler(IContentAssembler contentAssembler) {
		this.contentAssembler = contentAssembler;
	}
}
