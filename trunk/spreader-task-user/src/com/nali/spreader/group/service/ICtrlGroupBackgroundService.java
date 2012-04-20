package com.nali.spreader.group.service;

import com.nali.spreader.data.CtrlGroupConflict;
import com.nali.spreader.data.UserGroup;
import com.nali.spreader.model.GrouppedUser;
import com.nali.spreader.util.DataIterator;

public interface ICtrlGroupBackgroundService {
	void submitCtrlUserGroup(UserGroup userGroup, DataIterator<GrouppedUser> iterator);
	
	void resolveAll(DataIterator<CtrlGroupConflict> iterator);
}
