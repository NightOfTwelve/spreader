package com.nali.spreader.test;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import com.nali.spreader.service.ClientService;

@RunWith(BlockJUnit4ClassRunner.class)
public class TestClientService {
	private static ClientService cs = new ClientService();
	public static void main(String[] args) {
		TestClientService ts = new TestClientService();
		ts.test();
	}
	
	public  void test() {
		String tk = cs.login("967", "ss", "192.168.3.26");
		System.out.println(tk);
		Long ids = cs.check(tk);
		System.out.println(ids);
		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
		scheduler.scheduleWithFixedDelay(new testThread(tk), 30, 30, TimeUnit.SECONDS);
	}
	class testThread implements Runnable{
		String tk;
		public testThread(String tk) {
			this.tk = tk;
		}
		@Override
		public void run() {
			Long id = cs.check(tk);
			System.out.println(id);
			//System.out.println(ClientService.recordMap);
			
		}
		
	}
}
