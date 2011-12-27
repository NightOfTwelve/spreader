package com.nali.spreader.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nali.spreader.factory.TaskProduceLine;
import com.nali.spreader.factory.passive.AutowireProductLine;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:application-context-test.xml")
public class TestSavePhotoImg {
	@AutowireProductLine
	private TaskProduceLine<Long> uploadUserAvatar;
	
	@Test
	public void test() {

		uploadUserAvatar.send(867L);
		
	}
}
