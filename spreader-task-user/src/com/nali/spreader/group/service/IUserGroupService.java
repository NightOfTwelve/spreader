package com.nali.spreader.group.service;

import java.util.Date;
import java.util.List;

import com.nali.common.model.Limit;
import com.nali.common.pagination.PageResult;
import com.nali.spreader.constants.Website;
import com.nali.spreader.data.UserGroup;
import com.nali.spreader.group.exception.GroupUserQueryException;
import com.nali.spreader.group.exp.PropertyExpressionDTO;
import com.nali.spreader.group.meta.UserGroupType;
import com.nali.spreader.model.GrouppedUser;
import com.nali.spreader.util.DataIterator;

/**
 * <code>IUserGroupService</code> contains all the service about user group. A
 * user group contain number of users groupped by manual or their properties;
 * 
 * @author gavin
 * 
 */
public interface IUserGroupService {

	/**
	 * 查询一个分组
	 * 
	 * @param gid
	 * @return
	 */
	UserGroup queryUserGroup(long gid);

	/**
	 * 删除一个用户分组
	 * 
	 * @param gid
	 */
	void deleteUserGroup(long gid);

	/**
	 * 保存一个用户分组
	 * 
	 * @param userGroup
	 */
	long createGroup(UserGroup userGroup);

	/**
	 * 更新一个用户分组
	 * 
	 * @param userGroup
	 */
	void updateUserGroup(UserGroup userGroup);

	/**
	 * 根据用户属性列表更新用户分组
	 * 
	 * @param gid
	 * @param propertyExpressionDTO
	 */
	void updateUserGroup(long gid, PropertyExpressionDTO propertyExpressionDTO);

	/**
	 * 添加分组中排除的用户
	 * 
	 * @param gid
	 * @param uids
	 */
	void excludeUsers(long gid, long... uids);

	/**
	 * 添加手动映射的用户
	 * 
	 * @param gid
	 * @param uids
	 */
	void addManualUsers(long gid, long... uids);

	/**
	 * 删除已经被手动排除的用户，再次将他们还原回分组
	 * 
	 * @param gid
	 * @param uids
	 */
	void rollbackExcludeUsers(long gid, long... uids);

	/**
	 * 根据UID删除某些手动映射的用户
	 * 
	 * @param gid
	 * @param uids
	 */
	void removeManualUsers(long gid, long... uids);

	/**
	 * 根据UID删除某个分组的用户
	 * 
	 * @param gid
	 * @param uids
	 */
	void removeUsers(long gid, long... uids);

	/**
	 * 分页查询被排除分组的用户
	 * 
	 * @param gid
	 * @param limit
	 * @return
	 */
	PageResult<GrouppedUser> queryExcludeUsers(long gid, Limit limit);

	/**
	 * 查询手动添加用户
	 * 
	 * @param gid
	 * @param limit
	 * @return
	 */
	PageResult<GrouppedUser> queryManualUsers(long gid, Limit limit);

	/**
	 * 查询某个分组的用户的uids。
	 * 
	 * @param gid
	 * @param batchSize
	 * @return 分组迭代器
	 * @throws GroupUserQueryException
	 */
	DataIterator<Long> queryGrouppedUids(long gid, int batchSize)
			throws GroupUserQueryException;

	/**
	 * 分页查询某个分组的用户
	 * 
	 * @param gid
	 * @param limit
	 * @return
	 * @throws GroupUserQueryException
	 */
	PageResult<GrouppedUser> queryGrouppedUsers(long gid, Limit limit)
			throws GroupUserQueryException;

	/**
	 * 分页查询所有满足条件的用户分组
	 * 
	 * @param website
	 * @param gname
	 * @param userGroupType
	 * @param propVal
	 * @param fromModifiedTime
	 * @param toModifiedTime
	 * @return
	 */
	PageResult<UserGroup> queryUserGroups(Website website, String gname,
			UserGroupType userGroupType, int propVal, Date fromModifiedTime,
			Date toModifiedTime, Limit limit);

	List<GrouppedUser> queryGrouppedUsers(long gid, long manualCount,
			long propertyCount, int offset, int limit)
			throws GroupUserQueryException;

	/**
	 * 验证是否重命名
	 * 
	 * @param gname
	 * @return
	 */
	boolean checkUserGroupUniqueByName(String gname);
}