package com.nali.spreader.dao;

import java.util.List;
import java.util.Set;

import org.springframework.data.redis.core.ZSetOperations.TypedTuple;

import com.nali.spreader.data.Reply;

public interface ISegmenAnalyzerDao {

	/**
	 * 保存某个分词下的相关分词列表ID与分数的sorted set
	 * 
	 * @param contentSegId
	 * @param replySegId
	 * @param score
	 * @return
	 */
	double saveSegmenScore(Long contentSegId, Long replySegId, double score);

	/**
	 * 获取某个内容分词下分数排名前rank的分词
	 * 
	 * @param contentSegId
	 * @return
	 */
	Set<TypedTuple<Object>> getReplySegmenByScore(Long contentSegId, int rank);

	/**
	 * 根据ID查询一条回复记录
	 * 
	 * @param id
	 * @return
	 */
	Reply queryReplyById(Long id);

	/**
	 * 根据回复的内容查询一条记录
	 * 
	 * @param content
	 * @return
	 */
	Reply queryReplyByContent(String content);

	/**
	 * 保存一条reply并返回id
	 * 
	 * @param reply
	 * @return
	 */
	Long insertReply(Reply reply);

	/**
	 * 更新reply，必须提供reply的id
	 * 
	 * @param reply
	 * @return
	 */
	int updateReply(Reply reply);

	List<Reply> query(String content, int atCountGte, int atCountLte,
			int reReplyCountGte, int reReplyCountLte, int topicCountGte,
			int topicCountLte, int contentLengthGte, int contentLengthLte,
			int useCountGte, int useCountLte, int offset, int limit);

	int count(String content, int atCountGte, int atCountLte,
			int reReplyCountGte, int reReplyCountLte, int topicCountGte,
			int topicCountLte, int contentLengthGte, int contentLengthLte,
			int useCountGte, int useCountLte);

	int count(String content);

	List<Reply> query(String content, int offset, int limit);

	/**
	 * 获取最后一次建索引的ID
	 * 
	 * @return
	 */
	Long getLastReplyId();

	/**
	 * 设置最后一次记录的ID
	 * 
	 * @param id
	 */
	void setLastReplyId(Long id);

	/**
	 * 分批查询 ，增量查询
	 * 
	 * @param id
	 * @param offset
	 * @param limit
	 * @return
	 */
	List<Reply> query(Long id, int offset, int limit);

	/**
	 * 分批查询所有的回复
	 * 
	 * @param offset
	 * @param limit
	 * @return
	 */
	List<Reply> query(int offset, int limit);

}
