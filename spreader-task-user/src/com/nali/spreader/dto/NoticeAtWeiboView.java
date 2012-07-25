package com.nali.spreader.dto;

import java.io.Serializable;

/**
 * '@'到微博的DTO
 * 
 * @author xiefei
 * 
 */
public class NoticeAtWeiboView implements Serializable {

	private static final long serialVersionUID = 5369563745505713837L;

	private Long noticeId;
	private String fromUser;
	private String toUser;
	private String content;
	private String refContent;
	private Long fromUid;
	private Long toUid;
	private Long contentId;
	private Long refContentId;

	public Long getFromUid() {
		return fromUid;
	}

	public void setFromUid(Long fromUid) {
		this.fromUid = fromUid;
	}

	public Long getToUid() {
		return toUid;
	}

	public void setToUid(Long toUid) {
		this.toUid = toUid;
	}

	public Long getContentId() {
		return contentId;
	}

	public void setContentId(Long contentId) {
		this.contentId = contentId;
	}

	public Long getRefContentId() {
		return refContentId;
	}

	public void setRefContentId(Long refContentId) {
		this.refContentId = refContentId;
	}

	public Long getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(Long noticeId) {
		this.noticeId = noticeId;
	}

	public String getFromUser() {
		return fromUser;
	}

	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}

	public String getToUser() {
		return toUser;
	}

	public void setToUser(String toUser) {
		this.toUser = toUser;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getRefContent() {
		return refContent;
	}

	public void setRefContent(String refContent) {
		this.refContent = refContent;
	}
}
