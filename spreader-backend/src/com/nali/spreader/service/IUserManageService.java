package com.nali.spreader.service;

import com.nali.common.pagination.PageResult;
import com.nali.spreader.data.User;

public interface IUserManageService {
	/**
	 * 获取用户分页数据
	 * 
	 * @param id
	 * @param nickname
	 * @param start
	 * @param limit
	 * @return
	 */
	public PageResult<User> findUserInfo(Long id, String nickname,
			Integer start, Integer limit);

}
