package com.nali.spreader.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class CycleIterator<T> implements Iterator<T> {
	private int markIdx = -1;
	private int idx;
	private List<T> list;

	public CycleIterator(List<T> list, boolean copyList) {
		this.idx = list.size() - 1;
		if (copyList) {
			this.list = new ArrayList<T>(list);
		} else {
			this.list = list;
		}
	}

	/**
	 * 循环迭代一个list,不拷贝list，建议用randomAccess的list
	 */
	public CycleIterator(List<T> list) {
		this(list, false);
	}

	public void mark() {
		markIdx = idx;
	}
	
	public boolean reset() {
		if(isMarked()) {
			idx = markIdx;
			return true;
		}
		return false;
	}

	public boolean isMarked() {
		return markIdx != -1;
	}
	
	public int size() {
		return list.size();
	}

	@Override
	public boolean hasNext() {
		return list.size() > 0;
	}

	@Override
	public T next() {
		if (!hasNext()) {
			throw new NoSuchElementException();
		}
		idx++;
		idx %= list.size();
		return list.get(idx);
	}

	@Override
	public void remove() {
		if (!hasNext()) {
			throw new IllegalStateException();
		}
		list.remove(idx);// 多次调用remove没有检查。。
		if (isMarked()) {
			if (markIdx == idx) {
				markIdx = -1;// 标记的被移除
			} else if (markIdx > idx) {
				markIdx--;
			}
		}
		if (hasNext()) {
			idx = idx == 0 ? (list.size() - 1) : idx - 1;
		} else {
			idx = 0;
		}
	}

}
