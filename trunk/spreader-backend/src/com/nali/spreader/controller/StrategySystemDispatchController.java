package com.nali.spreader.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nali.common.pagination.PageResult;
import com.nali.lang.StringUtils;
import com.nali.lts.exceptions.SchedulerException;
import com.nali.spreader.factory.config.ConfigableType;
import com.nali.spreader.factory.config.IConfigService;
import com.nali.spreader.factory.config.desc.ConfigDefinition;
import com.nali.spreader.factory.config.desc.ConfigableInfo;
import com.nali.spreader.factory.regular.RegularScheduler;
import com.nali.spreader.model.RegularJob;
import com.nali.spreader.model.RegularJob.TriggerDto;

@Controller
@RequestMapping(value = "/dispsys")
public class StrategySystemDispatchController {
	private static final Logger LOGGER = Logger
			.getLogger(StrategySystemDispatchController.class);
	private static ObjectMapper jacksonMapper = new ObjectMapper();
	@Autowired
	private RegularScheduler cfgService;
	@Autowired
	private IConfigService<Long> regularConfigService;

	/**
	 * 策略调度列表的显示页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/dispatchlist")
	public String init() {
		return "/show/main/StrategySystemDispatchShow";
	}

	/**
	 * 构造GRID的STROE
	 * 
	 * @return
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	@ResponseBody
	@RequestMapping(value = "/stgdispgridstore")
	public String stgGridStore(String dispname, Integer triggerType, int start,
			int limit) throws JsonGenerationException, JsonMappingException,
			IOException {
		if (limit <= 0) {
			limit = 20;
		}
		start = start / limit + 1;
		PageResult<RegularJob> pr = cfgService.findRegularJob(dispname,
				triggerType, null, ConfigableType.system, start, limit);
		List<RegularJob> list = pr.getList();
		List<ConfigableInfo> dispnamelist = regularConfigService
				.listConfigableInfo(ConfigableType.system);
		int rowcount = pr.getTotalCount();
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("cnt", rowcount);
		jsonMap.put("data", list);
		jsonMap.put("dispname", dispnamelist);
		return jacksonMapper.writeValueAsString(jsonMap);
	}

	/**
	 * 构建树结构的数据源
	 * 
	 * @param name
	 * @param disname
	 *            用于显示的名称
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/createdisptree")
	public String createStgTreeData(String name, Long id)
			throws JsonGenerationException, JsonMappingException, IOException {
		return jacksonMapper
				.writeValueAsString(new DispatchData(null, regularConfigService
						.getConfigableInfo(name).getDisplayName(),
						regularConfigService.getConfigDefinition(name),
						id != null && id > 0 ? cfgService.getRegularJobObject(id)
								.getConfig() : null));
	}

	/**
	 * 获取调度参数
	 * 
	 * @param id
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 * @throws SchedulerException
	 */
	@ResponseBody
	@RequestMapping(value = "/settgrparam")
	public String settingTriggerParam(Long id) throws JsonGenerationException,
			JsonMappingException, IOException, SchedulerException {
		RegularJob job = cfgService.getRegularJobObject(id);
		TriggerDto triggerObject = job.getTriggerObject();
		triggerObject.setDescription(job.getDescription());
		triggerObject.setTriggerType(job.getTriggerType());
		return jacksonMapper.writeValueAsString(triggerObject);
	}

	/**
	 * 保存前台编辑的配置对象
	 * 
	 * @param name
	 * @param config
	 * @return
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	@ResponseBody
	@RequestMapping(value = "/dispsave")
	public String saveStrategyConfig(RegularJob regularJob, TriggerDto triggerDto)
			throws JsonGenerationException, JsonMappingException, IOException {
		Map<String, Boolean> message = new HashMap<String, Boolean>();
		message.put("success", false);
		Class<?> dataClass = regularConfigService.getConfigableInfo(regularJob.getName())
				.getDataClass();
		if(regularJob.getConfig()!=null) {
			Object configObj = jacksonMapper.readValue(regularJob.getConfig(), dataClass);
			regularJob.setConfigObject(configObj);
		}
		regularJob.setTriggerObject(triggerDto);
		regularJob.setJobType(ConfigableType.system.jobType);
		try {
			cfgService.scheduleRegularJob(regularJob);
			message.put("success", true);
		} catch (Exception e) {
			LOGGER.error("保存Trigger失败", e);
		}
		return jacksonMapper.writeValueAsString(message);
	}

	/**
	 * 批量删除调度信息
	 * 
	 * @param idstr
	 * @return
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	@ResponseBody
	@RequestMapping(value = "/deletetrg")
	public String deleteTrigger(String idstr) throws JsonGenerationException,
			JsonMappingException, IOException {
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
		return jacksonMapper.writeValueAsString(map);
	}

	/**
	 * 构造ComboBox的数据源
	 * 
	 * @return
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	@ResponseBody
	@RequestMapping(value = "/combstore")
	public String createStgCombStore() throws JsonGenerationException,
			JsonMappingException, IOException {
		List<ConfigableInfo> list = regularConfigService
				.listConfigableInfo(ConfigableType.system);
		return jacksonMapper.writeValueAsString(list);
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

		public DispatchData(String treeid, String treename,
				ConfigDefinition def, Object data) {
			this.treeid = treeid;
			this.treename = treename;
			this.def = def;
			this.data = data;
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
