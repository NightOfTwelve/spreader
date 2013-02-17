package com.nali.spreader.spider.service;

import com.nali.spreader.dto.WeiboAndComments;

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
	WeiboAndComments getWeiboAndComments(String url, int page);

	/**
	 * 设置cookies
	 * 
	 * @param cookies
	 */
	void settingCookies(String cookies);
}
