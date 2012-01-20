package com.nali.spreader.group;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nali.spreader.data.UserGroup;
import com.nali.spreader.group.exception.AssembleException;
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
		UserGroup userGroup = this.userGroupCreater.createArticlesUserGroup();
	    this.userGroupService.createGroup(userGroup);
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
	
	public void testQueryUserGroupByCriteria() {
		
	}
	
	public void tearDown() {
		
	}
}
