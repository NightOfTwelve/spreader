package com.nali.spreader.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.RandomAccess;

public class AvgRandomer<T> implements Randomer<T> {
	private List<T> datas;
	private Randomer<Integer> idxRandomer;
	
	public AvgRandomer(Collection<T> datas) {
		super();
		if (datas instanceof List && datas instanceof RandomAccess) {
			this.datas = (List<T>) datas;
		} else {
			this.datas = new ArrayList<T>(datas);
		}
		idxRandomer = new NumberRandomer(0, datas.size());
	}

	public T get() {
		return datas.get(idxRandomer.get());
	}
	
	public static<T> T randomItem(List<T> datas, Random random) {
		int idx = random.nextInt(datas.size());
		return datas.get(idx);
	}

	@Override
	public AvgRandomer<T> mirror() {
		return new AvgRandomer<T>(datas);
	}

	@Override
	public List<T> multiGet(int count) {
		List<Integer> idxes = idxRandomer.multiGet(count);
		List<T> rlt = new ArrayList<T>(idxes.size());
		for (Integer idx : idxes) {
			rlt.add(datas.get(idx));
		}
		return rlt;
	}
}
