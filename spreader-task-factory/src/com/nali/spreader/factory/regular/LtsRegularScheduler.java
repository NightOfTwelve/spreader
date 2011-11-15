package com.nali.spreader.factory.regular;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.common.model.Limit;
import com.nali.common.pagination.PageResult;
import com.nali.lts.SchedulerFactory;
import com.nali.lts.context.TaskExecuteContext;
import com.nali.lts.exceptions.SchedulerException;
import com.nali.lts.exceptions.TaskExecuteException;
import com.nali.lts.task.AbstractTask;
import com.nali.lts.trigger.Trigger;
import com.nali.lts.trigger.TriggerFactory;
import com.nali.lts.trigger.TriggerMetaInfo;
import com.nali.lts.trigger.TriggerScheduleInfo;
import com.nali.lts.trigger.TriggerType;
import com.nali.spreader.dao.ICrudRegularJobDao;
import com.nali.spreader.dao.IRegularJobDao;
import com.nali.spreader.factory.config.Configable;
import com.nali.spreader.factory.config.ConfigableUnit;
import com.nali.spreader.factory.config.desc.ConfigableInfo;
import com.nali.spreader.model.RegularJob;
import com.nali.spreader.model.RegularJob.JobDto;
import com.nali.spreader.model.RegularJobExample;
import com.nali.spreader.model.RegularJobExample.Criteria;

@Component
public class LtsRegularScheduler extends AbstractTask implements RegularScheduler, BeanNameAware {
	private static Logger logger = Logger.getLogger(LtsRegularScheduler.class);
	private String beanName;
	@Autowired
	private ICrudRegularJobDao crudRegularJobDao;
	@Autowired
	private IRegularJobDao regularJobDao;
	private ObjectMapper objectMapper = new ObjectMapper();
	@Autowired
	private RegularProducerManager regularProducerManager;
	private List<ConfigableInfo> infoList;
	
	@Override
	public void execute(TaskExecuteContext taskContext) throws TaskExecuteException {
		String triggerName = taskContext.getAssociatedTrigger().getTriggerMetaInfo().getTriggerName();
		RegularJob regularJob = getRegularJob(triggerName);
		String name = regularJob.getName();
		try {
			Object config = unSerialize(regularJob.getConfig(), name);
			regularProducerManager.invokeRegularObject(name, config);
		} catch (Exception e) {
			throw new TaskExecuteException("invoke task fail, triggerName:"+triggerName, e);
		}
	}

	@Override
	public List<ConfigableInfo> listRegularObjectInfos() {
		if(infoList==null) {
			synchronized(this) {
				if(infoList==null) {
					Collection<ConfigableInfo> units = regularProducerManager.getRegularObjectInfos().values();
					infoList = new ArrayList<ConfigableInfo>(units);
				}
			}
		}
		return infoList;
	}

	@Override
	public PageResult<RegularJob> findRegularJob(String name, Integer triggerType, int page, int pageSize) {
		RegularJobExample example = new RegularJobExample();
		Criteria c = example.createCriteria();
		if(name!=null && !"".equals(name)) {
			c.andNameEqualTo(name);
		}
		if(triggerType!=null) {
			c.andTriggerTypeEqualTo(triggerType);
		}
		Limit limit = Limit.newInstanceForPage(page, pageSize);
		example.setLimit(limit);
		List<RegularJob> list = crudRegularJobDao.selectByExampleWithoutBLOBs(example);
		int count = crudRegularJobDao.countByExample(example);
		return new PageResult<RegularJob>(list, limit, count);
	}

	@Override
	public Long scheduleCronTrigger(String name, Object config, String desc, String cron) {
		Map<String, Object> triggerInfo = new HashMap<String, Object>();
		triggerInfo.put("cron", cron);
		
		Long id = registerRegularJob(name, desc, config, RegularJob.TRIGGER_TYPE_CRON, triggerInfo);
		TriggerScheduleInfo scheInfo = new TriggerScheduleInfo(cron);
		ltsSchedule(name, id, scheInfo);
		return id;
	}

