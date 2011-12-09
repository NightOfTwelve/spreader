package com.nali.spreader.config;

import com.nali.spreader.factory.config.desc.PropertyDescription;

public class RobotForwardDto extends BaseUserDto {
	private static final long serialVersionUID = 4096803530255853612L;
	@PropertyDescription("分类")
	private String category;
	@PropertyDescription("需转发次数")
    private Integer count;
	@PropertyDescription("内容实效性（分钟）")
	private Long expiredSeconds;
	@PropertyDescription("内容最小长度")
	private Integer minLength;
	@PropertyDescription("转发数")
	private Range<Integer> refCount;
	@PropertyDescription("回复数")
	private Range<Integer> replyCount;
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
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
	public Range<Integer> getRefCount() {
		return refCount;
	}
	public void setRefCount(Range<Integer> refCount) {
		this.refCount = refCount;
	}
	public Range<Integer> getReplyCount() {
		return replyCount;
	}
	public void setReplyCount(Range<Integer> replyCount) {
		this.replyCount = replyCount;
	}
}
