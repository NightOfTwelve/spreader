package com.nali.spreader.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.nali.common.util.CollectionUtils;
import com.nali.dal.expression.ExpressionValue;
import com.nali.dal.expression.query.Criteria;
import com.nali.dal.statement.DalTemplate;
import com.nali.dal.statement.SelectBuilder;
import com.nali.spreader.dao.IAppleAppTopDao;
import com.nali.spreader.data.AppleAppCurrentTop;
import com.nali.spreader.data.AppleAppHistoryTop;
import com.nali.spreader.data.AppleAppInfo;
import com.nali.spreader.util.bean.BeanProperties;

@Repository
public class AppleAppTopDaoImpl implements IAppleAppTopDao {
	@Autowired
	private DalTemplate dalTemplate;
	private BeanProperties appInfoBean = new BeanProperties(AppleAppInfo.class);
	private BeanProperties currTopBean = new BeanProperties(
			AppleAppCurrentTop.class);
	private BeanProperties hisTopBean = new BeanProperties(
			AppleAppHistoryTop.class);

	@Override
	public AppleAppInfo getAppInfoByAppId(Long appId) {
		@SuppressWarnings("unchecked")
		Map<String, Object> dataMap = dalTemplate.selectForObject(
				"dal.appleAppInfo.select", new ExpressionValue<Criteria>(
						"appId", Criteria.eq, appId));
		return appInfoBean.convertBean(dataMap);
	}

	@Override
	public AppleAppHistoryTop getHistoryTopByGenreAndId(int genreId,
			Long appId, String createDate, int popId) {
		@SuppressWarnings("unchecked")
		Map<String, Object> dataMap = dalTemplate.selectForObject(
				"dal.appleAppHistoryTop.select", new ExpressionValue<Criteria>(
						"genreId", Criteria.eq, genreId),
				new ExpressionValue<Criteria>("appId", Criteria.eq, appId),
				new ExpressionValue<Criteria>("createDate", Criteria.eq,
						createDate), new ExpressionValue<Criteria>("popId",
						Criteria.eq, popId));
		return hisTopBean.convertBean(dataMap);
	}

	@Override
	public AppleAppCurrentTop getCurrentTopByGenreAndRank(int genreId,
			int rank, int popId) {
		@SuppressWarnings("unchecked")
		Map<String, Object> dataMap = dalTemplate.selectForObject(
				"dal.appleAppCurrentTop.select", new ExpressionValue<Criteria>(
						"genreId", Criteria.eq, genreId),
				new ExpressionValue<Criteria>("ranking", Criteria.eq, rank),
				new ExpressionValue<Criteria>("popId", Criteria.eq, popId));
		return currTopBean.convertBean(dataMap);
	}

	@Override
	public int upsertHistoryTop(Long appId, int genreId, String createDate,
			Date rankTime, int rank, int popId) {
		Assert.notNull(appId, " appId is null");
		if (StringUtils.isBlank(createDate)) {
			throw new IllegalArgumentException(" createDate is black");
		}
		Map<String, Object> map = CollectionUtils.newHashMap(5);
		map.put("rankCount", rank);
		map.put("time", rankTime);
		map.put("rank", rank);
		map.put("appId", appId);
		map.put("genreId", genreId);
		map.put("createDate", createDate);
		map.put("popId", popId);
		return dalTemplate.upsert("dal.appleAppHistoryTop.upsert", map);
	}

	@Override
	public int upsertAppInfo(Long appId, String appName, String url,
			String artworkUrl, String genre, int genreId, int popId) {
		Assert.notNull(appId, " appId is null");
		Map<String, Object> map = CollectionUtils.newHashMap(6);
		map.put("appId", appId);
		map.put("appName", appName);
		map.put("appUrl", url);
		map.put("artworkUrl", artworkUrl);
		map.put("genre", genre);
		map.put("genreId", genreId);
		return dalTemplate.upsert("dal.appleAppInfo.upsert", map);
	}

