package com.nali.spreader.service;

import com.nali.spreader.model.LoginConfig;

public interface ILoginConfigService {

	LoginConfig findByUid(Long uid);

	void modifyActionId(Long oldActionId, Long newActionId);

	void mergeLoginConfigByUid(Long uid, Long actionId, String contents);

}
