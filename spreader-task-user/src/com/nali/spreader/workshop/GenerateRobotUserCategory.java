package com.nali.spreader.workshop;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.spreader.config.CategoryWeight;
import com.nali.spreader.config.NameValue;
import com.nali.spreader.config.Range;
import com.nali.spreader.data.Category;
import com.nali.spreader.data.UserTag;
import com.nali.spreader.factory.config.Configable;
import com.nali.spreader.factory.config.desc.ClassDescription;
import com.nali.spreader.factory.passive.PassiveAnalyzer;
import com.nali.spreader.service.ICategoryService;
import com.nali.spreader.service.IGlobalUserService;
import com.nali.spreader.util.NumberRandomer;
import com.nali.spreader.util.Randomer;
import com.nali.spreader.util.WeightRandomer;

@Component
@ClassDescription("机器人分类策略")
public class GenerateRobotUserCategory implements PassiveAnalyzer<Long>, Configable<CategoryWeight> {
	private Randomer<Integer> countRandomer;
	private Randomer<Category> categoryRandomer;
	@Autowired
	private ICategoryService categoryService;
	@Autowired
	private IGlobalUserService globalUserService;
	
	@Override
	public void work(Long uid) {
		Integer count = countRandomer.get();
		List<Category> categories = categoryRandomer.multiGet(count);
		List<UserTag> tags = new ArrayList<UserTag>(categories.size());
		for (Category c : categories) {
			UserTag ut = new UserTag();
			ut.setCategoryId(c.getId());
			ut.setTag(c.getName());
			ut.setUid(uid);
			tags.add(ut);
		}
		globalUserService.updateUserTags(uid, tags);
	}
	
	@Override
	public void init(CategoryWeight categoryWeight) {
		Range<Integer> categoryCount = categoryWeight.getCategoryCount();
		countRandomer = new NumberRandomer(categoryCount.getGte(), categoryCount.getLte()+1);
		
		WeightRandomer<Category> weightRandomer = new WeightRandomer<Category>();
		List<NameValue<String, Integer>> categories = categoryWeight.getCategories();
		for (NameValue<String, Integer> categoryInfo : categories) {
			Category c = categoryService.getCategoryByName(categoryInfo.getName());
			weightRandomer.add(c, categoryInfo.getValue());
		}
		categoryRandomer = weightRandomer;
	}
}
