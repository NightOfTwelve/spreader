package com.nali.spreader.pool;

public class BoundUid {
	private Long uid;
	private Long clientId;
	private long time;
	public BoundUid(Long uid, Long clientId, long time) {
		super();
		this.uid = uid;
		this.clientId = clientId;
		this.time = time;
	}
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public Long getClientId() {
		return clientId;
	}
	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}

}
