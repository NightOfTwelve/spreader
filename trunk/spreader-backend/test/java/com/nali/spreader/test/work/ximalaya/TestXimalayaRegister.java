package com.nali.spreader.test.work.ximalaya;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nali.spreader.analyzer.system.FetchWeiboDetailAnalyzer;
import com.nali.spreader.analyzer.ximalaya.WeiboGenerateXimalayaUsers;
import com.nali.spreader.config.GenXimalayaUsersConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("220-application-context-test.xml")
public class TestXimalayaRegister {
//	@Autowired
//	private WeiboGenerateXimalayaUsers weiboGenerateXimalayaUsers;
	@Autowired
	private FetchWeiboDetailAnalyzer fetchWeiboDetailAnalyzer;
//	@Test
//	public void test() {
//		GenXimalayaUsersConfig config = new GenXimalayaUsersConfig();
////		config.setExcludeUserGroup(excludeUserGroup);
//		config.setGenCount(100);
//		weiboGenerateXimalayaUsers.init(config);
//		weiboGenerateXimalayaUsers.work();
//	}
	@Test
	public void test2() {
		fetchWeiboDetailAnalyzer.work();
	}
}
