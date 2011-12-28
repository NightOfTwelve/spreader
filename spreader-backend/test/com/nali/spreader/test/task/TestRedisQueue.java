package com.nali.spreader.test.task;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nali.spreader.factory.regular.RegularAnalyzer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:application-context-test.xml")
public class TestRedisQueue {
	@Autowired
	private RegularAnalyzer fetchStarUser;
	@Test
	public void doit() {
		fetchStarUser.work();
	}
}
