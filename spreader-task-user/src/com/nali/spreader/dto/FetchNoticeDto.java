package com.nali.spreader.dto;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

import com.nali.spreader.constants.Website;
import com.nali.spreader.data.Content;
import com.nali.spreader.data.ContentReplay;

/**
 * 爬取消息系统的通信Dto
 * 
 * @author xiefei
 * 
 */
public class FetchNoticeDto implements Serializable {
	private static final long serialVersionUID = -874689172247965420L;
	// 通知类型
	private Integer noticeType;
	// 评论的作者id
	private Long fromWebsiteUid;
	// @到评论引用的微博作者
	private Long atWebsiteUid;
	// 微博的内容ID
	private Long websiteContentId;
	private Long websiteRefId;
	private Integer refCount;
	private Integer replyCount;
	private String entry;
	private String picUrl;
	private String videoUrl;
	private String audioUrl;
	// @数量
	private Integer atCount;
	// 引用的微博的内容
	private String weiboContent;
	// 评论的内容
	private String replayContent;
	// 微博的发布时间
	private Date weiboPubDate;
	// 评论的发布时间
	private Date replayPubDate;

	/**
	 * 转换成Content
	 * 
	 * @param dto
	 * @param updateTime
	 * @param uid
	 * @param websiteUid
	 * @return
	 */
	public Content thisToContent(FetchNoticeDto dto, Date updateTime, Long uid, Long websiteUid) {
		Assert.notNull(dto, "FetchNoticeDto is null");
		Content c = new Content();
		c.setWebsiteId(Website.weibo.getId());
		c.setWebsiteUid(websiteUid);
		c.setEntry(dto.getEntry());
		c.setAtCount(dto.getAtCount());
		c.setAudioUrl(dto.getAudioUrl());
		String text = dto.getWeiboContent();
		c.setContent(text);
		c.setContentLength(StringUtils.isEmpty(text) ? 0 : text.length());
		c.setPicUrl(dto.getPicUrl());
		c.setPubDate(dto.getWeiboPubDate());
		c.setRefCount(dto.getRefCount());
		c.setReplyCount(dto.getReplyCount());
		c.setSyncDate(updateTime);
		c.setType(Content.TYPE_WEIBO);
		c.setUid(uid);
		c.setVideoUrl(dto.getVideoUrl());
		c.setWebsiteContentId(dto.getWebsiteContentId());
		return c;
	}

	/**
	 * 转换成ContentReplay
	 * 
	 * @param dto
	 * @param contentId
	 * @param fromUid
	 * @param toUid
	 * @return
	 */
	public ContentReplay thisToContentReplay(FetchNoticeDto dto, Long contentId, Long fromUid,
			Long toUid) {
		Assert.notNull(dto, "FetchNoticeDto is null");
		ContentReplay cr = new ContentReplay();
		cr.setContent(dto.getReplayContent());
		cr.setContentId(contentId);
		cr.setFromUid(fromUid);
		cr.setPubDate(dto.getReplayPubDate());
		cr.setToUid(toUid);
		cr.setWebsiteId(Website.weibo.getId());
		return cr;
	}

	public Long getAtWebsiteUid() {
		return atWebsiteUid;
	}

	public void setAtWebsiteUid(Long atWebsiteUid) {
		this.atWebsiteUid = atWebsiteUid;
	}

	public Integer getNoticeType() {
		return noticeType;
	}

	public void setNoticeType(Integer noticeType) {
		this.noticeType = noticeType;
	}

	public Long getFromWebsiteUid() {
		return fromWebsiteUid;
	}

	public void setFromWebsiteUid(Long fromWebsiteUid) {
		this.fromWebsiteUid = fromWebsiteUid;
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

	public Integer getRefCount() {
		return refCount;
	}

	public void setRefCount(Integer refCount) {
		this.refCount = refCount;
	}

	public Integer getReplyCount() {
		return replyCount;
	}

	public void setReplyCount(Integer replyCount) {
		this.replyCount = replyCount;
	}

	public String getEntry() {
		return entry;
	}

	public void setEntry(String entry) {
		this.entry = entry;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public String getAudioUrl() {
		return audioUrl;
	}

	public void setAudioUrl(String audioUrl) {
		this.audioUrl = audioUrl;
	}

	public Integer getAtCount() {
		return atCount;
	}

	public void setAtCount(Integer atCount) {
		this.atCount = atCount;
	}

	public String getWeiboContent() {
		return weiboContent;
	}

	public void setWeiboContent(String weiboContent) {
		this.weiboContent = weiboContent;
	}

	public String getReplayContent() {
		return replayContent;
	}

	public void setReplayContent(String replayContent) {
		this.replayContent = replayContent;
	}

	public Date getWeiboPubDate() {
		return weiboPubDate;
	}

	public void setWeiboPubDate(Date weiboPubDate) {
		this.weiboPubDate = weiboPubDate;
	}

	public Date getReplayPubDate() {
		return replayPubDate;
	}

	public void setReplayPubDate(Date replayPubDate) {
		this.replayPubDate = replayPubDate;
	}
}
