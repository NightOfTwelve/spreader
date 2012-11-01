package com.nali.spreader.dao;

import java.util.Collection;
import java.util.List;

public interface IGrouppedUserDao {
	/**
	 * 获取锁
	 * 
	 * @param gid
	 * @return
	 */
	boolean tryLock(Long gid);

	/**
	 * 解除锁
	 * 
	 * @param gid
	 */
	void unLock(Long gid);

	/**
	 * 根据gid批量保存用户id
	 * 
	 * @param gid
	 * @param uids
	 */
	void addUserCollection(Long gid, Collection<Long> uids);

	/**
	 * 根据gid分页查询用户信息
	 * 
	 * @param gid
	 * @param startIndex
	 * @param endIndex
	 * @return
	 */
	List<Long> queryAllGroupUidsByLimit(Long gid, int startIndex, int endIndex);

	/**
	 * 根据gid查询所有的用户ID
	 * 
	 * @param gid
	 * @return
	 */
	List<Long> queryAllGroupUids(Long gid);

	/**
	 * 统计用户分组中user数量
	 * 
	 * @param gid
	 * @return
	 */
	int countGroupUidsSize(Long gid);

	/**
	 * 删除指定分组中的指定用户
	 * 
	 * @param gid
	 * @param uids
	 * @return
	 */
	void deleteGroupUidsByGid(Long gid, List<Long> uids);

	/**
	 * 将临时的数据替换成正式数据，并删除临时数据
	 * 
	 * @param gid
	 */
	void replaceUserList(Long gid);

	void addGroupUsers(Long gid, Long uid);
}