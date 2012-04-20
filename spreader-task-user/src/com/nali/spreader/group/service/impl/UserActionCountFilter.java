package com.nali.spreader.group.service.impl;

import java.util.List;

import com.nali.spreader.group.service.ICtrlUserGroupService;
import com.nali.spreader.group.service.IUserGroupFilter;
import com.nali.spreader.group.service.UidFilterIterator;
import com.nali.spreader.util.DataIterator;

public class UserActionCountFilter implements IUserGroupFilter {

	private ICtrlUserGroupService ctrlUserGroupService;
	private String taskCode;

	public UserActionCountFilter(ICtrlUserGroupService ctrlUserGroupService, String taskCode) {
		this.taskCode = taskCode;
		this.ctrlUserGroupService = ctrlUserGroupService;
	}

	@Override
	public DataIterator<Long> filterUsers(DataIterator<Long> uidIterator) {
		return new UidFilterIterator(uidIterator, this);
	}

	@Override
	public List<Long> filterUsers(List<Long> users) {
		return this.ctrlUserGroupService.whoCanDo(taskCode, users);
	}
}
