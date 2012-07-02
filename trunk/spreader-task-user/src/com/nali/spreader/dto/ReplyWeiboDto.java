package com.nali.spreader.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 封装转发微博策略参数
 * 
 * @author xiefei
 * 
 */
public class ReplyWeiboDto implements Serializable {

	private static final long serialVersionUID = 1619158742352492553L;
	// 机器人ID
	private Long uid;
	// 内容ID
	private Long contentId;
	// 微博内容
	private String text;
	// 时间间隔
	private Date startTime;
	// 是否转发
	private Boolean needForward;

	public Boolean getNeedForward() {
		return needForward;
	}

	public void setNeedForward(Boolean needForward) {
		this.needForward = needForward;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public Long getContentId() {
		return contentId;
	}

	public void setContentId(Long contentId) {
		this.contentId = contentId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
}
