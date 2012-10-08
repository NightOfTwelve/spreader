package com.nali.spreader.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nali.spreader.config.PostWeiboConfig;
import com.nali.spreader.dao.IUserDao;
import com.nali.spreader.dto.PostWeiboContentDto;
import com.nali.spreader.factory.TaskProduceLine;
import com.nali.spreader.factory.passive.AutowireProductLine;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:application-context-test.xml")
public class TestPostWeibo {
	@Autowired
	private IUserDao userDao;
	@AutowireProductLine
	private TaskProduceLine<PostWeiboConfig> postKeywordWeibo;
	@Test
	public void test() {
		PostWeiboConfig cfg = new PostWeiboConfig();
		List<String> list = new ArrayList<String>();
		list.add("音乐");
		cfg.setKeywords(list);
		PostWeiboContentDto dto = new PostWeiboContentDto();
		cfg.setPostInterval(15);
		postKeywordWeibo.send(cfg);
//		Range<Long> articles = new Range<Long>();
//		articles.setGte(500L);
//		articles.setLte(1000L);
//		dto.setArticles(articles);
//		// 关键字列表
//		List<String> sendKeywords = new ArrayList<String>();
//		sendKeywords.add("音乐");
//		// 加V类型
//		dto.setUserCondition(true);
//		dto.setIsPic(true);
//		dto.setSendKeywords(sendKeywords);
//		List<Long> list = userDao.queryContentIdByPostContentDto(dto);
//		System.out.println(list.size());
	}
}
