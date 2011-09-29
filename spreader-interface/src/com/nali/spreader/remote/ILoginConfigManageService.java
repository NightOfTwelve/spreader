package com.nali.spreader.remote;

public interface ILoginConfigManageService {

	void modifyActionId(Long oldActionId, Long newActionId);

	void mergeLoginConfigByUid(Long uid, Long actionId, String contents);
}
