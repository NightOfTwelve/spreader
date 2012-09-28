package com.nali.spreader.workshop.register;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.spreader.constants.Channel;
import com.nali.spreader.constants.Website;
import com.nali.spreader.data.KeyValue;
import com.nali.spreader.factory.SimpleActionConfig;
import com.nali.spreader.factory.base.ContextMeta;
import com.nali.spreader.factory.base.SingleTaskMachineImpl;
import com.nali.spreader.factory.base.SingleTaskMeta;
import com.nali.spreader.factory.exporter.SingleTaskExporter;
import com.nali.spreader.factory.passive.SinglePassiveTaskProducer;
import com.nali.spreader.factory.result.ContextedResultProcessor;
import com.nali.spreader.model.ClientTask;
import com.nali.spreader.service.IFirstLoginGuideService;

@Component
public class FirstTimeGuide extends SingleTaskMachineImpl implements SinglePassiveTaskProducer<KeyValue<Long, Date>>, ContextedResultProcessor<Long, SingleTaskMeta> {
	private static Logger logger = Logger.getLogger(FirstTimeGuide.class);
	private static final int basePriority = ClientTask.BASE_PRIORITY_MAX*9/10;
	@Autowired
	private IFirstLoginGuideService firstLoginGuideService;

	public FirstTimeGuide() {
		super(SimpleActionConfig.firstTimeGuide, Website.weibo, Channel.normal);
		ContextMeta contextMeta = new ContextMeta("uid");
		setContextMeta(contextMeta);
	}
	
	@Override
	public void work(KeyValue<Long, Date> uidDate, SingleTaskExporter exporter) {
		Long uid = uidDate.getKey();
		Date startTime = uidDate.getValue();
		exporter.setBasePriority(basePriority);
		exporter.setProperty("uid", uid);
		exporter.setUid(uid);
		exporter.setTimes(startTime, new Date(TimeUnit.DAYS.toMillis(30)+startTime.getTime()));
		exporter.send();
	}

	@Override
	public void handleResult(Date updateTime, Long resultObject, Map<String, Object> contextContents, Long uid) {
		if(uid.equals(contextContents.get("uid")) && uid.equals(resultObject)) {
			firstLoginGuideService.guide(uid);
		} else {
			logger.error("uid mismatch, uid:" + uid + ", resultObject:" + resultObject + ", context:" + contextContents.get("uid"));
		}
	}

}
