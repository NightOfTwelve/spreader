package com.nali.spreader.service;

import java.util.List;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.nali.common.model.Limit;
import com.nali.common.pagination.PageResult;
import com.nali.spreader.config.Range;
import com.nali.spreader.config.UserTagParamsDto;
import com.nali.spreader.data.KeyValue;
import com.nali.spreader.data.RobotRegister;
import com.nali.spreader.data.User;
import com.nali.spreader.data.UserTag;
import com.nali.spreader.model.RobotUser;

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

	/**
	 * 更新用户信息
	 * 
	 * @param user
	 * @return
	 */
	int updateUserProprietor(User user);

	/**
	 * 根据Uid获取密码
	 * 
	 * @param uid
	 * @return
	 */
	String findUserRegisterPassword(Long uid);

	/**
	 * 获取随机List
	 * 
	 * @param <T>
	 * 
	 * @param list
	 * @param range
	 * @return
	 */
	<T> List<T> getRandomList(List<T> list, Range<Integer> range);

	/**
	 * 根据关键字列表创建UserTag
	 * 
	 * @param keywordList
	 * @return
	 */
	List<UserTag> createUserTags(List<String> keywordList);

	/**
	 * 导入指定微博帐号
	 * 
	 * @param file
	 * @return
	 */
	List<KeyValue<RobotUser, User>> importWeiboAccount(CommonsMultipartFile file);
}