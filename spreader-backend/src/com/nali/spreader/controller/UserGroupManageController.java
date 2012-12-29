package com.nali.spreader.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nali.common.model.Limit;
import com.nali.common.pagination.PageResult;
import com.nali.common.util.CollectionUtils;
import com.nali.log.MessageLogger;
import com.nali.log.impl.LoggerFactory;
import com.nali.spreader.constants.Website;
import com.nali.spreader.controller.basectrl.BaseController;
import com.nali.spreader.data.User;
import com.nali.spreader.factory.config.desc.ConfigDefinition;
import com.nali.spreader.factory.config.desc.ConfigableInfo;
import com.nali.spreader.factory.config.desc.DescriptionResolve;
import com.nali.spreader.group.exception.AssembleException;
import com.nali.spreader.group.exception.UserGroupException;
import com.nali.spreader.group.exp.PropertyExpressionDTO;
import com.nali.spreader.group.meta.UserGroupType;
import com.nali.spreader.group.service.IUserGroupInfoService;
import com.nali.spreader.group.service.IUserGroupPropertyService;
import com.nali.spreader.model.UserGroup;
import com.nali.spreader.service.IGlobalUserService;

/**
 * 用户分组管理controller
 * 
 * @author xiefei
 * 
 */
@Controller
@RequestMapping(value = "/usergroup")
public class UserGroupManageController extends BaseController {
	private static final MessageLogger logger = LoggerFactory
			.getLogger(UserGroupManageController.class);
	@Autowired
	private IUserGroupInfoService userGroupService;
	@Autowired
	private IUserGroupPropertyService userGroupPropertyService;
	@Autowired
	private IGlobalUserService globalUserService;

	/**
	 * 初始化页面
	 * 
	 * @return
	 */
	public String init() {
		return "/show/main/UserGroupManageShow";
	}

	/**
	 * 刷新指定分组的用户
	 * 
	 * @param gid
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/refreshuser")
	public String refreshUser(Long gid) {
		String message = "分组刷新失败";
		if (this.userGroupService.refreshGroupUsers(gid)) {
			message = "分组刷新成功";
		}
		return this.write(message);
	}

	/**
	 * 刷新选中的分组用户
	 * 
	 * @param gids
	 * @return
	 */

	@ResponseBody
	@RequestMapping(value = "/refreshselectusers")
	public String refreshSelectUser(Long... gids) {
		if (gids != null) {
			for (Long gid : gids) {
				this.userGroupService.refreshGroupUsers(gid);
			}
		}
		return this.write("已执行");
	}

	/**
	 * 查询用户分组列表
	 * 
	 * @param website
	 * @param gname
	 * @param userGroupType
	 * @param start
	 * @param limit
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/grouplist")
	public String queryAllUserGroup(Integer websiteid, String gname, Integer gtype,
			Date fromModifiedTime, Date toModifiedTime, Integer start, Integer limit) {
		Website website = null;
		if (websiteid != null) {
			website = Website.valueOf(websiteid);
		}
		UserGroupType userGroupType = null;
		if (gtype != null) {
			userGroupType = UserGroupType.valueOf(gtype);
		}
		Limit lit = this.initLimit(start, limit);
		PageResult<UserGroup> result = userGroupService.queryUserGroups(website, gname,
				userGroupType, fromModifiedTime, toModifiedTime, lit);
		return this.write(result);
	}

	/**
	 * 创建一个新分组
	 * 
	 * @param gname
	 * @param gtype
	 * @param websiteid
	 * @param description
	 * @return
	 * @throws AssembleException
	 */
	@ResponseBody
	@RequestMapping(value = "/createusergroup")
	public String addNewUserGroup(String gname, Integer gtype, Integer websiteid, String description)
			throws AssembleException {
		Map<String, Object> result = CollectionUtils.newHashMap(1);
		result.put("success", false);
		result.put("gid", null);
		if (gtype != null && websiteid != null) {
			PropertyExpressionDTO dto = new PropertyExpressionDTO();
			UserGroup group = userGroupPropertyService.assembleUserGroup(
					Website.valueOf(websiteid), gname, description, UserGroupType.valueOf(gtype),
					dto);
			group.setPropVal(null);
			try {
				long gid = this.userGroupPropertyService.createGroup(group);
				if (gid > 0) {
					result.put("success", true);
					result.put("gid", gid);
					logger.debug("usergroup创建成功,gid=" + gid);
				}
			} catch (UserGroupException e) {
				logger.error("usergroup创建异常", e);
			}
		} else {
			logger.error("gtype或websiteid为null,不能创建");
		}
		return this.write(result);
	}

