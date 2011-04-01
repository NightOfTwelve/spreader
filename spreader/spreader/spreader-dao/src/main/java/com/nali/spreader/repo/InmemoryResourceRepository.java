package com.nali.spreader.repo;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public abstract class InmemoryResourceRepository<T extends Resource> implements
		IResourceRepository<T> {
	private volatile ConcurrentHashMap<Integer, T> map;

	private final Object RESOURCE_LOAD_LOCK = new Object();

	@Override
	public void add(int id, T t) {
		map.put(id, t);
	}

	@Override
	public List<T> getAll() {
		return null;
	}

	@Override
	public abstract T load(int id);

	@Override
	public T lookup(int id) {
		if (map == null) {
			synchronized (RESOURCE_LOAD_LOCK) {
				if (map == null) {
					Collection<T> resources = this.loadAll();
					ConcurrentHashMap<Integer, T> resourceMap = new ConcurrentHashMap<Integer, T>();
					for(T resource : resources) {
						int resId = resource.getId();
						resourceMap.put(resId, resource);	
					}
					this.map = resourceMap;
				}
			}
		}
		if (map.containsKey(id)) {
			return map.get(id);
		} else {
			T t = this.load(id);
			map.putIfAbsent(id, t);
			return t;
		}

	}
}
