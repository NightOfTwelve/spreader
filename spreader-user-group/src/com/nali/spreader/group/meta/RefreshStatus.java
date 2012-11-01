package com.nali.spreader.group.meta;

public enum RefreshStatus {
	refreshing(1, "正在刷新中"), exception(2, "执行中出现异常"), success(3, "执行成功"), unknownError(4, "未知错误"), groupNotFound(
			5, "分组未找到");
	private int id;
	private String name;

	private RefreshStatus(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
