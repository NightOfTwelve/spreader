package com.nali.spreader.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nali.spreader.aop.annotation.AuthAnnotation;
import com.nali.spreader.controller.basectrl.BaseController;
import com.nali.spreader.factory.config.ConfigableType;
import com.nali.spreader.factory.config.IConfigService;
import com.nali.spreader.factory.config.desc.ConfigDefinition;
import com.nali.spreader.factory.config.desc.ConfigableInfo;

@Controller
@RequestMapping(value = "/strategysys")
public class StrategySystemManageController extends BaseController {
	private static final Logger LOGGER = Logger
			.getLogger(StrategySystemManageController.class);
	@Autowired
	private IConfigService<String> passiveConfigService;

	/**
	 * 初始化进入策略详细配置
	 * 
	 * @return
	 */
	public String init() {
		return "/show/main/StrategySystemShow";
	}

	/**
	 * 构造GRID的STROE
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/stggridstore")
	public String stgGridStore() {
		List<ConfigableInfo> list = passiveConfigService
				.listConfigableInfo(ConfigableType.system);
		Map<String, List<ConfigableInfo>> jsonMap = new HashMap<String, List<ConfigableInfo>>();
		jsonMap.put("data", list);
		return this.write(jsonMap);
	}

	/**
	 * 构建树结构的数据源
	 * 
	 * @param name
	 * @param disname
	 *            用于显示的名称
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/createtree")
	public String createStgTreeData(String name) {
		return this.write(new DefAndData(passiveConfigService
				.getConfigableInfo(name), passiveConfigService
				.getConfigDefinition(name), passiveConfigService
				.getConfigData(name)));
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
	@AuthAnnotation(opName = "系统策略管理>创建一个系统策略")
	@ResponseBody
	@RequestMapping(value = "/cfgsave")
	public String saveStrategyConfig(String name, String config)
			throws JsonGenerationException, JsonMappingException, IOException {
		Map<String, Boolean> message = new HashMap<String, Boolean>();
		message.put("success", false);
		if (!StringUtils.isEmpty(name) && config != null) {
			Class<?> configClass = passiveConfigService.getConfigableInfo(name)
					.getDataClass();
			Object configObject = this.getObjectMapper().readValue(config,
					configClass);
			try {
				passiveConfigService.saveConfigData(name, configObject);
				message.put("success", true);
			} catch (Exception e) {
				LOGGER.error("保存策略配置失败", e);
			}
		} else {
			LOGGER.info("前台对象获取错误,name为空或config为空");
		}
		return this.write(message);
	}

	public static class DefAndData {
		private String id;
		private String name;
		private ConfigDefinition def;
		private Object data;

		public DefAndData(ConfigableInfo configableInfo, ConfigDefinition def,
				Object data) {
			this(configableInfo.getName(), configableInfo.getDisplayName(),
					def, data);
		}

		public DefAndData(String id, String name, ConfigDefinition def,
				Object data) {
			this.id = id;
			this.name = name;
			this.def = def;
			this.data = data;
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
