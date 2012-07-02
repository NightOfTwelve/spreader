package com.nali.spreader.dao;

import java.util.List;

import com.nali.spreader.data.KeyValue;

/**
 * 
 * @author xiefei
 * 
 */
public interface IRobotContentDao {

	/**
	 * 根据内容和类型获取用户ID
	 * 
	 * @param params
	 * @return
	 */
	List<Long> queryRobotIdByContentAndType(KeyValue<Long, Integer> params);

	/**
	 * 根据UID和类型获取contentID
	 * 
	 * @param params
	 * @return
	 */
	List<Long> queryContentIdByUid(KeyValue<Long, Integer> params);
}
