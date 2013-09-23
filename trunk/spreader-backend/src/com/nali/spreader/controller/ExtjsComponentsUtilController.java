package com.nali.spreader.controller;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nali.common.model.Limit;
import com.nali.common.pagination.PageResult;
import com.nali.common.util.CollectionUtils;
import com.nali.log.MessageLogger;
import com.nali.log.impl.LoggerFactory;
import com.nali.spreader.constants.Website;
import com.nali.spreader.controller.basectrl.BaseController;
import com.nali.spreader.data.Category;
import com.nali.spreader.dto.UserComboxDisplayDto;
import com.nali.spreader.model.StrategyGroup;
import com.nali.spreader.model.UserGroup;
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
	public String testComboBoxStore(String groupName, Integer groupType,
			Long id, String query, Integer start, Integer limit) {
		StrategyGroup sg = new StrategyGroup();
		sg.setId(id);
		sg.setGroupName(query);
		sg.setGroupType(groupType);
		PageResult<StrategyGroup> pr = groupService
				.findStrategyGroupPageResult(sg, this.initLimit(start, limit));
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
		Limit lit = this.initLimit(start, limit);
		int websiteId;
		String name;
		if (StringUtils.isBlank(query)) {
			websiteId = 1;
			name = null;
		} else {
			String[] str = StringUtils.trim(query).split("\\s+");
			if (str.length < 2) {
				websiteId = 1;
				name = str[0];
			} else {
				websiteId = startLikeWebsite(str[0]);
				name = StringUtils.trim(str[1]);
			}
		}
		PageResult<UserComboxDisplayDto> pr = extjsService
				.findUserByNameAndWebsite(name, websiteId, lit);
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
	public String userGroupComboxStore(String query, Integer start,
			Integer limit) {
		Limit lit = this.initLimit(start, limit);
		PageResult<UserGroup> pr = this.extjsService.findUserGroupByName(query,
				lit);
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
		Limit lit = this.initLimit(start, limit);
		PageResult<Category> pr = this.extjsService.findCategoryByName(query,
				lit);
		return this.write(pr);
	}

	/**
	 * 查询app信息
	 * 
	 * @param query
	 * @param start
	 * @param limit
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/appscombo")
	public String appInfoStore(String query, Integer start, Integer limit) {
		Limit lit = this.initLimit(start, limit);
		try {
			Long appId = Long.parseLong(query);
			PageResult<Map<String, Object>> pr = extjsService.findAppInfoById(
					appId, lit);
			return write(pr);
		} catch (NumberFormatException e) {
			PageResult<Map<String, Object>> pr = extjsService
					.findAppInfoLikeName((String) query, lit);
			return write(pr);
		}
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

	/**
	 * 获取所有策略的中文名
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/dispnames")
	public String allStrategyDisplayName() {
		return this.write(this.extjsService.getAllStrategyDisplayName());
	}

	@ResponseBody
	@RequestMapping(value = "/ximalayatask")
	public String ximalayaExecu() {
		String msg = extjsService.ximalayaExecute();
		Map<String, String> m = CollectionUtils.newHashMap(1);
		m.put("msg", msg);
		return write(m);
	}

	/**
	 * 根据字符串的首字符判断属于哪个website
	 * 
	 * @param str
	 * @return
	 */
	private int startLikeWebsite(String str) {
		if (StringUtils.isNotBlank(str)) {
			for (Website w : Website.values()) {
				String desc = StringUtils.left(w.getDescriptions()[0], 1);
				if (str.startsWith(desc)) {
					return w.getId();
				}
			}
		}
		return Website.weibo.getId();
	}
}