package com.nali.spreader.test;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import com.nali.spreader.service.impl.MDaemonEmailRegister;

@RunWith(BlockJUnit4ClassRunner.class)
public class TestMDaemonEmailRegister {
	private String mailLoginUrl = "http://211.149.146.132:1000/login.wdm";
	private String mailRegisterUrl = "http://211.149.146.132:1000/useredit_account.wdm?postXML=1";
	private String mailDeleteUrl = "http://211.149.146.132:1000/userlist.wdm?postXML=1";

	@Test
	public void reg() throws IOException {
		MDaemonEmailRegister m = new MDaemonEmailRegister(mailLoginUrl,
				mailRegisterUrl, mailDeleteUrl);
		String names[] = { "t1", "t2", "t3", "t4", "t5" };
		for (String x : names) {
			boolean r = m.register(x, "360ke.net", "123");
			System.out.println(r);
		}
	}

	@Test
	public void del() throws IOException {
		MDaemonEmailRegister m = new MDaemonEmailRegister(mailLoginUrl,
				mailRegisterUrl, mailDeleteUrl);
		String names[] = { "t1", "t2", "t3", "t4", "t5" };
		for (String x : names) {
			m.del(x, "360ke.net");
		}
	}
}
