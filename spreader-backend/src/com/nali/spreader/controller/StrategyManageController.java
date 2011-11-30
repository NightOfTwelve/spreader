package com.nali.spreader.controller;

import java.io.IOException;
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

import com.nali.spreader.factory.config.Configable;
import com.nali.spreader.factory.config.ConfigableUnit;
import com.nali.spreader.factory.config.ISingletonConfigCenter;
import com.nali.spreader.factory.config.desc.ConfigDefinition;
import com.nali.spreader.factory.config.desc.ConfigableInfo;

@Controller
public class StrategyManageController {
	private static final Logger LOGGER = Logger
			.getLogger(StrategyManageController.class);
	private static ObjectMapper jacksonMapper = new ObjectMapper();
	@Autowired
	private ISingletonConfigCenter cfgService;

	/**
	 * 初始化进入策略详细配置
	 * 
	 * @return
	 */
	@RequestMapping(value = "/strategy/showinit")
	public String indexPageInit() {
		return "/show/main/strategyshow";
	}

	/**
	 * 策略列表的显示页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/strategy/showlist")
	public String showStgList() {
		return "/show/main/strategylistshow";
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
	@RequestMapping(value = "/strategy/stggridstore")
	public String stgGridStore() throws JsonGenerationException,
			JsonMappingException, IOException {
		List<ConfigableInfo> list = cfgService.listAllConfigableInfo();
		Map<String, List<ConfigableInfo>> jsonMap = new HashMap<String, List<ConfigableInfo>>();
		jsonMap.put("data", list);
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
	@RequestMapping(value = "/strategy/createtree")
	public String createStgTreeData(String name)
			throws JsonGenerationException, JsonMappingException, IOException {
		return jacksonMapper.writeValueAsString(new DefAndData(cfgService
				.getConfigableUnit(name), cfgService.getConfig(name)));
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
	@RequestMapping(value = "/strategy/cfgsave")
	public String saveStrategyConfig(String name, String config)
			throws JsonGenerationException, JsonMappingException, IOException {
		Map<String, Boolean> message = new HashMap<String, Boolean>();
		message.put("success", false);
		if (!StringUtils.isEmpty(name) && config != null) {
			Class<?> configClass = cfgService.getConfigableUnit(name).getConfigClass();
			Object configObject = jacksonMapper.readValue(config, configClass);
			try {
				cfgService.saveConfig(name, configObject);
				message.put("success", true);
			} catch (Exception e) {
				LOGGER.error("保存策略配置失败", e);
			}
		} else {
			LOGGER.info("前台对象获取错误,name为空或config为空");
		}
		return jacksonMapper.writeValueAsString(message);
	}

	public static class DefAndData {
		private String id;
		private String name;
		private ConfigDefinition def;
		private Object data;

		public DefAndData(String id, String name, ConfigDefinition def,
				Object data) {
			this.id = id;
			this.name = name;
			this.def = def;
			this.data = data;
		}

		public DefAndData(ConfigableUnit<Configable<?>> configableUnit,
				Object config) {
			this(configableUnit.getConfigableInfo().getName(), configableUnit
					.getConfigableInfo().getDisplayName(), configableUnit
					.getConfigDefinition(), config);
		}

		public String getId() {
			return id;
		}

		public String getName() {
			return name;
		}

		public ConfigDefinition getDef() {
			return def;
		}

		public Object getData() {
			return data;
		}
	}

}
