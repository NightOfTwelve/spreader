package com.nali.spreader.client.android.tencent.config;

import java.util.Random;

import org.apache.commons.lang.math.RandomUtils;

import com.nali.spreader.util.random.NumberRandomer;

public class TencentParamsContext {
	private static final ThreadLocal<TencentParamsContext> ctx = new ThreadLocal<TencentParamsContext>();
	private static final NumberRandomer guidRandom = new NumberRandomer(
			100000000, 999999999);
	private static final Random androidRandom = new Random();
	private long time;

	private int guid = guidRandom.get();
	private String machineUniqueId = genMachineUniqueId();
	private int requestId = RandomUtils.nextInt(99);

	public TencentParamsContext(long time) {
		super();
		this.time = time;
	}

	private static String genMachineUniqueId() {
		long id = 10000 + androidRandom.nextInt(90000);
		for (int i = 0; i < 2; i++) {
			id += androidRandom.nextInt(100000) + id * 100000L;
		}
		return String.valueOf(id);
	}

	public void setMachineUniqueId(String machineUniqueId) {
		this.machineUniqueId = machineUniqueId;
	}

	public static TencentParamsContext getCurrentContext() {
		return ctx.get();
	}

	public static void setTencentParamsContext(TencentParamsContext t) {
		ctx.set(t);
	}

	public static void remove() {
		ctx.remove();
	}

	public int getGuid() {
		return guid;
	}

	public void setGuid(int guid) {
		this.guid = guid;
	}

	public int getRequestId() {
		return requestId++;
	}

	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}

	public long getTime() {
		return time;
	}

	public void timeGo(long used) {
		time += used;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public String getMachineUniqueId() {
		return machineUniqueId;
	}
}
