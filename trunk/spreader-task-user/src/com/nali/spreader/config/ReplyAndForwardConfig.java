package com.nali.spreader.config;

import com.nali.spreader.factory.config.desc.PropertyDescription;

/**
 * 转发回复的配置
 * 
 * @author xiefei
 * 
 */
public class ReplyAndForwardConfig extends PostWeiboConfig {

	private static final long serialVersionUID = -2687544919741820367L;
	// 默认回复比例
	public static final int DEFAULT_REPLY_GTE = 10;
	public static final int DEFAULT_REPLY_LTE = 50;
	// 默认转发比例
	public static final int DEFAULT_FORWARD_GTE = 10;
	public static final int DEFAULT_FORWARD_LTE = 50;

	public static final String REPLY = "reply";
	public static final String FORWARD = "forword";
	public static final String REPLYFORWARD = "replyforword";
	public static final String DONOTHING = "donothing";
	@PropertyDescription("回复比例")
	private Range<Integer> replyPer;
	@PropertyDescription("转发比例")
	private Range<Integer> forwardPer;

	public Range<Integer> getReplyPer() {
		return replyPer;
	}

	public void setReplyPer(Range<Integer> replyPer) {
		this.replyPer = replyPer;
	}

	public Range<Integer> getForwardPer() {
		return forwardPer;
	}

	public void setForwardPer(Range<Integer> forwardPer) {
		this.forwardPer = forwardPer;
	}

}
