package com.nali.spreader.dao.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.nali.center.service.IIdentityService;
import com.nali.common.util.CollectionUtils;
import com.nali.dal.expression.ExpressionValue;
import com.nali.dal.expression.query.Criteria;
import com.nali.dal.statement.DalTemplate;
import com.nali.dal.statement.SelectBuilder;
import com.nali.spreader.dao.ISegmenAnalyzerDao;
import com.nali.spreader.data.Reply;
import com.nali.spreader.util.bean.BeanProperties;

@Repository
public class SegmenAnalyzerDaoImpl implements ISegmenAnalyzerDao {
	private static Logger logger = Logger
			.getLogger(SegmenAnalyzerDaoImpl.class);
	private static final String CONTENT_SEG_KEY = "spreader_content_segmen_";
	private static final String LAST_REPLY_INDEX_ID = "last_reply_index_id";
	@Autowired
	private DalTemplate dalTemplate;
	@Autowired
	private IIdentityService identityService;
	private static final String APP_NAME = "spreader.content.reply";
	private BeanProperties bean = new BeanProperties(Reply.class);
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	public double saveSegmenScore(Long contentSegId, Long replySegId,
			double score) {
		String key = getSegmenSetKey(contentSegId);
		return redisTemplate.opsForZSet()
				.incrementScore(key, replySegId, score);
	}

	private String getSegmenSetKey(Long contentSegId) {
		return CONTENT_SEG_KEY + contentSegId;
	}

	@SuppressWarnings("unchecked")
	public Reply queryReplyById(Long id) {
		if (id == null) {
			return null;
		}
		Map<String, Object> dataMap = dalTemplate.selectForObject(
				"dal.selectReply", new ExpressionValue<Criteria>("id",
						Criteria.eq, id));
		return bean.convertBean(dataMap);
	}

	@SuppressWarnings("unchecked")
	public Reply queryReplyByContent(String content) {
		if (StringUtils.isBlank(content)) {
			throw new IllegalArgumentException(" reply content is blank ");
		}
		Map<String, Object> dataMap = dalTemplate.selectForObject(
				"dal.selectReply", new ExpressionValue<Criteria>("content",
						Criteria.eq, content));
		return bean.convertBean(dataMap);
	}

	public Long insertReply(Reply reply) {
		Assert.notNull(reply, "reply is null");
		Long id = identityService.getNextId(APP_NAME);
		reply.setId(id);
		Map<String, Object> data = bean.convertMap(reply);
		try {
			dalTemplate.insert("dal.insertReply", data);
			return reply.getId();
		} catch (Exception e) {
			logger.error("insert failed,reply:" + reply, e);
		}
		return null;
	}

	public int updateReply(Reply reply) {
		Assert.notNull(reply, "reply is null");
		Long id = reply.getId();
		Assert.notNull(id, "replyId is null");
		Map<String, Object> m = bean.convertMap(reply);
		int rows = dalTemplate.upsert("dal.upsertReply", m);
		return rows;
	}

	public List<Reply> query(String content, int atCountGte, int atCountLte,
			int reReplyCountGte, int reReplyCountLte, int topicCountGte,
			int topicCountLte, int contentLengthGte, int contentLengthLte,
			int useCountGte, int useCountLte, int offset, int limit) {
		SelectBuilder builder = dalTemplate.startFor("dal.selectReply");
		if (StringUtils.isNotBlank(content)) {
			builder.regexMatch("content", "^.*" + content + ".*$");
		}
		builder.cond("atCount", Criteria.gte, atCountGte);
		builder.cond("atCount", Criteria.lte, atCountLte);
		builder.cond("reReplyCount", Criteria.gte, reReplyCountGte);
		builder.cond("reReplyCount", Criteria.lte, reReplyCountLte);
		builder.cond("topicCount", Criteria.gte, topicCountGte);
		builder.cond("topicCount", Criteria.lte, topicCountLte);
		builder.cond("contentLength", Criteria.gte, contentLengthGte);
		builder.cond("contentLength", Criteria.lte, contentLengthLte);
		builder.cond("useCount", Criteria.gte, useCountGte);
		builder.cond("useCount", Criteria.lte, useCountLte);
		List<Map<String, Object>> tmpList = builder.select(offset, limit);
		return getReplyList(tmpList);
	}

