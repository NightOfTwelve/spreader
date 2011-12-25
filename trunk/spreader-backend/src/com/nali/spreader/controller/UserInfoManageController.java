package com.nali.spreader.controller;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nali.common.pagination.PageResult;
import com.nali.spreader.config.UserTagParamsDto;
import com.nali.spreader.data.RobotRegister;
import com.nali.spreader.data.User;
import com.nali.spreader.service.IUserManageService;

@Controller
@RequestMapping(value = "/userinfo")
public class UserInfoManageController {
	private static final Logger LOGGER = Logger
			.getLogger(UserInfoManageController.class);
	private static ObjectMapper jacksonMapper = new ObjectMapper();
	@Autowired
	private IUserManageService userService;

	@RequestMapping(value = "/init")
	public String showInit() {
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
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	@ResponseBody
	@RequestMapping(value = "/userlist")
	public String userInfoDtl(Long id, String nickName, Integer minFans,
			Integer maxFans, Integer minRobotFans, Integer maxRobotFans,
			String tag, Boolean isRobot, Integer start, Integer limit)
			throws JsonGenerationException, JsonMappingException, IOException {
		if (start == null)
			start = 0;
		UserTagParamsDto utp = new UserTagParamsDto();
		utp.setMinFans(minFans);
		utp.setMaxFans(maxFans);
		utp.setId(id);
		utp.setMinRobotFans(minRobotFans);
		utp.setMaxRobotFans(maxRobotFans);
		utp.setNickName(nickName);
		utp.setTag(tag);
		utp.setIsRobot(isRobot);
		PageResult<User> result = userService.findUserInfo(utp, start, limit);
		return jacksonMapper.writeValueAsString(result);
	}

	/**
	 * 查询真人粉丝信息
	 * 
	 * @param id
	 * @param start
	 * @param limit
	 * @return
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	@ResponseBody
	@RequestMapping(value = "/realfansinfo")
	public String userRealFansInfo(Long id, Integer start, Integer limit)
			throws JsonGenerationException, JsonMappingException, IOException {
		if (start == null || start < 1)
			start = 1;
		if (limit == null || limit < 1)
			limit = 25;
		if (id != null) {
			UserTagParamsDto utp = new UserTagParamsDto();
			utp.setId(id);
			utp.setIsRobot(false);
			PageResult<User> result = userService.findUserFansInfo(utp, start,
					limit);
			return jacksonMapper.writeValueAsString(result);
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
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	@ResponseBody
	@RequestMapping(value = "/robotfansinfo")
	public String userRobotFansInfo(Long id, Integer start, Integer limit)
			throws JsonGenerationException, JsonMappingException, IOException {
		if (start == null || start < 1)
			start = 1;
		if (limit == null || limit < 1)
			limit = 25;
		if (id != null) {
			UserTagParamsDto utp = new UserTagParamsDto();
			utp.setId(id);
			utp.setIsRobot(true);
			PageResult<User> result = userService.findUserFansInfo(utp, start,
					limit);
			return jacksonMapper.writeValueAsString(result);
		} else {
			LOGGER.info("id不能为空");
			return null;
		}
	}
}
