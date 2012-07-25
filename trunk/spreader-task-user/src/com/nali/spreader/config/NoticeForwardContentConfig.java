package com.nali.spreader.config;

import java.io.Serializable;

import com.nali.spreader.factory.config.desc.PropertyDescription;

/**
 * 消息 转发微博
 * 
 * @author xiefei
 * 
 */
public class NoticeForwardContentConfig implements Serializable {

	private static final long serialVersionUID = -4564048754291971819L;

	@PropertyDescription("转发人的ID")
	private Long toUid;
	@PropertyDescription("转发内容的ID")
	private Long contentId;

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
}
