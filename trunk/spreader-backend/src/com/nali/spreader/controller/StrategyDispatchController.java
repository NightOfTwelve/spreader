package com.nali.spreader.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nali.common.model.Limit;
import com.nali.common.pagination.PageResult;
import com.nali.common.util.CollectionUtils;
import com.nali.lang.StringUtils;
import com.nali.lts.exceptions.SchedulerException;
import com.nali.spreader.controller.basectrl.BaseController;
import com.nali.spreader.factory.config.ConfigableType;
import com.nali.spreader.factory.config.IConfigService;
import com.nali.spreader.factory.config.desc.ConfigDefinition;
import com.nali.spreader.factory.config.desc.ConfigableInfo;
import com.nali.spreader.factory.regular.RegularScheduler;
import com.nali.spreader.model.RegularJob;
import com.nali.spreader.model.RegularJob.TriggerDto;
import com.nali.spreader.model.StrategyGroup;
import com.nali.spreader.model.StrategyUserGroup;
import com.nali.spreader.service.IStrategyGroupService;

@Controller
@RequestMapping(value = "/strategydisp")
public class StrategyDispatchController extends BaseController {
	private static final Logger LOGGER = Logger.getLogger(StrategyDispatchController.class);
	@Autowired
	private RegularScheduler cfgService;
	@Autowired
	private IConfigService<Long> regularConfigService;
	@Autowired
	private IStrategyGroupService groupService;
	// 简单分组
	private static final Integer SIMPLE_GROUP_TYPE = 1;
	// 消息分组用于区分普通和复杂分组，实际上不保存分组操作
	private static final Integer NOTICE_GROUP_TYPE = 3;
	// 普通策略
	private static final String NORMAL_JOBTYPE = "normal";
	// 系统策略
	private static final String SYSTEM_JOBTYPE = "system";
	// 消息策略
	private static final String NOTICE_JOBTYPE = "notice";

	// 复杂分组
	// private final Integer COMPLEX_GROUP_TYPE = 2;

	/**
	 * 策略调度列表的显示页
	 * 
	 * @return
	 */
	public String init() {
		return "/show/main/strategyDispatchListShow";
	}

	/**
	 * 构造GRID的STROE
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/stgdispgridstore")
	public String stgGridStore(String dispname, Integer triggerType, Long groupId, Integer start,
			Integer limit, String jobType) {
		Assert.notNull(jobType, "jobType is null");
		Limit lit = this.initLimit(start, limit);
		PageResult<RegularJob> pr = cfgService.findRegularJob(dispname, triggerType, groupId,
				this.getConfigableTypeByJobType(jobType), lit, null);
		List<RegularJob> list = pr.getList();
		List<ConfigableInfo> dispnamelist = regularConfigService.listConfigableInfo(this
				.getConfigableTypeByJobType(jobType));
		int rowcount = pr.getTotalCount();
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("cnt", rowcount);
		jsonMap.put("data", list);
		jsonMap.put("dispname", dispnamelist);
		return this.write(jsonMap);
	}

	/**
	 * 根据job类型获取相应的枚举类型
	 * 
	 * @param jobType
	 * @return
	 */
	private ConfigableType getConfigableTypeByJobType(String jobType) {
		if (StrategyDispatchController.SYSTEM_JOBTYPE.equalsIgnoreCase(jobType)) {
			return ConfigableType.system;
		}
		if (StrategyDispatchController.NOTICE_JOBTYPE.equalsIgnoreCase(jobType)) {
			return ConfigableType.noticeRelated;
		}
		if (StrategyDispatchController.NORMAL_JOBTYPE.equalsIgnoreCase(jobType)) {
			return ConfigableType.normal;
		} else {
			throw new IllegalArgumentException("not find this jobType:" + jobType);
		}
	}

