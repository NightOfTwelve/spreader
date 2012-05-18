package com.nali.spreader.controller;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nali.common.model.Limit;
import com.nali.common.pagination.PageResult;
import com.nali.spreader.config.UserTagParamsDto;
import com.nali.spreader.controller.basectrl.BaseController;
import com.nali.spreader.data.User;
import com.nali.spreader.service.IUserManageService;

@Controller
@RequestMapping(value = "/userinfo")
public class UserInfoManageController extends BaseController {
	private static final Logger LOGGER = Logger.getLogger(UserInfoManageController.class);
	@Autowired
	private IUserManageService userService;

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
	public String userInfoDtl(Long id, String nickName, Integer minFans, Integer maxFans,
			Integer minRobotFans, Integer maxRobotFans, String tag, Boolean isRobot, Integer start,
			Integer limit) throws JsonGenerationException, JsonMappingException, IOException {
		UserTagParamsDto utp = new UserTagParamsDto();
		utp.setMinFans(minFans);
		utp.setMaxFans(maxFans);
		utp.setId(id);
		utp.setMinRobotFans(minRobotFans);
		utp.setMaxRobotFans(maxRobotFans);
		utp.setNickName(nickName);
		utp.setTag(tag);
		utp.setIsRobot(isRobot);
		Limit lit = this.initLimit(start, limit);
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
}
