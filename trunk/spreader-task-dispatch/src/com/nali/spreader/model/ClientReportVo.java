package com.nali.spreader.model;

import com.nali.common.model.BaseModel;

import java.io.Serializable;
import java.util.Date;

/**
 * 对比ClientReport增加marketName（为解决前台市场排序问题）
 * 
 * @author zfang
 * 
 */
public class ClientReportVo extends BaseModel implements Serializable {
	private static final long serialVersionUID = 1693129684932127226L;

	private Long clientId;
	private Long clientSeq;
	private Date taskDate;
	private String marketName;
	private Integer taskType;
	private Date updateTime;
	private Date createTime;
	private Integer expectCount;
	private Integer actualCount;
	private Long actionId;
	private String appName;
	private Integer successCount;

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public Long getClientSeq() {
		return clientSeq;
	}

	public void setClientSeq(Long clientSeq) {
		this.clientSeq = clientSeq;
	}

	public Date getTaskDate() {
		return taskDate;
	}

	public void setTaskDate(Date taskDate) {
		this.taskDate = taskDate;
	}

	public String getMarketName() {
		return marketName;
	}

	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}

	public Integer getTaskType() {
		return taskType;
	}

	public void setTaskType(Integer taskType) {
		this.taskType = taskType;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getExpectCount() {
		return expectCount;
	}

	public void setExpectCount(Integer expectCount) {
		this.expectCount = expectCount;
	}

	public Integer getActualCount() {
		return actualCount;
	}

	public void setActualCount(Integer actualCount) {
		this.actualCount = actualCount;
	}

	public Long getActionId() {
		return actionId;
	}

	public void setActionId(Long actionId) {
		this.actionId = actionId;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public Integer getSuccessCount() {
		return successCount;
	}

	public void setSuccessCount(Integer successCount) {
		this.successCount = successCount;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ClientReportVo [clientId=");
		builder.append(clientId);
		builder.append(", clientSeq=");
		builder.append(clientSeq);
		builder.append(", taskDate=");
		builder.append(taskDate);
		builder.append(", marketName=");
		builder.append(marketName);
		builder.append(", taskType=");
		builder.append(taskType);
		builder.append(", updateTime=");
		builder.append(updateTime);
		builder.append(", createTime=");
		builder.append(createTime);
		builder.append(", expectCount=");
		builder.append(expectCount);
		builder.append(", actualCount=");
		builder.append(actualCount);
		builder.append(", actionId=");
		builder.append(actionId);
		builder.append(", appName=");
		builder.append(appName);
		builder.append(", successCount=");
		builder.append(successCount);
		builder.append("]");
		return builder.toString();
	}
}
