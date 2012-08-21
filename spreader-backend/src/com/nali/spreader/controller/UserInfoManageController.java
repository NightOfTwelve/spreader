package com.nali.spreader.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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

import com.nali.common.model.Limit;
import com.nali.common.pagination.PageResult;
import com.nali.common.util.CollectionUtils;
import com.nali.spreader.config.Range;
import com.nali.spreader.config.UserTagParamsDto;
import com.nali.spreader.controller.basectrl.BaseController;
import com.nali.spreader.data.User;
import com.nali.spreader.data.UserTag;
import com.nali.spreader.model.RobotUser;
import com.nali.spreader.service.IGlobalUserService;
import com.nali.spreader.service.IUserManageService;

@Controller
@RequestMapping(value = "/userinfo")
public class UserInfoManageController extends BaseController {
	private static final Logger LOGGER = Logger.getLogger(UserInfoManageController.class);
	@Autowired
	private IUserManageService userService;
	@Autowired
	private IGlobalUserService globaUserService;

	public String init() {
		return "/show/main/UserInfoManageShow";
	}

	/**
	 * 查询用户列表的分页数据
	 * 
	 * @param id
	 * @param nickname
	 * @param start
	 * @param limit
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/userlist")
	public String userInfoDtl(UserTagParamsDto utp)
			throws JsonGenerationException, JsonMappingException, IOException {
		// UserTagParamsDto utp = new UserTagParamsDto();
		// utp.setMinFans(minFans);
		// utp.setMaxFans(maxFans);
		// utp.setId(id);
		// utp.setMinRobotFans(minRobotFans);
		// utp.setMaxRobotFans(maxRobotFans);
		// utp.setNickName(nickName);
		// utp.setTag(tag);
		// utp.setIsRobot(isRobot);
		Limit lit = this.initLimit(utp.getStart(), utp.getLimit());
		PageResult<User> result = userService.findUserInfo(utp, lit);
		return this.write(result);
	}

	/**
	 * 查询真人粉丝信息
	 * 
	 * @param id
	 * @param start
	 * @param limit
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/realfansinfo")
	public String userRealFansInfo(Long id, Integer start, Integer limit) {
		Limit lit = this.initLimit(start, limit);
		if (id != null) {
			UserTagParamsDto utp = new UserTagParamsDto();
			utp.setId(id);
			utp.setIsRobot(false);
			PageResult<User> result = userService.findUserFansInfo(utp, lit);
			return this.write(result);
		} else {
			LOGGER.info("id不能为空");
			return null;
		}
	}

	/**
	 * 查询机器人粉丝信息
	 * 
	 * @param id
	 * @param start
	 * @param limit
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/robotfansinfo")
	public String userRobotFansInfo(Long id, Integer start, Integer limit) {
		Limit lit = this.initLimit(start, limit);
		if (id != null) {
			UserTagParamsDto utp = new UserTagParamsDto();
			utp.setId(id);
			utp.setIsRobot(true);
			PageResult<User> result = userService.findUserFansInfo(utp, lit);
			return this.write(result);
		} else {
			LOGGER.info("id不能为空");
			return null;
		}
	}

	/**
	 * 更新用户信息
	 * 
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateuser")
	public String updateUserData(User user) {
		Map<String, Boolean> response = CollectionUtils.newHashMap(1);
		response.put("success", false);
		if (user == null) {
			throw new IllegalArgumentException("参数错误，修改用户失败");
		} else {
			int rows = userService.updateUserProprietor(user);
			if (rows > 0) {
				response.put("success", true);
			}
		}
		return this.write(response);
	}

	/**
	 * 获取用户密码
	 * 
	 * @param uid
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/querypwd")
	public String queryPassword(Long uid) {
		Map<String, String> m = CollectionUtils.newHashMap(1);
		m.put("password", "");
		if (uid == null) {
			throw new IllegalArgumentException("ID为null，无法获取密码");
		}
		String pwd = this.userService.findUserRegisterPassword(uid);
		m.put("password", pwd);
		return this.write(m);
	}

	/**
	 * 导出用户信息
	 * 
	 * @param uids
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/expuser")
	public String expUserInfo(Long... uids) {
		Map<String, List<RobotUser>> data = CollectionUtils.newHashMap(1);
		List<RobotUser> list = new ArrayList<RobotUser>();
		for (Long uid : uids) {
			RobotUser r = globaUserService.exportUser(uid);
			if (r != null) {
				list.add(r);
			}
		}
		data.put("list", list);
		return this.write(data);
	}

	/**
	 * 批量修改用户标签
	 * 
	 * @param uids
	 * @param rangegte
	 * @param rangelte
	 * @param corekeyword
	 * @param randomkeyword
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updatetag")
	public String updateUserTags(Long[] uids, Integer rangegte, Integer rangelte,
			String corekeyword, String randomkeyword) {
		if (uids == null || uids.length == 0) {
			throw new IllegalArgumentException("uid is null");
		}
		// 核心关键字不能为空
		if (StringUtils.isEmpty(corekeyword)) {
			throw new IllegalArgumentException("corekeyword is null");
		}
		String[] coreArray = corekeyword.split("[;]");
		List<String> coreList = new ArrayList<String>(Arrays.asList(coreArray));
		if (StringUtils.isNotEmpty(randomkeyword)) {
			String[] randomArray = randomkeyword.split("[;]");
			Range<Integer> range = new Range<Integer>();
			range.setGte(rangegte);
			range.setLte(rangelte);
			List<String> randomList = this.userService.getRandomList(Arrays.asList(randomArray),
					range);
			coreList.addAll(randomList);
		}
		List<UserTag> tagList = this.userService.createUserTags(coreList);
		int count = 0;
		for (Long uid : uids) {
			try {
				this.globaUserService.updateUserTags(uid, tagList);
				count++;
			} catch (Exception e) {
				LOGGER.debug("修改UserTag出错", e);
			}
		}
		Map<String, Integer> m = CollectionUtils.newHashMap(1);
		m.put("count", count);
		return this.write(m);
	}
}
