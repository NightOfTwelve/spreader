package com.nali.spreader.service;

import com.nali.common.model.Limit;
import com.nali.common.pagination.PageResult;
import com.nali.spreader.model.ClientConfig;

public interface IClitentConfigService {
	/**
	 * 分页查询客户端配置信息
	 * 
	 * @param clientId
	 * @param limit
	 * @return
	 */
	PageResult<ClientConfig> queryPageData(Long clientId, Limit limit);

	/**
	 * 保存一条配置
	 * 
	 * @param id
	 * @param clientId
	 * @param cfgs
	 */
	void saveClientConfigs(Long id, Long clientId, String configName,
			int configType, String cfgs, String cfgMD5, int clientType);

	/**
	 * 根据ID获取配置对象
	 * 
	 * @param clientId
	 * @return
	 */
	ClientConfig getConfigByClientId(Long clientId, String configName,int clientType);

	/**
	 * 获取配置对象
	 * 
	 * @param id
	 * @return
	 */
	ClientConfig getConfigById(Long id);

}
