package com.nali.spreader.factory.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nali.spreader.factory.TaskProduceLine;
import com.nali.spreader.factory.passive.AutowireProductLine;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:application-context.xml")
public class TestAutowired {
	@Qualifier("registerRobotUserEmail")
	@AutowireProductLine
	private TaskProduceLine<Object> registerWeiboAccount;
	
	@Test
	public void test() {
		System.out.println(registerWeiboAccount);
	}
}
