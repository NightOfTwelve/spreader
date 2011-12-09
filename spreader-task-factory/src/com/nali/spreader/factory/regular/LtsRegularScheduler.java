package com.nali.spreader.factory.regular;

import java.io.IOException;
import java.util.Date;
import java.util.List;

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
import com.nali.spreader.factory.config.ConfigableType;
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
	
	@Override
	public void execute(TaskExecuteContext taskContext) throws TaskExecuteException {
		String triggerName = taskContext.getAssociatedTrigger().getTriggerMetaInfo().getTriggerName();
		RegularJob regularJob = getRegularJob(triggerName);
		String name = regularJob.getName();
		try {
			Object config = regularProducerManager.unSerializeConfigData(regularJob.getConfig(), name);
			regularProducerManager.invokeRegularObject(name, config);
		} catch (Exception e) {
			throw new TaskExecuteException("invoke task fail, triggerName:"+triggerName, e);
		}
	}

	@Override
	public PageResult<RegularJob> findRegularJob(String name, Integer triggerType, ConfigableType configableType, int page, int pageSize) {
		RegularJobExample example = new RegularJobExample();
		Criteria c = example.createCriteria().andJobTypeEqualTo(configableType.jobType);
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
		JobDto triggerInfo = new JobDto();
		triggerInfo.setCron(cron);
		
		Long id = registerRegularJob(name, desc, config, RegularJob.TRIGGER_TYPE_CRON, triggerInfo);
		TriggerScheduleInfo scheInfo = new TriggerScheduleInfo(cron);
		ltsSchedule(name, id, scheInfo);
		return id;
	}

	@Override
	public Long scheduleSimpleTrigger(String name, Object config, String desc, Date start, int repeatTimes,
			int repeatInternal) {
		JobDto triggerInfo = new JobDto();
		triggerInfo.setStart(start);
		triggerInfo.setRepeatInternal(repeatInternal);
		triggerInfo.setRepeatTimes(repeatTimes);
		
		Long id = registerRegularJob(name, desc, config, RegularJob.TRIGGER_TYPE_SIMPLE, triggerInfo);
		TriggerScheduleInfo scheInfo = new TriggerScheduleInfo(start, null, repeatTimes, repeatInternal);
		ltsSchedule(name, id, scheInfo);
		return id;
	}
	
	private Long registerRegularJob(String name, String desc, Object config, Integer triggerType, JobDto triggerInfo) {
		RegularJob regularJob = new RegularJob();
		regularJob.setName(name);
		regularJob.setDescription(desc);
		regularJob.setConfig(regularProducerManager.serializeConfigData(config));
		regularJob.setTriggerType(triggerType);
		regularJob.setTriggerInfo(jobDto2String(triggerInfo));
		return regularJobDao.insert(regularJob);
	}

	private String jobDto2String(JobDto obj) {
		try {
			return objectMapper.writeValueAsString(obj);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}

	private JobDto string2JobDto(String triggerInfo) throws IOException {
		return objectMapper.readValue(triggerInfo, JobDto.class);
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
	public JobDto getConfig(Long id) {
		RegularJob regularJob = getRegularJob(id);
		JobDto rlt;
		try {
			rlt = string2JobDto(regularJob.getTriggerInfo());
			Object config = regularProducerManager.unSerializeConfigData(regularJob.getConfig(), regularJob.getName());
			rlt.setConfig(config);
		} catch (Exception e) {
			logger.error(e, e);
			throw new RuntimeException(e);
		}
		rlt.setDescription(regularJob.getDescription());
		rlt.setTriggerType(regularJob.getTriggerType());
		return rlt;
	}

	private RegularJob getRegularJob(Long id) {
		RegularJob regularJob = crudRegularJobDao.selectByPrimaryKey(id);
		if(regularJob==null) {
			throw new IllegalArgumentException("regularJob doesnot exist:" + id);
		}
		return regularJob;
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
		TriggerMetaInfo metaInfo = new TriggerMetaInfo(triggerName, name, beanName+"Task" ,null, TriggerType.INDEPENDENT_TRIGGER);
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
	
}