	/**
	 * 检查分组名称是否已经存在
	 * 
	 * @param gname
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/checkname")
	public String checkGroupName(String gname) {
		Map<String, Boolean> result = CollectionUtils.newHashMap(1);
		Boolean flg = userGroupPropertyService.checkUserGroupUniqueByName(gname);
		result.put("success", flg);
		return this.write(result);
	}

	/**
	 * 根据分组ID生成一套PropExp，用于展示
	 * 
	 * @param gid
	 * @return
	 * @throws AssembleException
	 */
	@ResponseBody
	@RequestMapping(value = "/genpropexp")
	public String generatePropExp(Long gid) {
		ConfigDefinition def = null;
		ConfigableInfo configableInfo = null;
		Object data = null;
		if (gid != null && gid > 0) {
			UserGroup usergroup = this.userGroupService.queryUserGroup(gid);
			if (usergroup != null) {
				try {
					String propexp = usergroup.getPropExp();
					try {
						data = userGroupPropertyService.toExpression(propexp);
					} catch (AssembleException e) {
						logger.error("Can't parse string to PropertyExpressionDTO , gid=" + gid, e);
					}
					def = DescriptionResolve.get(PropertyExpressionDTO.class);
					configableInfo = DescriptionResolve.getConfigableInfo(
							PropertyExpressionDTO.class, usergroup.getGname());
				} catch (Exception e) {
					logger.error("show Property fail ,gid=" + gid, e);
				}
			}
		}
		return this.write(new UserGroupExpTreeDto(configableInfo, def, data));
	}

