package com.nali.spreader.service.mongo;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nali.spreader.config.PostWeiboConfig;
import com.nali.spreader.config.Range;
import com.nali.spreader.dto.PostWeiboContentDto;
import com.nali.spreader.service.IContentService;
import com.nali.spreader.service.IKeywordService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:application-context-test.xml")
public class TestkeywordService {
	private static final Logger logger = Logger.getLogger(TestkeywordService.class);
	private static final String TEST_STR = ">>>>>>>>>>TEST>>>>>>>:";
	@Autowired
	private IKeywordService keywordService;
	@Autowired
	private IContentService contentService;

	// @Test
	// public void testCreateMergerKeyword() {
	// List<String> keywords = new ArrayList<String>();
	// keywords.add("时事热点");
	// keywords.add("时尚生活");
	// keywords.add("宠物爱好者");
	// List<String> categories = new ArrayList<String>();
	// categories.add("电影");
	// try {
	// Set<Long> list = this.keywordService.createMergerKeyword(keywords,
	// categories);
	// for (Long kid : list) {
	// logger.debug(TEST_STR + kid);
	// }
	// } catch (Exception e) {
	// logger.debug(e);
	// }
	// }

	@Test
	public void testCreateSendKeywordList() {
		List<String> ks = new ArrayList<String>();
		ks.add("时事热点");
		ks.add("时尚生活");
		ks.add("宠物爱好者");
		List<String> categories = new ArrayList<String>();
		categories.add("电影");
		try {
			Set<Long> list = this.keywordService.createMergerKeyword(ks, categories);
			Long[] keywords = this.keywordService.createSendKeywordList(list, 206865L);
			PostWeiboConfig cfg = new PostWeiboConfig();
			Range<Long> articles = new Range<Long>();
			 articles.setGte(10L);
			 articles.setLte(1000L);
			Range<Integer> atCount = new Range<Integer>();
			 atCount.setGte(1);
			 atCount.setLte(100);
			Range<Integer> contentLength = new Range<Integer>();
			 contentLength.setGte(20);
			 contentLength.setLte(500);
			Range<Long> fans = new Range<Long>();
			 fans.setGte(10L);
			 fans.setLte(1000L);
			Range<Integer> refCount = new Range<Integer>();
			refCount.setGte(10);
			refCount.setLte(200);
			Range<Integer> replyCount = new Range<Integer>();
			replyCount.setGte(10);
			replyCount.setLte(100);
//			cfg.setArticles(articles);
			cfg.setAtCount(atCount);
			cfg.setContentLength(contentLength);
			cfg.setEffective(14400);
			cfg.setIsAudio(true);
			cfg.setIsPic(false);
			cfg.setIsVideo(false);
			cfg.setPostInterval(10);
			cfg.setRefCount(refCount);
			cfg.setReplyCount(replyCount);
			cfg.setvType(0);
			Long[] uids = { 415734L, 415733L, 415732L, 415731L, 415730L, 415729L, 415728L };
			PostWeiboContentDto query = cfg.getPostWeiboContentDto(keywords, null);
			List<Long> allContent = this.contentService.findContentIdByPostContentDto(query);
			logger.debug(allContent);
		} catch (Exception e) {
			logger.debug(e);
		}
	}
}
