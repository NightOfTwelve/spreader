package com.nali.spreader.dao.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.nali.center.service.IIdentityService;
import com.nali.common.model.Limit;
import com.nali.common.util.CollectionUtils;
import com.nali.dal.expression.ExpressionValue;
import com.nali.dal.expression.query.Criteria;
import com.nali.dal.statement.DalTemplate;
import com.nali.spreader.config.ContentQueryParamsDto;
import com.nali.spreader.config.Range;
import com.nali.spreader.dao.IContentMassDataDao;
import com.nali.spreader.dao.IKeywordDao;
import com.nali.spreader.dao.IUserDao;
import com.nali.spreader.data.Content;
import com.nali.spreader.dto.PostWeiboContentDto;
import com.nali.spreader.util.bean.BeanProperties;

@Repository
@SuppressWarnings({"unchecked", "rawtypes"})
public class ContentMassDataDaoImpl implements IContentMassDataDao {
	@Autowired
	private DalTemplate dalTemplate;
	@Autowired
	private IKeywordDao keywordDao;
	@Autowired
	private IUserDao userDao;
	@Autowired
	private IIdentityService identityService;
	private static final String APP_NAME = "spreader.content";
	private static final String SPIDER_LAST_CONTENT_ID = "spreader_user_spider_last_content_id";
	private static Map<String, Long[]> CONDITIONS = new ConcurrentHashMap<String, Long[]>();
	private BeanProperties bean = new BeanProperties(null, Content.class,
			"nickName", "webSiteName", "typeName", "tags", "url", "refContent",
			"shard", "limit");
	private static final Logger LOGGER = Logger
			.getLogger(ContentMassDataDaoImpl.class);
	@Autowired
	private RedisTemplate redisTemplate;

	@Override
	public Content selectByPrimaryKey(Long contentId) {
		Map<String, Object> dataMap = dalTemplate.selectForObject(
				"dal.selectContent", new ExpressionValue<Criteria>("id",
						Criteria.eq, contentId));
		return bean.convertBean(dataMap);
	}

	@Override
	public Long insertContent(Content content) {
		Assert.notNull(content, "content is null");
		Long cid = identityService.getNextId(APP_NAME);
		content.setId(cid);
		Long[] keywords = content.getKeywords();
		Map<String, Object> data = bean.convertMap(content);
		try {
			dalTemplate.insert("dal.insertContent", data);
			// 关键字不为空的情况下，更新keywords
			if (ArrayUtils.isNotEmpty(keywords)) {
				this.upsertContentKeyword(cid, keywords);
			}
			return content.getId();
		} catch (Exception e) {
			LOGGER.error("insert failed,content:" + content, e);
		}
		return null;
	}

	@Override
	public int updateWebsiteContentId(Long contentId, Long websiteContentId) {
		Map<String, Object> map = CollectionUtils.newHashMap(2);
		map.put("websiteContentId", websiteContentId);
		map.put("id", contentId);
		return dalTemplate.upsert("dal.upsertContentWebsiteContentId", map);
	}

	@Override
	public Content selectContentsByUniqueKey(Integer type, Integer websiteId,
			Long websiteUid, String entry) {
		Assert.notNull(type, "type is null");
		Assert.notNull(websiteId, "websiteId is null");
		Assert.notNull(websiteUid, "websiteUid is null");
		Assert.notNull(entry, "entry is null");
		Map<String, Object> dataMap = dalTemplate.selectForObject(
				"dal.selectContent", new ExpressionValue<Criteria>("type",
						Criteria.eq, type), new ExpressionValue<Criteria>(
						"websiteId", Criteria.eq, websiteId),
				new ExpressionValue<Criteria>("websiteUid", Criteria.eq,
						websiteUid), new ExpressionValue<Criteria>("entry",
						Criteria.eq, entry));
		return bean.convertBean(dataMap);
	}

	@Override
	public int updateContent(Content content) {
		Assert.notNull(content, "content is null");
		Long cid = content.getId();
		Assert.notNull(cid, "contentId is null");
		Map<String, Object> m = bean.convertMap(content);
		int rows = dalTemplate.upsert("dal.upsertContent", m);
		// 对keywords做特殊处理
		if (m.containsKey("keywords")) {//TODO content.getKeywords()
			Long[] keywords = (Long[]) m.get("keywords");
			if (ArrayUtils.isNotEmpty(keywords)) {
				return this.upsertContentKeyword(cid, keywords);
			}
		}
		return rows;
	}