	public List<Reply> query(String content, int offset, int limit) {
		SelectBuilder builder = dalTemplate.startFor("dal.selectReply");
		if (StringUtils.isBlank(content)) {
			return new ArrayList<Reply>();
		}
		builder.regexMatch("content", "^.*" + content + ".*$");
		List<Map<String, Object>> tmpList = builder.select(offset, limit);
		return getReplyList(tmpList);
	}

	private List<Reply> getReplyList(List<Map<String, Object>> tmpList) {
		List<Reply> result = new ArrayList<Reply>();
		if (!CollectionUtils.isEmpty(tmpList)) {
			for (Map<String, Object> m : tmpList) {
				Reply r = bean.convertBean(m);
				result.add(r);
			}
		}
		return result;
	}

	public int count(String content, int atCountGte, int atCountLte,
			int reReplyCountGte, int reReplyCountLte, int topicCountGte,
			int topicCountLte, int contentLengthGte, int contentLengthLte,
			int useCountGte, int useCountLte) {
		SelectBuilder builder = dalTemplate.startFor("dal.selectReply");
		if (StringUtils.isNotBlank(content)) {
			builder.regexMatch("content", "^.*" + content + ".*$");
		}
		builder.cond("atCount", Criteria.gte, atCountGte);
		builder.cond("atCount", Criteria.lte, atCountLte);
		builder.cond("reReplyCount", Criteria.gte, reReplyCountGte);
		builder.cond("reReplyCount", Criteria.lte, reReplyCountLte);
		builder.cond("topicCount", Criteria.gte, topicCountGte);
		builder.cond("topicCount", Criteria.lte, topicCountLte);
		builder.cond("contentLength", Criteria.gte, contentLengthGte);
		builder.cond("contentLength", Criteria.lte, contentLengthLte);
		builder.cond("useCount", Criteria.gte, useCountGte);
		builder.cond("useCount", Criteria.lte, useCountLte);
		return (int) builder.count();
	}

	public int count(String content) {
		SelectBuilder builder = dalTemplate.startFor("dal.selectReply");
		if (StringUtils.isBlank(content)) {
			return 0;
		}
		builder.regexMatch("content", "^.*" + content + ".*$");
		return (int) builder.count();
	}

	@Override
	public Long getLastReplyId() {
		Long id = (Long) redisTemplate.opsForValue().get(LAST_REPLY_INDEX_ID);
		if (id == null) {
			return 0L;
		}
		return id;
	}

	@Override
	public List<Reply> query(Long id, int offset, int limit) {
		SelectBuilder builder = dalTemplate.startFor("dal.selectReply");
		builder.cond("id", Criteria.gt, id);
		List<Map<String, Object>> tmpList = builder.select(offset, limit);
		return getReplyList(tmpList);
	}

	@Override
	public Set<TypedTuple<Object>> getReplySegmenByScore(Long contentSegId,
			int rank) {
		Set<TypedTuple<Object>> set = new HashSet<TypedTuple<Object>>();
		if (rank <= 0) {
			rank = 5;
		}
		if (contentSegId != null) {
			String segKey = getSegmenSetKey(contentSegId);
			set = redisTemplate.opsForZSet().reverseRangeWithScores(segKey, 0,
					rank - 1);
		}
		return set;
	}

	@Override
	public void setLastReplyId(Long id) {
		redisTemplate.opsForValue().set(LAST_REPLY_INDEX_ID, id);
	}

	@Override
	public List<Reply> query(int offset, int limit) {
		SelectBuilder builder = dalTemplate.startFor("dal.selectReply");
		List<Map<String, Object>> tmpList = builder.select(offset, limit);
		return getReplyList(tmpList);
	}
}
