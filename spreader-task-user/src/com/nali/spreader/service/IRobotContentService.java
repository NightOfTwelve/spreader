package com.nali.spreader.service;

import java.util.List;

import com.nali.spreader.model.RobotContent;

/**
 * IRobotContentService<br>
 * &nbsp;
 * 
 * @author sam Created on 2012-3-14
 */
public interface IRobotContentService {
	/**
	 * 生成或更新status，并且补全其他字段
	 * 
	 * @see RobotContent#TYPE_POST
	 * @see RobotContent#TYPE_FORWARD
	 * @see RobotContent#TYPE_REPLY
	 */
	void save(Long robotId, Long contentId, Integer type);

	/**
	 * 根据帖子id查找相关机器人id，type传空表示类型不限
	 * 
	 * @see RobotContent#TYPE_POST
	 * @see RobotContent#TYPE_FORWARD
	 * @see RobotContent#TYPE_REPLY
	 */
	List<Long> findRelatedRobotId(Long contentId, Integer type);

	/**
	 * 获取机器人已发的内容ID
	 * 
	 * @param uid
	 * @param type
	 * @return
	 */
	List<Long> findRelatedContentId(Long uid, Integer type);
}
