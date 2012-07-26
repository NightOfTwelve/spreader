package com.nali.spreader.data;

/**
 * 微博消息通知的类型枚举
 * 
 * @author xiefei
 * 
 */
public enum NoticeTypeEnum {
	// 评论微博
	commentWeibo(1),
	// 评论回复
	commentReplay(2),
	// 新增粉丝
	addFans(3),
	// 私信
	message(4),
	// @到我的微博
	atWeibo(5),
	// @到我的评论
	atComment(6),
	// 群组消息
	groupMessage(7),
	// 相册消息
	albumMessage(8);

	private Integer nocticeType;

	private NoticeTypeEnum(Integer nocticeType) {
		this.nocticeType = nocticeType;
	}

	public Integer getNocticeType() {
		return nocticeType;
	}

	public void setNocticeType(Integer nocticeType) {
		this.nocticeType = nocticeType;
	}
}
