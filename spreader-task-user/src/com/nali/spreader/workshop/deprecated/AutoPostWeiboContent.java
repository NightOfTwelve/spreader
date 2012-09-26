package com.nali.spreader.workshop.deprecated;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.spreader.data.Content;
import com.nali.spreader.data.KeyValue;
import com.nali.spreader.factory.TaskProduceLine;
import com.nali.spreader.factory.passive.AutowireProductLine;
import com.nali.spreader.factory.passive.PassiveAnalyzer;
import com.nali.spreader.service.IContentService;

@Component
public class AutoPostWeiboContent implements PassiveAnalyzer<Long> {
	private static Logger logger = Logger.getLogger(AutoPostWeiboContent.class);
	@AutowireProductLine
	private TaskProduceLine<KeyValue<Long, String>> postWeiboContent;
	@Autowired
	private IContentService contentService;
	
	@Override
	public void work(Long uid) {
		Content matchedContent = contentService.getMatchedContent(uid);
		if(matchedContent==null) {
			logger.warn("not matchedContent, uid:" + uid);
			return;
		}
		postWeiboContent.send(new KeyValue<Long, String>(uid, matchedContent.getContent()));
		contentService.addPostContentId(uid, matchedContent.getId());
	}

}
