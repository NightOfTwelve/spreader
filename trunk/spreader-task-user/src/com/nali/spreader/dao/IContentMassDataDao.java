package com.nali.spreader.dao;

import java.util.List;

import com.nali.spreader.config.ContentQueryParamsDto;
import com.nali.spreader.data.Content;
import com.nali.spreader.dto.PostWeiboContentDto;

/**
 * 支持mongodb的ContentDao
 * 
 * @author xiefei
 * 
 */
public interface IContentMassDataDao {
	/**
	 * 根据主键查询实例
	 * 
	 * @param contentId
	 * @return
	 */
	Content selectByPrimaryKey(Long contentId);

	/**
	 * 插入一条新纪录并返回ID
	 * 
	 * @param content
	 * @return
	 */
	Long insertContent(Content content);

	/**
	 * 用于从其它数据库迁移数据，必须设置id
	 * 
	 * @param content
	 * @return
	 */
	Long importContent(Content content);

	/**
	 * 更新WebsiteContentId
	 * 
	 * @param contentId
	 * @param websiteContentId
	 */
	int updateWebsiteContentId(Long contentId, Long websiteContentId);

	/**
	 * 根据唯一索引获取content
	 * 
	 * @param type
	 * @param websiteId
	 * @param websiteUid
	 * @param entry
	 * @return
	 */
	Content selectContentsByUniqueKey(Integer type, Integer websiteId, Long websiteUid, String entry);

	/**
	 * 更新Content，必须分配contentId
	 * 
	 * @param content
	 * @return
	 */
	int updateContent(Content content);

	/**
	 * 查询内容库
	 * 
	 * @param param
	 * @return
	 */
	List<Content> getContentLibrary(ContentQueryParamsDto param);

	/**
	 * 统计content总数
	 * 
	 * @param param
	 * @return
	 */
	int countContentLibraryRows(ContentQueryParamsDto param);

	/**
	 * 保存一个内容与关键字的映射
	 * 
	 * @param contentId
	 * @param keywordId
	 */
	int upsertContentKeyword(Long contentId, Long... keywords);

	/**
	 * 筛选出待发送的微博
	 * 
	 * @param param
	 * @return
	 */
	List<Long> queryPostContents(PostWeiboContentDto param);
}