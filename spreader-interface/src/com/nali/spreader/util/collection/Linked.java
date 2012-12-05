package com.nali.spreader.util.collection;

public class Linked<O> {
	private O o;
	private Linked<O> next;
	public Linked(O o) {
		super();
		this.o = o;
	}
	public O get() {
		return o;
	}
	public Linked<O> next() {
		return next;
	}
	public void setNext(Linked<O> next) {
		this.next = next;
	}
}
