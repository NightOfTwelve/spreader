package com.nali.spreader.service.impl;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;

import com.nali.spreader.util.KeyValuePair;

public class EmailJob {
	private static Logger logger = Logger.getLogger(EmailJob.class);
	private static Method registerMethod;
	private static Method delMethod;
	private EmailRegister emailRegister = new EmailRegister();
	private BlockingQueue<KeyValuePair<Method, Object[]>> methodInvokers = new LinkedBlockingQueue<KeyValuePair<Method,Object[]>>();
	private Timer timer = new Timer();
	
	static {
		try {
			registerMethod = EmailRegister.class.getMethod("register", String.class, String.class, String.class);
			delMethod = EmailRegister.class.getMethod("del", String.class, String.class);
		} catch (NoSuchMethodException e) {
			throw new Error(e);
		}
	}
	{
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					KeyValuePair<Method, Object[]> methodInvoker = methodInvokers.take();
					Method method = methodInvoker.getKey();
					Object[] args = methodInvoker.getValue();
					method.invoke(emailRegister, args);
				} catch (Exception e) {
					logger.error(e, e);
				}
			}
		}, new Date(), DateUtils.MILLIS_PER_SECOND);
	}

	public void register(String email, String domain, String pwd) {
		methodInvokers.add(new KeyValuePair<Method, Object[]>(registerMethod, new Object[] {email, domain, pwd}));
	}
	
	public void del(String user, String domain) {
		methodInvokers.add(new KeyValuePair<Method, Object[]>(delMethod, new Object[] {user, domain}));
	}

}
