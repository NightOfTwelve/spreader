package com.nali.spreader.util.random;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BooleanRandomer implements Randomer<Boolean> {
	private WeightRandomer<Boolean> booleans;

	public BooleanRandomer(WeightRandomer<Boolean> booleans) {
		super();
		this.booleans = booleans;
	}

	public BooleanRandomer(int truePercent) {
		if(truePercent<0||truePercent>100) {
			throw new IllegalArgumentException("truePercent<0||truePercent>100:"+truePercent);
		}
		this.booleans = new WeightRandomer<Boolean>();
		booleans.add(true, truePercent);
		booleans.add(false, 100-truePercent);
	}

	@Override
	public Boolean get() {
		return booleans.get();
	}

	@Override
	public List<Boolean> multiGet(int count) {
		if(count==0) {
			return Collections.emptyList();
		}
		if(count>=2) {
			List<Boolean> rlt = Arrays.asList(true, false);
			Collections.shuffle(rlt);
			return rlt;
		}
		return Arrays.asList(get());
	}

	@Override
	public Randomer<Boolean> mirror() {
		return null;
	}

}
