package com.nali.spreader.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nali.spreader.config.Range;
import com.nali.spreader.config.UserDto;
import com.nali.spreader.factory.config.Configable;
import com.nali.spreader.factory.config.ConfigableUnit;
import com.nali.spreader.factory.config.IConfigCenter;
import com.nali.spreader.factory.config.desc.ConfigDefinition;
import com.nali.spreader.factory.config.desc.ConfigableInfo;
import com.nali.spreader.factory.config.desc.DescriptionResolve;

@Controller
public class StrategyManageController {
	private static ObjectMapper jacksonMapper = new ObjectMapper();
	@Autowired
	private IConfigCenter cfgService;

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
	 * @param disname 用于显示的名称
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/strategy/createtree")
	public String createStgTreeData(String name)
			throws JsonGenerationException, JsonMappingException, IOException {
		return jacksonMapper.writeValueAsString(new DefAndData(cfgService.getConfigableUnit(name), cfgService.getConfig(name)));
	}

	public static class DefAndData {
		private String id;
		private String name;
		private ConfigDefinition def;
		private Object data;

		public DefAndData(String id, String name, ConfigDefinition def, Object data) {
			this.id = id;
			this.name = name;
			this.def = def;
			this.data = data;
		}

		public DefAndData(ConfigableUnit<Configable<?>> configableUnit,
				Object config) {
			this(configableUnit.getConfigableInfo().getName(), 
				configableUnit.getConfigableInfo().getDisplayName(),
				configableUnit.getConfigDefinition(),
				config);
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
