package com.nali.spreader.remote;

/**
 * 客户端配置通用读取接口
 * 
 * @author xiefei
 * 
 */
public interface IRemoteClientConfigService {
	/**
	 * 通过客户端ID和配置名称获取配置信息
	 */
	String readClientConfig(String configName, String configMD5);

	/**
	 * 通过分组ID和配置名称 分类获取配置信息
	 */
	String readGroupConfig(Long groupId, String configName, String configMD5);
}
