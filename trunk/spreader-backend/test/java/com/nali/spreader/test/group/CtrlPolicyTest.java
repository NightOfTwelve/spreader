package com.nali.spreader.test.group;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nali.spreader.data.CtrlPolicy;
import com.nali.spreader.group.service.ICtrlPolicyService;
import com.nali.spreader.util.time.TimeUnit;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:application-context-test.xml" })
public class CtrlPolicyTest {
	@Autowired
	private ICtrlPolicyService ctrlPolicyService;

	@Test
	public void testCreateCtrlPolicy(){
		CtrlPolicy ctrlPolicy = new CtrlPolicy();
		ctrlPolicy.setCtrlGid(10L);
		Date now = new Date();
		ctrlPolicy.setLastModifiedTime(now);
		ctrlPolicy.setCreateTime(now);
		ctrlPolicy.setTaskCode("addUserFans");
		ctrlPolicy.setTimeunit(TimeUnit.HOUR.getVal());
		ctrlPolicy.setUnitCount(3);
		ctrlPolicy.setCount(5);
		this.ctrlPolicyService.createCtrlPolicy(ctrlPolicy);
	}
}
