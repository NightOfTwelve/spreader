package com.nali.spreader.dto;

import java.io.Serializable;

/**
 * 显示评论消息的DTO
 * 
 * @author xiefei
 * 
 */
public class NoticeCommentView implements Serializable {

	private static final long serialVersionUID = 8737288417288830764L;
	private Long replayId;
	private Long fromUid;
	private Long toUid;
	private Long replayContentId;
	// 来源人
	private String fromUser;
	// 接收人
	private String toUser;
	// 回复内容
	private String replayContent;
	// 引用的回复
	private String refReplayContent;
	// 引用的微博
	private String refContent;
	// 直接回复的微博
	private String content;

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

	public Long getReplayContentId() {
		return replayContentId;
	}

	public void setReplayContentId(Long replayContentId) {
		this.replayContentId = replayContentId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public String getReplayContent() {
		return replayContent;
	}

	public void setReplayContent(String replayContent) {
		this.replayContent = replayContent;
	}

	public Long getReplayId() {
		return replayId;
	}

	public void setReplayId(Long replayId) {
		this.replayId = replayId;
	}

	public String getRefReplayContent() {
		return refReplayContent;
	}

	public void setRefReplayContent(String refReplayContent) {
		this.refReplayContent = refReplayContent;
	}

	public String getRefContent() {
		return refContent;
	}

	public void setRefContent(String refContent) {
		this.refContent = refContent;
	}
}
