package com.nali.spreader.test.work;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nali.spreader.config.PostWeiboConfig;
import com.nali.spreader.constants.Website;
import com.nali.spreader.data.Content;
import com.nali.spreader.data.ContentReplay;
import com.nali.spreader.data.KeyValue;
import com.nali.spreader.data.NoticeTypeEnum;
import com.nali.spreader.dto.FetchNoticeDto;
import com.nali.spreader.factory.TaskProduceLine;
import com.nali.spreader.factory.passive.AutowireProductLine;
import com.nali.spreader.workshop.notice.FetchNotice;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:application-context-test.xml")
public class TestFetchNotice {
	@Autowired
	private FetchNotice fetch;
	@AutowireProductLine
	private TaskProduceLine<KeyValue<Long, Date>> fetchNotice;
	@Test
	public void test() {
		List<FetchNoticeDto> list = this.getData();
		KeyValue<Long, Date> kv = new KeyValue<Long, Date>();
		kv.setKey(88L);
		kv.setValue(new Date());
		fetchNotice.send(kv);
		fetch.handleResult(new Date(), list, null, 1L);
	}

	private List<FetchNoticeDto> getData() {
		FetchNoticeDto dto = new FetchNoticeDto();
		dto.setNoticeType(NoticeTypeEnum.commentReplay.getNocticeType());
		dto.setFromWebsiteUid(2483525083L);
		ContentReplay cr = new ContentReplay();
		ContentReplay comment = new ContentReplay();
		comment.setContent("测试：微博的评论内容");
		Content c = new Content();
		c.setAtCount(10);
		c.setAudioUrl("http://www.qq.com");
		c.setContent("测试：评论引用的微博");
		c.setContentLength(10);
		c.setEntry("yqB4Ol9Sl");
		c.setPubDate(new Date());
		c.setReplyCount(100);
		c.setWebsiteContentId(3383227331994699L);
		c.setWebsiteUid(2483525083L);
		c.setWebsiteId(Website.weibo.getId());
		c.setType(1);
		comment.setRefContent(c);
		comment.setFromWebsiteUid(3383553207641252L);
		comment.setPubDate(new Date());
		comment.setWebsiteId(Website.weibo.getId());
		cr.setRefReplay(comment);
		cr.setContent("测试:评论的回复内容");
		cr.setFromWebsiteUid(3384694108874061L);
		cr.setWebsiteId(Website.weibo.getId());
		cr.setPubDate(new Date());
		dto.setRefData(cr);
		List<FetchNoticeDto> list = new ArrayList<FetchNoticeDto>();
		list.add(dto);
		return list;
	}
}
