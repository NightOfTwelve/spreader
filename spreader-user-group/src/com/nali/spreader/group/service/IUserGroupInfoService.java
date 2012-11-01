package com.nali.spreader.group.service;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.nali.common.model.Limit;
import com.nali.common.pagination.PageResult;
import com.nali.spreader.constants.Website;
import com.nali.spreader.group.exception.GroupUserQueryException;
import com.nali.spreader.group.meta.UserGroupType;
import com.nali.spreader.model.UserGroup;

/**
 * <code>IUserGroupService</code> contains all the service about user group. A
 * user group contain number of users groupped by manual or their properties;
 * 
 * @author gavin
 * 
 */
public interface IUserGroupInfoService {

	/**
	 * 内存随机upperCount
	 * 
	 * @param gid
	 * @param batchSize
	 * @param upCount
	 * @return
	 * @throws GroupUserQueryException
	 */
	List<Long> queryMemoryGrouppedUids(long gid, int upCount, Collection<Long> excludeUids);

	/**
	 * 查询某个分组的用户的uids。
	 * 
	 * @param gid
	 * @return 分组迭代器
	 * @throws GroupUserQueryException
	 */
	Iterator<Long> queryGrouppedUserIterator(long gid);

	/**
	 * 根据UID删除某个分组的用户
	 * 
	 * @param gid
	 * @param uids
	 */
	void removeUsers(Long gid, Long... uids);

	/**
	 * 删除一个用户分组
	 * 
	 * @param gid
	 */
	void deleteUserGroup(long gid);

	/**
	 * 根据UID删除某个手动分组的用户
	 * 
	 * @param gid
	 * @param uids
	 */
	void removeManualUsers(long gid, long... uids);

	/**
	 * 添加手动映射的用户
	 * 
	 * @param gid
	 * @param uids
	 */
	void addManualUsers(long gid, long... uids);

	/**
	 * 分页查询某个分组的用户
	 * 
	 * @param gid
	 * @param limit
	 * @return
	 * @throws GroupUserQueryException
	 */
	PageResult<Long> queryGrouppedUsers(Long gid, Limit limit);

	/**
	 * 查询一个分组
	 * 
	 * @param gid
	 * @return
	 */
	UserGroup queryUserGroup(long gid);

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
			UserGroupType userGroupType, int propVal, Date fromModifiedTime, Date toModifiedTime,
			Limit limit);

	/**
	 * 刷新用户分组中的用户
	 * 
	 * @param gid
	 */
	String refreshGroupUsers(Long gid);

	/**
	 * 查询所有的UserGroup，按gtype降序排列
	 * 
	 * @return
	 */
	List<Long> queryAllUserGroup();
}