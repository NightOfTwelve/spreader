package com.nali.spreader.service.mongo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nali.common.model.Limit;
import com.nali.common.pagination.PageResult;
import com.nali.spreader.config.ContentQueryParamsDto;
import com.nali.spreader.config.Range;
import com.nali.spreader.constants.Website;
import com.nali.spreader.data.Content;
import com.nali.spreader.service.IContentService;
import com.nali.spreader.service.IGlobalUserService;
import com.nali.spreader.util.random.RandomUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:application-context-test.xml")
public class TestContentService {
	private static final Logger logger = Logger.getLogger(TestContentService.class);
	private static final String TEST_STR = ">>>>>>>>>>TEST>>>>>>>:";
	@Autowired
	private IContentService contentService;
	@Autowired
	private IGlobalUserService globalUserService;

	// @Test
	// public void testGetMatchedContent() {
	// Content c = this.contentService.getMatchedContent(3082L);
	// // null Exception
	// Content c2 = this.contentService.getMatchedContent(null);
	// logger.debug(c);
	// logger.debug(c2);
	// }

	// @Test
	// public void testGetContentById() {
	// Content c = this.contentService.getContentById(376L);
	// logger.debug(c);
	// }

	// @Test
	// public void testAssignContentId() {
	// // Content c = new Content();
	// // c.setType(Content.TYPE_WEIBO);
	// // c.setWebsiteId(Website.weibo.getId());
	// // c.setEntry("yDjO5C1jD");
	// // c.setWebsiteUid(1660452532L);
	// // c.setPubDate(DateUtils.addDays(new Date(), 2));
	// // Long tId = null;
	// // tId = this.contentService.assignContentId(c);
	// // logger.debug(TEST_STR + tId);
	//
	// List<Content> insertList = new ArrayList<Content>();
	// for (int i = -60; i < 0; i++) {
	// Content c = new Content();
	// c.setType(Content.TYPE_WEIBO);
	// c.setAudioUrl(randomUrl());
	// c.setReplyCount(RandomUtils.nextInt(200));
	// c.setRefCount(RandomUtils.nextInt(200));
	// c.setPicUrl(randomUrl());
	// c.setContentLength(RandomUtils.nextInt(200));
	// c.setVideoUrl(randomUrl());
	// c.setWebsiteId(Website.weibo.getId());
	// c.setAtCount(RandomUtils.nextInt(20));
	// c.setContentLength(RandomUtils.nextInt(200));
	// c.setEntry("yDjO5C1jD" + RandomUtils.nextLong());
	// c.setWebsiteUid(1660452532L + RandomUtils.nextInt());
	// c.setPubDate(DateUtils.addDays(new Date(), i));
	// insertList.add(c);
	// }
	// List<Long> ids = new ArrayList<Long>();
	// for (Content cc : insertList) {
	// Long id = this.contentService.assignContentId(cc);
	// ids.add(id);
	// logger.debug("ID>>>>" + id);
	// }
	// logger.debug("IDS>>>>" + ids);
	// }

	// private String randomUrl() {
	// int x = RandomUtils.nextInt(2);
	// if (x == 1) {
	// return "http://weibo.com";
	// }
	// return null;
	// }

	// @Test
	// public void testAssignContentIdHaveRefContent() {
	// Content c = new Content();
	// c.setType(Content.TYPE_WEIBO);
	// c.setWebsiteId(Website.weibo.getId());
	// c.setEntry("yCzsCyzIB");
	// c.setWebsiteUid(1660452532L);
	// // 引用的微博
	// Content refc = new Content();
	// refc.setType(Content.TYPE_WEIBO);
	// refc.setWebsiteId(Website.weibo.getId());
	// refc.setEntry("yCvtC55ww");
	// refc.setWebsiteUid(1999871887L);
	// c.setRefContent(refc);
	// Long tId = this.contentService.assignContentId(c);
	// logger.debug(TEST_STR + tId);
	// }
	// @Test
	// public void testSaveContentKeyword() {
	// Long[] keys = { 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L, 13L, 14L, 15L, 16L,
	// 17L, 18L, 20L,
	// 21L, 25L, 26L, 27L, 29L, 30L, 31L, 34L, 39L, 42L, 43L, 48L, 49L, 50L,
	// 51L, 52L, 53L };
	// ContentQueryParamsDto param = new ContentQueryParamsDto();
	// Limit lit = Limit.newInstanceForLimit(0, 100);
	// param.setLit(lit);
	// List<Long> allKey = Arrays.asList(keys);
	// List<Content> lis =
	// this.contentService.findContentPageResult(param).getList();
	// for (Content cc : lis) {
	// int rn = RandomUtils.nextInt(37);
	// List<Long> rlist = RandomUtil.randomItems(allKey, rn);
	// if (rlist.size() != 0) {
	// Long[] t = new Long[rlist.size()];
	// Long[] rKey = rlist.toArray(t);
	// Long cid = cc.getId();
	// this.contentService.saveContentKeyword(cid, rKey);
	// }
	// }
	// }

	// @Test
	// public void testFindContentPageResult() {
	// ContentQueryParamsDto param = new ContentQueryParamsDto();
	// param.setsPubDate(DateUtils.addDays(new Date(), -20));
	// param.setePubDate(DateUtils.addDays(new Date(), -3));
	// param.setKeyword("时尚生活");
	// Limit lit = Limit.newInstanceForLimit(0, 20);
	// param.setLit(lit);
	// PageResult<Content> pg =
	// this.contentService.findContentPageResult(param);
	// List<Content> listt = pg.getList();
	// logger.debug(TEST_STR + listt);
	// }
	@Test
	public void testFindPostContentUids() {
		Range<Long> fans = new Range<Long>();
		fans.setGte(10L);
		fans.setLte(200L);
		Range<Long> articles = new Range<Long>();
		articles.setGte(10L);
		articles.setLte(500L);
		Long[] ids = globalUserService.findPostContentUids(1, fans, articles);
		logger.debug(TEST_STR + ids.toString());
	}
}
