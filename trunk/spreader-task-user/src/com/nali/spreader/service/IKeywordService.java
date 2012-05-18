package com.nali.spreader.service;

import java.util.List;

import com.nali.spreader.data.Category;
import com.nali.spreader.data.Keyword;

/**
 * 关键字服务接口
 * 
 * @author xiefei
 * 
 */
public interface IKeywordService {
	/**
	 * 通过分类集合查找所有的关键字
	 * 
	 * @param categories
	 * @return
	 */
	List<Keyword> findKeywordsByCategoryList(List<Category> categories);

	/**
	 * 通过关键字ID查询所有的分类ID
	 * 
	 * @param keywordIds
	 * @return
	 */
	List<Long> fingCategoryIdsByKeywordIds(List<Long> keywordIds);
}
