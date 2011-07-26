package com.nali.spreader.test;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import com.nali.spreader.util.avg.Average;
import com.nali.spreader.util.avg.ItemCount;

@RunWith(BlockJUnit4ClassRunner.class)
public class TestAverage {

	@Test
	public void test() {
		int count = 100;
		for (int i = 0; i < count; i++) {
			testOne();
		}
	}

	@Test
	public void testCertain() {
		int batchSize = 20;
		int[] counts = new int[] {
				2, 26, 97, 5, 1, 16, 33, 27, 6, 9,
				};
		testCertain(counts, batchSize);
	}

	private void testCertain(int[] counts, int batchSize) {
		List<ItemCount<Integer>> items = new ArrayList<ItemCount<Integer>>(counts.length);
		for (int i = 0; i < counts.length; i++) {
			items.add(new ItemCount<Integer>(counts[i], i));
		}
		testOne(items, batchSize);
	}
	private void testOne() {
		int batchSize = 20;
		int itemCount = 200;
		List<ItemCount<Integer>> items = new ArrayList<ItemCount<Integer>>(itemCount);
		for (int i = 0; i < itemCount; i++) {
			items.add(new ItemCount<Integer>(Math.abs((int)(Math.random()*100)+1), i));
		}
		testOne(items, batchSize);
	}

	private void testOne(List<ItemCount<Integer>> items, int batchSize) {
		int count = count(items);
		int batch = count/batchSize;
		if(count%batchSize>0) {
			batch+=1;
		}
		int big = count%batch;
		int realBatchSize = count/batch;
		if(big!=0) {
			realBatchSize++;
		}
//		System.out.println("count:" + count + ", batch:" + batch + ", realBatchSize:" + realBatchSize + ", big:" + big);
//		showUnAverage(items);
//		System.out.println("=======");
		Average<Integer> av=Average.startFromBatchSize(items, batchSize);
		while (av.hasNext()) {
			List<ItemCount<Integer>> list = av.next();
			int countItem = count(list);
//			System.out.println(countItem);
			count-=countItem;
			Assert.assertEquals(realBatchSize, countItem);
			big--;
			if(big==0) {
				realBatchSize--;
			}
		}
		Assert.assertEquals(0, count);
	}

	private int count(List<ItemCount<Integer>> list) {
		int c=0;
		for (ItemCount<Integer> averageItem : list) {
			c += averageItem.getCount();
		}
		return c;
	}
	
	@SuppressWarnings("unused")
	private<T> void showUnAverage(List<ItemCount<T>> unAverageList) {
		System.out.print('[');
		for (ItemCount<T> itemCount : unAverageList) {
			System.out.print(itemCount.getCount());
			System.out.print(", ");
		}
		System.out.print(']');
		System.out.println();
	}
}
