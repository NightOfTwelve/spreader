package com.nali.spreader.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.nali.common.pagination.PageResult;
import com.nali.spreader.config.ContentDto;
import com.nali.spreader.config.ContentQueryParamsDto;
import com.nali.spreader.data.Content;
import com.nali.spreader.dto.ContentKeywordInfoDto;
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
	 * 分配一个contentID content == null return null
	 * 
	 * contentType websiteId websiteUid entry 有一项为空 return null
	 * 
	 * @param content
	 * @return
	 */
	Long assignContentId(Content content);

	/**
	 * 分配一个content
	 * 
	 * @param content
	 * @return
	 */
	Content assignContent(Content content);

	/**
	 * 获取内容ID集合
	 * 
	 * @param dto
	 * @return
	 */
	List<Long> findContentIdByPostContentDto(PostWeiboContentDto dto);

	/**
	 * 获取内容集合
	 * 
	 * @param dto
	 * @return
	 */
	List<Map<String, Long>> findContentByPostContentDto(PostWeiboContentDto dto);

	/**
	 * 获取内容的长度
	 * 
	 * @param content
	 * @return
	 */
	int getContentLength(String content);

	/**
	 * 保存一条内容与关键字的映射关系
	 * 
	 * @param contentId
	 * @param keywordId
	 * @return
	 */
	int saveContentKeyword(Long contentId, Long... keywords);

	/**
	 * 查询内容库分页数据
	 * 
	 * @param cqd
	 * @return
	 */
	PageResult<Content> findContentPageResult(ContentQueryParamsDto param);

	/**
	 * 查询文章的关键字
	 * 
	 * @param contentId
	 * @return
	 */
	List<ContentKeywordInfoDto> findKeywordByContentId(Long contentId);

	/**
	 * 获取微博的URL websiteUid，entry 必须不为空，否则返回null
	 * 
	 * @param c
	 * @return
	 */
	String getContentUrl(Content c);

	/**
	 * 获取最后一次爬取的ID
	 * 
	 * @return
	 */
	Long getLastFetchContentId();

	/**
	 * 记录最后一次爬取的ID
	 * 
	 * @param contentId
	 * @return
	 */
	void recordLastFetchContentId(Long contentId);

	/**
	 * 清空记录的ID
	 */
	void clearLastFetchContentId();
}