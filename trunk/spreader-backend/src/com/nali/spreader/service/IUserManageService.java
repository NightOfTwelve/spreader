package com.nali.spreader.service;

import com.nali.common.pagination.PageResult;
import com.nali.spreader.config.UserTagParamsDto;
import com.nali.spreader.data.RobotRegister;
import com.nali.spreader.data.User;

public interface IUserManageService {
	/**
	 * 获取用户信息分页数据
	 * 
	 * @param utp
	 * @param start
	 * @param limit
	 * @return
	 */
	PageResult<User> findUserInfo(UserTagParamsDto utp, Integer start,
			Integer limit);

	/**
	 * 查询机器人注册信息
	 * 
	 * @param param
	 * @param start
	 * @param limit
	 * @return
	 */
	PageResult<RobotRegister> findRobotRegisterInfo(String nickName, String province,
			Integer start, Integer limit);

	/**
	 * 获取用户粉丝信息
	 * 
	 * @param utp
	 * @param start
	 * @param limit
	 * @return
	 */
	PageResult<User> findUserFansInfo(UserTagParamsDto utp, Integer start,
			Integer limit);
}
