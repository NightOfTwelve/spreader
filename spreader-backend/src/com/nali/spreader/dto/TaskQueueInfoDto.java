package com.nali.spreader.dto;

public class TaskQueueInfoDto {

	// 普通任务队列长度
	private Long normalSize;
	// 注册任务
	private Long registerSize;
	// 实时任务
	private Long instantSize;

	public Long getNormalSize() {
		return normalSize;
	}

	public void setNormalSize(Long normalSize) {
		this.normalSize = normalSize;
	}

	public Long getRegisterSize() {
		return registerSize;
	}

	public void setRegisterSize(Long registerSize) {
		this.registerSize = registerSize;
	}

	public Long getInstantSize() {
		return instantSize;
	}

	public void setInstantSize(Long instantSize) {
		this.instantSize = instantSize;
	}
}
