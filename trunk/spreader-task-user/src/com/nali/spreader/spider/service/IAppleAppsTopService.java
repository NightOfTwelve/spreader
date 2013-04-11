package com.nali.spreader.spider.service;

import java.util.List;

import com.nali.spreader.dto.AppleAppHistoryDto;

/**
 * 苹果App排名
 * 
 * @author xiefei
 * 
 */
public interface IAppleAppsTopService {
	/**
	 * 抓取App排行信息
	 * 
	 * @param genreId
	 * @param popId
	 * @param rows
	 *            爬取的总数目
	 */
	void fetchAppsTopByGenre(int genreId, int popId, int rows);

	/**
	 * 查询当天的排名
	 * 
	 * @param genreId
	 * @param appId
	 * @param popId
	 * @param createDate
	 * @return
	 */
	List<AppleAppHistoryDto> getAppDayTop(int genreId, Long appId, int popId,
			String createDate);

	/**
	 * 查询多天的排名
	 * 
	 * @param genreId
	 * @param appId
	 * @param popId
	 * @param startCreateDate
	 * @param endCreateDate
	 * @return
	 */
	List<AppleAppHistoryDto> getAppDayTop(int genreId, Long appId, int popId,
			String startCreateDate, String endCreateDate);
}
