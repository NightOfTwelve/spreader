package com.nali.spreader.factory.exporter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.nali.lwtmq.sender.AsyncSender;
import com.nali.spreader.constants.TaskType;
import com.nali.spreader.model.ClientTask;

@Component
public class ClientTaskExporterFactorys {
	private MultiLwtmqTaskSender passiveTaskSender = new MultiLwtmqTaskSender();

	@Autowired
	public void setWeiboNormalPassiveTaskSender(AsyncSender<ClientTask> weiboNormalPassiveTaskSender) {
		passiveTaskSender.register(TaskType.weiboNormal.getId(), weiboNormalPassiveTaskSender);
	}

	@Autowired
	public void setWeiboRegisterPassiveTaskSender(AsyncSender<ClientTask> weiboRegisterPassiveTaskSender) {
		passiveTaskSender.register(TaskType.weiboRegister.getId(), weiboRegisterPassiveTaskSender);
	}
	
	@Bean
	public ClientTaskExporterFactory passiveTaskExporterFactory() {//TODO 减少queue配置
		return new ClientTaskExporterFactory(passiveTaskSender);
	}

	@Bean
	@Autowired
	public ClientTaskExporterFactory regularTaskExporterFactory(AsyncSender<ClientTask> regularTaskSender) {
		LwtmqTaskSender lwtmqTaskSender = new LwtmqTaskSender();
		lwtmqTaskSender.setLwtmqSender(regularTaskSender);
		return new ClientTaskExporterFactory(lwtmqTaskSender);
	}
}
