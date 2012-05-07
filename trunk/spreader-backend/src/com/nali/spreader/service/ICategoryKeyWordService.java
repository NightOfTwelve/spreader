package com.nali.spreader.service;

import com.nali.common.pagination.PageResult;
import com.nali.spreader.config.KeywordInfoQueryDto;
import com.nali.spreader.config.KeywordQueryParamsDto;

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
	 * 创建一个新关键字
	 * 
	 * @param keywordName
	 * @param categoryId
	 */
	void createKeyword(String keywordName, Long categoryId);

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
	 * 更新tb_user_tag中的CategoryId CategoryId可以为null，表示取消了Category与Tag的绑定关系
	 * 
	 * @param tagId
	 * @param categoryId
	 * @return
	 */
	int updateUserTagCategory(Long tagId, Long categoryId);

	/**
	 * 更新Keyword的状态
	 * 
	 * @param keywordId
	 * @param status
	 * @return
	 */
	int updateKeywordExecutable(Long keywordId, boolean status);

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

}
