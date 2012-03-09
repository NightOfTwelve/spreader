package com.nali.spreader.test.group;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nali.common.model.Limit;
import com.nali.common.pagination.PageResult;
import com.nali.lang.ToStringBuilder;
import com.nali.spreader.group.service.IUserGroupService;
import com.nali.spreader.model.GrouppedUser;
import com.nali.spreader.test.util.RandomUtils;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:application-context-test.xml"})
public class GrouppedUserTest {
	
	@Autowired
	private IUserGroupService userGroupService;
	
	@Test
	public void testAddManualUsers() {
//		this.userGroupService.addManualUsers(10001L, 1, 2, 3, 4, 5,6, 7,8, 9 ,10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21,22);
		Limit limit = Limit.newInstanceForLimit(0, 15);
		PageResult<GrouppedUser> grouppedUsers = this.userGroupService.queryManualUsers(10001L, limit);
		System.out.println("Total count: " + grouppedUsers.getTotalCount());
		System.out.println(ToStringBuilder.toString(grouppedUsers.getList()));
	}
	
	@Test
	public void testAddExcludeUsers() {
		this.userGroupService.excludeUsers(10001L, 23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48);
		Limit limit = Limit.newInstanceForLimit(0, 15);
		PageResult<GrouppedUser> grouppedUsers =  this.userGroupService.queryExcludeUsers(10001L, limit);
		System.out.println("Total count: " + grouppedUsers.getTotalCount());
		System.out.println(ToStringBuilder.toString(grouppedUsers.getList()));
	}
	
	@Test
	public void testDeleteManualUsers() {
		this.userGroupService.removeManualUsers(10001L, 2, 3);
		Limit limit = Limit.newInstanceForLimit(0, 15);
		PageResult<GrouppedUser> grouppedUsers = this.userGroupService.queryManualUsers(10001L, limit);
		System.out.println("Total count: " + grouppedUsers.getTotalCount());
		System.out.println(ToStringBuilder.toString(grouppedUsers.getList()));
	}
	
	@Test
	public void testRemoveExcludeUsers() {
		this.userGroupService.rollbackExcludeUsers(10001L, 29,30,31);
		Limit limit = Limit.newInstanceForLimit(0, 15);
		PageResult<GrouppedUser> grouppedUsers =  this.userGroupService.queryExcludeUsers(10001L, limit);
		System.out.println("Total count: " + grouppedUsers.getTotalCount());
		System.out.println(ToStringBuilder.toString(grouppedUsers.getList()));
	}
	
	@Test
	public void testQueryManualUsers() {
		Limit limit = Limit.newInstanceForLimit(15, 30);
		PageResult<GrouppedUser> grouppedUsers = this.userGroupService.queryManualUsers(10001L, limit);
		System.out.println("Total count: " + grouppedUsers.getTotalCount());
		System.out.println(ToStringBuilder.toString(grouppedUsers.getList()));
	}
	
	@Test
	public void testQueryExcludeUsers() {
		Limit limit = Limit.newInstanceForLimit(0, 15);
		PageResult<GrouppedUser> grouppedUsers =  this.userGroupService.queryExcludeUsers(10001L, limit);
		System.out.println("Total count: " + grouppedUsers.getTotalCount());
		System.out.println(ToStringBuilder.toString(grouppedUsers.getList()));
	}
	
	@Test
	public void testQueryGrouppedUsers() {
		Limit limit = Limit.newInstanceForLimit(15,30);
		PageResult<GrouppedUser> grouppedUsers=  this.userGroupService.queryGrouppedUsers(158L, limit);
		System.out.println("Total count: " + grouppedUsers.getTotalCount());
		System.out.println(ToStringBuilder.toString(grouppedUsers.getList()));
	}
	
	@Test
	public void testQueryGrouppedUsersWithExclude() {
		Limit limit = Limit.newInstanceForLimit(15,30);
		PageResult<GrouppedUser> grouppedUsers=  this.userGroupService.queryGrouppedUsers(10001L, limit);
		System.out.println("Total count: " + grouppedUsers.getTotalCount());
		
		List<GrouppedUser> userList = grouppedUsers.getList();
		System.out.println(ToStringBuilder.toString(userList));
		
		int size = userList.size();
		if(size > 0) {
			int random = RandomUtils.getRandomInt(size - 1);
			long uid = userList.get(random).getUid();
			System.out.println("Exclude uid: " + uid);
			this.userGroupService.excludeUsers(10001L, uid);
		}
		
	    grouppedUsers=  this.userGroupService.queryGrouppedUsers(10001L, limit);
		System.out.println("Total count: " + grouppedUsers.getTotalCount());
		
		userList = grouppedUsers.getList();
		System.out.println(ToStringBuilder.toString(userList));
	}
}
