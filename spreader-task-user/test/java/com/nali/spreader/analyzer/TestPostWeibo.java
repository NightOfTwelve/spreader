package com.nali.spreader.analyzer;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.common.util.CollectionUtils;
import com.nali.spreader.analyzer.config.TestPostWeiboConfig;
import com.nali.spreader.data.Content;
import com.nali.spreader.dto.WeiboContentDto;
import com.nali.spreader.factory.TaskProduceLine;
import com.nali.spreader.factory.config.Configable;
import com.nali.spreader.factory.config.desc.ClassDescription;
import com.nali.spreader.factory.passive.AutowireProductLine;
import com.nali.spreader.factory.regular.RegularAnalyzer;
import com.nali.spreader.service.IContentService;

@Component
@ClassDescription("测试·发微博")
public class TestPostWeibo implements RegularAnalyzer, Configable<TestPostWeiboConfig> {
	private TestPostWeiboConfig config;
	@Autowired
	private IContentService contentService;
	@AutowireProductLine
	private TaskProduceLine<WeiboContentDto> postWeiboContent;

	@Override
	public String work() {
		List<Long> contents = config.getContentList();
		Long uids[] = { 206492L, 206491L, 206488L, 206487L };
		for (Long uid : uids) {
			if (uid != null) {
				if (!CollectionUtils.isEmpty(contents)) {
					for (Long cid : contents) {
						Content c = contentService.getContentById(cid);
						if (c != null) {
							WeiboContentDto dto = WeiboContentDto.getWeiboContentDto(uid, c,
									new Date());
							postWeiboContent.send(dto);
						}
					}
				}
			}
		}
		return null;
	}

	@Override
	public void init(TestPostWeiboConfig config) {
		this.config = config;
	}
}
