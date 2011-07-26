package com.nali.spreader.util.avg;

public class ItemCount<T> implements Cloneable, Comparable<ItemCount<T>> {
	private int count;
	private T item;
	public ItemCount(int count, T item) {
		super();
		this.count = count;
		this.item = item;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public T getItem() {
		return item;
	}
	public void setItem(T item) {
		this.item = item;
	}
	@SuppressWarnings("unchecked")
	public ItemCount<T> clone() {
		try {
			return (ItemCount<T>) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new Error(e);
		}
	}

	@Override
	public int compareTo(ItemCount<T> o) {
		return this.count - o.count;
	}
}
