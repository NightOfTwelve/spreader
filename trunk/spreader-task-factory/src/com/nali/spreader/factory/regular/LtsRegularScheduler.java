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
import com.nali.common.util.DateFormatUtil;
import com.nali.lang.StringUtils;
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
import com.nali.spreader.model.RegularJob.TriggerDto;
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
		String triggerName = taskContext.getAssociatedTrigger().getTriggerMetaInfo()
				.getTriggerName();
		RegularJob regularJob = getRegularJob(triggerName);
		String name = regularJob.getName();
		try {
			Object config = regularProducerManager.unSerializeConfigData(regularJob.getConfig(),
					name);
			regularProducerManager.invokeRegularObject(name, config, regularJob.getId());
		} catch (Exception e) {
			throw new TaskExecuteException("invoke task fail, triggerName:" + triggerName, e);
		}
	}

	@Override
	public PageResult<RegularJob> findRegularJob(String name, Integer triggerType, Long groupId,
			ConfigableType configableType, Limit lit, Long refId) {
		RegularJobExample example = new RegularJobExample();
		Criteria c = example.createCriteria().andJobTypeEqualTo(configableType.jobType);
		if (name != null && !"".equals(name)) {
			c.andNameEqualTo(name);
		}
		if (triggerType != null) {
			c.andTriggerTypeEqualTo(triggerType);
		}
		if (groupId != null) {
			c.andGidEqualTo(groupId);
		}
		if (refId != null) {
			c.andRefIdEqualTo(refId);
		}
		example.setLimit(lit);
		List<RegularJob> list = crudRegularJobDao.selectByExampleWithoutBLOBs(example);
		int count = crudRegularJobDao.countByExample(example);
		return new PageResult<RegularJob>(list, lit, count);
	}

	@Override
	public Long scheduleRegularJob(RegularJob regularJob) {
		String name = regularJob.getName();
		TriggerDto triggerObject = regularJob.getTriggerObject();
		Integer triggerType = triggerObject.getTriggerType();
		TriggerScheduleInfo scheInfo;
		if (RegularJob.TRIGGER_TYPE_SIMPLE.equals(triggerType)) {
			scheInfo = new TriggerScheduleInfo(triggerObject.getStart(), null,
					triggerObject.getRepeatTimes(), triggerObject.getRepeatInternal());
		} else if (RegularJob.TRIGGER_TYPE_CRON.equals(triggerType)) {
			scheInfo = new TriggerScheduleInfo(triggerObject.getCron());
		} else {
			throw new IllegalArgumentException("illegal triggerType:" + triggerType);
		}
		regularJob.setTriggerInfo(triggerDto2String(triggerObject));
		regularJob.setConfig(regularProducerManager.serializeConfigData(regularJob
				.getConfigObject()));

		Long id = regularJob.getId();
		if (id == null) {
			id = regularJobDao.insert(regularJob);
		} else {
			crudRegularJobDao.updateByPrimaryKeySelective(regularJob);
			unSchedule(name, id);
		}
		regularProducerManager.saveExtendConfig(name, id, regularJob.getExtendConfigObject());
		ltsSchedule(name, id, scheInfo);
		return id;
	}

	private String triggerDto2String(TriggerDto obj) {
		try {
			return objectMapper.writeValueAsString(obj);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}

	private TriggerDto string2TriggerDto(String triggerInfo) throws IOException {
		return objectMapper.readValue(triggerInfo, TriggerDto.class);
	}

	@Override
	public void unSchedule(Long id) {
		RegularJob regularJob = crudRegularJobDao.selectByPrimaryKey(id);
		if (regularJob == null) {
			throw new IllegalArgumentException("regularJob doesnot exist:" + id);
		}
		crudRegularJobDao.deleteByPrimaryKey(id);
		String name = regularJob.getName();
		unSchedule(name, id);
	}

	@Override
	public RegularJob getRegularJobObject(Long id) {
		try {
			RegularJob regularJob = getRegularJob(id);
			Object config = regularProducerManager.unSerializeConfigData(regularJob.getConfig(),
					regularJob.getName());
			Object extendConfig = getExtendConfig(regularJob.getName(), id);
			TriggerDto triggerDto = string2TriggerDto(regularJob.getTriggerInfo());
			String remind = getRemind(getTriggerName(regularJob.getName(), id),
					regularJob.getName());
			triggerDto.setRemind(remind);

			regularJob.setConfigObject(config);
			regularJob.setExtendConfigObject(extendConfig);
			regularJob.setTriggerObject(triggerDto);
			return regularJob;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// String triggerName = getTriggerName(name, id);
	private String getRemind(String triggerName, String groupName) throws SchedulerException {
		String remind = "";
		// TriggerMetaInfo tm = new TriggerMetaInfo(triggerName, name, name
		// + "Task", null, TriggerType.INDEPENDENT_TRIGGER);
		Trigger trigger = SchedulerFactory.getInstance().getScheduler()
				.getTrigger(triggerName, groupName);
		if (trigger != null) {
			TriggerScheduleInfo scheduleInfo = trigger.getTriggerScheduleInfo();
			StringBuffer buff = new StringBuffer();
			String cronExpression = scheduleInfo.getCronExpression();
			if (StringUtils.isEmptyNoOffset(cronExpression)) {
				// simple trigger
				int rcount = scheduleInfo.getRepeatCount();
				// 已完成的次数
				int times = trigger.getTimesTriggered();
				int left = times == -1 ? 0 : rcount + 1 - times;

				buff.append("剩余执行次数:");
				buff.append(left);
			} else {
				buff.append("Cron表达式:").append(cronExpression);
			}

			buff.append(";");
			Date nextDate = trigger.getNextFireTime();
			if (nextDate != null) {
				String nextTime = DateFormatUtil.formatDateTime(nextDate);
				buff.append("下次运行时间为:");
				buff.append(nextTime);
				buff.append(";");
			}
			Date endDate = scheduleInfo.getEndTime();
			if (endDate != null) {
				String endTime = DateFormatUtil.formatDateTime(endDate);
				buff.append("任务结束时间为:");
				buff.append(endTime);
				buff.append(";");
			}
			remind = buff.toString();
		} else {
			remind = "任务执行完毕, 您可以重新编辑保存，产生新的任务";
		}
		return remind;
	}

	private RegularJob getRegularJob(Long id) {
		RegularJob regularJob = crudRegularJobDao.selectByPrimaryKey(id);
		if (regularJob == null) {
			throw new IllegalArgumentException("regularJob doesnot exist:" + id);
		}
		return regularJob;
	}

	private RegularJob getRegularJob(String triggerName) {
		String[] splitedStr = triggerName.split("_");
		String name = splitedStr[0];
		Long id = Long.parseLong(splitedStr[1]);
		RegularJob regularJob = getRegularJob(id);
		if (!name.equals(regularJob.getName())) {
			throw new IllegalArgumentException("regularJob's names do not match, name:" + name
					+ ", triggerName:" + triggerName);
		}
		return regularJob;
	}

	private String getTriggerName(String name, Long id) {
		return name + "_" + id;
	}

	private void unSchedule(String name, Long id) {
		try {
			SchedulerFactory.getInstance().getScheduler()
					.unscheduleTask(getTriggerName(name, id), name);
		} catch (SchedulerException e) {
			throw new IllegalArgumentException(e);
		}
	}

	private void ltsSchedule(String name, Long id, TriggerScheduleInfo scheInfo) {
		String triggerName = getTriggerName(name, id);
		TriggerMetaInfo metaInfo = new TriggerMetaInfo(triggerName, name, beanName + "Task", null,
				TriggerType.INDEPENDENT_TRIGGER);
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
	public Long findRegularJobIdBySimpleGroupId(Long gid) {
		if (gid != null) {
			RegularJobExample example = new RegularJobExample();
			Criteria c = example.createCriteria();
			c.andGidEqualTo(gid);
			List<RegularJob> list = crudRegularJobDao.selectByExampleWithoutBLOBs(example);
			if (list.size() > 0) {
				return list.get(0).getId();
			} else {
				logger.warn("未能获取分组ID为:" + gid + "的调度，可能未同步数据");
				return null;
			}
		} else {
			logger.warn("简单分组ID为空，不能获取调度对象");
			return null;
		}
	}

	@Override
	public Object getExtendConfig(String name, Long sid) {
		return regularProducerManager.getExtendConfig(name, sid);
	}
}
