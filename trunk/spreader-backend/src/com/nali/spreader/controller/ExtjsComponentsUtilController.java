package com.nali.spreader.controller;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nali.common.model.Limit;
import com.nali.common.pagination.PageResult;
import com.nali.log.MessageLogger;
import com.nali.log.impl.LoggerFactory;
import com.nali.spreader.data.User;
import com.nali.spreader.model.StrategyGroup;
import com.nali.spreader.service.IExtjsComponentsUtilService;
import com.nali.spreader.service.IStrategyGroupService;

/**
 * 前端工具组件的Ctr
 * 
 * @author xiefei
 * 
 */
@Controller
@RequestMapping(value = "/extutil")
public class ExtjsComponentsUtilController {
	private static ObjectMapper json = new ObjectMapper();
	@Autowired
	private IStrategyGroupService groupService;
	@Autowired
	private IExtjsComponentsUtilService extjsService;
	@SuppressWarnings("unused")
	private static final MessageLogger logger = LoggerFactory
			.getLogger(ExtjsComponentsUtilController.class);

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
	@RequestMapping(value = "/strategygroupcombo")
	public String testComboBoxStore(String groupName, Integer groupType,
			Long id, String query, Integer start, Integer limit)
			throws JsonGenerationException, JsonMappingException, IOException {
		if (start == null)
			start = 0;
		StrategyGroup sg = new StrategyGroup();
		sg.setId(id);
		sg.setGroupName(query);
		sg.setGroupType(groupType);
		PageResult<StrategyGroup> pr = groupService
				.findStrategyGroupPageResult(sg, start, limit);
		return json.writeValueAsString(pr);
	}

	/**
	 * 筛选USER的数据源
	 * 
	 * @param query
	 * @param start
	 * @param limit
	 * @return
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	@ResponseBody
	@RequestMapping(value = "/usercombo")
	public String userGroupComboxStore(String query, Integer start,
			Integer limit) throws JsonGenerationException,
			JsonMappingException, IOException {
		if (start == null)
			start = 0;
		if (limit == null)
			limit = 20;
		Limit lit = Limit.newInstanceForLimit(start, limit);
		PageResult<User> pr = this.extjsService.findUserByName(query, lit);
		return json.writeValueAsString(pr);
	}
}
