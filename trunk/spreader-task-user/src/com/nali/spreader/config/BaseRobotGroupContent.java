package com.nali.spreader.config;

import java.io.Serializable;

import com.nali.spreader.factory.config.desc.PropertyDescription;

public class BaseRobotGroupContent implements Serializable {
	private static final long serialVersionUID = -5192869137013985965L;
	// 转发
	public static final String CONFIG_FORWARD = "forward";
	// 回复
	public static final String CONFIG_REPLY = "reply";
	@PropertyDescription("内容时效性(分钟)")
	private Long expiredSeconds;
	@PropertyDescription("内容最小长度")
	private Integer minLength;
	@PropertyDescription("符合条件的内容数量上限")
	private Range<Integer> contentCount;
	@PropertyDescription("当前已转发的次数")
	private Range<Integer> refCount;
	@PropertyDescription("当前已回复的次数")
	private Range<Integer> replyCount;

	public Range<Integer> getReplyCount() {
		return replyCount;
	}

	public void setReplyCount(Range<Integer> replyCount) {
		this.replyCount = replyCount;
	}

	public Range<Integer> getRefCount() {
		return refCount;
	}

	public void setRefCount(Range<Integer> refCount) {
		this.refCount = refCount;
	}

	public Long getExpiredSeconds() {
		return expiredSeconds;
	}

	public void setExpiredSeconds(Long expiredSeconds) {
		this.expiredSeconds = expiredSeconds;
	}

	public Integer getMinLength() {
		return minLength;
	}

	public void setMinLength(Integer minLength) {
		this.minLength = minLength;
	}

	public Range<Integer> getContentCount() {
		return contentCount;
	}

	public void setContentCount(Range<Integer> contentCount) {
		this.contentCount = contentCount;
	}
}
