package com.nali.spreader.remote;

import com.nali.spreader.model.ClientAction;

public interface IRemoteActionService {
	/**
	 * 查询客户端行为
	 * @return action和actionStepList，如果不存在则为空
	 */
	ClientAction getActionById(Long id);
}
