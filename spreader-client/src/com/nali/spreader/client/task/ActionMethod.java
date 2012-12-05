package com.nali.spreader.client.task;

import java.util.Map;

public interface ActionMethod {
	Object execute(Map<String, Object> params, Map<String, Object> userContext, Long uid);
	Long getActionId();
}
