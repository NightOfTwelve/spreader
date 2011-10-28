package com.nali.spreader.test.register;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nali.spreader.workshop.RegularGenerateUser;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:application-context-test.xml")
public class GoRegister {
	@Autowired
	RegularGenerateUser r;
	@Test
	public void getAccont() {
		r.work();
	}
}
