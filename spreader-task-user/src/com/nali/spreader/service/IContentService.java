package com.nali.spreader.service;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.nali.spreader.config.ContentDto;
import com.nali.spreader.config.PostWeiboConfig;
import com.nali.spreader.data.Content;
import com.nali.spreader.dto.PostWeiboContentDto;

public interface IContentService {
	Content getMatchedContent(Long uid);

	Date getAndTouchLastFetchTime(Long uid);

	void saveContent(Content content);

	Content getContentById(Long contentId);

	List<Long> findContentIdByDto(ContentDto dto);

	Set<Long> getPostContentIds(Long uid);

	void addPostContentId(Long uid, Long contentId);

	Content assignContent(Integer websiteId, Long websiteUid, String entry);

	Content parseUrl(String url);

	/**
	 * 分配一个contentID
	 * 
	 * @param content
	 * @return
	 */
	Long assignContentId(Content content);

	/**
	 * 获取内容ID集合
	 * 
	 * @param dto
	 * @return
	 */
	List<Long> findContentIdByPostContentDto(PostWeiboContentDto dto);

	/**
	 * 根据配置获取内容集合
	 * 
	 * @param cfg
	 * @param allKeywords
	 * @param uid
	 * @return
	 */

	List<Long> findContentIdByConfig(PostWeiboConfig cfg, List<String> allKeywords, Long uid);
}
