package com.nali.spreader.remote;

import com.nali.spreader.model.LoginConfig;

public interface IRemoteLoginConfigService {

	/**
	 * 查询登录配置，不存在用户则返回空
	 */
	LoginConfig getLoginConfig(Long uid);

}
