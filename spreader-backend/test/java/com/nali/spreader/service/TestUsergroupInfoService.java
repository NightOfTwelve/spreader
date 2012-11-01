package com.nali.spreader.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nali.common.model.Limit;
import com.nali.common.pagination.PageResult;
import com.nali.spreader.constants.Website;
import com.nali.spreader.dao.ICrudUserDao;
import com.nali.spreader.data.User;
import com.nali.spreader.data.UserExample;
import com.nali.spreader.data.UserExample.Criteria;
import com.nali.spreader.group.exception.AssembleException;
import com.nali.spreader.group.exp.PropertyExpressionDTO;
import com.nali.spreader.group.meta.UserGroupType;
import com.nali.spreader.group.service.IUserGroupInfoService;
import com.nali.spreader.group.service.IUserGroupPropertyService;
import com.nali.spreader.model.UserGroup;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("220-application-context-test.xml")
public class TestUsergroupInfoService {
	private static final Logger logger = Logger.getLogger(TestUsergroupInfoService.class);
	@Autowired
	private IUserGroupInfoService groupInfoService;
	@Autowired
	private IUserGroupPropertyService userGroupPropertyService;
	@Autowired
	private ICrudUserDao crudUserDao;

	public void testQueryMemoryGrouppedUids() {
		List<Long> list = groupInfoService.queryMemoryGrouppedUids(10002L, 20, null);
		logger.debug(list);
	}

	public void testRefreshGroupUsers() {
		String msg = groupInfoService.refreshGroupUsers(10002L);
		System.out.println(msg);
		List<Long> list = groupInfoService.queryMemoryGrouppedUids(10002L, 20, null);
		logger.debug(list);
	}

	public void testCreateUserGroup() {
		PropertyExpressionDTO dto = new PropertyExpressionDTO();
		try {
			UserGroup group = userGroupPropertyService.assembleUserGroup(
					Website.valueOf(Website.weibo.getId()), "测试新用户分组", "测试新用户分组",
					UserGroupType.valueOf(2), dto);
			group.setPropVal(-1);
			long gid = this.userGroupPropertyService.createGroup(group);
			logger.debug("gid=>>>" + gid);
		} catch (AssembleException e) {
			logger.debug(e);
		}
	}

	public void testAddUsers() {
		UserExample ue = new UserExample();
		Criteria c = ue.createCriteria();
		c.andIdGreaterThanOrEqualTo(3105L);
		c.andIdLessThanOrEqualTo(3180L);
		List<User> list = this.crudUserDao.selectByExample(ue);
		long[] arr = new long[list.size()];
		for (int i = 0; i < list.size(); i++) {
			User u = list.get(i);
			long uid = u.getId();
			arr[i] = uid;
		}
		this.groupInfoService.addManualUsers(10002L, arr);
	}

	public void testQueryGrouppedUserIterator() {
		Iterator<Long> iter = getTestDataIterator(10002L);
		while (iter.hasNext()) {
			Long uid = iter.next();
			System.out.println("uid>>" + uid);
		}
	}

	public void testRemoveUsers() {
		Long[] dArr = { 3161L, 3162L, 3163L, 3165L, 3166L, 3167L, 3168L };
		groupInfoService.removeUsers(10002L, dArr);
	}

	@Test
	public void testQueryUisPageData() {
		getTestDataIterator(10002L);
		Limit lit = Limit.newInstanceForLimit(10, 10);
		PageResult<Long> pg = this.groupInfoService.queryGrouppedUsers(10002L, lit);
		List<Long> list = pg.getList();
		for (Long uid : list) {
			System.out.println("uid >>" + uid);
		}
	}

	private Iterator<Long> getTestDataIterator(Long gid) {
		String msg = groupInfoService.refreshGroupUsers(10002L);
		System.err.println(msg);
		Iterator<Long> iter = groupInfoService.queryGrouppedUserIterator(gid);
		return iter;
	}

	private List<Long> getTestDataList(Long gid) {
		Iterator<Long> iter = getTestDataIterator(10002L);
		List<Long> list = new ArrayList<Long>();
		while (iter.hasNext()) {
			Long uid = iter.next();
			list.add(uid);
		}
		return list;
	}
}
