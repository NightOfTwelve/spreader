package com.nali.spreader.pool;

public class ChannelConfig {
	public final int uidSize;
	public final int allFetchSize;
	public ChannelConfig(int uidSize, int allFetchSize) {
		super();
		this.uidSize = uidSize;
		this.allFetchSize = allFetchSize;
	}
}
