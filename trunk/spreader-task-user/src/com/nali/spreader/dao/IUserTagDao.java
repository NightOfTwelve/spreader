package com.nali.spreader.dao;

import com.nali.spreader.data.UserTag;

/**
 * UserTag表自定义Dao
 * 
 * @author xiefei
 * 
 */
public interface IUserTagDao {

	/**
	 * 更新tb_user_tag中的CategoryId
	 * 
	 * @param userTag
	 * @return
	 */
	int updateCategoryIdByTagId(UserTag userTag);

}