	@Override
	public int upsertCurrentTop(Long appId, String genre, int genreId,
			int ranking, int popId) {
		Map<String, Object> map = CollectionUtils.newHashMap(6);
		map.put("appId", appId);
		map.put("ranking", ranking);
		map.put("genre", genre);
		map.put("genreId", genreId);
		map.put("popId", popId);
		return dalTemplate.upsert("dal.appleAppCurrentTop.upsert", map);
	}

	@Override
	public List<AppleAppHistoryTop> getHistoryTopByGenreAndId(int genreId,
			Long appId, String startCreateDate, String endCreateDate, int popId) {
		Assert.notNull(appId, " appId is null");
		if (StringUtils.isBlank(startCreateDate)) {
			throw new IllegalArgumentException(" startCreateDate is black");
		}
		if (StringUtils.isBlank(endCreateDate)) {
			throw new IllegalArgumentException(" endCreateDate is black");
		}
		List<AppleAppHistoryTop> list = new ArrayList<AppleAppHistoryTop>();
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> data = dalTemplate.select(
				"dal.appleAppHistoryTop.select", new ExpressionValue<Criteria>(
						"genreId", Criteria.eq, genreId),
				new ExpressionValue<Criteria>("appId", Criteria.eq, appId),
				new ExpressionValue<Criteria>("createDate", Criteria.gte,
						startCreateDate), new ExpressionValue<Criteria>(
						"createDate", Criteria.lte, endCreateDate),
				new ExpressionValue<Criteria>("popId", Criteria.eq, popId));
		for (Map<String, Object> m : data) {
			AppleAppHistoryTop top = hisTopBean.convertBean(m);
			list.add(top);
		}
		return list;
	}

	@Override
	public List<AppleAppCurrentTop> getCurrentTop(Long appId, Integer genreId,
			Integer popId, int start, int limit) {
		List<AppleAppCurrentTop> list = new ArrayList<AppleAppCurrentTop>();
		SelectBuilder builder = dalTemplate
				.startFor("dal.appleAppCurrentTop.select");
		if (appId != null) {
			builder.cond("appId", Criteria.eq, appId);
		}
		if (genreId != null) {
			builder.cond("genreId", Criteria.eq, genreId);
		}
		if (popId != null) {
			builder.cond("popId", Criteria.eq, popId);
		}
		List<Map<String, Object>> data = builder.select(start, limit);
		for (Map<String, Object> m : data) {
			AppleAppCurrentTop top = currTopBean.convertBean(m);
			list.add(top);
		}
		return list;
	}

	@Override
	public int countCurrentTop(Long appId, Integer genreId, Integer popId) {
		SelectBuilder builder = dalTemplate
				.startFor("dal.appleAppCurrentTop.select");
		builder.cond("appId", Criteria.eq, appId);
		builder.cond("genreId", Criteria.eq, genreId);
		builder.cond("popId", Criteria.eq, popId);
		return (int) builder.count();
	}

	@Override
	public List<Map<String, Object>> getAppInfoLikeName(String appName,
			int start, int limit) {
		SelectBuilder select = dalTemplate
				.startFor("dal.appleAppInfo.select.like.appName");
		if (StringUtils.isNotBlank(appName)) {
			select.cond("appName", Criteria.like, appName);
		}
		return select.select(start, limit);
	}

	@Override
	public int countAppInfoLikeName(String appName) {
		SelectBuilder select = dalTemplate
				.startFor("dal.appleAppInfo.select.like.appName");
		if (StringUtils.isNotBlank(appName)) {
			select.cond("appName", Criteria.like, appName);
		}
		return (int) select.count();
	}

	@Override
	public List<Map<String, Object>> getAppInfoById(Long appId, int start,
			int limit) {
		SelectBuilder select = dalTemplate
				.startFor("dal.appleAppInfo.select.like.appName");
		if (appId != null) {
			select.cond("appId", Criteria.eq, appId);
		}
		return select.select(start, limit);
	}

	@Override
	public int countAppInfoById(Long appId) {
		SelectBuilder select = dalTemplate
				.startFor("dal.appleAppInfo.select.like.appName");
		if (appId != null) {
			select.cond("appId", Criteria.eq, appId);
		}
		return (int) select.count();
	}
}
