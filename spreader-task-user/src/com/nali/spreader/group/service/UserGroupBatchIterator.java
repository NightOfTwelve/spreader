package com.nali.spreader.group.service;

import java.util.ArrayList;
import java.util.List;

import com.nali.spreader.model.GrouppedUser;
import com.nali.spreader.util.DataIterator;

public class UserGroupBatchIterator extends DataIterator<Long> {
	private long gid;
	private long manualCount;
	private long propertyCount;
	private IUserGroupService userGroupService;

	public UserGroupBatchIterator(IUserGroupService userGroupService, long gid,
			long manualCount, long propertyCount, int batchSize) {
		super(manualCount + propertyCount, batchSize);
		this.gid = gid;
		this.manualCount = manualCount;
		this.propertyCount = propertyCount;
		this.userGroupService = userGroupService;
	}

	@Override
	protected List<Long> query(long offset, int limit) {
		List<GrouppedUser> grouppedUsers = this.userGroupService.queryGrouppedUsers(gid, manualCount,
				propertyCount, (int) offset, limit);
		List<Long> grouppedUids = new ArrayList<Long>(grouppedUsers.size());
		for(GrouppedUser grouppedUser : grouppedUsers) {
			grouppedUids.add(grouppedUser.getUid());
		}
		return grouppedUids;
	}
	public long getGid() {
		return gid;
	}

	public long getDynamicCount() {
		return manualCount;
	}

	public long getPropertyCount() {
		return propertyCount;
	}
}
