package com.nali.spreader.service;

import java.util.List;

import com.nali.spreader.config.BaseRobotGroupContent;
import com.nali.spreader.config.ContentDto;

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
	 * @param contentCount
	 * @return
	 */
	List<Long> findContentIds(BaseRobotGroupContent config, Long uid, Integer contentCount);

	/**
	 * 筛选符合条件内容
	 * 
	 * @param dto
	 * @return
	 */
	List<Long> findContentIdByDto(ContentDto dto);
}
