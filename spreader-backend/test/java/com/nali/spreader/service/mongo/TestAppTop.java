package com.nali.spreader.service.mongo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nali.spreader.spider.service.IAppleAppsTopService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("220-application-context-test.xml")
public class TestAppTop {
	@Autowired
	private IAppleAppsTopService service;

	@Test
	public void test() {
		service.fetchAppsTopByGenre(6011, 27, 100);
	}

}
