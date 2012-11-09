package com.nali.spreader.pool;

public class ChannelConfig {
	public final int uidSize;
	public final int allFetchSize;
	public final Integer taskType;
	public ChannelConfig(Integer taskType, int uidSize, int allFetchSize) {
		super();
		this.taskType = taskType;
		this.uidSize = uidSize;
		this.allFetchSize = allFetchSize;
	}
}
