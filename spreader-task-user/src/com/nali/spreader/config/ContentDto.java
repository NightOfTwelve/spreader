package com.nali.spreader.config;

import java.io.Serializable;
import java.util.Date;

public class ContentDto implements Serializable {
    private static final long serialVersionUID = 4201401029398919760L;
	private Integer type;
    private Integer websiteId;
    private Long websiteContentId;
    private Long websiteRefId;
    private Long websiteUid;
    private Long uid;
    private Range<Date> pubDate;
    private Range<Integer> refCount;
    private Range<Integer> replyCount;
    private Range<Integer> contentLength;
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
