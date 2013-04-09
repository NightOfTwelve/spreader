package com.nali.spreader.util.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class CollectionUtils {

	protected CollectionUtils() {
	}
	
	public static <T> List<T> exclude(Collection<T> a, Set<T> b) {
		if(a == null) {
			return null;
		}
		
		if(isEmpty(b)) {
			return new ArrayList<T>(a);
		}
		
		List<T> left = new ArrayList<T>(a.size() - b.size());
		for(T e : a) {
			if(!b.contains(e)) {
				left.add(e);
			}
		}
		return left;
	}
	
	public static <T> Set<T> intersection(Collection<T> t1, Collection<T> t2) {
		Set<T> set = new HashSet<T>();
		set.addAll(t1);
		set.retainAll(t2);
		return set;
	}
	
	public static <T> Set<T> union(Collection<T> t1, Collection<T> t2) {
		Set<T> set = new HashSet<T>();
		set.addAll(t1);
		set.addAll(t2);
		return set;
	}
	
	public static <T> Set<T> diffset(Collection<T> t1, Collection<T> t2) {
		Set<T> set = new HashSet<T>();
		set.addAll(t1);
		set.removeAll(t2);
		return set;
	}
	
	public static <T> Set<T> mergeAsSet(Collection<T> a, Collection<T> b) {
		int size = (a == null ? 0 : a.size()) + (b == null ? 0 : b.size());
		if(size > 0) {
			Set<T> rtnSet = new HashSet<T>(getMapSize(size));
			if(!isEmpty(a)) {
				rtnSet.addAll(a);
			}
			
			if(!isEmpty(b)) {
				rtnSet.addAll(b);
			}
			return rtnSet;
		}
		return Collections.emptySet();
	}
	
	public static <T> List<T> mergeAsList(Collection<T> a, Collection<T> b) {
		int size = (a == null ? 0 : a.size()) + (b == null ? 0 : b.size());
		if(size > 0) {
			List<T> rtnList = new ArrayList<T>(size);
			if(!isEmpty(a)) {
				rtnList.addAll(a);
			}
			
			if(!isEmpty(b)) {
				rtnList.addAll(b);
			}
			return rtnList;
		}
		return Collections.emptyList();
	}
	
	public static <T> boolean notContainsAll(Collection<T> a, Collection<T> b) {
		if(isEmpty(b)) {
			return true;
		}
		
		Set<T> aset = null;
		if(a instanceof Set) {
			aset = (Set<T>) a;
		}else{
			aset = newHashSet(a.size());
		}
		
		for(T e : b) {
			if(aset.contains(e)) {
				return false;
			}
		}
		return true;
	}
	
	public static <T> List<String> convertElementToStringList(Collection<T> c) {
		List<String> list = new ArrayList<String>(c.size()); 
		for(T t : c) {
			list.add(String.valueOf(t));
		}
		return list;
	}
	
	public static final <T> List<T> mergeSortedList(List<T> listOne, List<T> listTwo, Comparator<T> comparator) {
		int listOneSize = listOne.size();
		int listTwoSize = listTwo.size();
		List<T> rtnList = new ArrayList<T> (listOneSize + listTwoSize);
		int i = 0;
		int j = 0;
		while(i < listOneSize && j < listTwoSize) {
			T a = listOne.get(i);
			T b = listTwo.get(j);
			int comparedResult = comparator.compare(a, b);
			if(comparedResult <= 0) {
				rtnList.add(a);
				i++;
			}else{
				rtnList.add(b);
				j++;
			}
		}
		
		while(i < listOneSize) {
			rtnList.add(listOne.get(i++));
		}
		
		while(j < listTwoSize) {
			rtnList.add(listTwo.get(j++));
		}
		
		return rtnList;
	}
	
	public static boolean isEmpty(Collection<?> collection) {
		return collection == null ? true : collection.isEmpty();
	}
	
	public static int getMapSize(int collectionSize, float factor) {
		return (int)Math.ceil(collectionSize / factor);
	}
	
	public static int getMapSize(int collectionSize) {
		return getMapSize(collectionSize, 0.75f);
	}
	
	@SuppressWarnings("unchecked")
	public static <K,V> HashMap<K, V> newHashMap(Object... kvs) {
		if(kvs.length%2==1) {
			throw new IllegalArgumentException("Invalid kvs.length:" + Arrays.asList(kvs));
		}
		int size=kvs.length/2;
		HashMap<Object, Object> map = newHashMap(size);
		for (int i = 0; i < size; i++) {
			map.put(kvs[i*2], kvs[i*2+1]);
		}
		return (HashMap<K, V>) map;
	}
	
	public static <K,V> HashMap<K, V> newHashMap(int collectionSize) {
		return new HashMap<K, V>(getMapSize(collectionSize));
	}
	
	public static <V> HashSet<V> newHashSet(int collectionSize) {
		return new HashSet<V>(getMapSize(collectionSize));
	}
	
	
	public static <K,V> HashMap<K, V> newLinkedHashMap(int collectionSize) {
		return new LinkedHashMap<K, V>(getMapSize(collectionSize));
	}
	
	public static <E> Set<E> newConcurrentHashSet() {
		return Collections.newSetFromMap(new ConcurrentHashMap<E, Boolean>());
	}
	
	public static <E> Set<E> newConcurrentHashSet(int initialCapacity, float loadFactor, int concurrencyLevel) {
		return Collections.newSetFromMap(new ConcurrentHashMap<E, Boolean>(initialCapacity, loadFactor, concurrencyLevel));
	}
}
