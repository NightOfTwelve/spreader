package com.nali.spreader.dto;

import java.io.Serializable;

public class YybPacketClientIpCount implements Serializable {
	private static final long serialVersionUID = 5009593641232131975L;

	private String clientIp;
	private Integer ipCount;

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public Integer getIpCount() {
		return ipCount;
	}

	public void setIpCount(Integer ipCount) {
		this.ipCount = ipCount;
	}
}
