//package com.nali.spreader.test.group;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import junit.framework.Assert;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import com.nali.spreader.group.exception.AssembleException;
//import com.nali.spreader.group.service.ICtrlUserGroupService;
//import com.nali.spreader.group.service.IUserGroupInfoService;
//import com.nali.spreader.group.service.impl.CtrlUserGroupService;
//import com.nali.spreader.model.UserGroup;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration({ "classpath:application-context-test.xml" })
//public class CtrlUserGroupTest {
//	@Autowired
//	private ICtrlUserGroupService ctrlUserGroupService;
//	
//	@Autowired
//	private IUserGroupInfoService userGroupService;
//	
//	@Before
//	public void setup() {
//		((CtrlUserGroupService)this.ctrlUserGroupService).setBatchSize(20);
//	}
//	
//	@Test
//	public void testSubmit() throws AssembleException {
//		UserGroup userGroup = this.userGroupService.queryUserGroup(10L);
//		boolean result = this.ctrlUserGroupService.submitCtrlUserGroup(userGroup);
//		Assert.assertTrue(result);
//	}
//	
//	@Test
//	public void testWhoCanDo() {
//		List<Long> uidList = new ArrayList<Long>(2);
//		uidList.add(238746L);
//		uidList.add(238949L);
//		List<Long> filterUids =	this.ctrlUserGroupService.whoCanDo("addUserFans", uidList);
//		System.out.println(filterUids);
//	}
//}
