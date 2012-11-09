package com.nali.spreader.pool;

import java.util.List;

import com.nali.spreader.util.avg.ItemCount;

public class UidActionCount {
	private Long uid;
	private List<ItemCount<Long>> actionCounts;
	public UidActionCount(Long uid, List<ItemCount<Long>> actionCounts) {
		super();
		this.uid = uid;
		this.actionCounts = actionCounts;
	}
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public List<ItemCount<Long>> getActionCounts() {
		return actionCounts;
	}
	public void setActionCounts(List<ItemCount<Long>> actionCounts) {
		this.actionCounts = actionCounts;
	}

}
