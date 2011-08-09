package com.nali.spreader.front.test.spring;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nali.spreader.front.TestService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:application-context.xml")
public class TestAutowire {
	@Autowired
	private ApplicationContext context;

	@Test
	public void test() {
		Map<String, TestService> beans = context.getBeansOfType(TestService.class);
		System.out.println(beans.size());
		
		AutowireCapableBeanFactory factory = context.getAutowireCapableBeanFactory();
		TestService b1 = factory.createBean(TestService.class);
		TestService b2 = factory.createBean(TestService.class);
		TestService b3 = factory.createBean(TestService.class);
		System.out.println(b1.forTest());
		System.out.println(b2.forTest());
		System.out.println(b3.forTest());
		TestService service = new TestService();
		System.out.println(service.forTest());
		System.out.println("#########");
		factory.autowireBeanProperties(service, AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE, true);
		System.out.println(service.forTest());
		beans = context.getBeansOfType(TestService.class);
		System.out.println(beans.size());
	}
}
