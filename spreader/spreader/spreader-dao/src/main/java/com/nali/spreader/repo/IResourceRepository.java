package com.nali.spreader.repo;

import java.util.List;

public interface IResourceRepository<T extends Resource> {
	T lookup(int id);
	
	List<T> getAll();

	void add(int id, T t);

	List<T> loadAll();

	T load(int id);
}
