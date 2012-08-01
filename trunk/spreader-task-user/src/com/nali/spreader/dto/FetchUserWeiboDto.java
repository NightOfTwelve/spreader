package com.nali.spreader.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 爬取用户首页微博参数
 * 
 * @author xiefei
 * 
 */
public class FetchUserWeiboDto implements Serializable {

	private static final long serialVersionUID = 8168226100671668628L;

	private List<String> keywords;

	private Long uid;

	private Date lastFetchTime;

	public Date getLastFetchTime() {
		return lastFetchTime;
	}

	public void setLastFetchTime(Date lastFetchTime) {
		this.lastFetchTime = lastFetchTime;
	}

	public List<String> getKeywords() {
		return keywords;
	}

	public void setKeywords(List<String> keywords) {
		this.keywords = keywords;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}
}
