package com.nali.spreader.pool;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class UsedUids {
	private Set<Long> uids = new LinkedHashSet<Long>();
	private List<Long> reserveBacks = new LinkedList<Long>();
	
	public void addReserveBack(Long uid) {
		boolean add = uids.add(uid);
		if(add) {
			reserveBacks.add(uid);
		}
	}
	public void addUsed(Long uid) {
		uids.add(uid);
	}
	public Set<Long> getUids() {
		return uids;
	}
	public void setUids(Set<Long> uids) {
		this.uids = uids;
	}
	public List<Long> popReserveBacks() {
		List<Long> old = reserveBacks;
		reserveBacks = new LinkedList<Long>();
		return old;
	}
	public void setReserveBacks(List<Long> reserveBacks) {
		this.reserveBacks = reserveBacks;
	}
	public List<Long> getReserveBacks() {
		return reserveBacks;
	}

}
