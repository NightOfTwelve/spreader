package com.nali.spreader.remote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nali.spreader.service.ILoginConfigService;

@Service
public class LoginConfigManageService implements ILoginConfigManageService {
	@Autowired
	private ILoginConfigService loginConfigService;

	@Override
	public void mergeLoginConfigByUid(Long uid, Long actionId, String contents) {
		loginConfigService.mergeLoginConfigByUid(uid, actionId, contents);
	}

	@Override
	public void modifyActionId(Long oldActionId, Long newActionId) {
		loginConfigService.modifyActionId(oldActionId, newActionId);
	}

}
