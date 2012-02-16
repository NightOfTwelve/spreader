package com.nali.spreader.controller;

import java.io.IOException;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
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
import com.nali.spreader.data.UserGroup;
import com.nali.spreader.factory.config.desc.ConfigDefinition;
import com.nali.spreader.factory.config.desc.ConfigableInfo;
import com.nali.spreader.factory.config.desc.DescriptionResolve;
import com.nali.spreader.group.assembler.UserGroupAssembler;
import com.nali.spreader.group.exception.AssembleException;
import com.nali.spreader.group.exception.UserGroupException;
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
public class UserGroupManageController {
	private static ObjectMapper json = new ObjectMapper();
	private static final MessageLogger logger = LoggerFactory
			.getLogger(UserGroupManageController.class);
	@Autowired
	private IUserGroupService userGroupService;
	@Autowired
	private UserGroupAssembler userGroupAssembler;

	/**
	 * 初始化页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/init")
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
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	@ResponseBody
	@RequestMapping(value = "/grouplist")
	public String queryAllUserGroup(Integer websiteid, String gname,
			Integer gtype, Integer start, Integer limit)
			throws JsonGenerationException, JsonMappingException, IOException {
		if (limit == null) {
			limit = 20;
		}
		if (start == null) {
			start = 0;
		}
		if (websiteid == null) {
			websiteid = 1;
		}
		if (gtype == null) {
			gtype = 1;
		}
		Limit lit = Limit.newInstanceForLimit(start, limit);
		PageResult<UserGroup> result = userGroupService.queryUserGroups(
				Website.valueOf(websiteid), gname,
				UserGroupType.valueOf(gtype), 0, null, null, lit);
		return json.writeValueAsString(result);
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
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	@ResponseBody
	@RequestMapping(value = "/createusergroup")
	public String addNewUserGroup(String gname, Integer gtype,
			Integer websiteid, String description) throws AssembleException,
			JsonGenerationException, JsonMappingException, IOException {
		Map<String, Object> result = CollectionUtils.newHashMap(1);
		result.put("success", false);
		result.put("gid", null);
		if (gtype != null && websiteid != null) {
			PropertyExpressionDTO dto = new PropertyExpressionDTO();
			UserGroup group = userGroupAssembler.assembleUserGroup(
					Website.valueOf(websiteid), gname, description,
					UserGroupType.valueOf(gtype), dto);
			try {
				long gid = this.userGroupService.createGroup(group);
				if (gid > 0) {
					result.put("success", true);
					result.put("gid", gid);
					logger.info("usergroup创建成功,gid=" + gid);
				}
			} catch (UserGroupException e) {
				logger.error("usergroup创建异常", e);
			}
		} else {
			logger.error("gtype或websiteid为null,不能创建");
		}
		return json.writeValueAsString(result);
	}

	/**
	 * 检查分组名称是否已经存在
	 * 
	 * @param gname
	 * @return
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	@ResponseBody
	@RequestMapping(value = "/checkname")
	public String checkGroupName(String gname) throws JsonGenerationException,
			JsonMappingException, IOException {
		Map<String, Boolean> result = CollectionUtils.newHashMap(1);
		Boolean flg = userGroupService.checkUserGroupUniqueByName(gname);
		result.put("success", flg);
		return json.writeValueAsString(result);
	}

	/**
	 * 根据分组ID生成一套PropExp，用于展示
	 * 
	 * @param gid
	 * @return
	 * @throws AssembleException
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	@ResponseBody
	@RequestMapping(value = "/genpropexp")
	public String generatePropExp(Long gid) throws AssembleException,
			JsonGenerationException, JsonMappingException, IOException {
		ConfigDefinition def = null;
		ConfigableInfo configableInfo = null;
		if (gid != null && gid > 0) {
			UserGroup usergroup = this.userGroupService.queryUserGroup(gid);
			if (usergroup != null) {
				def = DescriptionResolve.get(PropertyExpressionDTO.class);
				configableInfo = DescriptionResolve.getConfigableInfo(
						PropertyExpressionDTO.class, usergroup.getGname());
			}
		}
		return json.writeValueAsString(new UserGroupExpTreeDto(configableInfo,
				def, null));
	}

	/**
	 * 查询某个分组的人员信息
	 * 
	 * @param gid
	 * @param start
	 * @param limit
	 * @return
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	@ResponseBody
	@RequestMapping(value = "/selectuserlist")
	public String queryGrouppedUserList(Long gid, Integer start, Integer limit)
			throws JsonGenerationException, JsonMappingException, IOException {
		PageResult<GrouppedUser> pr = null;
		if (limit == null) {
			limit = 20;
		}
		if (start == null) {
			start = 0;
		}
		if (gid != null && gid > 0) {
			Limit lit = Limit.newInstanceForLimit(start, limit);
			pr = this.userGroupService.queryGrouppedUsers(gid, lit);
		}
		return json.writeValueAsString(pr);
	}

	/**
	 * 查询已删除用户列表
	 * 
	 * @param gid
	 * @param start
	 * @param Limit
	 * @return
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteuserlist")
	public String queryDeleteUserList(Long gid, Integer start, Integer limit)
			throws JsonGenerationException, JsonMappingException, IOException {
		PageResult<GrouppedUser> pr = null;
		if (limit == null) {
			limit = 20;
		}
		if (start == null) {
			start = 0;
		}
		if (gid != null && gid > 0) {
			Limit lit = Limit.newInstanceForLimit(start, limit);
			pr = this.userGroupService.queryExcludeUsers(gid, lit);
		}
		return json.writeValueAsString(pr);
	}

	/**
	 * 手工添加用户
	 * 
	 * @param gid
	 * @param uids
	 * @return
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	@ResponseBody
	@RequestMapping(value = "/adduser")
	public String addGrouppedUser(Long gid, long[] uids)
			throws JsonGenerationException, JsonMappingException, IOException {
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
		return json.writeValueAsString(result);
	}

	/**
	 * 删除已经存在的用户
	 * 
	 * @param gid
	 * @param uids
	 * @return
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteuser")
	public String deleteGrouppedUser(Long gid, long[] uids)
			throws JsonGenerationException, JsonMappingException, IOException {
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
		return json.writeValueAsString(result);
	}

	/**
	 * 还原删除的用户
	 * 
	 * @param gid
	 * @param uids
	 * @return
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	@ResponseBody
	@RequestMapping(value = "/rollbackuser")
	public String rollbackDeleteUser(Long gid, long[] uids)
			throws JsonGenerationException, JsonMappingException, IOException {
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
		return json.writeValueAsString(result);
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

		public UserGroupExpTreeDto(ConfigableInfo configableInfo,
				ConfigDefinition def, Object data) {
			this(configableInfo.getName(), configableInfo.getDisplayName(),
					def, data);
		}

		public UserGroupExpTreeDto(String id, String name,
				ConfigDefinition def, Object data) {
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
