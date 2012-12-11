package com.nali.spreader.util.random;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.RandomAccess;
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
			for (int i = 0; i < count;) {
				int c = rand.nextInt(allList.size());
				if(!idxs.contains(c)) {
					i++;
					idxs.add(c);
					rlt.add(allList.get(c));
				}
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
	
	public static<T> T randomItem(List<T> datas) {
		return randomItem(datas, random);
	}
	
	public static<T> T randomItem(List<T> datas, Random random) {
		int idx = random.nextInt(datas.size());
		return datas.get(idx);
	}
	
	/**
	 * 洗牌allList版，注意：必须使用RandomAccess的list
	 */
	public static<E> List<E> randomItemsShuffle(List<E> allList, Set<E> exists, int count) {
		if (allList instanceof RandomAccess == false) {
			throw new IllegalArgumentException("allList must be RandomAccess");
		}
		List<E> rlt = new ArrayList<E>(count);
		int n = allList.size();
		for (int i = n; i > 0; i--) {
			int idx = random.nextInt(i);
			E replaced = swap(allList, idx, i-1);
			if(exists.contains(replaced)) {
				continue;
			} else {
				if(--count<=0) {
					break;
				}
			}
		}
		return rlt;
	}

	private static <E> E swap(List<E> list, int first, int second) {//return the previous element on first index
		if(first==second) {
			return list.get(first);
		}
		return list.set(first, list.set(second, list.get(first)));
	}

	/**
	 * 随机取n个不重复元素
	 */
	public static<E> List<E> randomItems(List<E> allList, Collection<E> exists, int count) {
		return randomItems(allList, new HashSet<E>(exists), count);
	}
	
	/**
	 * 随机取n个不重复元素
	 */
	public static<E> List<E> randomItems(List<E> allList, Set<E> exists, int count) {
		if (count >= allList.size() - exists.size()) {
			return randomItemsCopy(allList, exists, count);
		}
		return randomItemsReadOnly(allList, exists, count);
	}
	
	public static<E> List<E> randomItemsFillExistsSet(List<E> allList, Set<E> exists, int count) {
		ArrayList<E> rlt=new ArrayList<E>(Math.min(count, allList.size()));
		for (int i = 0; i < count;) {
			E e = randomItem(allList, random);
			if(!exists.contains(e)) {
				i++;
				exists.add(e);
				rlt.add(e);
			}
		}
		return rlt;
	}
	
	public static<E> List<E> randomItemsCopy(List<E> allList, Set<E> exists, int count) {
		List<E> cp = new ArrayList<E>(allList.size());
		for (E e : allList) {
			if(!exists.contains(e)) {
				cp.add(e);
			}
		}
		Collections.shuffle(cp, random);
		if(cp.size()<=count) {
			return cp;
		} else {
			return cp.subList(0, count);
		}
	
	}
	
	public static<E> List<E> randomItemsReadOnly(List<E> allList, Set<E> exists, int count) {
		int size = allList.size();
		if(size==0) {
			return Collections.emptyList();
		}
		ArrayList<E> rlt=new ArrayList<E>(count);
		HashSet<Integer> existIdxs=new HashSet<Integer>((int) Math.floor(count/0.75));
		int fetchCount = 0;
		for (int i = count * 5; i > 0 ; i--) {
			int idx = random.nextInt(size);
			if(existIdxs.contains(idx)) {
				continue;
			}
			existIdxs.add(idx);
			E e = allList.get(idx);
			if(!exists.contains(e)) {
				rlt.add(e);
				fetchCount++;
				if(fetchCount==count) {
					break;
				}
			}
			if(existIdxs.size()==size) {
				break;
			}
		}
		return rlt;
	}
	
	public static long nextLong(long n, Random random) {
        if (n <= 0)
            throw new IllegalArgumentException("n must be positive");

        if ((n & -n) == n)  // i.e., n is a power of 2
            return (n-1) & next(random);

        long bits, val;
        do {
            bits = next(random);
            val = bits % n;
        } while (bits - val + (n-1) < 0);
        return val;
	}
	
	private static long next(Random random) {
		return random.nextLong()>>>1;
	}
}
