package com.nali.spreader.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.nali.common.model.Limit;
import com.nali.spreader.config.ContentDto;
import com.nali.spreader.config.UserDto;
import com.nali.spreader.config.UserTagParamsDto;
import com.nali.spreader.data.KeyValue;
import com.nali.spreader.data.User;
import com.nali.spreader.dto.PostWeiboContentDto;

public interface IUserDao {

	Long assignUser(User user);

	/**
	 * key是用户id value是网站用户id
	 */
	List<KeyValue<Long, Long>> findUidToWebsiteUidMapByDto(UserDto dto);

	Date getAndTouchLastFetchTime(Long uid);

	List<User> findUserFansInfoByDto(UserDto dto);

	void increaseRobotFansCount(Long uid);

	/**
	 * 查询用户列表需TAG关联
	 * 
	 * @param utp
	 * @return
	 */
	List<User> findUserAndTagInfoList(UserTagParamsDto utp);

	/**
	 * 统计总数
	 * 
	 * @param utp
	 * @return
	 */
	Integer countUserAndTagNumer(UserTagParamsDto utp);

	List<Long> findContentIdByDto(ContentDto dto);

	/**
	 * 查询content
	 * 
	 * @param dto
	 * @return
	 */

	List<Long> queryContentIdByPostContentDto(PostWeiboContentDto dto);

	/**
	 * 获取粉丝信息
	 * 
	 * @param utp
	 * @return
	 */
	List<User> findUserFansInfoList(UserTagParamsDto utp);

	/**
	 * 获取粉丝总数
	 * 
	 * @param utp
	 * @return
	 */
	Integer countUserFansNumer(UserTagParamsDto utp);

	List<Long> queryUidsByProperties(Map<String, Object> properties, Limit limit);

	List<Long> queryUidsByProperties(Map<String, Object> properties);

	int countByProperties(Map<String, Object> properties);

	/**
	 * 获取无头像的机器人用户
	 * 
	 * @return
	 */
	List<User> queryNoAvatarRobotUser();

	boolean updateCtrlGid(long uid, long gid);

	Map<Long, Long> queryGids(List<Long> uids);

	/**
	 * 获取用户的密码，必须是机器人
	 * 
	 * @param user
	 * @return
	 */
	String getUserPassword(User user);
}
