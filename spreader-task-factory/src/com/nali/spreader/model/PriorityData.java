package com.nali.spreader.model;

public class PriorityData<E> {
	private int basePriority;
	private E data;
	public PriorityData(int basePriority, E data) {
		super();
		this.basePriority = basePriority;
		this.data = data;
	}
	public int getBasePriority() {
		return basePriority;
	}
	public void setBasePriority(int basePriority) {
		this.basePriority = basePriority;
	}
	public E getData() {
		return data;
	}
	public void setData(E data) {
		this.data = data;
	}
}
