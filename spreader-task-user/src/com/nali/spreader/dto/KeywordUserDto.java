package com.nali.spreader.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 关键字和用户的分组映射
 * 
 * @author xiefei
 * 
 */
public class KeywordUserDto implements Serializable {

	private static final long serialVersionUID = 7628321224248460123L;

	private Long keyword;

	private List<Long> users;

	public Long getKeyword() {
		return keyword;
	}

	public void setKeyword(Long keyword) {
		this.keyword = keyword;
	}

	public List<Long> getUsers() {
		return users;
	}

	public void setUsers(List<Long> users) {
		this.users = users;
	}
}
