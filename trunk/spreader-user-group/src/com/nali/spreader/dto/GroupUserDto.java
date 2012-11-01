package com.nali.spreader.dto;

import java.io.Serializable;

import com.nali.spreader.data.User;

/**
 * 封装分组与用户关系的DTO
 * 
 * @author xiefei
 * 
 */
public class GroupUserDto implements Serializable {

	private static final long serialVersionUID = 6142451501785132399L;

	private boolean manual;
	private Long uid;
	private User user;

	public boolean isManual() {
		return manual;
	}

	public void setManual(boolean manual) {
		this.manual = manual;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
