package com.nali.spreader.dto;

import java.io.Serializable;

import com.nali.common.model.Limit;

/**
 * 消息查询参数
 * 
 * @author xiefei
 * 
 */
public class NoticeQueryParams implements Serializable {

	private static final long serialVersionUID = -982366256041487653L;
	// ID
	private Long noticeId;
	// 消息类型
	private Integer noticeType;
	// 接受者ID
	private Long toUid;
	// 网站ID
	private Integer websiteId;
	// 是否来自机器人
	private Boolean isFromRobot;
	private Integer start;
	private Integer limit;
	private Limit lit;

	public Boolean getIsFromRobot() {
		return isFromRobot;
	}

	public void setIsFromRobot(Boolean isFromRobot) {
		this.isFromRobot = isFromRobot;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Limit getLit() {
		return lit;
	}

	public void setLit(Limit lit) {
		this.lit = lit;
	}

	public Long getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(Long noticeId) {
		this.noticeId = noticeId;
	}

	public Integer getNoticeType() {
		return noticeType;
	}

	public void setNoticeType(Integer noticeType) {
		this.noticeType = noticeType;
	}

	public Long getToUid() {
		return toUid;
	}

	public void setToUid(Long toUid) {
		this.toUid = toUid;
	}

	public Integer getWebsiteId() {
		return websiteId;
	}

	public void setWebsiteId(Integer websiteId) {
		this.websiteId = websiteId;
	}
}
