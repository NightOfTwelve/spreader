package com.nali.spreader.service;

import java.util.Date;
import java.util.List;

import com.nali.spreader.config.Range;
import com.nali.spreader.data.User;
import com.nali.spreader.data.UserTag;
import com.nali.spreader.data.WeiboAppeal;
import com.nali.spreader.model.RobotUser;

public interface IGlobalUserService {

	List<Long> findRelationUserId(Long toUid, Integer attentionType, Boolean isRobot);

	void updateUserTags(Long uid, List<UserTag> tags);

	User getUserById(Long id);
	
	Long getWebsiteUid(Long uid);
	//add by xiefei
	Long registerRobotUser(RobotUser robotUser,User user);

	void removeUser(Long id);
	
	RobotUser exportUser(Long id);

	Long getOrAssignUid(Integer websiteId, Long websiteUid);
	
	User assignUserByWebsiteUid(Integer websiteId, Long websiteUid);

	User findByUniqueKey(Integer websiteId, Long websiteUid);
	
	/**
	 * @return success
	 */
	boolean initWeiboAppeal(Long uid, boolean forceMerge);
	
	void mergeWeiboAppeal(WeiboAppeal weiboAppeal);
	
	void removeWeiboAppeal(Long uid);
	
	List<WeiboAppeal> findUncheckedWeiboAppeal(Date startDate);

	User getDeletedUser(Long uid);
	
	User recoverDeletedUser(Long uid);

	List<WeiboAppeal> findInitedWeiboAppeal(int limit);

	/**
	 * 查询符合条件的用户
	 * 
	 * @param vType
	 * @param fans
	 * @param articles
	 * @return 如果没有任何条件，返回null
	 */
	Long[] findPostContentUids(Integer vType, Range<Long> fans, Range<Long> articles);
	
	/**
	 * 获取用户昵称
	 * 
	 * @param websiteId 网站类型  必选参数
	 * @param websiteUid 网站Uid 必选参数
	 * @return
	 */
	String getNickNameByWebsiteUid(Integer websiteId,Long websiteUid);

	/**
	 * 获取符合关注上限的用户ID
	 * 
	 * @param uids
	 * @param attenLimit
	 * @return
	 */
	List<Long> getAttenLimitUids(List<Long> uids, Long attenLimit);

	/**
	 * 将uid转换成Map<Long,User>
	 * 
	 * @param data
	 * @return
	 */
	List<User> getUserMapByUids(List<Long> data);
	
	List<User> getUsersByIds(List<Long> uids);
}
