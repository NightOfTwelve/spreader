package com.nali.spreader.group;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.nali.common.model.Limit;
import com.nali.common.pagination.PageResult;
import com.nali.spreader.constants.Website;
import com.nali.spreader.factory.config.Configable;
import com.nali.spreader.factory.config.desc.ClassDescription;
import com.nali.spreader.factory.regular.RegularAnalyzer;
import com.nali.spreader.group.config.UserGroupSupportedImpl;
import com.nali.spreader.group.exception.GroupUserQueryException;
import com.nali.spreader.group.meta.UserGroupType;
import com.nali.spreader.group.service.IUserGroupService;
import com.nali.spreader.model.GrouppedUser;
import com.nali.spreader.model.UserGroup;
import com.nali.spreader.util.DataIterator;

@Component
@ClassDescription("测试用户分组")
public class TestUserGroup extends UserGroupSupportedImpl implements RegularAnalyzer, Configable<String> {
	private String config;
	private IUserGroupService userGroupService;

	@Override
	public void init(String config) {
		this.config = config;
	}

	@Override
	public void work() {
		System.out.println("###############test start");
		System.out.println(config);
		DataIterator<Long> it = userGroupService.queryGrouppedUids(getFromUserGroup(), 10);
		while(it.hasNext()) {
			System.out.println(it.next());
		}
		System.out.println("###############test end");
	}
	{
		userGroupService = new IUserGroupService() {
			
			@Override
			public void updateUserGroup(UserGroup userGroup) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void saveGroup(UserGroup userGroup) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public PageResult<UserGroup> queryUserGroups(Website website, String name, UserGroupType userGroupType,
					int prop_val, Date fromModifiedTime, Date toModifiedTime) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public UserGroup queryUserGroup(long gid) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public DataIterator<Long> queryGrouppedUids(final long gid, int batchSize) throws GroupUserQueryException {
				return new DataIterator<Long>(1, 0, 2) {
					@Override
					protected List<Long> query(long offset, int limit) {
						return Arrays.asList(gid);
					}
				};
			}

			@Override
			public PageResult<GrouppedUser> gueryGrouppedUsers(long gid, Limit limit) throws GroupUserQueryException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void excludeUsers(long gid, long... uids) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void addManualUsers(long gid, long... uids) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void rollbackExcludeUsers(long gid, long... uids) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void removeManualUsers(long gid, long... uids) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public PageResult<GrouppedUser> queryExcludeUids(long gid,
					Limit limit) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public List<GrouppedUser> queryGrouppedUsers(long gid,
					long manualCount, long propertyCount, int offset, int limit)
					throws GroupUserQueryException {
				// TODO Auto-generated method stub
				return null;
			}
		};
	}
}
