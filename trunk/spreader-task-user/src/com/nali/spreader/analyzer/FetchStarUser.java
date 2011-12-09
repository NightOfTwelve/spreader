package com.nali.spreader.analyzer;

import org.springframework.stereotype.Component;

import com.nali.spreader.factory.TaskProduceLine;
import com.nali.spreader.factory.config.SystemObject;
import com.nali.spreader.factory.config.desc.ClassDescription;
import com.nali.spreader.factory.passive.AutowireProductLine;
import com.nali.spreader.factory.regular.RegularAnalyzer;

@Component
@ClassDescription("爬取明星排行榜")
public class FetchStarUser implements RegularAnalyzer, SystemObject {
	@AutowireProductLine
	private TaskProduceLine<Object> fetchWeiboStarUser;

	@Override
	public void work() {
		fetchWeiboStarUser.send(null);
	}

}
