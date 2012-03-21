package com.nali.spreader.config;

import com.nali.spreader.factory.config.desc.PropertyDescription;

/**
 * 机器人分组转发的DTO
 * 
 * @author xiefei
 * 
 */
public class RobotGroupForwardDto extends BaseRobotGroupContent {
	private static final long serialVersionUID = 7203768493499378727L;
	@PropertyDescription("转发的用户分组")
	private Range<Integer> fromForwardGroup;
	@PropertyDescription("被转发的用户分组")
	private Range<Integer> toForwardGroup;

	public Range<Integer> getFromForwardGroup() {
		return fromForwardGroup;
	}

	public void setFromForwardGroup(Range<Integer> fromForwardGroup) {
		this.fromForwardGroup = fromForwardGroup;
	}

	public Range<Integer> getToForwardGroup() {
		return toForwardGroup;
	}

	public void setToForwardGroup(Range<Integer> toForwardGroup) {
		this.toForwardGroup = toForwardGroup;
	}
}