	/**
	 * 构建树结构的数据源 支持设定默认值
	 * 
	 * @param name
	 * @param id
	 * @param isGroup
	 * @param data
	 * @param trigger
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/createdisptree")
	public String createStgTreeData(String name, Long id, Boolean isGroup, String defaultData) {
		if (Boolean.TRUE.equals(isGroup)) {
			id = getRegularIdByGid(id);
		}
		ConfigableInfo cfg = regularConfigService.getConfigableInfo(name);
		if (cfg == null) {
			return null;
		}
		String dispname = cfg.getDisplayName();
		String extendType = cfg.getExtendType();
		Object meta = cfg.getExtendMeta();
		ConfigDefinition def = regularConfigService.getConfigDefinition(name);
		Object data;
		if (id != null) {
			data = cfgService.getRegularJobObject(id).getConfigObject();
		} else {
			Class<?> dataClass = regularConfigService.getConfigableInfo(name).getDataClass();
			if (!StringUtils.isEmpty(defaultData)) {
				try {
					data = this.getObjectMapper().readValue(defaultData, dataClass);
				} catch (Exception e) {
					return returnError("获取configObj异常" + name, e);
				}
			} else {
				data = null;
			}
		}

		Object sug = null;
		if (!StringUtils.isEmpty(extendType)) {
			sug = cfgService.getExtendConfig(name, id);
		}
		TriggerDto trigger = this.getTrigger(id);
		DispatchData dispatch = new DispatchData(null, dispname, extendType, meta, def, data, sug,
				trigger);
		return this.write(dispatch);
	}

	/**
	 * 获取调度参数
	 * 
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/settgrparam")
	public String settingTriggerParam(Long id, Boolean isGroup) throws SchedulerException {
		if (isGroup != null && isGroup) {
			id = getRegularIdByGid(id);
		}
		// 如果ID为空直接返回报错信息
		if (id == null) {
			Map<String, Boolean> err = CollectionUtils.newHashMap(1);
			err.put("error", true);
			LOGGER.error("不能获取任务ID，设置参数错误");
			return this.write(err);
		}
		RegularJob job = cfgService.getRegularJobObject(id);
		TriggerDto triggerObject = job.getTriggerObject();
		triggerObject.setDescription(job.getDescription());
		triggerObject.setTriggerType(job.getTriggerType());
		return this.write(triggerObject);
	}

	/**
	 * 获取任务Trigger
	 * 
	 * @param id
	 * @return
	 */
	private TriggerDto getTrigger(Long id) {
		TriggerDto triggerObject;
		// 默认trigger
		if (id == null) {
			triggerObject = new TriggerDto();
			triggerObject.setRepeatInternal(10000);
			triggerObject.setRepeatTimes(0);
			triggerObject.setStart(new Date());
			triggerObject.setTriggerType(RegularJob.TRIGGER_TYPE_SIMPLE);
		} else {
			RegularJob job = cfgService.getRegularJobObject(id);
			triggerObject = job.getTriggerObject();
			triggerObject.setDescription(job.getDescription());
			triggerObject.setTriggerType(job.getTriggerType());
		}
		return triggerObject;
	}

