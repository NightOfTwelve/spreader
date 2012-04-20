package com.nali.spreader.group;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.nali.spreader.util.random.RandomUtil;

public class RandomListTest {

	/** TODO description here
	 * @param args
	 */
	public static void main(String[] args) {
		Set<Long> ids = createExcludeSet(10000);
		List<Long> allIds = createAllIds(100000);
		Random rand = new Random();
		
		long startTimeMills = System.currentTimeMillis();
		List<Long> leftIds = excludeIds(allIds, ids);
		long endTimeMills = System.currentTimeMillis();
		System.out.println("gavin exclude time: " + (endTimeMills - startTimeMills));
		RandomUtil.randomItems(leftIds, 60000);
	    endTimeMills = System.currentTimeMillis();
		System.out.println("gavin executed time: " + (endTimeMills - startTimeMills));
		
		 startTimeMills = System.currentTimeMillis();
		 RandomUtil.randomItems(allIds, ids, 60000);
		 endTimeMills = System.currentTimeMillis();
		System.out.println("Sam executed time: " + (endTimeMills - startTimeMills));
	}
	
	private static List<Long> excludeIds(List<Long> allIds, Set<Long> excludeSet) {
		List<Long> leftIds = new ArrayList<Long>(allIds.size() - excludeSet.size());
		for(long id : allIds) {
			if(!excludeSet.contains(id)) {
				leftIds.add(id);
			}
		}
		System.out.println("Left Id size: " + leftIds.size());
		return leftIds;
	}
	
	
	private static Set<Long> createExcludeSet(int size) {
		Set<Long> ids = new HashSet<Long>(size);
		for(int i = 0; i < size; i++) {
			if(i / 3 != 0) {
				ids.add(Long.valueOf(i));
			}
		}
		return ids;
	}
	
	private static List<Long> createAllIds(int size) {
		List<Long> allIds = new ArrayList<Long>(size);
		for(int i = 0; i < size; i++) {
			allIds.add(Long.valueOf(i));
		}
		return allIds;
	}
}
