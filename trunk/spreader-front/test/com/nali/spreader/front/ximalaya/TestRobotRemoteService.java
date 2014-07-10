package com.nali.spreader.front.ximalaya;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nali.spreader.client.ximalaya.XimalayaImportUserActionMethod;
import com.nali.spreader.client.ximalaya.exception.AuthenticationException;
import com.nali.spreader.client.ximalaya.service.IRobotRemoteService;
import com.nali.spreader.client.ximalaya.service.IXimalayInterfaceCheckService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("220-application-context-test.xml")
public class TestRobotRemoteService {
	@Autowired
	private IRobotRemoteService robotRemoteService;
	@Autowired
	private XimalayaImportUserActionMethod mtd;
	@Autowired
	private IXimalayInterfaceCheckService interfaceCheckService;

	@Test
	public void test() throws AuthenticationException, NoSuchAlgorithmException, IOException {
		// Map<String, Object> param = new HashMap<String, Object>();
		// param.put("keyword", "xx");
		// param.put("offset", 10);
		// param.put("limit", 100);
		// param.put("fansGte", 10L);
		// param.put("fansLte", 100L);
		// param.put("vType", 1);
		// param.put("startCreateTime", new Date().getTime());
		// param.put("endCreateTime", new Date().getTime());
		// param.put("startUpdateTime", new Date().getTime());
		// param.put("endUpdateTime", new Date().getTime());
		// System.out.println("开始。。。。");
		// mtd.execute(param, new HashMap<String, Object>(), 1L);
		Date sd1 = DateUtils.addDays(new Date(), -100);
		Date ed1 = new Date();
		byte[] md = interfaceCheckService.getParamsMD5(new Object[] { null, 0, 10, null, null, null, sd1, ed1, null, null });
		List<Map<String, Object>> list = robotRemoteService.queryUser(null, 0, 10, null, null, null, sd1, ed1, null, null, md);
		System.out.println(list.toString());
	}

	@Test
	public void test2() throws AuthenticationException {

	}

}
