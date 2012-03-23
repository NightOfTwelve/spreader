package com.nali.spreader.group;

import org.springframework.stereotype.Component;

import com.nali.spreader.factory.config.Configable;
import com.nali.spreader.factory.config.desc.ClassDescription;
import com.nali.spreader.factory.regular.RegularAnalyzer;
import com.nali.spreader.group.config.UserGroupExtendedBeanImpl;
import com.nali.spreader.group.service.IUserGroupService;

@Component
@ClassDescription("测试用户分组")
public class TestUserGroup extends UserGroupExtendedBeanImpl implements RegularAnalyzer, Configable<String> {
	private String config;
	private IUserGroupService userGroupService;

	public TestUserGroup() {
		super("${fromGroup}test${toGroup}");
	}
	
	@Override
	public void init(String config) {
		this.config = config;
	}

	@Override
	public String work() {
		System.out.println("###############test start");
		return null;
//		System.out.println(config);
//		DataIterator<Long> it = userGroupService.queryGrouppedUids(getFromUserGroup(), 10);
//		while(it.hasNext()) {
//			System.out.println(it.next());
//		}
//		System.out.println("###############test end");
//	}
//	{
//		userGroupService = new IUserGroupService() {
//
//			@Override
//			public void addManualUsers(long gid, long... uids) {
//				// TODO Auto-generated method stub
//				
//			}
//
//			@Override
//			public long createGroup(UserGroup userGroup) {
//				return 0;
//			}
//
//			@Override
//			public void excludeUsers(long gid, long... uids) {
//				// TODO Auto-generated method stub
//				
//			}
//
//			@Override
//			public PageResult<GrouppedUser> queryGrouppedUsers(long gid,
//					Limit limit) throws GroupUserQueryException {
//				// TODO Auto-generated method stub
//				return null;
//			}
//
//			@Override
//			public DataIterator<Long> queryGrouppedUids(long gid, int batchSize)
//					throws GroupUserQueryException {
//				// TODO Auto-generated method stub
//				return null;
//			}
//
//			@Override
//			public List<GrouppedUser> queryGrouppedUsers(long gid,
//					long manualCount, long propertyCount, int offset, int limit)
//					throws GroupUserQueryException {
//				// TODO Auto-generated method stub
//				return null;
//			}
//
//			@Override
//			public UserGroup queryUserGroup(long gid) {
//				// TODO Auto-generated method stub
//				return null;
//			}
//
//			@Override
//			public PageResult<UserGroup> queryUserGroups(Website website,
//					String gname, UserGroupType userGroupType, int propVal,
//					Date fromModifiedTime, Date toModifiedTime, Limit limit) {
//				// TODO Auto-generated method stub
//				return null;
//			}
//
//			@Override
//			public void removeManualUsers(long gid, long... uids) {
//				// TODO Auto-generated method stub
//				
//			}
//
//			@Override
//			public void rollbackExcludeUsers(long gid, long... uids) {
//				// TODO Auto-generated method stub
//				
//			}
//
//			@Override
//			public void updateUserGroup(UserGroup userGroup) {
//				// TODO Auto-generated method stub
//				
//			}
//
//			@Override
//			public void deleteUserGroup(long gid) {
//				// TODO Auto-generated method stub
//				
//			}
//
//			@Override
//			public PageResult<GrouppedUser> queryExcludeUsers(long gid,
//					Limit limit) {
//				// TODO Auto-generated method stub
//				return null;
//			}
//
//			@Override
//			public PageResult<GrouppedUser> queryManualUsers(long gid,
//					Limit limit) {
//				// TODO Auto-generated method stub
//				return null;
//			}
//
//			@Override
//			public void removeUsers(long gid, long... uids) {
//				// TODO Auto-generated method stub
//				
//			}
//
//			@Override
//			public void updateUserGroup(long gid,
//					PropertyExpressionDTO propertyExpressionDTO) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			
//		};
	}
}
