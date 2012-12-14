package com.nali.spreader.client.ximalaya;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.nali.spreader.client.task.ActionMethod;

@Component
public class XimalayaLoginActionMethod implements ActionMethod {

	@Override
	public Object execute(Map<String, Object> params, Map<String, Object> userContext, Long uid) {
		return null;
	}

	@Override
	public Long getActionId() {
		return 3003L;
	}

}
