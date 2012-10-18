package com.nali.spreader.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 用户及对应的微博
 * 
 * @author xiefei
 * 
 */
public class UserContentsDto implements Serializable {

	private static final long serialVersionUID = 4695181160754116499L;

	private Long uid;

	private List<Long> contents;

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public List<Long> getContents() {
		return contents;
	}

	public void setContents(List<Long> contents) {
		this.contents = contents;
	}
}