	/**
	 * 更新编辑后的分组数据
	 * 
	 * @param gid
	 * @param propexp
	 * @return
	 * @throws AssembleException
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	@ResponseBody
	@RequestMapping(value = "/updategroup")
	public String updateGroupData(Long gid, String propexp) throws AssembleException,
			JsonParseException, JsonMappingException, IOException {
		Map<String, Object> result = CollectionUtils.newHashMap(1);
		result.put("success", false);
		if (gid != null && gid > 0) {
			UserGroup group = userGroupService.queryUserGroup(gid);
			PropertyExpressionDTO dto = this.getObjectMapper().readValue(propexp,
					PropertyExpressionDTO.class);
			List<Long> excludeGids = dto.getExcludeGids();
			if (!CollectionUtils.isEmpty(excludeGids)) {
				Map<Long, List<Long>> excMap = new HashMap<Long, List<Long>>();
				excMap.put(gid, excludeGids);
				if (!userGroupService.checkExcludeGroups(gid, excMap)) {
					result.put("error", "排除分组设置异常，请重新设置");
					this.write(result);
				}
			}
			userGroupService.updateGroupExclude(gid, excludeGids);
			String jsonPexp = this.userGroupPropertyService.toJson(dto);
			group.setPropExp(jsonPexp);
			group.setPropVal(null);
			userGroupPropertyService.updateUserGroup(group);
			result.put("success", true);
		}
		return this.write(result);
	}

	/**
	 * 查询某个分组的人员信息
	 * 
	 * @param gid
	 * @param start
	 * @param limit
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selectuserlist")
	public String queryGrouppedUserList(Long gid, Integer start, Integer limit) {
		PageResult<User> result = null;
		if (gid != null) {
			Limit lit = this.initLimit(start, limit);
			PageResult<Long> uidPage = this.userGroupService.queryGrouppedUsers(gid, lit);
			List<User> uList = this.globalUserService.getUserMapByUids(uidPage.getList());
			result = new PageResult<User>(uList, lit, uidPage.getTotalCount());
		}
		return this.write(result);
	}

	/**
	 * 手工添加用户
	 * 
	 * @param gid
	 * @param uids
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/adduser")
	public String addGrouppedUser(Long gid, long[] uids) {
		Map<String, Object> result = CollectionUtils.newHashMap(2);
		result.put("success", false);
		if (gid != null && uids.length > 0) {
			try {
				this.userGroupService.addManualUsers(gid, uids);
				result.put("success", true);
				result.put("message", "添加成功");
			} catch (UserGroupException e) {
				logger.debug("后台数据存储异常", e);
				result.put("message", "后台数据存储异常,添加失败");
			}
		} else {
			result.put("message", "gid为空不能添加用户,添加失败");
		}
		return this.write(result);
	}

	/**
	 * 自定义导入用户
	 * 
	 * @param gid
	 * @param uids
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/impusertogroup")
	public String importGrouppedUser(Long gid, long[] uids) {
		Map<String, Object> result = CollectionUtils.newHashMap(2);
		result.put("success", false);
		if (gid != null && uids.length > 0) {
			try {
				userGroupService.addManualUsers(gid, uids);
				userGroupService.refreshGroupUsers(gid);
				result.put("success", true);
				result.put("message", "添加成功");
			} catch (UserGroupException e) {
				logger.debug("后台数据存储异常", e);
				result.put("message", "后台数据存储异常,添加失败");
			}
		} else {
			result.put("message", "gid为空不能添加用户,添加失败");
		}
		return this.write(result);
	}

	/**
	 * 删除已经存在的用户
	 * 
	 * @param gid
	 * @param uids
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteuser")
	public String deleteGrouppedUser(Long gid, Long[] uids) {
		Map<String, Object> result = CollectionUtils.newHashMap(2);
		result.put("success", false);
		if (gid != null && uids.length > 0) {
			try {
				this.userGroupService.removeManualUsers(gid, uids);
				result.put("success", true);
				result.put("message", "删除成功");
			} catch (UserGroupException e) {
				logger.debug("后台数据存储异常", e);
				result.put("message", "后台数据存储异常,删除失败");
			}
		} else {
			result.put("message", "gid为空不能删除用户,删除失败");
		}
		return this.write(result);
	}

	/**
	 * 批量删除用户分组
	 * 
	 * @param gids
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/removegroup")
	public String deleteUserGroup(long[] gids) {
		Map<String, Object> result = CollectionUtils.newHashMap(1);
		result.put("success", false);
		result.put("message", "");
		List<Long> dependGroup = new ArrayList<Long>();
		if (gids != null && gids.length > 0) {
			for (long gid : gids) {
				if (this.userGroupService.isDependency(gid)) {
					dependGroup.add(gid);
				} else {
					this.userGroupService.deleteUserGroup(gid);
				}
			}
			if (dependGroup.size() > 0) {
				result.put("message", "分组编号:" + dependGroup.toString() + " 因在其它分组中有排除关系，不能删除");
			}
			result.put("success", true);
		}
		return this.write(result);
	}

	/**
	 * 查询手工分组的用户
	 * 
	 * @param gid
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/manualusers")
	public String queryManualUsers(Long gid, Integer start, Integer limit) {
		PageResult<User> result = null;
		if (gid != null && gid > 0) {
			Limit lit = this.initLimit(start, limit);
			PageResult<Long> uidPage = this.userGroupService.getManualUsersPageData(gid, lit);
			List<User> uList = this.globalUserService.getUserMapByUids(uidPage.getList());
			result = new PageResult<User>(uList, lit, uidPage.getTotalCount());
		}
		return this.write(result);
	}

	/**
	 * 修改分组名称和备注
	 * 
	 * @param gid
	 * @param value
	 * @param field
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/modifiedgroup")
	public String updateGroupInfo(Long gid, String value, String field) {
		Assert.notNull(gid, " gid is null");
		Map<String, Boolean> result = CollectionUtils.newHashMap(1);
		result.put("success", false);
		if (StringUtils.isBlank(field)) {
			throw new IllegalArgumentException(" field is blank");
		}
		if ("gname".equals(field)) {
			result.put("success", userGroupService.updateGroupName(gid, value));
		}
		if ("description".equals(field)) {
			result.put("success", userGroupService.updateGroupNote(gid, value));
		}
		return this.write(result);
	}

	/**
	 * 前台显示树结构DTO
	 * 
	 * @author xiefei
	 * 
	 */
	public static class UserGroupExpTreeDto {
		private String id;
		private String name;
		private ConfigDefinition def;
		private Object data;

		public UserGroupExpTreeDto(ConfigableInfo configableInfo, ConfigDefinition def, Object data) {
			this(configableInfo.getName(), configableInfo.getDisplayName(), def, data);
		}

		public UserGroupExpTreeDto(String id, String name, ConfigDefinition def, Object data) {
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
