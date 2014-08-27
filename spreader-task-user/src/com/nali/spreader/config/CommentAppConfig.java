package com.nali.spreader.config;

import java.io.Serializable;
import java.util.List;

import com.nali.spreader.factory.config.desc.PropertyDescription;

public class CommentAppConfig implements Serializable {
	private static final long serialVersionUID = -275899649150931661L;
	@PropertyDescription("app的url")
	private String url;
	@PropertyDescription("下载次数")
	private Integer count;
	@PropertyDescription("app大小（MB）")
	private Double millionBite;
	@PropertyDescription("评论的标题")
	private List<String> title;
	@PropertyDescription("评论内容")
	private List<String> comments;
	@PropertyDescription("只评星不做评论的比例")
	private Integer starOnly;
	@PropertyDescription("四星的比例")
	private Integer fourStar;
	@PropertyDescription("每次回复间隔（秒）")
	private Integer secondsDelay;
	@PropertyDescription("是否为付费账号")
	private boolean payingTag = false;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Double getMillionBite() {
		return millionBite;
	}

	public void setMillionBite(Double millionBite) {
		this.millionBite = millionBite;
	}

	public List<String> getTitle() {
		return title;
	}

	public void setTitle(List<String> title) {
		this.title = title;
	}

	public List<String> getComments() {
		return comments;
	}

	public void setComments(List<String> comments) {
		this.comments = comments;
	}

	public Integer getStarOnly() {
		return starOnly;
	}

	public void setStarOnly(Integer starOnly) {
		this.starOnly = starOnly;
	}

	public Integer getFourStar() {
		return fourStar;
	}

	public void setFourStar(Integer fourStar) {
		this.fourStar = fourStar;
	}

	public Integer getSecondsDelay() {
		return secondsDelay;
	}

	public void setSecondsDelay(Integer secondsDelay) {
		this.secondsDelay = secondsDelay;
	}

	public boolean isPayingTag() {
		return payingTag;
	}

	public void setPayingTag(boolean payingTag) {
		this.payingTag = payingTag;
	}
	
}
