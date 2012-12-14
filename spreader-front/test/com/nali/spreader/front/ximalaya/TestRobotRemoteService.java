package com.nali.spreader.front.ximalaya;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nali.spreader.client.ximalaya.service.IRobotRemoteService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("220-application-context-test.xml")
public class TestRobotRemoteService {
	@Autowired
	private IRobotRemoteService robotRemoteService;

	@Test
	public void test() {
		List<Map<String, Object>> list = robotRemoteService.queryUser(null, 0, 10, null, null,
				null, DateUtils.addDays(new Date(), -100), new Date(), null, null);
		// System.out.println(list.size());
		// System.out.println(robotRemoteService.follow(1000307L, 1001477L));
		// Map<String,Object> map =
		// robotRemoteService.registerRobot("spreader_test_reg_02", null,
		// "123456", 1, null, null);
		// System.out.println(map);
		for (Map<String, Object> m : list) {
			System.out.println(m.toString());
		}
	}

}
