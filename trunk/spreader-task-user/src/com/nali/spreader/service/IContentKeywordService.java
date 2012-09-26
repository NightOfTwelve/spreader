package com.nali.spreader.service;

/**
 * 内容关键字关联表相关服务
 * 
 * @author xiefei
 * 
 */
public interface IContentKeywordService {

	/**
	 * 获取一个ContentKeywordId
	 * 
	 * @param contentId
	 * @param keywordId
	 * @return
	 */
	// TODO
	Long getOrAssignContentKeywordId(Long contentId, Long keywordId);

}
