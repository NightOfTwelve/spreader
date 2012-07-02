package com.nali.spreader.dao;

import com.nali.spreader.data.ContentKeyword;

/**
 * ContentKeyword 相关操作
 * 
 * @author xiefei
 * 
 */
public interface IContentKeywordDao {
	/**
	 * 插入一条记录
	 * 
	 * @param data
	 * @return
	 */
	Long insertContentKeyword(ContentKeyword data);

	/**
	 * 通过唯一索引获取实例
	 * 
	 * @param data
	 * @return
	 */
	ContentKeyword getContentKeywordByUk(ContentKeyword data);
}
