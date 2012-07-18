package com.nali.spreader.dto;

import java.io.Serializable;

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
	// 来源作者的websiteUid
	private Long fromWebsiteUid;
	// 回复内容，微博，根据消息类型转换
	private Object refData;

	public Long getFromWebsiteUid() {
		return fromWebsiteUid;
	}

	public void setFromWebsiteUid(Long fromWebsiteUid) {
		this.fromWebsiteUid = fromWebsiteUid;
	}

	public Object getRefData() {
		return refData;
	}

	public void setRefData(Object refData) {
		this.refData = refData;
	}

	public Integer getNoticeType() {
		return noticeType;
	}

	public void setNoticeType(Integer noticeType) {
		this.noticeType = noticeType;
	}

}
