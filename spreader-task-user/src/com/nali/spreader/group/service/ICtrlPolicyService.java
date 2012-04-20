package com.nali.spreader.group.service;

import java.util.Collection;
import java.util.Map;

import com.nali.spreader.data.CtrlPolicy;

public interface ICtrlPolicyService {
	CtrlPolicy lookupPolicy(String taskCode, long gid);

	Map<Long, CtrlPolicy> lookupPolicy(String taskCode, Collection<Long> gids);

	CtrlPolicy lookupDefaultPolicy(String taskCode);
	
	void createCtrlPolicy(CtrlPolicy ctrlPolicy);
}
