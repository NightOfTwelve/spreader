package com.nali.spreader.util;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LazyOffLock {
	private Lock innerLock;
	private volatile boolean off;
	
	public LazyOffLock() {
		this(new ReentrantLock());
	}
	
	public LazyOffLock(Lock innerLock) {
		super();
		this.innerLock = innerLock;
	}

	public boolean lock() {
		if(off) {
			return false;
		}
		innerLock.lock();
		if(off) {
			unlock();
			return false;
		}
		return true;
	}

	public boolean tryLock() {
		if(off) {
			return false;
		}
		return handleTryLockResult(innerLock.tryLock());
	}

	private boolean handleTryLockResult(boolean getLock) {
		if(getLock) {
			if(off) {
				innerLock.unlock();
				return false;
			}
		}
		return getLock;
	}

	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
		if(off) {
			return false;
		}
		return handleTryLockResult(innerLock.tryLock(time, unit));
	}

	public void unlock() {
		innerLock.unlock();
	}
	
	public void turnOn() {
		off=false;
	}

	public void turnOff() {
		off=true;
		innerLock.lock();
		innerLock.unlock();
	}
	
}
