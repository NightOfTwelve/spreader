package com.nali.spreader.dto;

import java.io.Serializable;

/**
 * 客户端任务状态汇总的DTO
 * 
 * @author xiefei
 * 
 */
public class ClientTaskExcutionSummaryDto implements Serializable {

	private static final long serialVersionUID = 4196975401664139663L;

	// 客户端ID
	private Long cid;
	// 任务编号
	private String taskCode;
	// 成功
	private Integer success;
	// 失败
	private Integer fail;

	public Long getCid() {
		return cid;
	}

	public void setCid(Long cid) {
		this.cid = cid;
	}

	public String getTaskCode() {
		return taskCode;
	}

	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}

	public Integer getSuccess() {
		return success;
	}

	public void setSuccess(Integer success) {
		this.success = success;
	}

	public Integer getFail() {
		return fail;
	}

	public void setFail(Integer fail) {
		this.fail = fail;
	}
}
