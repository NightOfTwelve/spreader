package com.nali.spreader.dto;

import java.io.Serializable;

/**
 * 筛选用户
 * 
 * @author xiefei
 * 
 */
public class FilterUserDto implements Serializable {

	private static final long serialVersionUID = 7095020836290661092L;

	private Integer vType;

	private Long gteFans;

	private Long lteFans;

	private Long gteArticles;

	private Long lteArticles;

	private Integer websiteId;

	public Integer getvType() {
		return vType;
	}

	public void setvType(Integer vType) {
		this.vType = vType;
	}

	public Long getGteFans() {
		return gteFans;
	}

	public void setGteFans(Long gteFans) {
		this.gteFans = gteFans;
	}

	public Long getLteFans() {
		return lteFans;
	}

	public void setLteFans(Long lteFans) {
		this.lteFans = lteFans;
	}

	public Long getGteArticles() {
		return gteArticles;
	}

	public void setGteArticles(Long gteArticles) {
		this.gteArticles = gteArticles;
	}

	public Long getLteArticles() {
		return lteArticles;
	}

	public void setLteArticles(Long lteArticles) {
		this.lteArticles = lteArticles;
	}

	public Integer getWebsiteId() {
		return websiteId;
	}

	public void setWebsiteId(Integer websiteId) {
		this.websiteId = websiteId;
	}
}
