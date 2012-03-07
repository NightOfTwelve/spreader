package com.nali.spreader.util.random;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class RandomUtil {
	public final static Random random = new Random();
	/**
	 * 从一个集合（父集合）中随机取n个不重复的元素，如果count大等于父集合的size则返回父集合本身
	 * @param <E>
	 * @param allList
	 * @param count
	 * @return
	 */
	public static<E> List<E> randomItems(List<E> allList, int count) {
		return randomItems(allList, count, random);
	}
	/**
	 * 从一个集合（父集合）中随机取n个不重复的元素，如果count大等于父集合的size则返回父集合本身
	 * @param <E>
	 * @param allList
	 * @param count
	 * @return
	 */
	public static<E> List<E> randomItems(List<E> allList, int count, Random rand) {
		if(allList.size()>count * 2) {//一般情况下，随机取count个不重复的元素返回
			HashSet<Integer> idxs=new HashSet<Integer>((int) Math.ceil(count/0.75));
			ArrayList<E> rlt=new ArrayList<E>(count);
			for (int i = 0; i < count; i++) {
				int c = rand.nextInt(allList.size());
				while(idxs.contains(c)) {
					c = rand.nextInt(allList.size());
				}
				idxs.add(c);
				rlt.add(allList.get(c));
			}
			return rlt;
		} else if(allList.size()<=count) {//如果count大等于父集合的size则返回父集合本身
			return Collections.unmodifiableList(allList);
		} else {//如果count和父集合的size比较接近，则复制一份父集合进行随机替换
			ArrayList<E> clone = new ArrayList<E>(allList);
			Collections.shuffle(clone);
			return clone.subList(0, count);
		}
	}
	
	public static<T> T randomItem(List<T> datas, Random random) {
		int idx = random.nextInt(datas.size());
		return datas.get(idx);
	}
	
	/**
	 * 随机取n个不重复元素
	 * E 必须在set里可以排重
	 * @param exists 会被修改
	 */
	public static<E> List<E> randomItems(List<E> allList, Set<E> exists, int count) {
		int existCount=0;
		for (E e : allList) {
			if(exists.contains(e)) {
				existCount++;
			}
		}
		Random rand = random;
		if (count > allList.size() - existCount) {
			count = allList.size() - existCount;
		}
		ArrayList<E> rlt=new ArrayList<E>(count);
		for (int i = 0; i < count;) {
			E e = randomItem(allList, rand);
			if(!exists.contains(e)) {
				i++;
				exists.add(e);
				rlt.add(e);
			}
		}
		return rlt;
	}
}