	/**
	 * 保存前台编辑的配置对象
	 * 
	 * @param name
	 * @param config
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/dispsave")
	public String saveStrategyConfig(String groupName, Long groupId, Integer groupType,
			Long fromGroupId, Long toGroupId, RegularJob regularJob, TriggerDto triggerDto,
			Long refId) {
		// 检查参数
		if (groupType == null) {
			return returnError("分组类型为空，不能保存策略配置", null);
		}

		// 处理分组相关字段
		if (SIMPLE_GROUP_TYPE.equals(groupType)) {
			groupName = regularJob.getName();
			if (groupId != null) {
				Long id = getRegularIdByGid(groupId);
				regularJob.setId(id);// 把页面上传过来的错误的id刷成正确的
				if (id != null) {
					// 如果分组ID不为null,首先检查并同步策略表
					groupService.syncRegularJob(groupId, groupName, id);
				} else {
					return returnError("通过组ID:" + groupId + ",获取regularJob失败", null);
				}
			} else {
				// 否则先保存分组获取分组ID
				groupId = getNewGroupId(groupType, groupName, regularJob.getDescription());
			}
		} else if (NOTICE_GROUP_TYPE.equals(groupType)) {
			// 如果是消息子策略，需设置refid
			regularJob.setRefId(refId);
		} else {
			// 复杂分组
			if (groupId == null) {
				return returnError("复杂分组传入的groupId为空或小于等于0，不能保存策略", null);
			}
		}
		regularJob.setGid(groupId);
		regularJob.setGname(groupName);

		ConfigableInfo cfgInfo = regularConfigService.getConfigableInfo(regularJob.getName());
		// check config
		Class<?> dataClass = cfgInfo.getDataClass();
		if (regularJob.getConfig() != null) {
			try {
				Object configObj = this.getObjectMapper().readValue(regularJob.getConfig(),
						dataClass);
				regularJob.setConfigObject(configObj);
			} catch (Exception e) {
				return returnError("获取configObj异常" + groupId, e);
			}
		}

		// 处理StrategyUserGroup
		if (fromGroupId != null || toGroupId != null) {
			StrategyUserGroup sug = new StrategyUserGroup();
			sug.setFromUserGroup(fromGroupId);
			sug.setToUserGroup(toGroupId);
			regularJob.setExtendConfigObject(sug);
		}

		// 设置triggerDto和JobType
		regularJob.setTriggerObject(triggerDto);
		regularJob.setJobType(cfgInfo.getConfigableType().jobType);

		// save
		try {
			cfgService.scheduleRegularJob(regularJob);
			Map<String, Object> message = CollectionUtils.newHashMap(1);
			message.put("success", true);
			return this.write(message);
		} catch (Exception e) {
			LOGGER.error("策略保存失败", e);
			return returnError("保存失败,请检查数据重新填写", e);
		}
	}

	private String returnError(String msg, Throwable e) {
		if (e == null) {
			LOGGER.warn(msg);
		} else {
			LOGGER.warn(msg, e);
		}
		Map<String, Object> message = CollectionUtils.newHashMap(2);
		message.put("success", false);
		message.put("message", msg);
		return this.write(message);
	}

	/**
	 * 批量删除调度信息
	 * 
	 * @param idstr
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deletetrg")
	public String deleteTrigger(String idstr) {
		// 操作记录数
		int count = 0;
		// 返回消息
		Map<String, String> map = new HashMap<String, String>();
		if (StringUtils.isNotEmptyNoOffset(idstr)) {
			String[] idarray = idstr.split("[,]");
			if (idarray.length > 0) {
				for (int i = 0; i < idarray.length; i++) {
					try {
						cfgService.unSchedule(Long.parseLong(idarray[i]));
						count++;
					} catch (Exception e) {
						LOGGER.error("调度删除异常", e);
					}
				}
			}
		} else {
			map.put("message", "删除失败");
		}
		map.put("message", "成功删除" + count + "条记录");
		return this.write(map);
	}

	/**
	 * 构造ComboBox的数据源
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/combstore")
	public String createStgCombStore() {
		List<ConfigableInfo> list = regularConfigService.listConfigableInfo(ConfigableType.normal);
		return this.write(list);
	}

	/**
	 * 获取所有策略中文名
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/alldisplayname")
	public String renderStrategyDisplayName() {
		List<ConfigableInfo> data = new ArrayList<ConfigableInfo>();
		List<ConfigableInfo> normalList = regularConfigService
				.listConfigableInfo(ConfigableType.normal);
		data.addAll(normalList);
		List<ConfigableInfo> systemList = regularConfigService
				.listConfigableInfo(ConfigableType.system);
		data.addAll(systemList);
		List<ConfigableInfo> noticeList = regularConfigService
				.listConfigableInfo(ConfigableType.noticeRelated);
		data.addAll(noticeList);
		return this.write(data);
	}

	/**
	 * 获取新构建的分组ID
	 * 
	 * @param groupType
	 * @param groupName
	 * @param groupNote
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/newgroupid")
	public String createNewGroupId(Integer groupType, String groupName, String groupNote) {
		Long gid = this.getNewGroupId(groupType, groupName, groupNote);
		Map<String, Long> m = CollectionUtils.newHashMap(1);
		m.put("groupId", gid);
		return this.write(m);
	}

	/**
	 * 获取消息子策略列表
	 * 
	 * @param noticeId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/noticestrategy")
	public String queryNoticeStrategy(Long noticeId, Integer start, Integer limit) {
		Assert.notNull(noticeId, "noticeId is null");
		Limit lit = this.initLimit(start, limit);
		PageResult<RegularJob> data = this.cfgService.findRegularJob(null, null, null,
				ConfigableType.noticeRelated, lit, noticeId);
		return this.write(data);
	}

	/**
	 * 通过分组ID获取调度ID
	 * 
	 * @param gid
	 * @return
	 */
	private Long getRegularIdByGid(Long gid) {
		return cfgService.findRegularJobIdBySimpleGroupId(gid);
	}

