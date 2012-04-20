package com.nali.spreader.group.service;

import java.util.List;

import com.nali.spreader.data.CtrlGroupConflict;

public interface ICtrlGroupTask {
	void changeCtrlUserGroup(List<Long> uids, long gid);
	
	void resolveConflict(List<CtrlGroupConflict> conflicts);
}
