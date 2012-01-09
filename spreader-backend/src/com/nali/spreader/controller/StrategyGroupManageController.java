package com.nali.spreader.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nali.common.pagination.PageResult;
import com.nali.common.util.CollectionUtils;
import com.nali.spreader.factory.config.ConfigableType;
import com.nali.spreader.factory.config.IConfigService;
import com.nali.spreader.factory.config.desc.ConfigableInfo;
import com.nali.spreader.model.StrategyGroup;
import com.nali.spreader.service.IStrategyGroupService;

/**
 * 策略模块控制器，增加策略分组功能
 * 
 * @author xiefei
 * 
 */
@Controller
@RequestMapping(value = "/stggroup")
public class StrategyGroupManageController {
	private static ObjectMapper json = new ObjectMapper();
	@Autowired
	private IStrategyGroupService groupService;
	@Autowired
	private IConfigService<Long> regularConfigService;

	/**
	 * 初始化页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/init")
	public String init() {
		return "/show/main/StrategyGroupManageShow";
	}

	/**
	 * 分组列表的Store
	 * 
	 * @param groupName
	 * @param groupType
	 * @param start
	 * @param limit
	 * @return
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	@ResponseBody
	@RequestMapping(value = "/groupgrid")
	public String groupGridStore(String groupName, Integer groupType,
			Integer start, Integer limit) throws JsonGenerationException,
			JsonMappingException, IOException {
		StrategyGroup sg = new StrategyGroup();
		sg.setGroupName(groupName);
		sg.setGroupType(groupType);
		PageResult<StrategyGroup> pr = groupService
				.findStrategyGroupPageResult(sg, start, limit);
		List<StrategyGroup> sgList = pr.getList();
		int totalCount = pr.getTotalCount();
		List<ConfigableInfo> dispnamelist = regularConfigService
				.listConfigableInfo(ConfigableType.normal);
		Map<String, Object> dataMap = CollectionUtils.newHashMap(3);
		dataMap.put("totalCount", totalCount);
		dataMap.put("list", sgList);
		dataMap.put("dispname", dispnamelist);
		return json.writeValueAsString(dataMap);
	}
}
