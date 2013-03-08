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
	 * 
	 * @param configName
	 * @param configMD5
	 * @return
	 */
	String readClientConfig(String configName, String configMD5);
}
