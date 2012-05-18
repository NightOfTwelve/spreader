package com.nali.spreader.service;

import com.nali.common.pagination.PageResult;
import com.nali.spreader.config.KeywordInfoQueryDto;
import com.nali.spreader.config.KeywordQueryParamsDto;
import com.nali.spreader.data.Category;
import com.nali.spreader.data.Keyword;

/**
 * 分类与关键字服务接口
 * 
 * @author xiefei
 * 
 */
public interface ICategoryKeyWordService {

	/**
	 * 分页查询关键字列表
	 * 
	 * @param param
	 * @return
	 */
	PageResult<KeywordInfoQueryDto> findKeywordByParams(KeywordQueryParamsDto param);

	/**
	 * 分页查询不属于该分类的关键字列表
	 * 
	 * @param param
	 * @return
	 */
	PageResult<KeywordInfoQueryDto> findKeywordByNotEqualParams(KeywordQueryParamsDto param);

	/**
	 * 创建一个新关键字
	 * 
	 * @param Keyword
	 */
	void createKeyword(Keyword kw);

	/**
	 * 创建一个分类
	 * 
	 * @param c
	 */
	void createCategory(Category c);

	/**
	 * 检查关键字是否已经存在
	 * 
	 * @param keywordName
	 * @return
	 */
	boolean checkKeywordNameIsPresence(String keywordName);

	/**
	 * kewywordId与category批量删除绑定关系
	 * 
	 * @param keywordIds
	 * @return
	 */
	int unBinding(Long... keywordIds);

	/**
	 * 更新Keyword的状态
	 * 
	 * @param keywordId
	 * @param status
	 * @return
	 */
	int updateKeywordExecutable(Long keywordId, boolean status);

	/**
	 * 回滚时更新Keyword的状态
	 * 
	 * @param keywordId
	 * @return
	 */
	int updateKeywordExecutableByRollback(Long keywordId);

	/**
	 * 变更关键字与分类的绑定关系 categoryId 为 null表示取消绑定
	 * 
	 * @param keywordId
	 * @param categoryId
	 * @return
	 */
	boolean changeBinding(Long keywordId, Long categoryId);

	/**
	 * 检查关键字更新状态
	 * 
	 * @param keywordId
	 * @return
	 */
	boolean checkKeywordUpdateStatus(Long keywordId);

	/**
	 * 查询分类的分页信息
	 * 
	 * @param categoryName
	 * @param start
	 * @param limit
	 * @return
	 */
	PageResult<Category> findCategoryPageData(KeywordQueryParamsDto param);

	/**
	 * 更新关键字表和分类表后的相关回滚动作
	 * 
	 * @param keywordId
	 * @param oldCategoryId
	 * @return
	 */
	String keywordAndCategoryRollBackInfo(Long keywordId, Long oldCategoryId);

	/**
	 * 检查分类是否重名
	 * 
	 * @param name
	 * @return
	 */
	boolean checkCategoryName(String name);
}
