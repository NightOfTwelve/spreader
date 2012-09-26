package com.nali.spreader.dto;

import java.io.Serializable;
import java.util.Date;

import com.nali.spreader.config.Range;

/**
 * 筛选微博内容条件
 * 
 * @author xiefei
 * 
 */
public class PostWeiboContentDto implements Serializable {

	private static final long serialVersionUID = 6786639746848467960L;
	// 关键字列表
	private Long[] keywords;
	// 用户列表
	private Long[] uids;
	// @数
	private Range<Integer> atCount;
	// 是否图片
	private Boolean isPic;
	// 是否音频
	private Boolean isAudio;
	// 是否视频
	private Boolean isVideo;
	// 内容长度
	private Range<Integer> contentLength;
	// 发布时间
	private Range<Date> pubDate;
	// 转发数
	private Range<Integer> refCount;
	// 回复数
	private Range<Integer> replyCount;
	
	public Long[] getKeywords() {
		return keywords;
	}

	public void setKeywords(Long[] keywords) {
		this.keywords = keywords;
	}

	public Long[] getUids() {
		return uids;
	}

	public void setUids(Long[] uids) {
		this.uids = uids;
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

	public Range<Integer> getAtCount() {
		return atCount;
	}

	public void setAtCount(Range<Integer> atCount) {
		this.atCount = atCount;
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

	public Range<Date> getPubDate() {
		return pubDate;
	}

	public void setPubDate(Range<Date> pubDate) {
		this.pubDate = pubDate;
	}
}
