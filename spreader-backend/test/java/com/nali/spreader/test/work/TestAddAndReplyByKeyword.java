package com.nali.spreader.test.work;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nali.common.util.CollectionUtils;
import com.nali.spreader.analyzer.keyword.AddFansAndReplyByKeyword;
import com.nali.spreader.config.KeywordReplyAndAddConfig;
import com.nali.spreader.dto.UserContentsDto;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("161-lts-application-context-test.xml")
public class TestAddAndReplyByKeyword {
	@Autowired
	private AddFansAndReplyByKeyword addFansAndReplyByKeyword;
	
	@Test	
	public void test() {
		KeywordReplyAndAddConfig config = new KeywordReplyAndAddConfig();
		List<String> keywords = new ArrayList<String>();
		keywords.add("文学艺术音乐");
		keywords.add("军事爱好");
		keywords.add("后台");
		keywords.add("情况");
		config.setKeywords(keywords);
		config.setEffective(28800);
		config.setAddCount(5);
		config.setExecuAddCount(3);
		config.setPostInterval(17);
		addFansAndReplyByKeyword.setFromUserGroup(362L);
		addFansAndReplyByKeyword.init(config);
		addFansAndReplyByKeyword.work();
	}
	
	
	List<Map<String, Long>> getTestData() {
		List<Map<String, Long>> data = new ArrayList<Map<String, Long>>();
		Map<String, Long> m11 = new HashMap<String, Long>();
		m11.put("contentId", 11L);
		m11.put("uid", 1L);
		data.add(m11);
		Map<String, Long> m12 = new HashMap<String, Long>();
		m12.put("contentId", 12L);
		m12.put("uid", 1L);
		data.add(m12);
		Map<String, Long> m13 = new HashMap<String, Long>();
		m13.put("contentId", 13L);
		m13.put("uid", 1L);
		data.add(m13);
		Map<String, Long> m21 = new HashMap<String, Long>();
		m21.put("contentId", 21L);
		m21.put("uid", 2L);
		data.add(m21);
		Map<String, Long> m22 = new HashMap<String, Long>();
		m22.put("contentId", 22L);
		m22.put("uid", 2L);
		data.add(m22);
		Map<String, Long> m31 = new HashMap<String, Long>();
		m31.put("contentId", 31L);
		m31.put("uid", 3L);
		data.add(m31);
		Map<String, Long> m32 = new HashMap<String, Long>();
		m32.put("contentId", 32L);
		m32.put("uid", 3L);
		data.add(m32);
		Map<String, Long> m33 = new HashMap<String, Long>();
		m33.put("contentId", 33L);
		m33.put("uid", 3L);
		data.add(m33);
		Map<String, Long> m41 = new HashMap<String, Long>();
		m41.put("contentId", 41L);
		m41.put("uid", 4L);
		data.add(m41);
		Map<String, Long> m42 = new HashMap<String, Long>();
		m42.put("contentId", 42L);
		m42.put("uid", 4L);
		data.add(m42);
		Map<String, Long> m43 = new HashMap<String, Long>();
		m43.put("contentId", 43L);
		m43.put("uid", 4L);
		data.add(m43);
		return data;
	}
	
	private void printUserContentsDto(List<UserContentsDto> readyData) {
		if (!CollectionUtils.isEmpty(readyData)) {
			for (UserContentsDto dto : readyData) {
				System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
				Long uid = dto.getUid();
				List<Long> l = dto.getContents();
				for (Long cid : l) {
					System.out.println("UID:" + uid + ",content:" + cid);
				}
			}
		}
	}
}
