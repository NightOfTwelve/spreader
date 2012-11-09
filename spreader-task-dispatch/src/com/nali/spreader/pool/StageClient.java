package com.nali.spreader.pool;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class StageClient {
	private LinkedList<UidActionCount> reserveUidActionCountList = new LinkedList<UidActionCount>();
	private List<UidActionCount> boundUidActionCountList;
	
	public void addReserveUidActionCount(UidActionCount uidActionCount) {
		reserveUidActionCountList.addFirst(uidActionCount);
	}

	public void setBoundUidActionCountList(List<UidActionCount> boundUidActionCountList) {
		this.boundUidActionCountList = boundUidActionCountList;
	}

	public LinkedList<UidActionCount> getReserveUidActionCountList() {
		return reserveUidActionCountList;
	}

	public List<UidActionCount> getBoundUidActionCountList() {
		if(boundUidActionCountList==null) {
			return Collections.emptyList();
		}
		return boundUidActionCountList;
	}

	@Override
	public String toString() {
		return "reserve:"+reserveUidActionCountList+",bound:"+boundUidActionCountList;
	}
}
