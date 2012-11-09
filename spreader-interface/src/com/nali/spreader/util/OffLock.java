package com.nali.spreader.util;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class OffLock {
	private Lock innerLock;
	private volatile boolean off;
	private volatile boolean realOff;
	private AtomicInteger lockCount = new AtomicInteger();
	private Condition zero;
	
	public OffLock() {
		this(new ReentrantLock());
	}
	
	public OffLock(Lock innerLock) {
		super();
		this.innerLock = innerLock;
		this.zero=innerLock.newCondition();
	}

	public boolean lock() {
		if(off) {
			return false;
		}
		lockCount.getAndIncrement();
		innerLock.lock();
		if(realOff) {
			lockCount.getAndDecrement();
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
			if(realOff) {
				innerLock.unlock();
				return false;
			} else {
				lockCount.getAndIncrement();
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
		int currentLock = lockCount.decrementAndGet();
		if(currentLock==0) {
			zero.signal();
		}
		innerLock.unlock();
	}
	
	public void turnOn() {//TODO turnOn turnOff
		if(realOff) {
			innerLock.lock();
			realOff=false;
			off=false;
			innerLock.unlock();
		}
	}

	public void turnOff() throws InterruptedException {
		if(realOff) {
			return;
		}
		off=true;
		innerLock.lock();
		try {
			while(lockCount.get()>0) {
				zero.await();
			}
			off=true;
			realOff=true;
		} finally {
			innerLock.unlock();
		}
	}
	
}
