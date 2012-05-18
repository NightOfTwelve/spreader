package com.nali.spreader.controller;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
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
import com.nali.spreader.data.UserGroup;
import com.nali.spreader.factory.config.desc.ConfigDefinition;
import com.nali.spreader.factory.config.desc.ConfigableInfo;
import com.nali.spreader.factory.config.desc.DescriptionResolve;
import com.nali.spreader.group.assembler.UserGroupAssembler;
import com.nali.spreader.group.exception.AssembleException;
import com.nali.spreader.group.exception.UserGroupException;
import com.nali.spreader.group.exp.PropertyExpParser;
import com.nali.spreader.group.exp.PropertyExpression;
import com.nali.spreader.group.exp.PropertyExpressionDTO;
import com.nali.spreader.group.meta.UserGroupType;
import com.nali.spreader.group.service.IUserGroupService;
import com.nali.spreader.model.GrouppedUser;

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
	private IUserGroupService userGroupService;
	@Autowired
	private UserGroupAssembler userGroupAssembler;
	@Autowired
	private PropertyExpParser expParser;
	// 手动分组类型
	private static final Integer GROUPTYPE_MANUAL = 2;

	/**
	 * 初始化页面
	 * 
	 * @return
	 */
	public String init() {
		return "/show/main/UserGroupManageShow";
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
			Integer propVal, Date fromModifiedTime, Date toModifiedTime, Integer start,
			Integer limit) {
		Website website = null;
		if (websiteid != null) {
			website = Website.valueOf(websiteid);
		}
		UserGroupType userGroupType = null;
		if (gtype != null) {
			userGroupType = UserGroupType.valueOf(gtype);
		}
		if (propVal == null) {
			propVal = 0;
		}
		Limit lit = this.initLimit(start, limit);
		PageResult<UserGroup> result = userGroupService.queryUserGroups(website, gname,
				userGroupType, propVal, fromModifiedTime, toModifiedTime, lit);
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
			UserGroup group = userGroupAssembler.assembleUserGroup(Website.valueOf(websiteid),
					gname, description, UserGroupType.valueOf(gtype), dto);
			// 如果是手动分组propVale=-1
			if (group != null && GROUPTYPE_MANUAL.equals(gtype)) {
				group.setPropVal(-1);
			}
			try {
				long gid = this.userGroupService.createGroup(group);
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
		Boolean flg = userGroupService.checkUserGroupUniqueByName(gname);
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
	public String generatePropExp(Long gid) throws AssembleException {
		ConfigDefinition def = null;
		ConfigableInfo configableInfo = null;
		Object data = null;
		if (gid != null && gid > 0) {
			UserGroup usergroup = this.userGroupService.queryUserGroup(gid);
			if (usergroup != null) {
				String propexp = usergroup.getPropExp();
				PropertyExpression pexp = this.userGroupAssembler.toExpression(propexp);
				data = new PropertyExpressionDTO(pexp);
				def = DescriptionResolve.get(PropertyExpressionDTO.class);
				configableInfo = DescriptionResolve.getConfigableInfo(PropertyExpressionDTO.class,
						usergroup.getGname());
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
		Map<String, Boolean> result = CollectionUtils.newHashMap(1);
		result.put("success", false);
		if (gid != null && gid > 0) {
			UserGroup group = userGroupService.queryUserGroup(gid);
			PropertyExpressionDTO dto = this.getObjectMapper().readValue(propexp,
					PropertyExpressionDTO.class);
			PropertyExpression pexp = new PropertyExpression(dto);
			String jsonPexp = this.userGroupAssembler.toJson(pexp);
			group.setPropExp(jsonPexp);
			int propVal = expParser.parsePropVal(dto);
			group.setPropVal(propVal);
			userGroupService.updateUserGroup(group);
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
		PageResult<GrouppedUser> pr = null;
		if (gid != null && gid > 0) {
			Limit lit = this.initLimit(start, limit);
			pr = this.userGroupService.queryGrouppedUsers(gid, lit);
		}
		return this.write(pr);
	}

	/**
	 * 查询已删除用户列表
	 * 
	 * @param gid
	 * @param start
	 * @param Limit
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteuserlist")
	public String queryDeleteUserList(Long gid, Integer start, Integer limit) {
		PageResult<GrouppedUser> pr = null;

		if (gid != null && gid > 0) {
			Limit lit = this.initLimit(start, limit);
			pr = this.userGroupService.queryExcludeUsers(gid, lit);
		}
		return this.write(pr);
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
	 * 删除已经存在的用户
	 * 
	 * @param gid
	 * @param uids
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteuser")
	public String deleteGrouppedUser(Long gid, long[] uids) {
		Map<String, Object> result = CollectionUtils.newHashMap(2);
		result.put("success", false);
		if (gid != null && uids.length > 0) {
			try {
				this.userGroupService.removeUsers(gid, uids);
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
	 * 还原删除的用户
	 * 
	 * @param gid
	 * @param uids
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/rollbackuser")
	public String rollbackDeleteUser(Long gid, long[] uids) {
		Map<String, Object> result = CollectionUtils.newHashMap(2);
		result.put("success", false);
		if (gid != null && uids.length > 0) {
			try {
				this.userGroupService.rollbackExcludeUsers(gid, uids);
				result.put("success", true);
				result.put("message", "还原成功");
			} catch (UserGroupException e) {
				logger.debug("后台数据存储异常", e);
				result.put("message", "后台数据存储异常,还原失败");
			}
		} else {
			result.put("message", "gid为空不能还原用户,还原失败");
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
		Map<String, Boolean> result = CollectionUtils.newHashMap(1);
		result.put("success", false);
		if (gids != null && gids.length > 0) {
			for (long gid : gids) {
				this.userGroupService.deleteUserGroup(gid);
			}
			result.put("success", true);
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
