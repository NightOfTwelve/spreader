package com.nali.spreader.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.spreader.service.IEmailRegisterService;

@Component
public class LimitedEmailRegister {
	private static final Object token = new Object();
	private static Logger logger = Logger.getLogger(LimitedEmailRegister.class);
	@Autowired
	private IEmailRegisterService emailRegister;
	private BlockingQueue<Object> tokenHolder = new LinkedBlockingQueue<Object>(1);
	private Timer timer = new Timer();
	
	{
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					tokenHolder.take();
				} catch (InterruptedException e) {
					logger.error(e, e);
				}
			}
		}, new Date(), DateUtils.MILLIS_PER_SECOND);
	}

	public boolean register(String email, String domain, String pwd) throws IOException {
		waiting();
		return emailRegister.register(email, domain, pwd);
	}

	public void del(String user, String domain) throws IOException {
		waiting();
		emailRegister.del(user, domain);
	}
	
	private void waiting() {
		try {
			tokenHolder.put(token);
		} catch (InterruptedException e) {
			logger.error(e, e);
		}
	}

}
