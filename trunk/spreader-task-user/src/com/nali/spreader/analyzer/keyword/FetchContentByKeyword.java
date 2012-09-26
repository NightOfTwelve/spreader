package com.nali.spreader.analyzer.keyword;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.common.util.CollectionUtils;
import com.nali.spreader.config.KeywordContentDto;
import com.nali.spreader.data.Content;
import com.nali.spreader.data.KeyValue;
import com.nali.spreader.factory.TaskProduceLine;
import com.nali.spreader.factory.config.Configable;
import com.nali.spreader.factory.config.desc.ClassDescription;
import com.nali.spreader.factory.passive.AutowireProductLine;
import com.nali.spreader.factory.regular.RegularAnalyzer;
import com.nali.spreader.service.IKeywordService;

/**
 * 按关键字爬取内容
 * 
 * @author xiefei
 * 
 */
@Component
@ClassDescription("按关键字爬取内容")
public class FetchContentByKeyword implements RegularAnalyzer, Configable<KeywordContentDto> {
	private KeywordContentDto keywordContentDto;
	private Integer pageNumber;
	@Autowired
	private IKeywordService keywordService;
	@AutowireProductLine
	private TaskProduceLine<KeyValue<String, Integer>> fetchKeywordContent;

	@Override
	public String work() {
		List<String> keywords = keywordContentDto.getKeywords();
		List<String> categories = keywordContentDto.getCategories();
		// 所有关键的集合，包括通过分类查询出的关键字集合
		List<String> allKeyword = this.keywordService.createMergerKeywordName(keywords, categories);
		if (allKeyword.size() > 0) {
			for (String k : allKeyword) {
				KeyValue<String, Integer> kv = new KeyValue<String, Integer>();
				kv.setKey(k);
				kv.setValue(this.pageNumber);
				fetchKeywordContent.send(kv);
			}
		}
		return null;
	}

	@Override
	public void init(KeywordContentDto keywordContentDto) {
		this.keywordContentDto = keywordContentDto;
		Integer page = keywordContentDto.getPageNumber();
		if (page != null && page > 0) {
			this.pageNumber = page;
		} else {
			this.pageNumber = Content.DEFAULT_PAGENUMBER;
		}
		List<String> keywords = keywordContentDto.getKeywords();
		List<String> categories = keywordContentDto.getCategories();
		if (CollectionUtils.isEmpty(keywords) && CollectionUtils.isEmpty(categories)) {
			throw new IllegalArgumentException("keywords and categories is empty");
		}
	}
}
