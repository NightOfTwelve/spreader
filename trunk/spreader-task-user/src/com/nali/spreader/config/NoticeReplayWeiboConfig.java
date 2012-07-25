package com.nali.spreader.config;

import java.io.Serializable;

import com.nali.spreader.factory.config.desc.PropertyDescription;

/**
 * 消息互动回复微博
 * 
 * @author xiefei
 * 
 */
public class NoticeReplayWeiboConfig implements Serializable {

	private static final long serialVersionUID = 7302538520581483174L;
	@PropertyDescription("回复人的ID")
	private Long toUid;
	@PropertyDescription("回复的内容")
	private String content;
	@PropertyDescription("是否需要转发")
	private Boolean needForward;
	@PropertyDescription("回复内容的ID")
	private Long replayContentId;

	public Long getToUid() {
		return toUid;
	}

	public void setToUid(Long toUid) {
		this.toUid = toUid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Boolean getNeedForward() {
		return needForward;
	}

	public void setNeedForward(Boolean needForward) {
		this.needForward = needForward;
	}

	public Long getReplayContentId() {
		return replayContentId;
	}

	public void setReplayContentId(Long replayContentId) {
		this.replayContentId = replayContentId;
	}
}
