package com.nali.spreader.config;

import java.io.Serializable;
import java.util.Date;

import com.nali.spreader.factory.config.desc.PropertyDescription;

public class ContentDto implements Serializable {
    private static final long serialVersionUID = 4201401029398919760L;
    @PropertyDescription("类型")
	private Integer type;
	@PropertyDescription("网站id")
    private Integer websiteId;
	@PropertyDescription("网站内容id")
    private Long websiteContentId;
	@PropertyDescription("引用网站内容id")
    private Long websiteRefId;
	@PropertyDescription("网站用户id")
    private Long websiteUid;
	@PropertyDescription("用户id")
    private Long uid;
	@PropertyDescription("发布时间")
    private Range<Date> pubDate;
	@PropertyDescription("转发数")
    private Range<Integer> refCount;
	@PropertyDescription("回复数")
    private Range<Integer> replyCount;
	@PropertyDescription("内容长度数")
    private Range<Integer> contentLength;
	@PropertyDescription("发帖人筛选条件")
    private UserRelatedDto userRelated;
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getWebsiteId() {
		return websiteId;
	}
	public void setWebsiteId(Integer websiteId) {
		this.websiteId = websiteId;
	}
	public Long getWebsiteContentId() {
		return websiteContentId;
	}
	public void setWebsiteContentId(Long websiteContentId) {
		this.websiteContentId = websiteContentId;
	}
	public Long getWebsiteRefId() {
		return websiteRefId;
	}
	public void setWebsiteRefId(Long websiteRefId) {
		this.websiteRefId = websiteRefId;
	}
	public Long getWebsiteUid() {
		return websiteUid;
	}
	public void setWebsiteUid(Long websiteUid) {
		this.websiteUid = websiteUid;
	}
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public Range<Date> getPubDate() {
		return pubDate;
	}
	public void setPubDate(Range<Date> pubDate) {
		this.pubDate = pubDate;
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
	public Range<Integer> getContentLength() {
		return contentLength;
	}
	public void setContentLength(Range<Integer> contentLength) {
		this.contentLength = contentLength;
	}
	public UserRelatedDto getUserRelated() {
		return userRelated;
	}
	public void setUserRelated(UserRelatedDto userRelated) {
		this.userRelated = userRelated;
	}
}
