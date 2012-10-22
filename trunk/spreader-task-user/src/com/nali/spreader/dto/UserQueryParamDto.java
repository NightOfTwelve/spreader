package com.nali.spreader.dto;

import java.io.Serializable;
import java.util.List;

public class UserQueryParamDto implements Serializable {

	private static final long serialVersionUID = -2928700514296322927L;

	private List<Long> uids;

	private Long attentionLimit;

	public List<Long> getUids() {
		return uids;
	}

	public void setUids(List<Long> uids) {
		this.uids = uids;
	}

	public Long getAttentionLimit() {
		return attentionLimit;
	}

	public void setAttentionLimit(Long attentionLimit) {
		this.attentionLimit = attentionLimit;
	}
}
