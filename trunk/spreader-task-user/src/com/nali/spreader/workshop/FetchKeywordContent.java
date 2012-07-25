package com.nali.spreader.workshop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.spreader.constants.Channel;
import com.nali.spreader.constants.Website;
import com.nali.spreader.data.Content;
import com.nali.spreader.data.KeyValue;
import com.nali.spreader.data.User;
import com.nali.spreader.factory.SimpleActionConfig;
import com.nali.spreader.factory.TaskProduceLine;
import com.nali.spreader.factory.base.ContextMeta;
import com.nali.spreader.factory.base.SingleTaskMachineImpl;
import com.nali.spreader.factory.base.SingleTaskMeta;
import com.nali.spreader.factory.exporter.SingleTaskExporter;
import com.nali.spreader.factory.passive.AutowireProductLine;
import com.nali.spreader.factory.passive.SinglePassiveTaskProducer;
import com.nali.spreader.factory.result.ContextedResultProcessor;
import com.nali.spreader.service.IContentKeywordService;
import com.nali.spreader.service.IContentService;
import com.nali.spreader.service.IKeywordService;
import com.nali.spreader.service.IUserService;
import com.nali.spreader.util.SpecialDateUtil;

/**
 * 根据关键字爬取微博内容
 * 
 * @author xiefei
 * 
 */
@Component
public class FetchKeywordContent extends SingleTaskMachineImpl implements
		SinglePassiveTaskProducer<KeyValue<String, Integer>>,
		ContextedResultProcessor<List<Content>, SingleTaskMeta> {
	@Autowired
	private WeiboRobotUserHolder weiboRobotUserHolder;
	@Autowired
	private IContentService contentService;
	@Autowired
	private IKeywordService keywordService;
	@Autowired
	private IContentKeywordService contentKeywordService;
	@Autowired
	private IUserService userService;
	@AutowireProductLine
	private TaskProduceLine<Long> fetchWeiboUserMainPage;

	public FetchKeywordContent() {
		super(SimpleActionConfig.fetchKeywordContent, Website.weibo, Channel.fetch);
		ContextMeta contextMeta = new ContextMeta(Collections.<String> emptyList(),
				Arrays.asList("keyword"));
		setContextMeta(contextMeta);
	}

	@Override
	public void work(KeyValue<String, Integer> data, SingleTaskExporter exporter) {
		String keyword = data.getKey();
		Integer pageNumber = data.getValue();
		if (pageNumber == null || pageNumber <= 0) {
			pageNumber = Content.DEFAULT_PAGENUMBER;
		}
		exporter.setProperty("keyword", keyword);
		exporter.setProperty("pageNumber", pageNumber);
		exporter.send(weiboRobotUserHolder.getRobotUid(), SpecialDateUtil.afterNow(2));
	}

	@Override
	public void handleResult(Date updateTime, List<Content> result,
			Map<String, Object> contextContents, Long uid) {
		String keyword = contextContents.get("keyword").toString();
		if (result != null && result.size() > 0) {
			for (Content c : result) {
				String text = c.getContent();
				Long websiteUid = c.getWebsiteUid();
				Long userId = this.userService.assignUser(websiteUid);
				// 用户不存在爬取用户
				if (userId != null) {
					fetchWeiboUserMainPage.send(userId);
				} else {
					User user = this.userService.getUserByWebsiteUid(websiteUid);
					userId = user.getId();
				}
				c.setUid(userId);
				// 设置内容长度
				if (StringUtils.isEmpty(text)) {
					c.setAtCount(0);
					c.setContentLength(0);
				} else {
					// 设置@数
					int atCount = this.charMatch(text, Content.AT_STR.charAt(0));
					c.setAtCount(atCount);
					c.setContentLength(text.length());
				}
				// 获取内容ID
				Long contentId = this.contentService.assignContentId(c);
				List<String> tagList = c.getTags();
				if (tagList == null) {
					tagList = new ArrayList<String>();
				}
				// 用于搜索内容的关键字也要放入标签列表
				tagList.add(keyword);
				for (String tag : tagList) {
					// 保存关键字并获取ID
					Long keywordId = this.keywordService.getOrAssignKeywordIdByName(tag);
					// 保存内容与关键字关系
					this.contentKeywordService.getOrAssignContentKeywordId(contentId, keywordId);
				}
			}
		}
	}

	private int charMatch(String sr, char ar) {
		int count = 0;
		int num = 0;
		int temp = 0;
		while ((sr.length() - temp) >= 1) {
			num = sr.indexOf(ar, temp);
			if (num == -1) {
				temp = sr.length();
			} else {
				temp = num + 1;
				count++;
			}
		}
		return count;
	}
}