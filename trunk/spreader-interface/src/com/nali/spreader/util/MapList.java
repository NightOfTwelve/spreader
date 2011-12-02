package com.nali.spreader.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapList<K, I> {
	private Map<K, List<I>> map;

	public MapList() {
		this(new HashMap<K, List<I>>());
	}
	
	public MapList(Map<K, List<I>> map) {
		this.map = map;
	}
	
	public void put(K key, I item) {
		List<I> list = map.get(key);
		if(list==null) {
			list = new ArrayList<I>();
			map.put(key, list);
		}
		list.add(item);
	}

	public Map<K, List<I>> getMap() {
		return map;
	}
}
