package com.nali.spreader.service;

import java.util.List;

import com.nali.spreader.model.RobotContent;

/**
 * IRobotContentService<br>&nbsp;
 * 
 * @author sam Created on 2012-3-14
 */
public interface IRobotContentService {
	/**
	 * 生成或更新status，并且补全其他字段
	 * @see RobotContent#STATUS_CREATE
	 * @see RobotContent#STATUS_DONE
	 */
	void save(Long robotId, Long contentId, Integer type, Integer status);
	
	/**
	 * 根据帖子id查找相关机器人id，type传空表示类型不限
	 * @see RobotContent#TYPE_POST
	 * @see RobotContent#TYPE_FORWARD
	 * @see RobotContent#TYPE_REPLY
	 */
	List<Long> findRelatedRobotId(Long contentId, Integer type);
}
