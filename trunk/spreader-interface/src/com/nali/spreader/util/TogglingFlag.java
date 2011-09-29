package com.nali.spreader.util;

public class TogglingFlag {
	private boolean[] flags;
	private int flagsIndex;
	
	public TogglingFlag(int truePercent) {
		if(truePercent>100) {
			throw new IllegalArgumentException("truePercent>100");
		}
		final int poolsize=10;
		flags = new boolean[poolsize];
		int level=0;
		for (int i = 0; i < poolsize; i++) {
			level+=truePercent;
			if(level>=100) {
				level-=100;
				flags[i]=true;
			}
		}
	}
	
	public synchronized boolean toggleFlag() {
		if (flagsIndex >= flags.length) {
			flagsIndex -= flags.length;
		}
		return flags[flagsIndex++];
	}
}

