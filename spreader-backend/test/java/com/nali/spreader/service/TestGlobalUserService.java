package com.nali.spreader.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nali.spreader.constants.Website;
import com.nali.spreader.group.service.IUserGroupInfoService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("220-application-context-test.xml")
public class TestGlobalUserService {
	@Autowired
	private IGlobalUserService globalUserService;
	@Autowired
	private IUserGroupInfoService userGroupInfoService;
	@Test
	public void test() {
		List<Long> uids = new ArrayList<Long>();
		uids.add(3136L);
		uids.add(3148L);
		uids.add(3154L);
		uids.add(3165L);
		uids.add(206487L);
		uids.add(206491L);
		List<Long> list = globalUserService.getAttenLimitUids(Website.weibo.getId(),uids, 50L);
		List<Long> list2 = globalUserService.getFansLimitUids(Website.weibo.getId(),uids, 50L);
		System.out.println(list2);
	}
}