	@Override
	public Long scheduleSimpleTrigger(String name, Object config, String desc, Date start, int repeatTimes,
			int repeatInternal) {
		Map<String, Object> triggerInfo = new HashMap<String, Object>();
		triggerInfo.put("start", start);
		triggerInfo.put("repeatTimes", repeatTimes);
		triggerInfo.put("repeatInternal", repeatInternal);
		
		Long id = registerRegularJob(name, desc, config, RegularJob.TRIGGER_TYPE_SIMPLE, triggerInfo);
		TriggerScheduleInfo scheInfo = new TriggerScheduleInfo(start, null, repeatTimes, repeatInternal);
		ltsSchedule(name, id, scheInfo);
		return id;
	}
	
	private Long registerRegularJob(String name, String desc, Object config, Integer triggerType, Object triggerInfo) {
		RegularJob regularJob = new RegularJob();
		regularJob.setName(name);
		regularJob.setDescription(desc);
		regularJob.setConfig(toJsonString(config));//TODO test config?
		regularJob.setTriggerType(triggerType);
		regularJob.setTriggerInfo(toJsonString(triggerInfo));
		return regularJobDao.insert(regularJob);
	}

	private String toJsonString(Object obj) {
		try {
			return objectMapper.writeValueAsString(obj);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}

	@Override
	public void unSchedule(Long id) {
		RegularJob regularJob = crudRegularJobDao.selectByPrimaryKey(id);
		if(regularJob==null) {
			throw new IllegalArgumentException("regularJob doesnot exist:" + id);
		}
		crudRegularJobDao.deleteByPrimaryKey(id);
		String name = regularJob.getName();
		unSchedule(name, id);
	}

	@Override
	@SuppressWarnings("unchecked")
	public JobDto getConfig(Long id) {
		RegularJob regularJob = getRegularJob(id);
		JobDto rlt = new JobDto();
		rlt.setDescription(regularJob.getDescription());
		rlt.setTriggerType(regularJob.getTriggerType());
		try {
			Object config = unSerialize(regularJob.getConfig(), regularJob.getName());
			rlt.setConfig(config);
			Map<String, Object> triggerInfo = objectMapper.readValue(regularJob.getTriggerInfo(), Map.class);
			rlt.setTriggerInfo(triggerInfo);
		} catch (Exception e) {
			logger.error(e, e);
			throw new RuntimeException(e);
		}
		return rlt;
	}

	private RegularJob getRegularJob(Long id) {
		RegularJob regularJob = crudRegularJobDao.selectByPrimaryKey(id);
		if(regularJob==null) {
			throw new IllegalArgumentException("regularJob doesnot exist:" + id);
		}
		return regularJob;
	}

	private Object unSerialize(String configStr, String name) throws Exception {
		Class<?> configClazz = regularProducerManager.getConfigMetaInfo(name);
		Object config = null;
		if(configClazz!=null) {
			config = objectMapper.readValue(configStr, configClazz);
		}
		return config;
	}

	private RegularJob getRegularJob(String triggerName) {
		String[] splitedStr = triggerName.split("_");
		String name = splitedStr[0];
		Long id = Long.parseLong(splitedStr[1]);
		RegularJob regularJob = getRegularJob(id);
		if(!name.equals(regularJob.getName())) {
			throw new IllegalArgumentException("regularJob's names do not match, name:" + name + ", triggerName:" + triggerName);
		}
		return regularJob;
	}
	
	private String getTriggerName(String name, Long id) {
		return name+"_"+id;
	}
	
	private void unSchedule(String name, Long id) {
		try {
			SchedulerFactory.getInstance().getScheduler().unscheduleTask(getTriggerName(name, id), name);
		} catch (SchedulerException e) {
			throw new IllegalArgumentException(e);
		}
	}

	private void ltsSchedule(String name, Long id, TriggerScheduleInfo scheInfo) {
		String triggerName = getTriggerName(name, id);
		TriggerMetaInfo metaInfo = new TriggerMetaInfo(triggerName, name, beanName ,null, TriggerType.INDEPENDENT_TRIGGER);
		Trigger trigger = TriggerFactory.getInstance().generateTrigger(metaInfo, scheInfo);
		try {
			SchedulerFactory.getInstance().getScheduler().scheduleTask(trigger);
		} catch (SchedulerException e) {
			throw new IllegalArgumentException(e);
		}
	}

	@Override
	public void setBeanName(String name) {
		beanName = name;
	}

	@Override
	public <T extends Configable<?>> ConfigableUnit<T> getConfigableUnit(Long id) {
		RegularJob regularJob = getRegularJob(id);
		return regularProducerManager.getConfigableUnit(regularJob.getName());
	}
	
}
