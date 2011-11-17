package com.nali.spreader.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nali.common.pagination.PageResult;
import com.nali.spreader.factory.config.desc.ConfigDefinition;
import com.nali.spreader.factory.config.desc.ConfigableInfo;
import com.nali.spreader.factory.regular.RegularScheduler;
import com.nali.spreader.model.RegularJob;
import com.nali.spreader.model.RegularJob.JobDto;

@Controller
public class StrategyDispatchController {
	public static final Logger LOGGER = Logger
			.getLogger(StrategyDispatchController.class);
	private static ObjectMapper jacksonMapper = new ObjectMapper();
	@Autowired
	private RegularScheduler cfgService;

	/**
	 * 策略调度列表的显示页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/strategy/dispatchlist")
	public String showStgList() {
		return "/show/main/strategyDispatchListShow";
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
	@RequestMapping(value = "/strategy/stgdispgridstore")
	public String stgGridStore(String dispname, Integer triggerType, int start,
			int limit) throws JsonGenerationException, JsonMappingException,
			IOException {
		if (limit <= 0) {
			limit = 20;
		}
		start = start / limit + 1;
		PageResult<RegularJob> pr = cfgService.findRegularJob(dispname,
				triggerType, start, limit);
		List<RegularJob> list = pr.getList();
		List<ConfigableInfo> dispnamelist = cfgService.listRegularObjectInfos();
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
	@RequestMapping(value = "/strategy/createdisptree")
	public String createStgTreeData(String name)
			throws JsonGenerationException, JsonMappingException, IOException {
		return jacksonMapper.writeValueAsString(new DispatchData(null,
				cfgService.getConfigableInfo(name).getDisplayName(), cfgService
						.getConfigDefinition(name), null));
	}

	/**
	 * 获取调度参数
	 * 
	 * @param id
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/strategy/settgrparam")
	public String settingTriggerParam(Long id) throws JsonGenerationException,
			JsonMappingException, IOException {
		return jacksonMapper.writeValueAsString(new DispatchData(cfgService
				.getConfig(id)));
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
	@RequestMapping(value = "/strategy/dispsave")
	public String saveStrategyConfig(String name, String config,
			Integer triggerType, String description, Date start,
			Integer repeatTimes, Integer repeatInternal, String cron)
			throws JsonGenerationException, JsonMappingException, IOException {
		Map<String, Boolean> message = new HashMap<String, Boolean>();
		message.put("success", false);
		Class<?> dataClass = cfgService.getConfigableInfo(name).getDataClass();
		Object configObj = null;
		if (StringUtils.isNotEmpty(config)) {
			configObj = jacksonMapper.readValue(config, dataClass);
		}
		if (triggerType == RegularJob.TRIGGER_TYPE_SIMPLE) {
			try {
				cfgService.scheduleSimpleTrigger(name, configObj, description,
						start, repeatTimes, repeatInternal);
				message.put("success", true);
			} catch (Exception e) {
				LOGGER.error("保存SimpleTrigger失败", e);
			}
		} else if (triggerType == RegularJob.TRIGGER_TYPE_CRON) {
			try {
				cfgService.scheduleCronTrigger(name, configObj, description,
						cron);
				message.put("success", true);
			} catch (Exception e) {
				LOGGER.error("保存CronTrigger失败", e);
			}
		} else {
			LOGGER.info("调度类型获取错误保存失败");
		}
		return jacksonMapper.writeValueAsString(message);
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
	@RequestMapping(value = "/strategy/combstore")
	public String createStgCombStore() throws JsonGenerationException,
			JsonMappingException, IOException {
		List<ConfigableInfo> list = cfgService.listRegularObjectInfos();
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
		private JobDto jobdto;

		public DispatchData(String treeid, String treename,
				ConfigDefinition def, Object data) {
			this.treeid = treeid;
			this.treename = treename;
			this.def = def;
			this.data = data;
		}

		public DispatchData(JobDto jobdto) {
			this.jobdto = jobdto;
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

		public JobDto getJobdto() {
			return jobdto;
		}

		public void setJobdto(JobDto jobdto) {
			this.jobdto = jobdto;
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
