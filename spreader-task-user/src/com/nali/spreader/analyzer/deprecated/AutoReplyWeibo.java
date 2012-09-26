package com.nali.spreader.analyzer.deprecated;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.spreader.config.ContentDto;
import com.nali.spreader.data.KeyValue;
import com.nali.spreader.factory.TaskProduceLine;
import com.nali.spreader.factory.config.Configable;
import com.nali.spreader.factory.config.desc.ClassDescription;
import com.nali.spreader.factory.passive.AutowireProductLine;
import com.nali.spreader.factory.regular.RegularAnalyzer;
import com.nali.spreader.service.IContentService;
import com.nali.spreader.util.TxtFileUtil;
import com.nali.spreader.util.random.AvgRandomer;
import com.nali.spreader.util.random.Randomer;

@ClassDescription("自动回复微博")
public class AutoReplyWeibo implements RegularAnalyzer,Configable<ContentDto> {
	private static final String FILE_REPLY_WORDS = "txt/reply.txt";
	private ContentDto dto;
	@Autowired
	private IContentService contentService;
	@AutowireProductLine
	private TaskProduceLine<KeyValue<Long, String>> replyWeibo;
	
	private Randomer<String> replyWords;
	
	@PostConstruct
	public void init() throws IOException {
		Set<String> datas = TxtFileUtil.read(AutoReplyWeibo.class.getClassLoader().getResource(FILE_REPLY_WORDS));
		replyWords = new AvgRandomer<String>(datas);
	}
	
	@Override
	public String work() {
		List<Long> contentIds = contentService.findContentIdByDto(dto);
		for (Long contentId : contentIds) {
			String text = replyWords.get();
			replyWeibo.send(new KeyValue<Long, String>(contentId, text));
		}
		return null;
	}

	@Override
	public void init(ContentDto dto) {
		this.dto = dto;
	}

}
