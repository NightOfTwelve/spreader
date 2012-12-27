package com.nali.spreader.test;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.nali.spreader.service.VPNService;
import com.nali.spreader.util.test.PojoClassRunner;

@RunWith(PojoClassRunner.class)
public class TestVPNService {
	@Test
	public void test() {
		VPNService s = new VPNService();
		for (int i = 0; i < 617; i++) {
			s.getVpn();
			System.out.println(i);
		}
	}
}
