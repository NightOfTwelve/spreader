package com.nali.spreader.dto;

import java.io.Serializable;
import java.util.Date;

import com.nali.common.model.Limit;

/**
 * 查询统计参数的DTO
 * 
 * @author xiefei
 * 
 */
public class ClientTaskSumQueryParamDto implements Serializable {

	private static final long serialVersionUID = -8260100699296583784L;
	// 客户端ID
	private Long cid;
	// 开始时间
	private Date startTime;
	// 结束时间
	private Date endTime;
	private Limit limit;

	public Limit getLimit() {
		return limit;
	}

	public void setLimit(Limit limit) {
		this.limit = limit;
	}

	public Long getCid() {
		return cid;
	}

	public void setCid(Long cid) {
		this.cid = cid;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
}
