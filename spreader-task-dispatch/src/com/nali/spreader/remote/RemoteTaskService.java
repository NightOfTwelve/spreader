package com.nali.spreader.remote;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.nali.lwtmq.receiver.Receiver;
import com.nali.spreader.constants.TaskType;
import com.nali.spreader.front.ClientContext;
import com.nali.spreader.model.ClientTask;
import com.nali.spreader.model.TaskResult;
import com.nali.spreader.service.IPassiveTaskService;
import com.nali.spreader.service.ITaskService;
import com.nali.spreader.service.PassiveTaskService;

@Service
public class RemoteTaskService implements IRemoteTaskService {//TODO 减少queue配置
	private static Logger logger = Logger.getLogger(RemoteTaskService.class);
	@Autowired
	private ITaskService taskService;
	private ObjectMapper objectMapper=new ObjectMapper();
	
	private Map<Integer, IPassiveTaskService> passiveTaskServices;

	@SuppressWarnings("unchecked")
	@Autowired
	public void initPassiveTaskService(ApplicationContext context) {
		passiveTaskServices = new HashMap<Integer, IPassiveTaskService>();
		for (TaskType taskType : TaskType.values()) {
			//TODO 基于接口IPassiveTaskService
			PassiveTaskService service = context.getAutowireCapableBeanFactory().createBean(PassiveTaskService.class);
			Receiver<ClientTask> receiver = context.getBean(taskType.getPassiveTaskReceiverBeanName(), Receiver.class);
			service.setPassiveTaskReceiver(receiver);
			service.setTaskType(taskType);
			passiveTaskServices.put(taskType.getId(), service);
		}
	}

	@Override
	public List<ClientTask> askForTasks() {
		ClientContext context = ClientContext.getCurrentContext();
		Long clientId = context.getClientId();
		Integer taskTypeId = context.getTaskType();
		IPassiveTaskService passiveTaskService = passiveTaskServices.get(taskTypeId);
		if(passiveTaskService==null) {
			throw new IllegalArgumentException("unknown taskType:" + taskTypeId);
		}
		List<ClientTask> taskList = passiveTaskService.getBatchTask();
		if(taskList.size()==0) {
			taskList = getRegularTasks(taskTypeId, clientId);
		}
		return tranContents(taskList);
	}

	private List<ClientTask> getRegularTasks(Integer taskType, Long clientId) {
		List<ClientTask> taskList = taskService.assignBatchTaskToClient(taskType, clientId);
		return taskList;
	}

	private List<ClientTask> tranContents(List<ClientTask> taskList) {
		for (ClientTask task : taskList) {
			String contents = task.getContents();
			try {
				Map<String, Object> contentObjects = objectMapper.readValue(contents, TypeFactory.mapType(HashMap.class, String.class, Object.class));
				task.setContentObjects(contentObjects);
				task.setContents(null);
			} catch (IOException e) {
				logger.error(e, e);
			}
		}
		return taskList;
	}

	@Override
	public void reportTask(List<TaskResult> rlts) {
		ClientContext context = ClientContext.getCurrentContext();
		Long clientId = context.getClientId();
		Integer taskType = context.getTaskType();
		taskService.reportTask(rlts, taskType, clientId);
	}

}
