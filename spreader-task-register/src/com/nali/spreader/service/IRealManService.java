package com.nali.spreader.service;

import java.util.List;

import com.nali.spreader.data.RealMan;

/**
 * 真实身份证库相关服务
 * 
 * @author xiefei
 * 
 */
public interface IRealManService {
	/**
	 * 获取一批还未验证的身份证信息列表
	 * 
	 * @param limit
	 * @return
	 */
	List<RealMan> getEffectiveRealMan(int limit);

	/**
	 * 更新是否为真实身份证信息的标识
	 * 
	 * @param id
	 * @param isReal
	 * @return
	 */
	int updateIsReal(Long id, boolean isReal);

	/**
	 * 更新是否被新浪禁用的身份证标识
	 * 
	 * @param id
	 * @param isForbid
	 * @return
	 */
	int updateIsForbidBySina(Long id, boolean isForbid);

	/**
	 * 更新注册新浪使用的次数
	 * 
	 * @param id
	 * @return
	 */
	int updateSinaUseCount(Long id);

	/**
	 * 根据id获取一个RealMan对象
	 * 
	 * @param id
	 * @return
	 */
	RealMan getRealManById(Long id);
}
