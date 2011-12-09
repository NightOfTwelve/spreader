package com.nali.spreader.service;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.nali.spreader.config.ContentDto;
import com.nali.spreader.data.Content;

public interface IContentService {
	Content getMatchedContent(Long uid);

	Date getAndTouchLastFetchTime(Long uid);

	void saveContent(Content content);

	Content getContentById(Long contentId);
	
	List<Long> findContentIdByDto(ContentDto dto);
	
	Set<Long> getPostContentIds(Long uid);
	
	void addPostContentId(Long uid, Long contentId);
}
