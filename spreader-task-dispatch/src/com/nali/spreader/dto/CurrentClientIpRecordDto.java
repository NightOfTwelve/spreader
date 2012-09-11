package com.nali.spreader.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 显示当前客户端IP信息
 * 
 * @author xiefei
 * 
 */
public class CurrentClientIpRecordDto implements Serializable {
	private static final long serialVersionUID = 4086286190852871915L;

	private Long clientId;
	private String ip;
	private Date createTime;
	private Date recordTime;
	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}
}
