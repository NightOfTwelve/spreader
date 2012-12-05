package com.nali.spreader.util.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CollectionUtils {

	protected CollectionUtils() {
	}
	
	public static <T> List<T> exclude(Collection<T> a, Set<T> b) {
		if(a == null) {
			return null;
		}
		
		if(com.nali.common.util.CollectionUtils.isEmpty(b)) {
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
			Set<T> rtnSet = new HashSet<T>(com.nali.common.util.CollectionUtils.getMapSize(size));
			if(!com.nali.common.util.CollectionUtils.isEmpty(a)) {
				rtnSet.addAll(a);
			}
			
			if(!com.nali.common.util.CollectionUtils.isEmpty(b)) {
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
			if(!com.nali.common.util.CollectionUtils.isEmpty(a)) {
				rtnList.addAll(a);
			}
			
			if(!com.nali.common.util.CollectionUtils.isEmpty(b)) {
				rtnList.addAll(b);
			}
			return rtnList;
		}
		return Collections.emptyList();
	}
	
	public static <T> boolean notContainsAll(Collection<T> a, Collection<T> b) {
		if(com.nali.common.util.CollectionUtils.isEmpty(b)) {
			return true;
		}
		
		Set<T> aset = null;
		if(a instanceof Set) {
			aset = (Set<T>) a;
		}else{
			aset = com.nali.common.util.CollectionUtils.newHashSet(a.size());
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
}
