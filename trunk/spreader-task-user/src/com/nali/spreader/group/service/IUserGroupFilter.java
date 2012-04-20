package com.nali.spreader.group.service;

import java.util.List;

import com.nali.spreader.util.DataIterator;

public interface IUserGroupFilter {
	DataIterator<Long> filterUsers(DataIterator<Long> uidIterator);
	
	List<Long> filterUsers(List<Long> users);
}
