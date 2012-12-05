package com.nali.spreader.util.collection;

public class LinkedQueue<O> {
	private Linked<O> header = new Linked<O>(null);
	private Linked<O> last = header;

	public O getFirst() {
		Linked<O> next = header.next();
		if(next!=null) {
			return next.get();
		}
		return null;
	}
	
	public O getLast() {
		return last.get();
	}
	
	public O removeFirst() {
		Linked<O> next = header.next();
		if(next!=null) {
			header.setNext(next.next());
			next.setNext(null);
			if(next==last) {
				last = header;
			}
			return next.get();
		}
		return null;
	}

	public void add(O o) {
		Linked<O> linkedObject = new Linked<O>(o);
		last.setNext(linkedObject);
		last = linkedObject;
	}

}
