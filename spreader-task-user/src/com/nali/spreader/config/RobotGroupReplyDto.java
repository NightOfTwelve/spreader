package com.nali.spreader.config;

import com.nali.spreader.factory.config.desc.PropertyDescription;

/**
 * 机器人组回复DTO
 * 
 * @author xiefei
 * 
 */
public class RobotGroupReplyDto extends BaseRobotGroupContent {
	private static final long serialVersionUID = 8641645130593742559L;
	@PropertyDescription("回复的用户分组")
	private Range<Integer> fromReplyGroup;
	@PropertyDescription("被回复的用户分组")
	private Range<Integer> toReplyGroup;

	public Range<Integer> getFromReplyGroup() {
		return fromReplyGroup;
	}

	public void setFromReplyGroup(Range<Integer> fromReplyGroup) {
		this.fromReplyGroup = fromReplyGroup;
	}

	public Range<Integer> getToReplyGroup() {
		return toReplyGroup;
	}

	public void setToReplyGroup(Range<Integer> toReplyGroup) {
		this.toReplyGroup = toReplyGroup;
	}
}
