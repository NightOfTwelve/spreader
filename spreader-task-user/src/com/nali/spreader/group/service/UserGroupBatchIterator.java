package com.nali.spreader.group.service;

import java.util.List;

import com.nali.spreader.model.GrouppedUser;
import com.nali.spreader.util.DataIterator;

public class UserGroupBatchIterator extends DataIterator<GrouppedUser> {
	private long gid;
	private long manualCount;
	private long propertyCount;
	private IUserGroupService userGroupService;
	private long upCount;
	
	public UserGroupBatchIterator(IUserGroupService userGroupService, long gid,
			long manualCount, long propertyCount, int batchSize, long upCount) {
		super(getCount(manualCount, propertyCount, upCount), batchSize);
		this.gid = gid;
		this.userGroupService = userGroupService;
		this.upCount = upCount;
		if(count > manualCount) {
			this.manualCount = manualCount;
			this.propertyCount = count - manualCount;
		}else{
			this.manualCount = count;
			this.propertyCount = 0;
		}
	}
	
	private static long getCount(long manualCount, long propertyCount, long upCount) {
		long count = manualCount + propertyCount;
		if(propertyCount < 0) {
			throw new IllegalArgumentException("property count is less than 0");
		}
		if(upCount < 0) {
			return count;
		}
		return Math.min(count, upCount);
	}

	@Override
	public List<GrouppedUser> query(long offset, int limit) {
		List<GrouppedUser> grouppedUsers = this.userGroupService.queryGrouppedUsers(gid, manualCount,
				propertyCount, (int) offset, limit);
		return grouppedUsers;
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
	
	public long getCount() {
		return this.manualCount + this.propertyCount;
	}

	public long getUpCount() {
		return upCount;
	}
}