	@Override
	public List<Content> getContentLibrary(ContentQueryParamsDto param) {
		Assert.notNull(param, "param is null");
		Limit limit = param.getLit();
		List<Content> result = new ArrayList<Content>();
		List<ExpressionValue<Criteria>> criteriaList = contentLibCondition(param);
		ExpressionValue<Criteria>[] cTmp = new ExpressionValue[criteriaList
				.size()];
		List<Map<String, Object>> tmp = dalTemplate.select("dal.selectContent",
				criteriaList.toArray(cTmp), limit.offset, limit.maxRows);
		if (!CollectionUtils.isEmpty(tmp)) {
			for (Map<String, Object> m : tmp) {
				Content c = bean.convertBean(m);
				result.add(c);
			}
		}
		return result;
	}

	/**
	 * 内容库查询通用查询条件
	 * 
	 * @param param
	 * @return
	 */
	private List<ExpressionValue<Criteria>> contentLibCondition(
			ContentQueryParamsDto param) {
		List<ExpressionValue<Criteria>> criteriaList = new ArrayList<ExpressionValue<Criteria>>();
		String categoryName = param.getCategoryName();
		String keywordName = param.getKeyword();
		Date sPubDate = param.getsPubDate();
		Date ePubDate = param.getePubDate();
		Date sSyncDate = param.getsSyncDate();
		Date eSyncDate = param.geteSyncDate();
		String userName = param.getUserName();
		Long[] keywords = getKeywordIdArrays(categoryName, keywordName);
		Long websiteUid = param.getWebsiteUid();
		Long lastContentId = param.getLastId();
		if (lastContentId != null) {
			criteriaList.add(new ExpressionValue<Criteria>("id", Criteria.gt,
					lastContentId));
		}
		if (keywords != null) {
			criteriaList.add(new ExpressionValue<Criteria>("keywords",
					Criteria.in, keywords));
		}
		if (sPubDate != null) {
			criteriaList.add(new ExpressionValue<Criteria>("pubDate",
					Criteria.gte, sPubDate));
		}
		if (ePubDate != null) {
			criteriaList.add(new ExpressionValue<Criteria>("pubDate",
					Criteria.lte, ePubDate));
		}
		if (sSyncDate != null) {
			criteriaList.add(new ExpressionValue<Criteria>("syncDate",
					Criteria.gte, sSyncDate));
		}
		if (eSyncDate != null) {
			criteriaList.add(new ExpressionValue<Criteria>("syncDate",
					Criteria.lte, eSyncDate));
		}
		if (StringUtils.isNotEmpty(userName)) {
			Long[] users = this.getUidArrays(userName);
			criteriaList.add(new ExpressionValue<Criteria>("uid", Criteria.in,
					users));
		}
		if (websiteUid != null) {
			criteriaList.add(new ExpressionValue<Criteria>("websiteUid",
					Criteria.eq, websiteUid));
		}
		return criteriaList;
	}

	/**
	 * 构建一个KeywordId的数组用于查询
	 * 
	 * @param category
	 * @return
	 */
	private Long[] getKeywordIdArrays(String category, String keyword) {
		List<Long> allKeyword = new ArrayList<Long>();
		List<Long> cgKeyword = this.keywordDao.getKeywordIdByCategory(category);
		List<Long> keywords = this.keywordDao.getKeywordIdByName(keyword);
		if (cgKeyword == null && keywords == null) {
			return null;
		}
		if (cgKeyword != null) {
			allKeyword.addAll(cgKeyword);
		}
		if (keywords != null) {
			allKeyword.addAll(keywords);
		}
		Long[] array = new Long[allKeyword.size()];
		return allKeyword.toArray(array);
	}

	/**
	 * 
	 * @param nickName
	 * @return
	 */
	private Long[] getUidArrays(String nickName) {
		if (CONDITIONS.containsKey(nickName)) {
			return CONDITIONS.get(nickName);
		}
		List<Long> uidList = this.userDao.getUidByNickName(nickName);

		Long[] array = new Long[uidList.size()];
		CONDITIONS.put(nickName, uidList.toArray(array));
		return array;
	}

	@Override
	public int upsertContentKeyword(Long contentId, Long... keywords) {
		Assert.notNull(contentId, "contentId is null");
		int rows = 0;
		if (ArrayUtils.isEmpty(keywords)) {
			return rows;
		}
		for (Long keyword : keywords) {
			Map<String, Object> map = CollectionUtils.newHashMap(2);
			map.put("keywords", keyword);
			map.put("id", contentId);
			dalTemplate.upsert("dal.addKeywordInContent", map);
			rows++;
		}
		return rows;
	}

