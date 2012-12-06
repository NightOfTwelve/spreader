package com.nali.spreader.client.task;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.nali.spreader.client.task.exception.TaskDataException;
import com.nali.spreader.data.User;
import com.nali.spreader.model.LoginConfig;
import com.nali.spreader.remote.IRemoteLoginConfigService;

public class UserContextHolder {
	private ActionMethodHolder actionMethodHolder;
	private IRemoteLoginConfigService remoteLoginConfigService;
	
	private long expireTime = 1000 * 60 * 30;
	private Lock accessLock = new ReentrantLock();
	private LinkedHashMap<Long, ContextUnit> contexts = new LinkedHashMap<Long, ContextUnit>(64, 0.75f, true) {
		private static final long serialVersionUID = -7988157689915244353L;
		@Override
		protected boolean removeEldestEntry(Entry<Long, ContextUnit> eldest) {
			return eldest.getValue().expireMillis < System.currentTimeMillis();
		}};
		
	public UserContextHolder(ActionMethodHolder actionMethodHolder, IRemoteLoginConfigService remoteLoginConfigService) {
		super();
		this.actionMethodHolder = actionMethodHolder;
		this.remoteLoginConfigService = remoteLoginConfigService;
	}

	public Map<String, Object> getUserContext(Long uid) {
		if(uid.equals(User.UID_NOT_LOGIN)) {
			return null;
		}
		if(uid.equals(User.UID_ANYONE)) {
			throw new UnsupportedOperationException("uid anyone unsupported");
		}
		accessLock.lock();
		try {
			ContextUnit contextUnit = contexts.get(uid);
			if(contextUnit==null) {
				contextUnit = new ContextUnit();
				contextUnit.context = login(uid);
				contexts.put(uid, contextUnit);
			}
			
			contextUnit.expireMillis = System.currentTimeMillis() + expireTime;
			return contextUnit.context;
		} finally {
			accessLock.unlock();
		}
	}
	
	private Map<String, Object> login(Long uid) {
		Map<String,Object> context = new HashMap<String, Object>();//假定一个context不会同时给多个人使用
		LoginConfig loginConfig = remoteLoginConfigService.getLoginConfig(uid);
		if(loginConfig==null) {
			throw new TaskDataException("user loginConfig not found:" + uid);
		}
		ActionMethod actionMethod = actionMethodHolder.getActionMethod(loginConfig.getActionId());
		actionMethod.execute(loginConfig.getContentObjects(), context, uid);
		return null;
	}

	private static class ContextUnit {
		long expireMillis;
		Map<String, Object> context;
	}
}
