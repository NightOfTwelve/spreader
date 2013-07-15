package com.nali.spreader.front.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nali.spreader.model.ClientConfig;
import com.nali.spreader.service.IClitentConfigService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:application-context.xml")
public class TestService {
	@Autowired
	private IClitentConfigService clientService;

	@Test
	public void test() {
		// 新增重名 null "105测试组"
		// 新增不重名 null "105测试组不重名"
		// 修改配置，不修改名称 4L,"105测试组"
		// 修改配置同时修改名称 4L,"测试分组说明1"
		boolean x1 = clientService.configNameIsValid(null, "105测试组",
				ClientConfig.CONFIG_TYPE_GROUP, null);
		boolean x2 = clientService.configNameIsValid(null, "105测试组不重名",
				ClientConfig.CONFIG_TYPE_GROUP, null);
		boolean x3 = clientService.configNameIsValid(4L, "105测试组",
				ClientConfig.CONFIG_TYPE_GROUP, null);
		boolean x4 = clientService.configNameIsValid(4L, "测试分组说明1",
				ClientConfig.CONFIG_TYPE_GROUP, null);
		System.out.println(x1);
		System.out.println(x2);
		System.out.println(x3);
		System.out.println(x4);
	}

	@Test
	public void testClient() {
		// 新增，同一客户端重名
		boolean x1 = clientService.configNameIsValid(null, "sdfdf",
				ClientConfig.CONFIG_TYPE_CLIENT, 111L);
		// 新增，同一客户端不重名
		boolean x2 = clientService.configNameIsValid(null, "sdfdf不重名",
				ClientConfig.CONFIG_TYPE_CLIENT, 111L);
		// 新增，不同客户端重名
		boolean x5 = clientService.configNameIsValid(null, "sdfdf",
				ClientConfig.CONFIG_TYPE_CLIENT, 98612L);
		// 新增，不同客户端不重名
		boolean x6 = clientService.configNameIsValid(null, "sdfdf不重名",
				ClientConfig.CONFIG_TYPE_CLIENT, 98612L);
		// 修改，重名
		boolean x3 = clientService.configNameIsValid(11L, "sdfdf",
				ClientConfig.CONFIG_TYPE_CLIENT, 111L);
		// 修改，不重名
		boolean x4 = clientService.configNameIsValid(11L, "sdfdf不重名",
				ClientConfig.CONFIG_TYPE_CLIENT, 111L);
		System.out.println(x1 + ",false");
		System.out.println(x2 + ",true");
		System.out.println(x3 + ",true");
		System.out.println(x4 + ",true");
		System.out.println(x5 + ",true");
		System.out.println(x6 + ",true");
	}
}
