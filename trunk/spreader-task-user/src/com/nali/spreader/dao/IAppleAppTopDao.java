package com.nali.spreader.dao;

import java.util.Date;
import java.util.List;

import com.nali.spreader.data.AppleAppCurrentTop;
import com.nali.spreader.data.AppleAppHistoryTop;
import com.nali.spreader.data.AppleAppInfo;

/**
 * app排行榜dao
 * 
 * @author xiefei
 * 
 */
public interface IAppleAppTopDao {
	/**
	 * 根据AppId获取App基本信息
	 * 
	 * @param appId
	 * @return
	 */
	AppleAppInfo getAppInfoByAppId(Long appId);

	/**
	 * 根据分类和AppId查询App历史排名信息
	 * 
	 * @param genreId
	 * @param appId
	 * @return
	 */
	AppleAppHistoryTop getHistoryTopByGenreAndId(int genreId, Long appId,
			String createDate, int popId);

	List<AppleAppHistoryTop> getHistoryTopByGenreAndId(int genreId, Long appId,
			String startCreateDate, String endCreateDate, int popId);

	/**
	 * 根据排名和分类查找当前排名信息
	 * 
	 * @param genreId
	 * @param rank
	 * @return
	 */
	AppleAppCurrentTop getCurrentTopByGenreAndRank(int genreId, int rank,
			int popId);

	/**
	 * 保存或更新APP历史排名
	 * 
	 * @param appId
	 * @param genreId
	 * @param createDate
	 * @param rankTime
	 * @param rank
	 * @return
	 */
	int upsertHistoryTop(Long appId, int genreId, String createDate,
			Date rankTime, int rank, int popId);

	/**
	 * 保存或更新APP信息
	 * 
	 * @param appId
	 * @param appName
	 * @param url
	 * @param artworkUrl
	 * @param genre
	 * @param genreId
	 * @return
	 */
	int upsertAppInfo(Long appId, String appName, String url,
			String artworkUrl, String genre, int genreId, int popId);

	/**
	 * 保存或更新实时排行
	 * 
	 * @param appId
	 * @param genre
	 * @param genreId
	 * @param ranking
	 * @return
	 */
	int upsertCurrentTop(Long appId, String genre, int genreId, int ranking,
			int popId);

}
