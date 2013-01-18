package com.nali.spreader.spider.service;

import java.util.List;

import com.nali.spreader.data.KeyValue;

/**
 * 爬取微博信息服务类
 * 
 * @author xiefei
 * 
 */
public interface ICommentsService {
	/**
	 * 获取微博内容和它的评论，需指定页数
	 * 
	 * @param url
	 * @param page
	 * @return
	 */
	KeyValue<String, List<String>> getWeiboAndComments(String url, int page);
}
