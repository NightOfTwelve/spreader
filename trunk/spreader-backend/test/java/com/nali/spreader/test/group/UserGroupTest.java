package com.nali.spreader.test.group;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nali.common.model.Limit;
import com.nali.common.pagination.PageResult;
import com.nali.lang.ToStringBuilder;
import com.nali.spreader.constants.Website;
import com.nali.spreader.data.UserGroup;
import com.nali.spreader.group.exception.AssembleException;
import com.nali.spreader.group.exp.Properties;
import com.nali.spreader.group.meta.UserGroupType;
import com.nali.spreader.group.service.IUserGroupService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:application-context-test.xml"})
public class UserGroupTest {
	
	@Autowired
	private IUserGroupService userGroupService;
	
	@Autowired
	private UserGroupCreater userGroupCreater;
	
	
	@Test
	public void testCreateUserGroup() throws AssembleException {
		try{
			UserGroup userGroup = this.userGroupCreater.createCtrlUserGroup();
//			userGroup.setGid(10001L);
			userGroup.setGid(10L);
		    this.userGroupService.createGroup(userGroup);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testUpdateUserGroup() throws AssembleException {
		UserGroup userGroup = this.userGroupService.queryUserGroup(10001L);
		userGroup = this.userGroupCreater.createFansUserGroup();
		userGroup.setGid(10001L);
		
		this.userGroupService.updateUserGroup(userGroup);
	}
	
	@Test
	public void testDeleteUserGroup() {
		this.userGroupService.deleteUserGroup(10001L);
	}
	
	@Test
	public void testQueryUserGroupByCriteria() throws AssembleException {
//		UserGroup[] userGroups = this.userGroupCreater.createRandomUserGroups(1000);
//		for(UserGroup userGroup : userGroups) {
//			this.userGroupService.createGroup(userGroup);
//		}
		
		Date fromDate = new Date();
		long toTimeMills = fromDate.getTime() + 86400000L * 360;
		Date toDate = new Date(toTimeMills);
		
		Limit limit = Limit.newInstanceForLimit(0, 20);
		PageResult<UserGroup> queriedUserGroups = this.userGroupService.queryUserGroups(Website.weibo, null, UserGroupType.dynamic, Properties.articles.getPropVal() + Properties.attentions.getPropVal(), fromDate, toDate, limit);
		System.out.println("Total Count: " + queriedUserGroups.getTotalCount());
		System.out.println(ToStringBuilder.toString(queriedUserGroups));
	}
	
	public void tearDown() {
		
	}
}
