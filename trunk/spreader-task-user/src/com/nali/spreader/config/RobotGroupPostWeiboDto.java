package com.nali.spreader.config;

import com.nali.spreader.factory.config.desc.PropertyDescription;

/**
 * 机器人分组发微博Dto
 * 
 * @author xiefei
 * 
 */
public class RobotGroupPostWeiboDto extends BaseRobotGroupContent {
	private static final long serialVersionUID = -4258663802804916797L;
	@PropertyDescription("每条微博被发送次数")
	private Range<Integer> fromPostGroup;
	@PropertyDescription("每次内容来源者的人数")
	private Range<Integer> toPostGroup;

	public Range<Integer> getFromPostGroup() {
		return fromPostGroup;
	}

	public void setFromPostGroup(Range<Integer> fromPostGroup) {
		this.fromPostGroup = fromPostGroup;
	}

	public Range<Integer> getToPostGroup() {
		return toPostGroup;
	}

	public void setToPostGroup(Range<Integer> toPostGroup) {
		this.toPostGroup = toPostGroup;
	}
}
