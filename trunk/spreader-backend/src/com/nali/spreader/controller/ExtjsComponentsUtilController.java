package com.nali.spreader.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nali.common.model.Limit;
import com.nali.common.pagination.PageResult;
import com.nali.log.MessageLogger;
import com.nali.log.impl.LoggerFactory;
import com.nali.spreader.controller.basectrl.BaseController;
import com.nali.spreader.data.Category;
import com.nali.spreader.data.User;
import com.nali.spreader.data.UserGroup;
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
public class ExtjsComponentsUtilController extends BaseController {
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
	 */
	@ResponseBody
	@RequestMapping(value = "/strategygroupcombo")
	public String testComboBoxStore(String groupName, Integer groupType, Long id, String query,
			Integer start, Integer limit) {
		StrategyGroup sg = new StrategyGroup();
		sg.setId(id);
		sg.setGroupName(query);
		sg.setGroupType(groupType);
		PageResult<StrategyGroup> pr = groupService.findStrategyGroupPageResult(sg,
				this.initLimit(start, limit));
		return this.write(pr);
	}

	/**
	 * 筛选USER的数据源
	 * 
	 * @param query
	 * @param start
	 * @param limit
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/usercombo")
	public String userComboxStore(String query, Integer start, Integer limit) {
		if (start == null)
			start = 0;
		if (limit == null)
			limit = 20;
		Limit lit = Limit.newInstanceForLimit(start, limit);
		PageResult<User> pr = this.extjsService.findUserByName(query, lit);
		return this.write(pr);
	}

	/**
	 * 筛选USERGROUP的数据源
	 * 
	 * @param query
	 * @param start
	 * @param limit
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/usergroupcombo")
	public String userGroupComboxStore(String query, Integer start, Integer limit) {
		if (start == null)
			start = 0;
		if (limit == null)
			limit = 20;
		Limit lit = Limit.newInstanceForLimit(start, limit);
		PageResult<UserGroup> pr = this.extjsService.findUserGroupByName(query, lit);
		return this.write(pr);
	}

	/**
	 * 筛选分类
	 * 
	 * @param query
	 * @param start
	 * @param limit
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/categorycombo")
	public String categoryComboxStore(String query, Integer start, Integer limit) {
		if (start == null)
			start = 0;
		if (limit == null)
			limit = 20;
		Limit lit = Limit.newInstanceForLimit(start, limit);
		PageResult<Category> pr = this.extjsService.findCategoryByName(query, lit);
		return this.write(pr);
	}

	/**
	 * websiteId下拉框
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/website")
	public String websiteIdComboxStore() {
		return this.write(this.extjsService.findWebsite());
	}

	@Override
	public String init() {
		return null;
	}
}
