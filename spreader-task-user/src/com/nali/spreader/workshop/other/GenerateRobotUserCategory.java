package com.nali.spreader.workshop.other;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.spreader.config.CategoryWeight;
import com.nali.spreader.config.NameValue;
import com.nali.spreader.config.Range;
import com.nali.spreader.data.Keyword;
import com.nali.spreader.data.UserTag;
import com.nali.spreader.factory.config.Configable;
import com.nali.spreader.factory.config.desc.ClassDescription;
import com.nali.spreader.factory.passive.PassiveAnalyzer;
import com.nali.spreader.service.IGlobalUserService;
import com.nali.spreader.service.IKeywordService;
import com.nali.spreader.util.random.NumberRandomer;
import com.nali.spreader.util.random.Randomer;
import com.nali.spreader.util.random.WeightRandomer;

@Component
@ClassDescription("机器人标签策略")
public class GenerateRobotUserCategory implements PassiveAnalyzer<Long>, Configable<CategoryWeight> {
	private Randomer<Integer> countRandomer;
	private Randomer<Keyword> keywordRandomer;
	@Autowired
	private IGlobalUserService globalUserService;
	@Autowired
	private IKeywordService keywordService;

	@Override
	public void work(Long uid) {
		Integer count = countRandomer.get();
		List<Keyword> keywords = keywordRandomer.multiGet(count);
		List<UserTag> tags = new ArrayList<UserTag>(keywords.size());
		for (Keyword k : keywords) {
			UserTag ut = new UserTag();
			Long kid = k.getId();
			ut.setTagId(kid);
			ut.setTag(k.getName());
			ut.setUid(uid);
			tags.add(ut);
		}
		globalUserService.updateUserTags(uid, tags);
	}

	@Override
	public void init(CategoryWeight categoryWeight) {
		Range<Integer> categoryCount = categoryWeight.getCategoryCount();
		countRandomer = new NumberRandomer(categoryCount.getGte(), categoryCount.getLte() + 1);
		WeightRandomer<Keyword> weightRandomer = new WeightRandomer<Keyword>();
		List<NameValue<String, Integer>> keywords = categoryWeight.getCategories();
		for (NameValue<String, Integer> keywordInfo : keywords) {
			Keyword k = keywordService.findKeywordByKeywordName(keywordInfo.getName());
			weightRandomer.add(k, keywordInfo.getValue());
		}
		keywordRandomer = weightRandomer;
	}
}
