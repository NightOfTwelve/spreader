package com.nali.spreader.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.nali.lwtmq.receiver.Receiver;
import com.nali.spreader.model.ClientTask;
import com.nali.spreader.util.TogglingFlag;

@Service
public class PassiveTaskService implements IPassiveTaskService {
	private int batchSize;
	private TogglingFlag flag;
	private Receiver<ClientTask> passiveTaskReceiver;

//	@Autowired
//	public void setWeiboNormalPassiveTaskReceiver(Receiver<ClientTask> weiboNormalPassiveTaskReceiver) {
//		passiveTaskReceivers.put(TaskTypes.weiboNormal.getId(), weiboNormalPassiveTaskReceiver);
//	}
//
//	@Autowired
//	public void setWeiboRegisterPassiveTaskReceiver(Receiver<ClientTask> weiboRegisterPassiveTaskReceiver) {
//		passiveTaskReceivers.put(TaskTypes.weiboRegister.getId(), weiboRegisterPassiveTaskReceiver);
//	}
	
	@Override
	public List<ClientTask> getBatchTask() {
		if(flag.toggleFlag()) {
			synchronized (passiveTaskReceiver) {
				List<ClientTask> list = new ArrayList<ClientTask>(batchSize);
				for (int i = 0; i < batchSize; i++) {
					ClientTask task = passiveTaskReceiver.receive(1);//TODO receiveNoWait
					if(task==null) {
						break;
					}
					list.add(task);
				}
				return list;
			}
		} else {
			return Collections.emptyList();
		}
	}
	
	public void setBatchSize(int batchSize) {
		this.batchSize = batchSize;
	}

	public void setPassiveFetchPercent(int percent) {
		flag = new TogglingFlag(percent);
	}

	public void setPassiveTaskReceiver(Receiver<ClientTask> passiveTaskReceiver) {
		this.passiveTaskReceiver = passiveTaskReceiver;
	}
	
}
