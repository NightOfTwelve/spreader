package com.nali.spreader.remote;

import java.util.List;
import java.util.Set;

public interface ISegmenAnalyzerService {
	/**
	 * 根据分析器获取内容的分词结果
	 * 
	 * @param content
	 * @return
	 */
	Set<String> getContentSegmen(String content);

	/**
	 * 获取一个分词ID
	 * 
	 * @param segmen
	 * @return
	 */
	Long assignContentSegmenId(String segmen);

	/**
	 * 对reply索引 以后优化成增量索引
	 * 
	 * @param lastId
	 */
	void createReplyIndex();

	boolean isLock();

	void unLock();

	/**
	 * 保存一条回复记录并返回ID
	 * 
	 * @param reply
	 * @return
	 */
	Long assignReplayId(String reply);

	/**
	 * 建立索引
	 * 
	 * @param weibo
	 * @param comments
	 */
	void execuAnalysisSegmen(String weibo, List<String> comments);

	/**
	 * 分析微博
	 * 
	 * @param content
	 * @param title
	 */
	void analysisHotWeiboSegmen(String content, String title);

	/**
	 * 根据微博匹配最相似的回复内容
	 * 
	 * @param weibo
	 * @return
	 */
	String getWeiboSearchKeywords(String weibo);
}
