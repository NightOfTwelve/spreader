package com.nali.spreader.service;

import com.nali.spreader.model.ClientAction;

public interface IActionService {
	/**
	 * 查询客户端行为
	 * @return action和actionStepList，如果不存在则为空
	 */
	ClientAction getActionWithStepsById(Long id);
}
