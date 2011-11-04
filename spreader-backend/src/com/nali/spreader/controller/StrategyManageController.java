package com.nali.spreader.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nali.spreader.config.Range;
import com.nali.spreader.config.UserDto;
import com.nali.spreader.factory.config.desc.ConfigDefinition;
import com.nali.spreader.factory.config.desc.DescriptionResolve;

@Controller
public class StrategyManageController {
	private static ObjectMapper jacksonMapper = new ObjectMapper();

	@RequestMapping(value = "/strategy/showinit")
	public String indexPageInit() {
		return "/show/main/strategyshow";
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ResponseBody
	@RequestMapping(value = "/strategy/createtree")
	public String createStgTreeData() throws JsonGenerationException, JsonMappingException, IOException {
		ConfigDefinition def = DescriptionResolve.get(UserDto.class);
		UserDto data = new UserDto();
		data.setGender(2);
		data.setWebsiteId(1);
		data.setLimit(100);
		data.setProvince("上海");
		Range<Long> fans = new Range<Long>();
		fans.setGte(1L);
		fans.setLte(100L);
		data.setFans(fans);
		data.setCategories(Arrays.asList("互联网", "愤青", "民工"));
		
//		List<Map> list = new ArrayList<Map>();
//		Map m = new HashMap();
//		m.put("id", "a1");
//		m.put("text", "Extjs设计");
//		m.put("leaf", true);
//		list.add(m);
//		m = new HashMap();
//		m.put("id", "a2");
//		m.put("text", "JAVA设计");
//		m.put("leaf", true);
//		list.add(m);
//		return jacksonMapper.writeValueAsString(list);
		return jacksonMapper.writeValueAsString(new DefAndData("user", "用户", def, data));
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
