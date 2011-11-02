package com.nali.spreader.service;

import java.util.Date;

import com.nali.spreader.data.Content;

public interface IContentService {
	Content getMatchedContent(Long uid);

	Date getAndTouchLastFetchTime(Long uid);

	void saveContent(Content content);
}
