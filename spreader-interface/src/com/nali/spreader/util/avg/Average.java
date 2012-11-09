package com.nali.spreader.util.avg;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Average<br>&nbsp;
 * 平均分配，零头将进行轮询，不保证按照顺序返回
 * @author sam Created on 2011-7-22
 */
public class Average<T> implements Iterator<List<ItemCount<T>>> {//TOOD batchsize支持小数
	private static final Average<Object> EMPTY = new Average<Object>(null, 0);
	private int leftBatch;
	private SortedItems<CountDetail<T>> sortedItemCounts;
	private int batchUnAverage;
	private int bigs;
	
	@SuppressWarnings("unchecked")
	public static <T> Average<T> empty() {
		return (Average<T>) EMPTY;
	}
	
	public static <T> Average<T> startFromBatchCount(List<ItemCount<T>> items, int batchCount) {
		return new Average<T>(items, batchCount);
	}
	
	public static <T> Average<T> startFromBatchSize(List<ItemCount<T>> items, int batchSize) {
		int totalCount = 0;
		for (ItemCount<T> item : items) {
			totalCount+=item.getCount();
		}
		int batchCount = totalCount==0? 0: (int) Math.ceil(totalCount/(double)batchSize);
		return new Average<T>(items, batchCount);
	}

	private Average(List<ItemCount<T>> items, int batchCount) {
		this(items, batchCount, false);
	}

	private Average(List<ItemCount<T>> items, int batchCount, boolean ignoreRedundant) {
		leftBatch = batchCount;
		if(batchCount==0) {
			return;
		}
		//items
		int cannotAverageCount=0;
		sortedItemCounts = new SortedItems<CountDetail<T>>();
		for (ItemCount<T> averageItem : items) {
			CountDetail<T> itemCount = new CountDetail<T>(averageItem, batchCount);
			sortedItemCounts.add(itemCount, itemCount.cannotAverageCount);
			cannotAverageCount += itemCount.cannotAverageCount;
		}
		//counts
		batchUnAverage = cannotAverageCount / batchCount;
		if(ignoreRedundant) {
			bigs=0;
		} else {
			bigs = cannotAverageCount % batchCount;
			if(bigs>0) {
				batchUnAverage++;
			}
		}
	}

	@Override
	public boolean hasNext() {
		return leftBatch>0;
	}

	@Override
	public List<ItemCount<T>> next() {
		if(!hasNext()) {
			throw new NoSuchElementException();
		}
		leftBatch--;

		List<ItemCount<T>> rlt = new ArrayList<ItemCount<T>>(sortedItemCounts.getSize());
		List<CountDetail<T>> tempItems = new LinkedList<CountDetail<T>>();
		for (int i = 0; i < batchUnAverage; i++) {
			CountDetail<T> floatItem = sortedItemCounts.pop();
			floatItem.cannotAverageCount--;
			rlt.add(new ItemCount<T>(floatItem.solidCount+1, floatItem.item));
			tempItems.add(floatItem);
		}
		for (CountDetail<T> solidItem : sortedItemCounts) {
			rlt.add(new ItemCount<T>(solidItem.solidCount, solidItem.item));//TODO 0的不返回了？
		}
		for (CountDetail<T> tempItem : tempItems) {
			sortedItemCounts.add(tempItem, tempItem.cannotAverageCount);
		}
		if(bigs>0) {
			bigs--;
			if(bigs==0) {
				batchUnAverage--;
			}
		}
		return rlt;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
	
	private static class CountDetail<T> implements Comparable<CountDetail<T>> {
		int solidCount;
		int cannotAverageCount;
		T item;
		public CountDetail(ItemCount<T> ai, int batchCount) {
			item = ai.getItem();
			solidCount = ai.getCount() / batchCount;
			cannotAverageCount = ai.getCount() % batchCount;
		}

		@Override
		public int compareTo(CountDetail<T> o) {
			return o.cannotAverageCount - this.cannotAverageCount;
		}
	}

}

class SortedItems<E> implements Iterable<E> {
	private LinkedList<ItemCount<LinkedList<E>>> dataLink = new LinkedList<ItemCount<LinkedList<E>>>();
	private int size = 0;

	public void add(E e, int score) {
		size++;
		ListIterator<ItemCount<LinkedList<E>>> listIterator = dataLink.listIterator();
		while (listIterator.hasNext()) {
			ItemCount<LinkedList<E>> itemCount = listIterator.next();
			if(itemCount.getCount() == score) {
				itemCount.getItem().addLast(e);
				return;
			} else if (itemCount.getCount() < score) {
				listIterator.previous();
				break;
			}
		}
		LinkedList<E> subList = new LinkedList<E>();
		subList.add(e);
		listIterator.add(new ItemCount<LinkedList<E>>(score, subList));
	}

	public E pop() {
		if(dataLink.size()==0) {
			return null;
		}
		size--;
		ItemCount<LinkedList<E>> first = dataLink.getFirst();
		LinkedList<E> subList = first.getItem();
		E e = subList.pop();
		if(subList.isEmpty()) {
			dataLink.removeFirst();
		}
		return e;
	}

	@Override
	public Iterator<E> iterator() {
		return new Iterator<E>() {
			private Iterator<ItemCount<LinkedList<E>>> iterator=dataLink.iterator();
			private Iterator<E> subIterator;

			@Override
			public boolean hasNext() {
				if(subIterator!=null) {
					return true;
				}
				return iterator.hasNext();
			}

			@Override
			public E next() {
				if(subIterator!=null) {
					E e = subIterator.next();
					if(!subIterator.hasNext()) {
						subIterator = null;
					}
					return e;
				} else {
					ItemCount<LinkedList<E>> nextGroup = iterator.next();
					subIterator = nextGroup.getItem().iterator();
					return next();
				}
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}};
	}

	public int getSize() {
		return size;
	}
}