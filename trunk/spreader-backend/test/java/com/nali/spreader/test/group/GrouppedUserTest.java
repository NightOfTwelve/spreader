package com.nali.spreader.test.group;

import java.util.List;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nali.common.model.Limit;
import com.nali.common.pagination.PageResult;
import com.nali.lang.ToStringBuilder;
import com.nali.spreader.data.User;
import com.nali.spreader.group.service.IUserGroupService;
import com.nali.spreader.group.service.IUserGroupService.UidCollection;
import com.nali.spreader.model.GrouppedUser;
import com.nali.spreader.test.util.RandomUtils;
import com.nali.spreader.util.CollectionUtils;
import com.nali.spreader.util.DataIterator;
import com.nali.spreader.util.MemoryRandomDataIterator;
import com.nali.spreader.util.random.RandomUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:application-context-test.xml" })
public class GrouppedUserTest {

	@Autowired
	private IUserGroupService userGroupService;

	@Test
	public void testAddManualUsers() {
		// this.userGroupService.addManualUsers(10001L, 1, 2, 3, 4, 5,6, 7,8, 9
		// ,10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21,22);
		Limit limit = Limit.newInstanceForLimit(0, 15);
		PageResult<GrouppedUser> grouppedUsers = this.userGroupService
				.queryManualUsers(10001L, limit);
		System.out.println("Total count: " + grouppedUsers.getTotalCount());
		System.out.println(ToStringBuilder.toString(grouppedUsers.getList()));
	}

	@Test
	public void testAddExcludeUsers() {
		this.userGroupService.excludeUsers(10001L, 23, 24, 25, 26, 27, 28, 29,
				30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45,
				46, 47, 48);
		Limit limit = Limit.newInstanceForLimit(0, 15);
		PageResult<GrouppedUser> grouppedUsers = this.userGroupService
				.queryExcludeUsers(10001L, limit);
		System.out.println("Total count: " + grouppedUsers.getTotalCount());
		System.out.println(ToStringBuilder.toString(grouppedUsers.getList()));
	}

	@Test
	public void testDeleteManualUsers() {
		this.userGroupService.removeManualUsers(10001L, 2, 3);
		Limit limit = Limit.newInstanceForLimit(0, 15);
		PageResult<GrouppedUser> grouppedUsers = this.userGroupService
				.queryManualUsers(10001L, limit);
		System.out.println("Total count: " + grouppedUsers.getTotalCount());
		System.out.println(ToStringBuilder.toString(grouppedUsers.getList()));
	}

	@Test
	public void testRemoveExcludeUsers() {
		this.userGroupService.rollbackExcludeUsers(10001L, 29, 30, 31);
		Limit limit = Limit.newInstanceForLimit(0, 15);
		PageResult<GrouppedUser> grouppedUsers = this.userGroupService
				.queryExcludeUsers(10001L, limit);
		System.out.println("Total count: " + grouppedUsers.getTotalCount());
		System.out.println(ToStringBuilder.toString(grouppedUsers.getList()));
	}

	@Test
	public void testQueryManualUsers() {
		Limit limit = Limit.newInstanceForLimit(15, 30);
		PageResult<GrouppedUser> grouppedUsers = this.userGroupService
				.queryManualUsers(10001L, limit);
		System.out.println("Total count: " + grouppedUsers.getTotalCount());
		System.out.println(ToStringBuilder.toString(grouppedUsers.getList()));
	}

	@Test
	public void testQueryExcludeUsers() {
		Limit limit = Limit.newInstanceForLimit(0, 15);
		PageResult<GrouppedUser> grouppedUsers = this.userGroupService
				.queryExcludeUsers(10001L, limit);
		System.out.println("Total count: " + grouppedUsers.getTotalCount());
		System.out.println(ToStringBuilder.toString(grouppedUsers.getList()));
	}

	@Test
	public void testQueryGrouppedUsers() {
		// Limit limit = Limit.newInstanceForLimit(15,30);
		DataIterator<GrouppedUser> iterator = this.userGroupService
				.queryGrouppedUserIterator(161L, 20);
		System.out.println("Total count: " + iterator.getCount());

		while (iterator.hasNext()) {
			List<GrouppedUser> users = iterator.next();
			System.out.println(ToStringBuilder.toString(users));
		}
	}

	@Test
	public void testQueryGrouppedUsersWithExclude() {
		Limit limit = Limit.newInstanceForLimit(15, 30);
		PageResult<GrouppedUser> grouppedUsers = this.userGroupService
				.queryGrouppedUsers(10001L, limit);
		System.out.println("Total count: " + grouppedUsers.getTotalCount());

		List<GrouppedUser> userList = grouppedUsers.getList();
		System.out.println(ToStringBuilder.toString(userList));

		int size = userList.size();
		if (size > 0) {
			int random = RandomUtils.getRandomInt(size - 1);
			long uid = userList.get(random).getUid();
			System.out.println("Exclude uid: " + uid);
			this.userGroupService.excludeUsers(10001L, uid);
		}

		grouppedUsers = this.userGroupService.queryGrouppedUsers(10001L, limit);
		System.out.println("Total count: " + grouppedUsers.getTotalCount());

		userList = grouppedUsers.getList();
		System.out.println(ToStringBuilder.toString(userList));
	}

	@Test
	public void testMemoryRandomGrouppedUsersWithExclude() {
		DataIterator<User> list = this.userGroupService
				.queryMemoryGrouppedUserIterator(191L, 20, 50, null);
		while (list.hasNext()) {
			List<User> userlist = list.next();
			System.out.println("uid list: "
					+ ToStringBuilder.toString(userlist));
		}
		Assert.assertEquals(list.getCount(), 50);
	}

	@Test
	public void testMemoryRandomGrouppedUsersWithBigUpCount() {
		UidCollection collection = this.userGroupService.getAllUids(191L);
		DataIterator<User> list = this.userGroupService
				.queryMemoryGrouppedUserIterator(191L, 20, 50000, null);
		while (list.hasNext()) {
			List<User> userlist = list.next();
			System.out.println("uid list: "
					+ ToStringBuilder.toString(userlist));
		}
		Assert.assertEquals(list.getCount(), collection.getPropertyUids()
				.size() + collection.getManualUids().size());
	}

	@Test
	public void testMemoryRandomGrouppedUsersExcludeUids() {
		UidCollection collection = this.userGroupService.getAllUids(191L);
		List<Long> containUids = collection.getPropertyUids();
		List<Long> excludeList = RandomUtil.randomItems(containUids, 3);
		MemoryRandomDataIterator<Long, User> list = (MemoryRandomDataIterator<Long, User>) this.userGroupService
				.queryMemoryGrouppedUserIterator(191L, 20, 90, excludeList);
		while (list.hasNext()) {
			List<User> userlist = list.next();
			System.out.println("uid list: "
					+ ToStringBuilder.toString(userlist));
		}
		
		Assert.assertTrue(CollectionUtils.notContainsAll(list.getAll(), excludeList));
		Assert.assertThat(list.getAll(), (Matcher)(Matchers.not(Matchers.hasItems(excludeList))));
	}
	
	
}
