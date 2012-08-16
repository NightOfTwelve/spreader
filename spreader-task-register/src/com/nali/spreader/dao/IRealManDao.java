package com.nali.spreader.dao;

import java.util.List;

import com.nali.spreader.data.RealMan;

/**
 * 身份证库相关DAO
 * 
 * @author xiefei
 * 
 */
public interface IRealManDao {
	/**
	 * 获取未验证的身份证信息
	 * 
	 * @param limit
	 * @return
	 */
	List<RealMan> getNotVerifiedRealMan(int limit);

	/**
	 * 更新新浪注册使用次数
	 * 
	 * @param id
	 * @return
	 */
	int updateSinaUseCount(Long id);
}
