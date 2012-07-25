package com.nali.spreader.dto;

import java.io.Serializable;

/**
 * 消息展示
 * 
 * @author xiefei
 * 
 */
public class NoticeView implements Serializable {

	private static final long serialVersionUID = -7722187398423362870L;

	private String fromUserName;
	private String toUserName;
	private Long noticeId;
	private Long fromUserId;
	private Long toUserId;
	private Long contentId;
	private Long replayId;
	private Integer noticeType;
	private Long fromWebsiteUid;
	private String replayContent;
	private String quoteContent;
	private String atCommentContent;

	public String getReplayContent() {
		return replayContent;
	}

	public void setReplayContent(String replayContent) {
		this.replayContent = replayContent;
	}

	public String getQuoteContent() {
		return quoteContent;
	}

	public void setQuoteContent(String quoteContent) {
		this.quoteContent = quoteContent;
	}

	public String getAtCommentContent() {
		return atCommentContent;
	}

	public void setAtCommentContent(String atCommentContent) {
		this.atCommentContent = atCommentContent;
	}

	public Long getFromWebsiteUid() {
		return fromWebsiteUid;
	}

	public void setFromWebsiteUid(Long fromWebsiteUid) {
		this.fromWebsiteUid = fromWebsiteUid;
	}

	public Integer getNoticeType() {
		return noticeType;
	}

	public void setNoticeType(Integer noticeType) {
		this.noticeType = noticeType;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	public Long getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(Long noticeId) {
		this.noticeId = noticeId;
	}

	public Long getFromUserId() {
		return fromUserId;
	}

	public void setFromUserId(Long fromUserId) {
		this.fromUserId = fromUserId;
	}

	public Long getToUserId() {
		return toUserId;
	}

	public void setToUserId(Long toUserId) {
		this.toUserId = toUserId;
	}

	public Long getContentId() {
		return contentId;
	}

	public void setContentId(Long contentId) {
		this.contentId = contentId;
	}

	public Long getReplayId() {
		return replayId;
	}

	public void setReplayId(Long replayId) {
		this.replayId = replayId;
	}
}