	/**
	 * 获取新保存的分组ID
	 * 
	 * @param groupType
	 * @param groupName
	 * @param note
	 * @return
	 */
	private Long getNewGroupId(Integer groupType, String groupName, String groupNote) {
		// 否则先保存分组获取分组ID
		StrategyGroup sg = new StrategyGroup();
		sg.setGroupType(groupType);
		sg.setGroupName(groupName);
		sg.setDescription(groupNote);
		sg.setCreateTime(new Date());
		return groupService.saveGroupInfo(sg);
	}

	/**
	 * 
	 * @author xiefei
	 * 
	 */
	public static class DispatchData {
		// 树节点ID
		private String treeid;
		// TEXT
		private String treename;
		// 属性配置
		private ConfigDefinition def;
		// 节点数据
		private Object data;
		private String extendType;
		private Object extendMeta;
		private Object sug;
		private TriggerDto trigger;

		public DispatchData(String treeid, String treename, String extendType, Object extendMeta,
				ConfigDefinition def, Object data, Object sug, TriggerDto trigger) {
			this.treeid = treeid;
			this.treename = treename;
			this.def = def;
			this.data = data;
			this.extendType = extendType;
			this.extendMeta = extendMeta;
			this.sug = sug;
			this.trigger = trigger;
		}

		public TriggerDto getTrigger() {
			return trigger;
		}

		public void setTrigger(TriggerDto trigger) {
			this.trigger = trigger;
		}

		public String getExtendType() {
			return extendType;
		}

		public Object getSug() {
			return sug;
		}

		public void setSug(Object sug) {
			this.sug = sug;
		}

		public void setExtendType(String extendType) {
			this.extendType = extendType;
		}

		public Object getExtendMeta() {
			return extendMeta;
		}

		public void setExtendMeta(Object extendMeta) {
			this.extendMeta = extendMeta;
		}

		public String getTreeid() {
			return treeid;
		}

		public void setTreeid(String treeid) {
			this.treeid = treeid;
		}

		public String getTreename() {
			return treename;
		}

		public void setTreename(String treename) {
			this.treename = treename;
		}

		public ConfigDefinition getDef() {
			return def;
		}

		public void setDef(ConfigDefinition def) {
			this.def = def;
		}

		public Object getData() {
			return data;
		}

		public void setData(Object data) {
			this.data = data;
		}
	}

	public static class GridListStore {
		private String displayName;
		private RegularJob job;

		public GridListStore(RegularJob job, String displayName) {
			this.displayName = displayName;
			this.job = job;
		}

		public String getDisplayName() {
			return displayName;
		}

		public void setDisplayName(String displayName) {
			this.displayName = displayName;
		}

		public RegularJob getJob() {
			return job;
		}

		public void setJob(RegularJob job) {
			this.job = job;
		}
	}
}
