package com.nali.spreader.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 查询发送微博的dto
 * 
 * @author xiefei
 * 
 */
public class QueryPostWeiboContentDto implements Serializable {

	private static final long serialVersionUID = 2483758527819677031L;
	// 符合内容的contentID
	private Long[] contentIds;
	// 是否图片
	private Boolean isPic;
	// 是否音频
	private Boolean isAudio;
	// 是否视频
	private Boolean isVideo;
	// 用户ID
	private Long[] uids;
	private Integer gteAtCount;
	private Integer lteAtCount; 
	private Integer gteContentLength;
	private Integer lteContentLength;
	private Date gtePubDate;
	private Date ltePubDate;
	private Integer getRefCount;
	private Integer letRefCount;
	private Integer gteReplyCount;
	private Integer lteReplyCount;

	public Long[] getContentIds() {
		return contentIds;
	}

	public void setContentIds(Long[] contentIds) {
		this.contentIds = contentIds;
	}

	public Long[] getUids() {
		return uids;
	}

	public void setUids(Long[] uids) {
		this.uids = uids;
	}

	public Boolean getIsPic() {
		return isPic;
	}

	public void setIsPic(Boolean isPic) {
		this.isPic = isPic;
	}

	public Boolean getIsAudio() {
		return isAudio;
	}

	public void setIsAudio(Boolean isAudio) {
		this.isAudio = isAudio;
	}

	public Boolean getIsVideo() {
		return isVideo;
	}

	public void setIsVideo(Boolean isVideo) {
		this.isVideo = isVideo;
	}

	public Integer getGteAtCount() {
		return gteAtCount;
	}

	public void setGteAtCount(Integer gteAtCount) {
		this.gteAtCount = gteAtCount;
	}

	public Integer getLteAtCount() {
		return lteAtCount;
	}

	public void setLteAtCount(Integer lteAtCount) {
		this.lteAtCount = lteAtCount;
	}

	public Integer getGteContentLength() {
		return gteContentLength;
	}

	public void setGteContentLength(Integer gteContentLength) {
		this.gteContentLength = gteContentLength;
	}

	public Integer getLteContentLength() {
		return lteContentLength;
	}

	public void setLteContentLength(Integer lteContentLength) {
		this.lteContentLength = lteContentLength;
	}

	public Date getGtePubDate() {
		return gtePubDate;
	}

	public void setGtePubDate(Date gtePubDate) {
		this.gtePubDate = gtePubDate;
	}

	public Date getLtePubDate() {
		return ltePubDate;
	}

	public void setLtePubDate(Date ltePubDate) {
		this.ltePubDate = ltePubDate;
	}

	public Integer getGetRefCount() {
		return getRefCount;
	}

	public void setGetRefCount(Integer getRefCount) {
		this.getRefCount = getRefCount;
	}

	public Integer getLetRefCount() {
		return letRefCount;
	}

	public void setLetRefCount(Integer letRefCount) {
		this.letRefCount = letRefCount;
	}

	public Integer getGteReplyCount() {
		return gteReplyCount;
	}

	public void setGteReplyCount(Integer gteReplyCount) {
		this.gteReplyCount = gteReplyCount;
	}

	public Integer getLteReplyCount() {
		return lteReplyCount;
	}

	public void setLteReplyCount(Integer lteReplyCount) {
		this.lteReplyCount = lteReplyCount;
	}
}