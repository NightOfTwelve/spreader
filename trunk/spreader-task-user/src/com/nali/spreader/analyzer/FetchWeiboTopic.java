package com.nali.spreader.analyzer;

import org.springframework.stereotype.Component;

import com.nali.spreader.factory.TaskProduceLine;
import com.nali.spreader.factory.config.desc.ClassDescription;
import com.nali.spreader.factory.passive.AutowireProductLine;
import com.nali.spreader.factory.regular.RegularAnalyzer;

@Component
@ClassDescription("爬取微话题")
public class FetchWeiboTopic implements RegularAnalyzer {
	@AutowireProductLine
	private TaskProduceLine<Object> fetchKeywordEntrance;

	@Override
	public String work() {
		fetchKeywordEntrance.send(null);
		return null;
	}
}
