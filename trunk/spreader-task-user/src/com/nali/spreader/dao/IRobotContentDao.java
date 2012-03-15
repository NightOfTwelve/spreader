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
}
