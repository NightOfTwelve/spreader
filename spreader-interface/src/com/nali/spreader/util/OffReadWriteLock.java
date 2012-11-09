package com.nali.spreader.util;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class OffReadWriteLock {
	private volatile boolean off;
	private Lock readLock;
	private Lock writeLock;
	
	public OffReadWriteLock() {
		this(new ReentrantReadWriteLock());
	}
	
	public OffReadWriteLock(ReadWriteLock innerLock) {
		super();
		readLock = innerLock.readLock();
		writeLock = innerLock.writeLock();
	}

	public boolean lockRead() {
		if(off) {
			return false;
		}
		readLock.lock();
		if(off) {
			unlockRead();
			return false;
		}
		return true;
	}
	
	public boolean lockWrite() {
		if(off) {
			return false;
		}
		writeLock.lock();
		if(off) {
			unlockWrite();
			return false;
		}
		return true;
	}

	public void unlockRead() {
		readLock.unlock();
	}
	
	public void unlockWrite() {
		writeLock.unlock();
	}
	
	public void turnOn() {
		off=false;
	}

	public void turnOff() {
		off=true;
		writeLock.lock();
		writeLock.unlock();
	}
	
}