	@Override
	public List<Long> queryPostContentIds(PostWeiboContentDto param) {
		Assert.notNull(param, "PostWeiboContentDto is null");
		List<ExpressionValue<Criteria>> criteriaList = new ArrayList<ExpressionValue<Criteria>>();
		Long[] keywords = param.getKeywords();
		Long[] uids = param.getUids();
		Boolean isPic = param.getIsPic();
		Boolean isAudio = param.getIsAudio();
		Boolean isVideo = param.getIsVideo();
		Range<Integer> atCount = param.getAtCount();
		Range<Integer> contentLength = param.getContentLength();
		Range<Date> pubDate = param.getPubDate();
		Range<Integer> refCount = param.getRefCount();
		Range<Integer> replyCount = param.getReplyCount();
		if (ArrayUtils.isNotEmpty(keywords)) {
			criteriaList.add(new ExpressionValue<Criteria>("keywords",
					Criteria.in, keywords));
		}
		if (uids != null) {
			criteriaList.add(new ExpressionValue<Criteria>("uid", Criteria.in,
					uids));
		}
		if (Boolean.TRUE.equals(isPic)) {
			criteriaList.add(new ExpressionValue<Criteria>("picUrl",
					Criteria.ne, null));
		}
		if (Boolean.TRUE.equals(isAudio)) {
			criteriaList.add(new ExpressionValue<Criteria>("audioUrl",
					Criteria.ne, null));
		}
		if (Boolean.TRUE.equals(isVideo)) {
			criteriaList.add(new ExpressionValue<Criteria>("videoUrl",
					Criteria.ne, null));
		}
		if (atCount != null) {
			if (atCount.getGte() != null) {
				criteriaList.add(new ExpressionValue<Criteria>("atCount",
						Criteria.gte, atCount.getGte()));
			}
			if (atCount.getLte() != null) {
				criteriaList.add(new ExpressionValue<Criteria>("atCount",
						Criteria.lte, atCount.getLte()));
			}
		}
		if (contentLength != null) {
			if (contentLength.getGte() != null) {
				criteriaList.add(new ExpressionValue<Criteria>("contentLength",
						Criteria.gte, contentLength.getGte()));
			}
			if (contentLength.getLte() != null) {
				criteriaList.add(new ExpressionValue<Criteria>("contentLength",
						Criteria.lte, contentLength.getLte()));
			}
		}
		if (pubDate != null) {
			if (pubDate.getGte() != null) {
				criteriaList.add(new ExpressionValue<Criteria>("pubDate",
						Criteria.gte, pubDate.getGte()));
			}
		}
		if (refCount != null) {
			if (refCount.getGte() != null) {
				criteriaList.add(new ExpressionValue<Criteria>("refCount",
						Criteria.gte, refCount.getGte()));
			}
			if (refCount.getLte() != null) {
				criteriaList.add(new ExpressionValue<Criteria>("refCount",
						Criteria.lte, refCount.getLte()));
			}
		}
		if (replyCount != null) {
			if (replyCount.getGte() != null) {
				criteriaList.add(new ExpressionValue<Criteria>("replyCount",
						Criteria.gte, replyCount.getGte()));
			}
			if (replyCount.getLte() != null) {
				criteriaList.add(new ExpressionValue<Criteria>("replyCount",
						Criteria.lte, replyCount.getLte()));
			}
		}
		ExpressionValue[] tmp = new ExpressionValue[criteriaList.size()];
		List<Map<String, Object>> tmpList = this.dalTemplate.select(
				"dal.selectContentId", criteriaList.toArray(tmp));
		List<Long> result = new ArrayList<Long>();
		if (!CollectionUtils.isEmpty(tmpList)) {
			for (Map<String, Object> m : tmpList) {
				Long cid = (Long) m.get("id");
				result.add(cid);
			}
		}
		return result;
	}

	@Override
	public int countContentLibraryRows(ContentQueryParamsDto param) {
		Assert.notNull(param, "param is null");
		List<ExpressionValue<Criteria>> criteriaList = contentLibCondition(param);
		ExpressionValue<Criteria>[] cTmp = new ExpressionValue[criteriaList
				.size()];
		Long tmp = dalTemplate.count("dal.selectContent",
				criteriaList.toArray(cTmp));
		return tmp.intValue();
	}

	@Override
	public Long importContent(Content content) {
		Assert.notNull(content, "content is null");
		Long cid = content.getId();
		Assert.notNull(cid, "contentId is null");
		Long[] keywords = content.getKeywords();
		Map<String, Object> data = bean.convertMap(content);
		try {
			dalTemplate.insert("dal.insertContent", data);
			// 关键字不为空的情况下，更新keywords
			if (ArrayUtils.isNotEmpty(keywords)) {
				this.upsertContentKeyword(cid, keywords);
			}
			return content.getId();
		} catch (Exception e) {
			LOGGER.error("insert failed,content:" + content, e);
		}
		return null;
	}

