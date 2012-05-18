package com.nali.spreader.service;

import com.nali.common.model.Limit;
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
	PageResult<User> findUserInfo(UserTagParamsDto utp, Limit lit);

	/**
	 * 查询机器人注册信息
	 * 
	 * @param param
	 * @return
	 */
	PageResult<RobotRegister> findRobotRegisterInfo(String nickName, String province, Limit lit);

	/**
	 * 获取用户粉丝信息
	 * 
	 * @param utp
	 * @param start
	 * @param limit
	 * @return
	 */
	PageResult<User> findUserFansInfo(UserTagParamsDto utp, Limit lit);
}
