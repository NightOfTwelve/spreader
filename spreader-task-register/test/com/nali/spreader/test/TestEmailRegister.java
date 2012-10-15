package com.nali.spreader.test;

import java.io.IOException;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import com.nali.spreader.service.impl.EmailRegister;

@RunWith(BlockJUnit4ClassRunner.class)
public class TestEmailRegister {
	private EmailRegister register = new EmailRegister();
	
	@Test
	public void testRegister() throws IOException {
		register.del("test", "mingshi123.net");
		Assert.assertTrue(register.register("test", "mingshi123.net", "123"));
		register.del("test", "mingshi123.net");
	}
	
	@Test
	public void testRegisterFail() throws IOException {
		register.del("test", "mingshi123.net");
		Assert.assertTrue(register.register("test", "mingshi123.net", "123"));
		Assert.assertFalse(register.register("test", "mingshi123.net", "123"));
		register.del("test", "mingshi123.net");
	}

}
