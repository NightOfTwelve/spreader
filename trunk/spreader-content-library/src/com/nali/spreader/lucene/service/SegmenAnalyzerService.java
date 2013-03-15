package com.nali.spreader.lucene.service;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.NumericField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.nali.common.model.Limit;
import com.nali.spreader.dao.ICrudContentSegmenDao;
import com.nali.spreader.dao.ISegmenAnalyzerDao;
import com.nali.spreader.data.ContentSegmen;
import com.nali.spreader.data.ContentSegmenExample;
import com.nali.spreader.data.ContentSegmenExample.Criteria;
import com.nali.spreader.data.Reply;
import com.nali.spreader.lucene.config.DefaultConfigEnum;
import com.nali.spreader.remote.ISegmenAnalyzerService;
import com.nali.spreader.util.PerformanceLogger;

@Service
public class SegmenAnalyzerService implements ISegmenAnalyzerService,
		ApplicationListener {
	private static Logger logger = Logger
			.getLogger(SegmenAnalyzerService.class);
	// private ContentAnalyzer contentAnalyzer = new ContentAnalyzer();
	private IKAnalyzer ikAnalyzer = new IKAnalyzer(true);
	private static final Pattern reReplyPatt = Pattern
			.compile("//@[\\u4e00-\\u9fa5\\w-_]+.*");
	private static final Pattern atPatt = Pattern
			.compile("@[\\u4e00-\\u9fa5\\w-_]+");
	private static final Pattern topPatt = Pattern
			.compile("#[\\u4e00-\\u9fa5\\w-_]+#");
	@Autowired
	private ICrudContentSegmenDao crudContentSegmenDao;
	@Autowired
	private ISegmenAnalyzerDao segmenAnalyzerDao;
	@Autowired
	private ISearchService searchService;
	private ThreadPoolExecutor segmenPool;
	private Map<String, Long> segmentMap = new ConcurrentHashMap<String, Long>();
	// 分词最高分数的基准比例
	private float scale;
	// 分词得分排名
	private int segRank;

	@PostConstruct
	public void init() {
		scale = 0.1f; // TODO
		segRank = 5;
		try {
			segmenPool = new ThreadPoolExecutor(2, 5, 5, TimeUnit.MINUTES,
					new LinkedBlockingQueue<Runnable>(5),
					new ThreadPoolExecutor.CallerRunsPolicy());
		} catch (Exception e) {
			logger.debug("segmenPool create fail", e);
		}
	}

	private void loadSegment() {
		int offset = 0;
		int limit = 2000;
		int rows = 0;
		List<ContentSegmen> list;
		ContentSegmenExample emp = new ContentSegmenExample();
		do {
			Limit lit = Limit.newInstanceForLimit(offset, limit);
			emp.setLimit(lit);
			list = crudContentSegmenDao.selectByExample(emp);
			for (ContentSegmen seg : list) {
				segmentMap.put(seg.getName(), seg.getId());
			}
			rows = list.size();
			offset = offset + limit;
		} while (rows > 0);
	}

	private void analysisContentSegmen(String content, List<String> replys) {
		Set<String> contentSegs = getContentSegmen(content, ikAnalyzer);
		for (String contentSeg : contentSegs) {
			Long csegId = assignContentSegmenId(contentSeg);
			for (String reply : replys) {
				Set<String> replySegs = getContentSegmen(reply, ikAnalyzer);
				for (String replySeg : replySegs) {
					Long rsegId = assignContentSegmenId(replySeg);
					segmenAnalyzerDao.saveSegmenScore(csegId, rsegId, 1);
				}
			}
		}
	}

	@Override
	public void execuAnalysisSegmen(final String weibo,
			final List<String> comments) {
		if (segmenPool != null) {
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					analysisContentSegmen(weibo, comments);
				}
			});
			segmenPool.submit(t);
		}
	}

	@Override
	public Set<String> getContentSegmen(String content) {
		return getContentSegmen(content, null);
	}

	/**
	 * 根据内容获取所有的分词
	 * 
	 * @param content
	 * @param analyzer
	 * @return
	 */
	private Set<String> getContentSegmen(String content, Analyzer analyzer) {
		Set<String> tokens = new HashSet<String>();
		if (StringUtils.isBlank(content)) {
			return tokens;
		}
		if (analyzer == null) {
			analyzer = new IKAnalyzer(true);
		}
		Reader reader = null;
		try {
			reader = new StringReader(content);
			TokenStream ts = analyzer.tokenStream(
					DefaultConfigEnum.tokenStreamFiledName.getValue(), reader);
			CharTermAttribute term = ts.getAttribute(CharTermAttribute.class);
			while (ts.incrementToken()) {
				if (term != null) {
					tokens.add(term.toString());
				}
			}
		} catch (IOException e) {
			logger.error(e);
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException e) {
				logger.error(e);
			}
		}
		return tokens;
	}

	public Long assignContentSegmenId(String segmen) {
		if (StringUtils.isBlank(segmen)) {
			throw new IllegalArgumentException("segmen is blank,assign id fail");
		}
		if (segmentMap.containsKey(segmen)) {
			return segmentMap.get(segmen);
		}
		ContentSegmen seg = new ContentSegmen();
		seg.setName(segmen);
		try {
			return crudContentSegmenDao.insert(seg);
		} catch (DuplicateKeyException e) {
			ContentSegmenExample example = new ContentSegmenExample();
			Criteria c = example.createCriteria();
			c.andNameEqualTo(segmen);
			List<ContentSegmen> list = crudContentSegmenDao
					.selectByExample(example);
			return list.get(0).getId();
		}
	}

	@Override
	public void createReplyIndex() {
		int start = 0;
		int limit = 5000;
		List<Reply> list = null;
		int rows = 0;
		do {
			list = segmenAnalyzerDao.query(start, limit);
			List<Document> docs = getDocument(list);
			searchService.indexDocs(docs);
			rows = list.size();
			start = start + limit;
		} while (rows > 0);
	}

	/**
	 * 增量的方式 TODO
	 */
	private void createReplyIndex2() {
		int start = 0;
		int limit = 5000;
		List<Reply> list = null;
		int rows = 0;
		Long lastId = segmenAnalyzerDao.getLastReplyId();
		Long setLastId = null;
		do {
			list = segmenAnalyzerDao.query(lastId, start, limit);
			List<Document> docs = getDocument(list);
			searchService.indexDocs(docs);
			rows = list.size();
			if (rows > 0) {
				if (start == 0) {
					setLastId = list.get(0).getId();
				}
			}
			start = start + limit;
		} while (rows > 0);
		segmenAnalyzerDao.setLastReplyId(setLastId);
	}

	private List<Document> getDocument(List<Reply> list) {
		List<Document> docs = new ArrayList<Document>();
		for (Reply r : list) {
			Document doc = new Document();
			doc.add(new NumericField("id", Field.Store.YES, false)
					.setLongValue(r.getId()));
			doc.add(new Field("content", r.getContent(), Field.Store.YES,
					Field.Index.ANALYZED));
			doc.add(new NumericField("atCount", Field.Store.NO, false)
					.setLongValue(getInteger(r.getAtCount())));
			doc.add(new NumericField("reReplyCount", Field.Store.NO, false)
					.setLongValue(getInteger(r.getReReplyCount())));
			doc.add(new NumericField("topicCount", Field.Store.NO, false)
					.setLongValue(getInteger(r.getTopicCount())));
			doc.add(new NumericField("useCount", Field.Store.NO, false)
					.setLongValue(getInteger(r.getUseCount())));
			docs.add(doc);
		}
		return docs;
	}

	private Integer getInteger(Integer i) {
		if (i == null) {
			return 0;
		}
		return i;
	}

	@Override
	public Long assignReplayId(String reply) {
		Assert.notNull(reply, " reply is null");
		Reply r = new Reply();
		Reply exist = segmenAnalyzerDao.queryReplyByContent(reply);
		if (exist != null) {
			Long existId = exist.getId();
			if (existId != null) {
				return existId;
			}
		}
		int atCount = replaceAll(reply, atPatt, "@", new StringBuffer());
		int reReplyCount = replaceAll(reply, reReplyPatt, "//@",
				new StringBuffer());
		int topicCount = replaceAll(reply, topPatt, "##", new StringBuffer());
		r.setAtCount(atCount);
		r.setContent(reply);
		r.setContentLength(reply.length());
		r.setReReplyCount(reReplyCount);
		r.setTopicCount(topicCount);
		r.setUseCount(0);
		return segmenAnalyzerDao.insertReply(r);
	}

	private int replaceAll(String target, Pattern pattern, String replacement,
			StringBuffer sb) {
		Matcher matcher = pattern.matcher(target);
		int count = 0;
		boolean result = matcher.find();
		if (result) {
			do {
				count++;
				matcher.appendReplacement(sb, replacement);
				result = matcher.find();
			} while (result);
			matcher.appendTail(sb);
		} else {
			sb.append(target);
		}
		return count;
	}

	/**
	 * 获取微博内容所有分词的回复关键字及评分
	 * 
	 * @param contentSegIds
	 * @return
	 */
	private SegmenScore mergeReplySegmenByScore(Set<String> contentSegs) {
		ConcurrentHashMap<String, Double> segScore = new ConcurrentHashMap<String, Double>();
		double maxScore = 0.0;
		for (String contentSeg : contentSegs) {
			Long contentSegId = assignContentSegmenId(contentSeg);
			Set<TypedTuple<Object>> commentSegs = segmenAnalyzerDao
					.getReplySegmenByScore(contentSegId, segRank);
			for (TypedTuple<Object> tt : commentSegs) {
				Long segId = (Long) tt.getValue();
				Double score = tt.getScore();
				ContentSegmen cs = crudContentSegmenDao
						.selectByPrimaryKey(segId);
				String segName = cs.getName();
				Double exists = segScore.putIfAbsent(segName, score);
				if (exists != null) {
					score = score + exists;
					segScore.put(segName, score);
				}
				if (maxScore < score) {
					maxScore = score;
				}
			}
		}
		SegmenScore ss = new SegmenScore();
		ss.setMaxScore(maxScore);
		ss.setScores(segScore);
		return ss;
	}

	// private Map<String, Float> getSegmenScale(Map<String, Double> data) {
	// Map<String, Float> m = new ConcurrentHashMap<String, Float>();
	// if (data.isEmpty()) {
	// return m;
	// }
	// Double total = 0.0;
	// Iterator<Double> it = data.values().iterator();
	// while (it.hasNext()) {
	// total = total + it.next();
	// }
	// for (Map.Entry<String, Double> entry : data.entrySet()) {
	// String seg = entry.getKey();
	// Double score = entry.getValue();
	// Float scale = formatScale(score, total);
	// m.put(seg, scale);
	// }
	// return m;
	// }

	// private float formatScale(double score, double total) {
	// DecimalFormat df = new DecimalFormat("0.000");
	// return Float.parseFloat(df.format(score / total));
	// }

	@Override
	public String getWeiboSearchKeywords(String weibo) {
		if (StringUtils.isBlank(weibo)) {
			return StringUtils.EMPTY;
		}
		Set<String> weiboSegs = getContentSegmen(weibo);
		SegmenScore ss = mergeReplySegmenByScore(weiboSegs);
		String keyword = filterSegmen(ss);
		return keyword;
	}

	private String filterSegmen(SegmenScore ss) {
		StringBuffer buff = new StringBuffer();
		double maxScore = ss.getMaxScore();
		double standard = maxScore * scale;
		Map<String, Double> m = ss.getScores();
		for (Map.Entry<String, Double> entry : m.entrySet()) {
			String seg = entry.getKey();
			double score = entry.getValue();
			if (score >= standard) {
				buff.append(seg);
			}
		}
		return buff.toString();
	}

	private class SegmenScore {
		private Map<String, Double> scores;
		private double maxScore;

		public Map<String, Double> getScores() {
			return scores;
		}

		public void setScores(Map<String, Double> scores) {
			this.scores = scores;
		}

		public double getMaxScore() {
			return maxScore;
		}

		public void setMaxScore(double maxScore) {
			this.maxScore = maxScore;
		}
	}

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				PerformanceLogger.infoStart();
				loadSegment();
				PerformanceLogger.info("分词加载完毕");
				PerformanceLogger.infoStart();
				createReplyIndex();
				PerformanceLogger.info("回复库索引建立完毕");
			}
		});
		t.setName("CreateReplyIndexThread");
		t.start();
	}
}
