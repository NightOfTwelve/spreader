package com.nali.spreader.group.service;

import java.util.List;

import com.nali.spreader.data.UserGroup;

public interface ICtrlUserGroupService {
	
	boolean submitCtrlUserGroup(UserGroup userGroup);
	
	List<Long> whoCanDo(String taskCode, List<Long> uids);
	
	boolean canIDo(String taskCode, long uid);
}
