package com.nali.spreader.client.ximalaya;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.spreader.client.task.ActionMethod;
import com.nali.spreader.client.ximalaya.service.IRobotRemoteService;

@Component
public class XimalayaAddFansActionMethod implements ActionMethod {
	@Autowired
	private IRobotRemoteService robotRemoteService;

	@Override
	public Object execute(Map<String, Object> params, Map<String, Object> userContext, Long uid) {
		Long fromUid = (Long) params.get("fromUid");
		Long toUid = (Long) params.get("toUid");
		return robotRemoteService.follow(fromUid, toUid);
	}

	@Override
	public Long getActionId() {
		return 3002L;
	}
}
