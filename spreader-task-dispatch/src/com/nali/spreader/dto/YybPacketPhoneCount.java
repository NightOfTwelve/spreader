package com.nali.spreader.dto;

import java.io.Serializable;

public class YybPacketPhoneCount implements Serializable {
	private static final long serialVersionUID = 709268533535326778L;

	private String phone;
	private Integer phoneCount;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getPhoneCount() {
		return phoneCount;
	}

	public void setPhoneCount(Integer phoneCount) {
		this.phoneCount = phoneCount;
	}
}
