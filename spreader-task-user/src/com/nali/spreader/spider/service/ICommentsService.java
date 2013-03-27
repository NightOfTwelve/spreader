package com.nali.spreader.spider.service;

import java.util.List;

import com.nali.spreader.data.KeyValue;
import com.nali.spreader.dto.HotWeiboDto;
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

	/**
	 * 根据热门微博入口的URL及热门标签获取指定页数的微博
	 * 
	 * @param titleUrl
	 * @param title
	 * @param page
	 * @return
	 */
	List<HotWeiboDto> getHotWeiboByTitle(String titleUrl, String title, int page);

	/**
	 * 获取热门微博的所有入口及对应的热门标签 key=热门标签 value=入口URL
	 * 
	 * @return
	 */
	List<KeyValue<String, String>> getHotWeiboEntrance();
}
