package com.nali.spreader.user.service;

import java.util.List;

import com.nali.spreader.model.RealUser;

public interface IReadUserService {

	/**
	 * 保存RealUser队列
	 * @param list
	 */
	public void saveRealUser(List<RealUser> list) throws Exception;
	
	/**
	 * 查询RealUser
	 * @param websiteUid
	 * @return
	 */
	public RealUser queryRealUser(long websiteUid, int siteId);
	
}
