package com.nali.spreader.workshop;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.nali.spreader.data.KeyValue;
import com.nali.spreader.factory.RegularResultProcessor;

@Service
public class PostWeiboContentResultProcessor extends RegularResultProcessor<KeyValue<Long, Long>, PostWeiboContent> {
	private static Logger logger = Logger.getLogger(PostWeiboContentResultProcessor.class);
	
	@Override
	public void handleResult(Date updateTime, KeyValue<Long, Long> uidToContentIdMap) {
		//目前只是打打酱油
		logger.info("user[" + uidToContentIdMap.getKey() + "] post a content[" + uidToContentIdMap.getValue() + "]");
	}
}