	@Override
	public List<Long> selectContentKeywords(Long contentId) {
		Assert.notNull(contentId, "contentId is null");
		Map<String, Object> dataMap = dalTemplate.selectForObject(
				"dal.selectContentKeywords", new ExpressionValue<Criteria>(
						"id", Criteria.eq, contentId));
		if (dataMap != null) {
			if (dataMap.containsKey("keywords")) {
				List<Long> keywords = (List<Long>) dataMap.get("keywords");
				return keywords;
			}
		}
		return Collections.emptyList();
	}

	@Override
	public List<Map<String, Long>> queryPostContents(PostWeiboContentDto param) {
		Assert.notNull(param, "PostWeiboContentDto is null");
		List<ExpressionValue<Criteria>> criteriaList = new ArrayList<ExpressionValue<Criteria>>();
		Long[] keywords = param.getKeywords();
		Long[] uids = param.getUids();
		Boolean isPic = param.getIsPic();
		Boolean isAudio = param.getIsAudio();
		Boolean isVideo = param.getIsVideo();
		Range<Integer> atCount = param.getAtCount();
		Range<Integer> contentLength = param.getContentLength();
		Range<Date> pubDate = param.getPubDate();
		Range<Integer> refCount = param.getRefCount();
		Range<Integer> replyCount = param.getReplyCount();
		if (ArrayUtils.isNotEmpty(keywords)) {
			criteriaList.add(new ExpressionValue<Criteria>("keywords",
					Criteria.in, keywords));
		}
		if (uids != null) {
			criteriaList.add(new ExpressionValue<Criteria>("uid", Criteria.in,
					uids));
		}
		if (Boolean.TRUE.equals(isPic)) {
			criteriaList.add(new ExpressionValue<Criteria>("picUrl",
					Criteria.ne, null));
		}
		if (Boolean.TRUE.equals(isAudio)) {
			criteriaList.add(new ExpressionValue<Criteria>("audioUrl",
					Criteria.ne, null));
		}
		if (Boolean.TRUE.equals(isVideo)) {
			criteriaList.add(new ExpressionValue<Criteria>("videoUrl",
					Criteria.ne, null));
		}
		if (atCount != null) {
			if (atCount.getGte() != null) {
				criteriaList.add(new ExpressionValue<Criteria>("atCount",
						Criteria.gte, atCount.getGte()));
			}
			if (atCount.getLte() != null) {
				criteriaList.add(new ExpressionValue<Criteria>("atCount",
						Criteria.lte, atCount.getLte()));
			}
		}
		if (contentLength != null) {
			if (contentLength.getGte() != null) {
				criteriaList.add(new ExpressionValue<Criteria>("contentLength",
						Criteria.gte, contentLength.getGte()));
			}
			if (contentLength.getLte() != null) {
				criteriaList.add(new ExpressionValue<Criteria>("contentLength",
						Criteria.lte, contentLength.getLte()));
			}
		}
		if (pubDate != null) {
			if (pubDate.getGte() != null) {
				criteriaList.add(new ExpressionValue<Criteria>("pubDate",
						Criteria.gte, pubDate.getGte()));
			}
		}
		if (refCount != null) {
			if (refCount.getGte() != null) {
				criteriaList.add(new ExpressionValue<Criteria>("refCount",
						Criteria.gte, refCount.getGte()));
			}
			if (refCount.getLte() != null) {
				criteriaList.add(new ExpressionValue<Criteria>("refCount",
						Criteria.lte, refCount.getLte()));
			}
		}
		if (replyCount != null) {
			if (replyCount.getGte() != null) {
				criteriaList.add(new ExpressionValue<Criteria>("replyCount",
						Criteria.gte, replyCount.getGte()));
			}
			if (replyCount.getLte() != null) {
				criteriaList.add(new ExpressionValue<Criteria>("replyCount",
						Criteria.lte, replyCount.getLte()));
			}
		}
		ExpressionValue[] tmp = new ExpressionValue[criteriaList.size()];
		List<Map<String, Object>> tmpList = this.dalTemplate.select(
				"dal.selectCidAndUidOrderByUid", criteriaList.toArray(tmp));
		List<Map<String, Long>> result = new ArrayList<Map<String, Long>>();
		if (!CollectionUtils.isEmpty(tmpList)) {
			for (Map<String, Object> m : tmpList) {
				Map<String, Long> map = CollectionUtils.newHashMap(2);
				map.put("contentId", (Long) m.get("id"));
				map.put("uid", (Long) m.get("uid"));
				result.add(map);
			}
		}
		return result;
	}

	@Override
	public Long getSpiderContentId() {
		return (Long) redisTemplate.opsForValue().get(SPIDER_LAST_CONTENT_ID);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setSpiderContentId(Long id) {
		Assert.notNull(id, " contentId is null");
		redisTemplate.opsForValue().getAndSet(SPIDER_LAST_CONTENT_ID, id);
	}
}