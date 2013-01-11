package com.nali.spreader.service;

import java.util.List;

import com.nali.spreader.config.BaseRobotGroupContent;
import com.nali.spreader.dto.PostWeiboContentDto;

/**
 * 转发，回复相关服务
 * 
 * @author xiefei
 * 
 */
public interface IRobotGroupContentService {
	/**
	 * 随机获取符合条件的内容ID
	 * 
	 * @param config
	 * @param uid
	 * @return
	 */
	PostWeiboContentDto getPostContentParams(BaseRobotGroupContent config, Long uid);

	/**
	 * 随机获取内容
	 * 
	 * @param contentIds
	 * @param randomCount
	 * @return
	 */
	List<Long> getRandomContent(List<Long> contentIds, Integer randomCount);
}
