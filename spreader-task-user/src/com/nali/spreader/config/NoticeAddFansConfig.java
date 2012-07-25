package com.nali.spreader.config;

import java.io.Serializable;

import com.nali.spreader.factory.config.desc.PropertyDescription;

/**
 * 消息关注用户
 * 
 * @author xiefei
 * 
 */
public class NoticeAddFansConfig implements Serializable {
	private static final long serialVersionUID = -3689786210221426334L;

	@PropertyDescription("机器人ID")
	private Long robotId;
	@PropertyDescription("被关注人ID")
	private Long uid;

	public Long getRobotId() {
		return robotId;
	}

	public void setRobotId(Long robotId) {
		this.robotId = robotId;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}
}
